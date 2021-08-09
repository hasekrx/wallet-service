package com.playtomic.tests.wallet.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequestDto {

  private BigDecimal amount;
  private String creditCardNumber;

}
