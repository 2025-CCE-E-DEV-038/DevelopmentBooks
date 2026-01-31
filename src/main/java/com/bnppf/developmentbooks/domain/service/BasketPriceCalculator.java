package com.bnppf.developmentbooks.domain.service;

import com.bnppf.developmentbooks.domain.model.Book;
import com.bnppf.developmentbooks.domain.model.ShoppingBasket;

import java.math.BigDecimal;
import java.util.*;

public class BasketPriceCalculator {

    private static final BigDecimal BOOK_PRICE = BigDecimal.valueOf(50);
    private static final Map<Integer, BigDecimal> DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS = Map.of(
            2, BigDecimal.valueOf(0.95),
            3, BigDecimal.valueOf(0.90),
            4, BigDecimal.valueOf(0.80),
            5, BigDecimal.valueOf(0.75));

    public BigDecimal computePrice(ShoppingBasket basket) {
        int numberOfBooks = basket.getBooks().size();
        int numberOfDifferentBooks = (int) basket.getBooks().stream().distinct().count();

        if (numberOfDifferentBooks == 0) {
            return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks));
        }
        if (numberOfBooks == numberOfDifferentBooks && DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.containsKey(numberOfDifferentBooks)) {
            BigDecimal discountMultiplier = DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.get(numberOfDifferentBooks);
            return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks)).multiply(discountMultiplier);
        } else {
            if (numberOfBooks == 4 && numberOfDifferentBooks == 3) {
                BigDecimal discountMultiplier = DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.get(3);
                BigDecimal threeBooksSetPrice = BOOK_PRICE.multiply(BigDecimal.valueOf(3)).multiply(discountMultiplier);
                return BOOK_PRICE.add(threeBooksSetPrice);
            } else {
                List<Set<Book>> bookPiles = rearrangeBooks(basket.getBooks());
                return bookPiles.stream().map(set -> BOOK_PRICE
                        .multiply(BigDecimal.valueOf(set.size()))
                        .multiply(DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.getOrDefault(set.size(), BigDecimal.ONE))).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }

    }

    private List<Set<Book>> rearrangeBooks(List<Book> basketBooks) {
        // create a collection of piles that will contain each set discount
        List<Set<Book>> pilesHolder = new ArrayList<>();

        // go through the basket and pick one book at a time
        for (Book book : basketBooks) {
            List<Set<Book>> bookPilesWithoutCopy = pilesHolder.stream().filter(pile -> !pile.contains(book)).toList();

            // if all piles contain a copy, then create a new pile
            if (bookPilesWithoutCopy.isEmpty()) {
                Set<Book> newBookPile = new HashSet<>();
                newBookPile.add(book);
                pilesHolder.add(newBookPile);

            } else {// else find the best combination allowing maximum discount

                Set<Book> bestSetOption = null;
                BigDecimal bestCombinationPrice = BigDecimal.ZERO;

                // go through each pile and find the one resulting in the best combination price
                for (Set<Book> currentPile : bookPilesWithoutCopy) {

                    // compute combination total price
                    BigDecimal currentCombinationPrice= computeCombinationPrice(currentPile, bookPilesWithoutCopy);
                    // if the current combination is the highest, then keep it
                    if (currentCombinationPrice.compareTo(bestCombinationPrice) < 0 || bestSetOption==null) {
                        bestSetOption = currentPile;
                        bestCombinationPrice = currentCombinationPrice;
                    }
                }
                // the kept set was the best option
                bestSetOption.add(book);
            }
        }
        return pilesHolder;
    }

    private BigDecimal computeCombinationPrice(Set<Book> selectedPile, List<Set<Book>> bookPiles) {
        int newSelectedPileSize = selectedPile.size() + 1;

        // if the pile is not eligible to discount apply 0
        BigDecimal selectedPilePrice = BOOK_PRICE
                .multiply(BigDecimal.valueOf(newSelectedPileSize))
                .multiply(DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.getOrDefault(newSelectedPileSize, BigDecimal.ONE));

        BigDecimal otherPilesPrice = BigDecimal.ZERO;
        for (Set<Book> bookPile : bookPiles) {
            if (bookPile != selectedPile) {
                otherPilesPrice = otherPilesPrice
                        .add(BOOK_PRICE
                                .multiply(BigDecimal.valueOf(bookPile.size()))
                                .multiply(DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.getOrDefault(bookPile.size(), BigDecimal.ONE)));
            }
        }
        return selectedPilePrice.add(otherPilesPrice);
    }
}
