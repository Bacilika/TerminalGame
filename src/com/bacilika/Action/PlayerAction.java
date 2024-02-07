package com.bacilika.Action;

import com.bacilika.Game;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;
import com.bacilika.Player;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class PlayerAction extends AbstractAction {
    private ActionType actionName;

    public PlayerAction(ActionType actionName, InputAnalyzer analyzer){
        super(actionName,null,null,-1,analyzer);
        this.actionName = actionName;

    }

    @Override
    public void printHelp(){
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("These are the things you can do:");
        for (ActionType action : ActionType.values()) {
            stringBuilder.append(action.toString().toLowerCase()).append(", ");
        }
        System.out.println(stringBuilder);
    }

    @Override
    public void startAction(Player player) {

        switch (actionName){
            case INVENTORY -> player.viewInventory();
            case SLEEP -> player.sleep();
            case DIE -> player.die();
            case UNKNOWN -> printHelp();
            case RECIPE -> System.out.println(recipeToString());
        }
    }

    @Override
    public int performAction(int amount, ObjectType objectType, ObjectType tool) {
        return 0;
    }

    public String recipeToString() {
        if(collectedObject == null){
            System.out.println("what would you like to see the recipe for?");
            String response = Game.scanner.nextLine();
            Action a = analyzer.analyzeInput(response,ActionType.RECIPE,collectedObject,-1);
            while(a.getObject() == null){
                System.out.println("Invalid item, try again");
                a = analyzer.analyzeInput(response,ActionType.RECIPE,collectedObject,-1);
            }
            collectedObject = a.getObject();
        }
        if(collectedObject == ObjectType.ALL){
            return  "Here is where all recipes will be printed";
        }

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<ObjectType,Integer> recipe = collectedObject.getCrafting();
        if (recipe != null) {
            for (Map.Entry<ObjectType, Integer> set :
                    recipe.entrySet()) {
                stringBuilder.append(set.getKey()).append(" = ").append(set.getValue());
                stringBuilder.append('\n');
            }
            return stringBuilder.toString();
        }
        return "This item cannot be crafted";
    }

}
