package com.bacilika.Action;

import com.bacilika.Game;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;
import com.bacilika.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Craft extends AbstractAction{


    public Craft(ObjectType craftedObject, int amountToCraft,Player player,InputAnalyzer analyzer){
        super(ActionType.CRAFT,craftedObject,craftedObject.getRequiredTool(),amountToCraft,analyzer);
        this.player = player;
    }
    @Override
    public int performAction(int amount, ObjectType craftedObject, ObjectType requiredTool) {
        if (canAfford(craftedObject,amount)){
            for (Map.Entry<ObjectType, Integer> set :
                    getCraftingRecipe(craftedObject).entrySet()) {
                player.removeFromInventory(set.getKey(), set.getValue());
            }
            return amount;
        }
            return -1;
    }
    private boolean canAfford(ObjectType objectType, int amount){
        for (Map.Entry<ObjectType, Integer> set :
                getCraftingRecipe(objectType).entrySet()) {
            if(player.checkInventory(set.getKey()) < set.getValue()*amount){
                return false;
            }

        }
        return true;
    }

    @Override
    public void printHelp() {
        System.out.println("If you want to know what you currently can craft, type 'recipe [item]'");
        System.out.println("if you want to see all craftable items type 'recipe all'");
    }


    public static HashMap<ObjectType,Integer> getCraftingRecipe(ObjectType object){
        return object.getCrafting();
    }
}
