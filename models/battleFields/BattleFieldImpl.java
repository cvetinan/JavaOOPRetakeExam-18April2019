package models.battleFields;

import models.battleFields.interfaces.Battlefield;
import models.cards.interfaces.Card;
import models.players.Beginner;
import models.players.interfaces.Player;

import static common.ConstantMessages.DEAD_PLAYER;

public class BattleFieldImpl implements Battlefield {
    private static final int HEALTH_POINTS_INCREASE = 40;
    private static final int CARD_DAMAGE_POINTS_INCREASE = 30;

    @Override
    public void fight(Player attackPlayer, Player enemyPlayer) {
        if (attackPlayer.isDead() || enemyPlayer.isDead()) {
            throw new IllegalArgumentException(DEAD_PLAYER);
        }

        checkForBeginnerBonus(attackPlayer);
        checkForBeginnerBonus(enemyPlayer);

        getHealthPointsFromDeck(attackPlayer);
        getHealthPointsFromDeck(enemyPlayer);

        startFight(attackPlayer, enemyPlayer);

    }

    private void startFight(Player attackPlayer, Player enemyPlayer) {
        while (true) {
            int attackPlayerDamage = attackPlayer
                    .getCardRepository()
                    .getCards().stream()
                    .mapToInt(Card::getDamagePoints)
                    .sum();
            enemyPlayer.takeDamage(attackPlayerDamage);

            if (enemyPlayer.isDead()) {
                return;
            }

            int enemyPlayerDamage = enemyPlayer
                    .getCardRepository()
                    .getCards()
                    .stream()
                    .mapToInt(Card::getDamagePoints)
                    .sum();

            attackPlayer.takeDamage(enemyPlayerDamage);

            if (attackPlayer.isDead()) {
                return;
            }
        }
    }

    private void getHealthPointsFromDeck(Player player) {
        int healthPoints = player.getCardRepository().getCards().stream().mapToInt(Card::getHealthPoints).sum();
        player.setHealth(player.getHealth() + healthPoints);
    }

    private void checkForBeginnerBonus(Player player) {
        if (!Beginner.class.getSimpleName().equals(player.getClass().getSimpleName())) {
            return;
        }
        player.setHealth(player.getHealth() + HEALTH_POINTS_INCREASE);

        player
                .getCardRepository()
                .getCards()
                .forEach(card -> card.setDamagePoints(card.getDamagePoints() + CARD_DAMAGE_POINTS_INCREASE));
    }
}
