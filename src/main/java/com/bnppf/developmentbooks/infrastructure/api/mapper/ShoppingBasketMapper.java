package com.bnppf.developmentbooks.infrastructure.api.mapper;

import com.bnppf.developmentbooks.domain.model.Book;
import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShoppingBasketMapper {

    public ShoppingBasket toDomain(List<String> bookTitles) {
        ShoppingBasket basket = new ShoppingBasket();

        if (bookTitles != null) {
            bookTitles.forEach(title -> basket.addBook(new Book(title)));
        }

        return basket;
    }
}
