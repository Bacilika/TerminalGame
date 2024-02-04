package com.bacilika.Action;

import com.bacilika.Game;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;
import com.bacilika.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Craft implements Action{
    private ActionType actionType;
    private ObjectType actionReturn;
    private ObjectType requiredStation;

    private Player player;

    public Craft(){

    }
    @Override
    public ActionType getName() {
        return actionType;
    }

    @Override
    public ObjectType getObject() {
        return actionReturn;
    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public void setAmount(int amount) {
    }

    @Override
    public void startAction(Player player) {
        this.player = player;

        List<ObjectType> craftable = new ArrayList<>();

        for (ObjectType type : ObjectType.values()) {
            if (InputAnalyzer.getCraftingRecipe(type) == null)
                continue;
            for (Map.Entry<ObjectType, Integer> recipe : InputAnalyzer.getCraftingRecipe(type).entrySet()) {
                if (player.getInventory().containsKey(recipe.getKey()) &&
                        player.getInventory().get(recipe.getKey()) >= recipe.getValue()) {
                    craftable.add(type);
                }
            }
        }
            System.out.println("What would you like to craft? type 'help' for crafting help");
            String response = Game.scanner.nextLine();
            if (response.equals("help")) {
                System.out.println("possible Craftings: ");
                System.out.println(craftable);
                System.out.println("All craftings");
                System.out.println(ObjectType.getCraftable());
                System.out.println("if you want to know the recipe for an item write 'recipe [item] or 'craft [item]' to craft");

            }
            else{
                if (craftable.contains(ObjectType.getClosest(response))){
                    performAction(1, ObjectType.getClosest(response));
                }
                else
                    System.out.println("You dont have enough materials to craft this");
                return;
            }
            String[] responses = Game.scanner.nextLine().split(" ");
            if (responses[0].equals("recipe")) {
                System.out.println(ObjectType.getClosest(responses[1]).recipeToString());
            }
            else if (responses[0].equals("craft")) {
               if (craftable.contains(ObjectType.getClosest(response))){
                   performAction(1, ObjectType.getClosest(responses[1]));
               }
               else
                   System.out.println("You dont have enough materials to craft this");
            }
    }
    @Override
    public void setObject(ObjectType type) {

    }
    @Override
    public int performAction(int amount, ObjectType type) {
        for (Map.Entry<ObjectType, Integer> set :
            type.getCraftingRecipe().entrySet()) {
            player.removeFromInventory(set.getKey(), set.getValue());
        }
        System.out.println("Added 1 " + type + " to inventory");
        player.addToInventory(type,1);
        return 1;
    }
}
