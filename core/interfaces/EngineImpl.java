package core.interfaces;

import common.Command;
import core.ManagerControllerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EngineImpl implements Engine {
    private BufferedReader reader;
    private ManagerController controller;
    public EngineImpl() {
       this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.controller = new ManagerControllerImpl();
    }

    @Override
    public void run() {
       while (true) {
           String result = null;
           try {
               result = processInput();
               if (Command.Exit.name().equals(result)){
                   break;
               }
           } catch (IOException e) {
               result = e.getMessage();
           }
           System.out.println(result);
       }
    }

    private String processInput() throws IOException {
        String input = reader.readLine();
        String[] tokens = input.split("\\s+");
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);
        String result = null;
        Command command = Command.valueOf(tokens[0]);
        switch (command) {
            case AddPlayer:
                result = AddPlayer(data);
                break;
            case AddCard:
                result = AddCard(data);
                break;
            case AddPlayerCard:
                result = AddPlayerCard(data);
                break;
            case Fight:
                result = Fight(data);
                break;
            case Report:
                result = Report();
                break;
            case Exit:
                result = Command.Exit.name();
                break;
        }
        return result;
    }

    private String Report() {
        String result = this.controller.report();
        return result;
    }

    private String Fight(String[] data) {
        String attackPlayer = data[0];
        String enemyPlayer = data[1];

        String result = this.controller.fight(attackPlayer, enemyPlayer);
        return result;
    }

    private String AddPlayerCard(String[] data) {
        String username = data[0];
        String cardName = data[1];

        String result = this.controller.addPlayerCard(username, cardName);
        return result;
    }

    private String AddCard(String[] data) {
        String type = data[0];
        String username = data[1];

        String result = this.controller.addCard(type, username);
        return result;
    }

    private String AddPlayer(String[] data) {
        String type = data[0];
        String username = data[1];

        String result = this.controller.addPlayer(type, username);
        return result;
    }
}
