package com.bacilika;

import com.bacilika.Action.*;
import com.bacilika.Action.GatherActions.ChopWood;
import com.bacilika.Action.GatherActions.MineStone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class InputAnalyzer {

    private static ActionType action;
    private static ObjectType object;
    private static int amount;
    private final Player player;
    public Map<String, Integer> stringIntegerHashMap = new HashMap<>() {{
        put("one", 1);
        put("two", 2);
        put("three", 3);
        put("four", 4);
        put("five", 5);
        put("six", 6);
        put("seven", 7);
    }};

    public InputAnalyzer(Player player) {
        this.player = player;
    }

    public Map<Enum, List<String>> similarNames = new HashMap<>() {{
        put(ActionType.CHOP, List.of("CHOP"));
        put(ActionType.MINE, List.of("MINE", "GATHER STONE"));
        put(ActionType.CRAFT, List.of("GET"));
        put(ActionType.INVENTORY, List.of("CHECK INVENTORY", "INVENTORY", "view inventory"));
        put(ActionType.SLEEP, List.of("go to bed"));
        put(ObjectType.HELP, List.of("help"));
        put(ActionType.UNKNOWN, List.of("unknown"));
        put(ActionType.DIE, List.of("quit"));
        put(ObjectType.WOOD, List.of("TREE", "PLANK"));
        put(ObjectType.STONE, List.of());
        put(ObjectType.AXE, List.of("axe"));
        put(ObjectType.PICKAXE, List.of("pick", "pickaxe"));
        put(ObjectType.HOUSE, List.of("go to bed"));
        put(ActionType.RECIPE, List.of("recipe"));
    }};


    public Action analyzeInput(String input, ActionType actionType, ObjectType objectType, int amountInput) {
        String[] inputs = input.split(" ");
        if (inputs.length == 0) {
            return null;
        }
        action = actionType;
        object = objectType;
        amount = amountInput;

        if (action == null) {
            action = (ActionType) findClosestAction(inputs, ActionType.values());
        }
        if (object == null) {
            object = (ObjectType) findClosestAction(inputs, ObjectType.values());
        }
        if (amountInput == -1) {
            amount = isInteger(inputs);
        }
        if(action == null && object == ObjectType.HELP){
            return new PlayerAction(ActionType.UNKNOWN,this);
        }

        Action performedAction;
        if (action != null) {
            performedAction = getActionFromEnum(action);
            if (object != null) {
                if (performedAction.getObject().equals(object)) {
                    performedAction.setAmount(amount);
                    return performedAction;
                }
            } else {
                performedAction.setObject(null);
                performedAction.setAmount(amount);
                return performedAction;
            }
        }
        return null;
    }

    public Enum findClosestAction(String[] inputs, Enum[] list) {
        Enum closestType = null;
        for (String input : inputs) {
            for (Enum type : list) {
                if (input.equalsIgnoreCase(type.toString())) {
                    return type;
                } else if (input.toLowerCase().contains(type.toString().toLowerCase())) {
                    closestType = type;
                }
            }
        }
        /*if (closestType == null) {
            return getClosest(inputs, list);
        }*/
        return closestType;
    }

    public int isInteger(String[] inputs) {
        int result = -1;
        for (String input : inputs) {
            for (Map.Entry<String, Integer> set : stringIntegerHashMap.entrySet()) {
                if (input.equals(set.getKey())) {
                    return set.getValue();
                }
            }
            try {
                return Integer.parseInt(input);
            } catch (Exception ignore) {
            }


        }
        return result;
    }

    public Action getActionFromEnum(Enum type) {
        switch (type) {
            case ActionType.CHOP -> {
                return new ChopWood(amount, this);
            }
            case ActionType.INVENTORY -> {
                return new PlayerAction(ActionType.INVENTORY,this);
            }
            case ActionType.MINE -> {
                return new MineStone((ObjectType) object, amount, this);
            }
            case ActionType.CRAFT -> {
                return new Craft((ObjectType) object, amount, player, this);
            }
            case ActionType.SLEEP, ActionType.DIE-> {
                return new PlayerAction((ActionType) type,this);
            }
            case ActionType.RECIPE -> {
                PlayerAction action = new PlayerAction(ActionType.RECIPE,this);
                action.setObject((ObjectType) object);
                return action;
            }
            default -> {
                System.out.println("Unknown command");
                System.out.println("please enter a valid action");
            }

        }
        return null;
    }

    public Enum getClosest(String[] inputs, Enum[] types) {
        for (String input : inputs) {
            for (Enum type : types) {
                for (String similar: similarNames.get(type)){
                    if (input.toLowerCase().contains(similar.toLowerCase())) {
                        return type;
                    }
                }
            }
        }
        return null;
    }
}
