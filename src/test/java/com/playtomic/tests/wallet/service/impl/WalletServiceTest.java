package com.playtomic.tests.wallet.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.exceptions.StripeServiceException;
import com.playtomic.tests.wallet.exceptions.WalletException;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletService;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

  @Mock
  private WalletRepository walletRepository;

  @Mock
  private StripeService stripService;

  private Wallet testWallet;

  private WalletService walletService;

  @BeforeEach
  public void setUp() {
    testWallet = Wallet.builder().id(1).balance(new BigDecimal(100)).build();
    walletService = new WalletService(walletRepository, stripService);
  }

  @Test
  public void findWalletById() throws WalletException {
    when(walletRepository.findById(1)).thenReturn(Optional.of(testWallet));

    WalletDTO walletDto = walletService.findWalletById(1);

    assertEquals(1, walletDto.getId());
    assertEquals(new BigDecimal(100.00), walletDto.getBalance());

  }

  @Test
  public void WalletException_WhenIdDoesNotExist() throws WalletException {

    WalletException exception = Assertions.assertThrows(WalletException.class, () -> {
      when(walletRepository.findById(2)).thenReturn(Optional.empty());
      walletService.findWalletById(2);
    });

    String errorMessage = "There is not wallet found for id: 2";
    String actualMessage = exception.getMessage();
    assertEquals(errorMessage, actualMessage);

  }

  @Test
  public void depositTest() throws WalletException, StripeServiceException {

    when(walletRepository.findById(1)).thenReturn(Optional.of(testWallet));
    doNothing().when(stripService).charge(Mockito.anyString(), Mockito.any());
    when(walletRepository.save(testWallet)).thenReturn(testWallet);

    WalletDTO deposit = walletService.deposit(1, "123", new BigDecimal(11));

    assertEquals(new BigDecimal(111), deposit.getBalance());

  }


  @Test
  public void subtractTest() throws WalletException, StripeServiceException {

    when(walletRepository.findById(1)).thenReturn(Optional.of(testWallet));
    when(walletRepository.save(testWallet)).thenReturn(testWallet);

    WalletDTO deposit = walletService.subtract(1, new BigDecimal(10));

    assertEquals(new BigDecimal(90), deposit.getBalance());

  }

}
