package com.playtomic.tests.wallet.exceptions;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.playtomic.tests.wallet.api.ApiErrorResponse;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(StripeServiceException.class)
  public ResponseEntity<ApiErrorResponse> handleStripServiceException(StripeServiceException e) {
    ApiErrorResponse error = ApiErrorResponse.builder().message("Error using third party api")
        .status(HttpStatus.BAD_REQUEST).timestamp(Instant.now()).build();

    return new ResponseEntity<ApiErrorResponse>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WalletException.class)
  public ResponseEntity<ApiErrorResponse> handleWalletException(WalletException e) {
    ApiErrorResponse error = ApiErrorResponse.builder().message(e.getMessage())
        .status(HttpStatus.BAD_REQUEST).timestamp(Instant.now()).build();

    return new ResponseEntity<ApiErrorResponse>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGenericException(Exception e) {
    ApiErrorResponse error = ApiErrorResponse.builder().message(e.getMessage())
        .status(HttpStatus.INTERNAL_SERVER_ERROR).timestamp(Instant.now()).build();

    return new ResponseEntity<ApiErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
