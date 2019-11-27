package repositories;

import models.cards.interfaces.Card;
import repositories.interfaces.CardRepository;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static common.ConstantMessages.*;

public class CardRepositoryImpl implements CardRepository {
    private Map<String, Card> cards;

    public CardRepositoryImpl() {
        this.cards = new LinkedHashMap<>();
    }

    @Override
    public int getCount() {
        return this.cards.values().size();
    }

    @Override
    public List<Card> getCards() {
        return Collections.unmodifiableList(this.cards.values().stream().collect(Collectors.toList()));
    }

    @Override
    public void add(Card card) {
        if (card == null) {
            throw new IllegalArgumentException(CARD_CANNOT_BE_NULL);
        }
        if (this.cards.containsKey(card.getName())) {
            throw new IllegalArgumentException(String.format(CARD_ALREADY_EXIST, card.getName()));
        }
        this.cards.put(card.getName(), card);
    }


    @Override
    public boolean remove(Card card) {
        if (card == null) {
            throw new IllegalArgumentException(CARD_CANNOT_BE_NULL);
        }
        boolean result = false;
        if (this.cards.containsKey(card.getName())) {
            this.cards.remove(card.getName(), card);
            result = true;
        }
        return result;

    }

    @Override
    public Card find(String name) {
        Card card = this.cards.get(name);
        return card;
    }
}
