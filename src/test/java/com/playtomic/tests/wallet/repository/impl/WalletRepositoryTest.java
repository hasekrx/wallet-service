package com.playtomic.tests.wallet.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.exceptions.WalletException;
import com.playtomic.tests.wallet.repository.WalletRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletRepositoryTest {

  @Autowired
  private WalletRepository walletRepository;

  @BeforeEach
  public void setUp() {
    Wallet wallet1 = Wallet.builder().id(1).balance(new BigDecimal(100)).build();
    Wallet wallet2 = Wallet.builder().id(2).balance(new BigDecimal(10)).build();

    walletRepository.save(wallet1);
    walletRepository.save(wallet2);
  }

  @Test
  public void findWalletByIdTest() {
    Wallet wallet = walletRepository.findById(1).get();

    assertEquals(1, wallet.getId());
    assertEquals(new BigDecimal(100).setScale(2), wallet.getBalance());
  }

  @Test
  public void IsPresentFalse_WhenEmptyOptional() {
    Optional<Wallet> walletOptional = walletRepository.findById(3);

    assertEquals(Boolean.FALSE, walletOptional.isPresent());
  }

  @Test
  public void depositTest() throws WalletException {
    Wallet wallet = walletRepository.findById(1).get();
    wallet.deposit(new BigDecimal(100));

    Wallet save = walletRepository.save(wallet);
    assertEquals(new BigDecimal(200).setScale(2), save.getBalance());
  }

  @Test
  public void depositTest_WhenNegativeValue() throws WalletException {

    WalletException exception = Assertions.assertThrows(WalletException.class, () -> {
      Wallet wallet = walletRepository.findById(1).get();
      wallet.deposit(new BigDecimal(-100));
    });

    String errorMessage = "The amount have to be greather than 0";
    String actualMessage = exception.getMessage();
    assertEquals(errorMessage, actualMessage);

  }

  @Test
  public void subtractTest() throws WalletException {
    Wallet wallet = walletRepository.findById(1).get();
    wallet.subtract(new BigDecimal(100));

    Wallet save = walletRepository.save(wallet);
    assertEquals(new BigDecimal(0).setScale(2), save.getBalance());
  }

  @Test
  public void subtractTest_WhenNegativeValue() throws WalletException {

    WalletException exception = Assertions.assertThrows(WalletException.class, () -> {
      Wallet wallet = walletRepository.findById(1).get();
      wallet.subtract(new BigDecimal(-100));
    });

    String errorMessage = "The amount have to be greather than 0";
    String actualMessage = exception.getMessage();
    assertEquals(errorMessage, actualMessage);

  }

  @Test
  public void subtractTest_WhenAmountIsHigherThanBalance() throws WalletException {

    WalletException exception = Assertions.assertThrows(WalletException.class, () -> {
      Wallet wallet = walletRepository.findById(1).get();
      wallet.subtract(new BigDecimal(1500));
    });


    String errorMessage = "the balance is lower than the amount";
    String actualMessage = exception.getMessage();
    assertEquals(errorMessage, actualMessage);

  }

}
