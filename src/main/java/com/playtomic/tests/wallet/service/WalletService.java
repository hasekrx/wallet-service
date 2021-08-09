package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.exceptions.StripeServiceException;
import com.playtomic.tests.wallet.exceptions.WalletException;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.util.MapperUtil;

@Service
public class WalletService {

  private WalletRepository walletRepository;
  private StripeService stripService;

  @Autowired
  public WalletService(WalletRepository walletRepository, StripeService stripService) {
    this.walletRepository = walletRepository;
    this.stripService = stripService;
  }

  public synchronized WalletDTO findWalletById(Integer id) throws WalletException {

    Wallet wallet = findWallet(id);
    return MapperUtil.map(wallet, WalletDTO.class);
  }

  public synchronized WalletDTO deposit(Integer id, String creditCardNumber, BigDecimal amount)
      throws WalletException, StripeServiceException {

    Wallet wallet = findWallet(id);
    stripService.charge(creditCardNumber, amount);
    wallet.deposit(amount);

    return MapperUtil.map(walletRepository.save(wallet), WalletDTO.class);
  }

  public synchronized WalletDTO subtract(Integer id, BigDecimal amount) throws WalletException {

    Wallet wallet = findWallet(id);
    wallet.subtract(amount);

    return MapperUtil.map(walletRepository.save(wallet), WalletDTO.class);
  }

  private Wallet findWallet(Integer id) throws WalletException {
    return walletRepository.findById(id)
        .orElseThrow(() -> new WalletException("There is not wallet found for id: " + id));
  }

}
