package com.bnppf.developmentbooks.domain.model;


import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {
    
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public int getNumberOfBooks() {
        return books.size();
    }
}
