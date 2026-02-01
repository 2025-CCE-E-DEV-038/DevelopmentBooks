package com.bnppf.developmentbooks.infrastructure.api.mapper;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingBasketMapperTest {

    private final ShoppingBasketMapper mapper = new ShoppingBasketMapper();

    @Test
    void should_map_list_of_titles_to_shopping_basket() {
        List<String> titles = List.of("Clean Code", "The Clean Coder");

        ShoppingBasket basket = mapper.toDomain(titles);

        assertThat(basket).isNotNull();
        assertThat(basket.getBooks()).hasSize(2);
        assertThat(basket.getBooks())
                .extracting("title")
                .containsExactlyInAnyOrder("Clean Code", "The Clean Coder");
    }

    @Test
    void should_return_empty_basket_when_input_is_null_or_empty() {
        assertThat(mapper.toDomain(null).getBooks()).isEmpty();
        assertThat(mapper.toDomain(List.of()).getBooks()).isEmpty();
    }
}