package com.playtomic.tests.wallet.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.dto.WalletDTO;
import com.playtomic.tests.wallet.dto.WalletRequestDto;
import com.playtomic.tests.wallet.exceptions.WalletException;
import com.playtomic.tests.wallet.service.WalletService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class WalletControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WalletService walletService;

  @Test
  public void testFindWalletById() throws Exception {

    WalletDTO wallet = WalletDTO.builder().id(1).balance(new BigDecimal(10)).build();

    given(walletService.findWalletById(1)).willReturn(wallet);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/wallet/{id}/find", 1).accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

  }


  @Test
  public void testFindWalletById_Exception() throws Exception {

    given(walletService.findWalletById(1)).willThrow(new WalletException("exception"));

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/wallet/{id}/find", 1).accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isBadRequest());

  }


  @Test
  public void testDeposit() throws Exception {

    WalletDTO wallet = WalletDTO.builder().id(1).balance(new BigDecimal(10)).build();

    given(walletService.deposit(1, "123", new BigDecimal(100))).willReturn(wallet);

    WalletRequestDto request =
        WalletRequestDto.builder().creditCardNumber("123").amount(new BigDecimal(100)).build();
    String jsonRequest = new ObjectMapper().writeValueAsString(request);

    this.mockMvc
        .perform(MockMvcRequestBuilders.post("/wallet/{id}/deposit", 1)
            .accept(MediaType.APPLICATION_JSON).content(jsonRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(10));

  }

}
