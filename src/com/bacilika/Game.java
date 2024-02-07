package com.bacilika;

import com.bacilika.Action.*;
import com.bacilika.Action.GatherActions.ChopWood;
import com.bacilika.Action.GatherActions.MineStone;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Game {
    public static Scanner scanner;
    private final Player player;
    private boolean isRunning = true;
    private final InputAnalyzer analyzer;
    public Game(){
        scanner = new Scanner(System.in);
        System.out.println("Hello and welcome! Your goal is to reach the end by ...");
        System.out.print("What is your name? ");
        String name = scanner.nextLine();
        while(name.isEmpty() ){
            System.out.println("you need to have a name, enter a name");
            name =scanner.nextLine();
        };
        player = new Player(name);
        analyzer = new InputAnalyzer(player);
        System.out.println("Write whatever you'd like to do, you can do things like " +
                "chop wood or mine stone. For general help type 'help' or type " +
                "'[action] help' for help on how to perform the action");
        while (isRunning) {
            selectAction();
        }
    }
    public void selectAction(){
        String choice = "";
        choice = scanner.nextLine().toLowerCase();
        Action action = analyzer.analyzeInput(choice,null,null,-1);
        if(action != null){
            action.startAction(player);
        }
        else{
            System.out.println("incorrect action type, try again");
        }
    }
    public static <T> T getValidInput(
            Function<String, T> function,
            Consumer<String> onFailure
    ) throws NoSuchElementException {
        T result = null;
        while(result == null) {
            String line = scanner.nextLine();
            try {
                result = function.apply(line);
            } catch (Exception ex) {
                onFailure.accept(line);
            }
        }
        return result;
    }

    public Player getPlayer() {
        return player;
    }

    public InputAnalyzer getAnalyzer() {
        return analyzer;
    }
}
