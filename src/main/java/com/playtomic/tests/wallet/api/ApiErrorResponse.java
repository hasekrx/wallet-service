package com.playtomic.tests.wallet.api;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {

  private final HttpStatus status;
  private final String message;
  private final Instant timestamp;

}
