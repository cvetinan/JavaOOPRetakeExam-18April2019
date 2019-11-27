package models.players;

import models.players.interfaces.Player;
import repositories.interfaces.CardRepository;

import static common.ConstantMessages.*;

public abstract class BasePlayer implements Player {
    private static final int MIN_HEALTH_POINTS = 0;
    private static final int MIN_DAMAGE_POINTS = 0;
    private String username;
    private int health;
    private CardRepository cardRepository;
    private boolean isDead;

    protected BasePlayer(CardRepository cardRepository, String username, int health) {
        this.cardRepository = cardRepository;
        this.setUsername(username);
        this.setHealth(health);
        this.setDead(false);
    }

    @Override
    public CardRepository getCardRepository() {
        return this.cardRepository;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }

    @Override
    public void takeDamage(int damagePoints) {
        if (damagePoints < MIN_DAMAGE_POINTS) {
            throw new IllegalArgumentException(DAMAGE_POINTS_LESS_THAN_ZERO);
        }

        if (this.getHealth() - damagePoints < MIN_HEALTH_POINTS) {
            this.setDead(true);
            this.setHealth(MIN_HEALTH_POINTS);
        } else {
            this.setHealth(this.getHealth() - damagePoints);
        }
    }

    @Override
    public void setHealth(int healthPoints) {
        if (healthPoints < MIN_HEALTH_POINTS) {
            throw new IllegalArgumentException(PLAYER_HEALTH_LESS_THAN_ZERO);
        }
        this.health = healthPoints;
    }

    private void setDead(boolean status) {
        this.isDead = status;
    }

    private void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException(PLAYER_NAME_NULL_OR_EMPTY);
        }
        this.username = username;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format(PLAYER_REPORT_INFO
                , this.getUsername()
                , this.getHealth()
                , this.getCardRepository().getCount()));

        this
                .getCardRepository()
                .getCards()
                .forEach(card -> output.append(System.lineSeparator()).append(card.toString()));
        output.append(System.lineSeparator()).append(DEFAULT_REPORT_SEPARATOR);
        return output.toString().trim();
    }
}
