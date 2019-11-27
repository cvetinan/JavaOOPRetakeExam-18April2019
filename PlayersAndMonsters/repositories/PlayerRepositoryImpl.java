package repositories;

import models.players.interfaces.Player;
import repositories.interfaces.PlayerRepository;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static common.ConstantMessages.PLAYER_ALREADY_EXISTS;
import static common.ConstantMessages.PLAYER_CANNOT_BE_NULL;

public class PlayerRepositoryImpl implements PlayerRepository {
    private Map<String, Player> players;

    public PlayerRepositoryImpl() {
        this.players = new LinkedHashMap<>();
    }

    @Override
    public int getCount() {
        return this.players.values().size();
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players.values().stream().collect(Collectors.toList()));
    }

    @Override
    public void add(Player player) {
        if (player == null) {
            throw new IllegalArgumentException(PLAYER_CANNOT_BE_NULL);
        }

        if (this.players.containsKey(player.getUsername())) {
            String message = String.format(PLAYER_ALREADY_EXISTS, player.getUsername());
            throw new IllegalArgumentException(message);
        }

        this.players.put(player.getUsername(), player);
    }

    @Override
    public boolean remove(Player player) {
        if (player == null) {
            throw new IllegalArgumentException(PLAYER_CANNOT_BE_NULL);
        }
        boolean result = false;
        if (this.players.containsKey(player.getUsername())){
            this.players.remove(player.getUsername(), player);
            result = true;
        }
        return result;
    }

    @Override
    public Player find(String name) {
        Player player = this.players.get(name);
        return player;
    }
}
