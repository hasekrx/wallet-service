package com.playtomic.tests.wallet;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;

/**
 * This class is just to have two wallets loaded
 * @author alexandre
 *
 */
@Component
public class DataLoader implements ApplicationRunner {

  private WalletRepository walletRepository;

  @Autowired
  public DataLoader(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  public void run(ApplicationArguments args) {

    Wallet wallet1 = Wallet.builder().id(1).balance(new BigDecimal(100)).build();
    Wallet wallet2 = Wallet.builder().id(2).balance(new BigDecimal(10)).build();

    walletRepository.save(wallet1);
    walletRepository.save(wallet2);
  }
}
