package com.bnppf.developmentbooks.domain.service;

import com.bnppf.developmentbooks.domain.model.Book;
import com.bnppf.developmentbooks.domain.model.ShoppingBasket;

import java.math.BigDecimal;
import java.util.*;

/**
 * Service responsible for calculating the total price of a shopping basket while
 * applying specific discount rules. The service is optimized to group books into the most optimal sets for the most favorable discount.
 */
public class BasketPriceCalculator {

    private static final BigDecimal BOOK_PRICE = BigDecimal.valueOf(50);
    private static final Map<Integer, BigDecimal> DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS = Map.of(
            2, BigDecimal.valueOf(0.95),
            3, BigDecimal.valueOf(0.90),
            4, BigDecimal.valueOf(0.80),
            5, BigDecimal.valueOf(0.75));
    /**
     * Computes the lowest possible price for the given basket.
     *
     * @param basket the domain object containing the selected books.
     * @return the total price as a {@link BigDecimal}.
     */
    public BigDecimal computePrice(ShoppingBasket basket) {
        if (basket.getBooks().isEmpty()) {
            return BigDecimal.ZERO;
        }

        int numberOfDifferentBooks = (int) basket.getBooks().stream().distinct().count();
        if (basket.getBooks().size() == numberOfDifferentBooks)
            return computeBookPilePrice(basket.getBooks().size());

        List<Set<Book>> bookPiles = rearrangeBooksIntoSets(basket.getBooks());
        return bookPiles.stream().map(bookPile -> computeBookPilePrice(bookPile.size())).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal computeBookPilePrice(int setSize) {
        return BOOK_PRICE.multiply(BigDecimal.valueOf(setSize))
                .multiply(DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.getOrDefault(setSize, BigDecimal.ONE));
    }

    private List<Set<Book>> rearrangeBooksIntoSets(List<Book> basketBooks) {
        List<Set<Book>> pilesHolder = new ArrayList<>();

        for (Book book : basketBooks) {
            List<Set<Book>> bookPilesWithoutCopy = pilesHolder.stream()
                    .filter(pile -> !pile.contains(book))
                    .toList();

            if (bookPilesWithoutCopy.isEmpty()) {
                Set<Book> newBookPile = new HashSet<>();
                newBookPile.add(book);
                pilesHolder.add(newBookPile);

            } else {
                addBookToBestPile(book, bookPilesWithoutCopy);
            }
        }
        return pilesHolder;
    }

    private void addBookToBestPile(Book book, List<Set<Book>> bookPilesWithoutCopy) {
        Set<Book> bestPile = null;
        BigDecimal bestPrice = null;

        for (Set<Book> currentPile : bookPilesWithoutCopy) {
            BigDecimal currentCombinationPrice = computeCombinationPrice(currentPile, bookPilesWithoutCopy);
            if (bestPile == null || currentCombinationPrice.compareTo(bestPrice) < 0) {
                bestPile = currentPile;
                bestPrice = currentCombinationPrice;
            }
        }
        if (bestPile != null) {
            bestPile.add(book);
        }
    }

    private BigDecimal computeCombinationPrice(Set<Book> targetPile, List<Set<Book>> allPiles) {
        BigDecimal total = BigDecimal.ZERO;
        for (Set<Book> pile : allPiles) {
            int size = (pile == targetPile) ? pile.size() + 1 : pile.size();
            total = total.add(computeBookPilePrice(size));
        }
        return total;
    }
}
