package com.playtomic.tests.wallet.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.springframework.util.Assert;
import com.playtomic.tests.wallet.exceptions.WalletException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

  @PrePersist
  @PreUpdate
  public void pricePrecisionConvertion() {
    this.balance.setScale(2, RoundingMode.HALF_UP);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "balance", nullable = false)
  private BigDecimal balance;


  public void deposit(BigDecimal amount) throws WalletException {
    Assert.notNull(amount, "amount == null");

    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new WalletException("The amount have to be greather than 0");
    }

    this.balance = this.balance.add(amount);
  }

  public void subtract(BigDecimal amount) throws WalletException {
    Assert.notNull(amount, "amount == null");

    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new WalletException("The amount have to be greather than 0");
    }

    if (amount.compareTo(this.balance) > 0) {
      throw new WalletException("the balance is lower than the amount");
    }

    this.balance = this.balance.subtract(amount);
  }

}
