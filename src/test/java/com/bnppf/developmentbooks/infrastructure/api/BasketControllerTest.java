package com.bnppf.developmentbooks.infrastructure.api;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import com.bnppf.developmentbooks.infrastructure.api.mapper.ShoppingBasketMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private BasketPriceCalculator basketPriceCalculator;
    @MockitoBean
    private ShoppingBasketMapper basketMapper;


    @Test
    void should_return_a_price_when_the_input_is_valid() throws Exception {
        when(basketPriceCalculator.computePrice(any(ShoppingBasket.class)))
                .thenReturn(BigDecimal.valueOf(50.0));

        ShoppingBasket mockBasket=new ShoppingBasket();
        when(basketMapper.toDomain(anyList())).thenReturn(mockBasket);


        String jsonPayload = """
                ["Clean Code"]
                """;

        mockMvc.perform(post("/api/basket/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("50.0"));

        verify(basketPriceCalculator).computePrice(any(ShoppingBasket.class));
    }
    @Test
    void should_return_400_when_request_body_is_missing() throws Exception {
        mockMvc.perform(post("/api/basket/price")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
