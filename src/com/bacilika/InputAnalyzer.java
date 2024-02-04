package com.bacilika;

import com.bacilika.Action.*;
import com.bacilika.Action.GatherActions.ChopWood;
import com.bacilika.Action.GatherActions.MineStone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class InputAnalyzer {
    private static Map<String,Integer> stringIntegerHashMap = new HashMap<>(){{
        put("one", 1);
        put("two",2);
        put("three",3);
        put("four",4);
        put("five",5);
        put("six",6);
        put("seven",7);
    }};

    private static Map<ActionType,List<String>> actionNames = new HashMap<>() {{
        put(ActionType.CHOP,List.of("CHOP"));
        put(ActionType.MINE,List.of("MINE","GATHER STONE"));
        put(ActionType.CRAFT,List.of("GET"));
        put(ActionType.VIEWINVENTORY,List.of("CHECK INVENTORY","INVENTORY"));
        put(ActionType.SLEEP,List.of("go to bed"));
        put(ActionType.HELP,List.of("help"));
        put(ActionType.UNKNOWN,List.of("unknown"));
        put(ActionType.DIE,List.of("commit suicide","end life"));
    }};

    private static Map<ObjectType,List<String>> objectNames = new HashMap<>() {{
        put(ObjectType.WOOD,List.of("TREE", "PLANK"));
        put(ObjectType.STONE,List.of());
        put(ObjectType.AXE,List.of("axe"));
        put(ObjectType.PICKAXE,List.of("pick","pickaxe"));
        put(ObjectType.HOUSE,List.of("go to bed"));
    }};

    private static Map<ObjectType,HashMap<ObjectType,Integer>> objectCrafting = new HashMap<>() {{
        put(ObjectType.WOOD,null);
        put(ObjectType.STONE,null);
        put(ObjectType.AXE,new HashMap<>() {{
            put(ObjectType.WOOD, 2);
        }});
        put(ObjectType.PICKAXE,new HashMap<>() {{
            put(ObjectType.WOOD, 5);
        }});
        put(ObjectType.HOUSE,new HashMap<>() {{
            put(ObjectType.WOOD, 50);
            put(ObjectType.STONE,20);
        }});
    }};

    public static Action analyzeInput(String input, ActionType actionType, ObjectType objectType, int amountInput){

        String[] inputs = input.split(" ");
        if(inputs.length == 0){
            throw new RuntimeException();
        }
        Enum action = actionType;
        Enum object = objectType;
        int amount = amountInput;

        if (action == null){ action = findClosestAction(inputs, ActionType.values()); }
        if(object == null){object = findClosestAction(inputs, ObjectType.values());}
        if (amountInput == -1){ amount = isInteger(inputs);}

        Action performedAction;
        if (action!= null){
            performedAction = getActionFromEnum(action);
            if(object != null){
                if(performedAction.getObject().name().equals(object.name())){
                    performedAction.setAmount(amount);
                    return performedAction;
                }
            }
            else{
                performedAction.setObject(null);
                performedAction.setAmount(amount);
                return performedAction;
            }
        }
        return null;
    }
    public static Enum findClosestAction(String[] inputs, Enum[] list){
        Enum closestType = null;
        for (String input:inputs){
            for(Enum type: list){
                if (input.equalsIgnoreCase(type.toString())){
                    return type;
                }
                else if (input.toLowerCase().contains(type.toString().toLowerCase())){
                    closestType = type;
                }
            }
        }
        return closestType;
    }
    public static int isInteger(String[] inputs){
        int result = -1;
        for (String input: inputs){
            for (Map.Entry<String,Integer> set: stringIntegerHashMap.entrySet()){
                if(input.equals(set.getKey())){
                    return set.getValue();
                }
            }
            try{
                return Integer.parseInt(input);
            }
            catch (Exception ignore){
            }


        }
        return result;
    }
    public static Action getActionFromEnum(Enum type) {
        switch (type) {
            case ActionType.CHOP -> {
                return new ChopWood();
            }
            case ActionType.VIEWINVENTORY -> {
                return new PlayerAction(ActionType.VIEWINVENTORY);
            }
            case ActionType.MINE -> {
                return new MineStone();
            }
            case ActionType.CRAFT -> {
                return new Craft();
            }
            case ActionType.SLEEP -> {
                return new PlayerAction(ActionType.SLEEP);

            }
            case ActionType.HELP -> {
               return new PlayerAction(ActionType.HELP);
            }
            default -> {
                System.out.println("Unknown command");
                System.out.println("please enter a valid action");
            }
        }
        return null;
    }
    public static ActionType getClosest(String input, ActionType etype) {
        input = input.toLowerCase();
        ActionType closest = null;
        for (Map.Entry<ActionType, List<String>> set : actionNames.entrySet()) {
            for (String similar : set.getValue()) {
                if (similar.equals(input)) {
                    return set.getKey();
                }
                if (input.contains(similar)) {
                    closest = set.getKey();
                }
            }
        }
        return closest;
    }
    public String recipeToString(ObjectType object) {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<ObjectType,Integer> recipe = objectCrafting.get(object);
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
    public static HashMap<ObjectType,Integer> getCraftingRecipe(ObjectType object){
        return objectCrafting.get(object);
    }
}
