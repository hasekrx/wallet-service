package com.playtomic.tests.wallet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.dto.WalletRequestDto;
import com.playtomic.tests.wallet.exceptions.StripeServiceException;
import com.playtomic.tests.wallet.exceptions.WalletException;
import com.playtomic.tests.wallet.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  private Logger log = LoggerFactory.getLogger(WalletController.class);

  @Autowired
  private WalletService walletService;

  @GetMapping("/{id}/find")
  public ResponseEntity<WalletDTO> findWalletById(@PathVariable Integer id) throws WalletException {

    return ResponseEntity.ok(walletService.findWalletById(id));
  }

  @PostMapping("/{id}/deposit")
  public ResponseEntity<WalletDTO> deposit(@PathVariable Integer id,
      @RequestBody WalletRequestDto request) throws WalletException, StripeServiceException {

    return ResponseEntity
        .ok(walletService.deposit(id, request.getCreditCardNumber(), request.getAmount()));
  }

  @PostMapping("/{id}/subtract")
  public ResponseEntity<WalletDTO> subtract(@PathVariable Integer id,
      @RequestBody WalletRequestDto request) throws WalletException, StripeServiceException {

    return ResponseEntity.ok(walletService.subtract(id, request.getAmount()));
  }
}
