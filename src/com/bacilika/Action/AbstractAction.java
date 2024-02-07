package com.bacilika.Action;

import com.bacilika.Game;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;
import com.bacilika.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAction implements Action {
    protected ActionType actionType;
    protected ObjectType collectedObject;
    protected ObjectType requiredTool;
    protected int objectAmount;

    protected Player player;
    protected InputAnalyzer analyzer;

    protected AbstractAction(ActionType actionType, ObjectType collectedObject,
                          ObjectType requiredTool, int objectAmount, InputAnalyzer analyzer) {
        this.actionType = actionType;
        this.collectedObject = collectedObject;
        this.requiredTool = requiredTool;
        this.objectAmount = objectAmount;
        this.analyzer = analyzer;
    }
    @Override
    public ObjectType getObject(){
        return collectedObject;
    }
    @Override
    public int getAmount() {
        return objectAmount;
    }
    public void setAmount(int amount){
        this.objectAmount = amount;
    }
    @Override
    public void startAction(Player player){
        int retrivedItems = -1;
        Action a =null;
        if(collectedObject == ObjectType.HELP){
            printHelp();
            return;
        }

        // if no object specified
        if(collectedObject == null) {
            System.out.println("what would you like to " + actionType.toString().toLowerCase());
            a = analyzer.analyzeInput(Game.scanner.nextLine(),actionType,null,objectAmount);

            while (a.getObject() == null) {
                System.out.println("invalid object");
                a = analyzer.analyzeInput(Game.scanner.nextLine(),actionType,null,objectAmount);
            }
            collectedObject = a.getObject();
        }
        ObjectType required = player.checkInventory(requiredTool) > 0 ? requiredTool :  null;

        if(objectAmount == 0){
            performAction(0,collectedObject,required);
        }


        // if no amount specified
        if(objectAmount == -1){
            if(a == null){
                System.out.println("how many " + collectedObject + " would you like?");
                a = analyzer.analyzeInput(Game.scanner.nextLine(),actionType,collectedObject,objectAmount);
            }

            if(a.getAmount() == -1){
                System.out.println("how many " + collectedObject.toString() + " would you like?");
                objectAmount = Game.getValidInput(Integer::parseInt, (line) -> {
                    System.err.printf("Please enter an integer, not \"%s\"\n", line);
                });
            }
            else{
                objectAmount = a.getAmount();
            }
        }
        //player has correct tool
        if(player.checkInventory(requiredTool) > 0){
            retrivedItems = performAction(objectAmount, collectedObject,requiredTool);
            //got less than expected
            if(retrivedItems < objectAmount){
                System.out.println("you can only " + actionType.toString() +
                        " " + retrivedItems +" " + collectedObject.toString() + " at a time.");
            }

        }
        else{
            retrivedItems = performAction(objectAmount,collectedObject,null);

            if (retrivedItems == 0){
                System.out.println("You don't have a " + requiredTool.toString() + " so you cannot " +
                        actionType.toString() + " " + collectedObject.toString());
                return;
            }
            if(retrivedItems == -1){
                printItemAmount();
                return;
            }

            else if(retrivedItems < objectAmount){
                System.out.println("You don't have a " + requiredTool + " so you could only get " +retrivedItems + " " +
                        collectedObject.toString() );
            }
            //got 0

        }
        System.out.println("Added " + retrivedItems +" "+ collectedObject.toString()+ " to inventory!");
        player.addToInventory(collectedObject,retrivedItems);
    }
    @Override
    public void setObject(ObjectType type){
        this.collectedObject = type;
    }
    private void printItemAmount(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need: \n");
        for (Map.Entry<ObjectType, Integer> set : collectedObject.getCrafting().entrySet()) {
            stringBuilder.append(set.getValue()*objectAmount).append(" ").append(set.getKey());
            if(set.getValue() > 1){
                stringBuilder.append("s");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("you have: \n");
        for (Map.Entry<ObjectType, Integer> set : collectedObject.getCrafting().entrySet()) {
            stringBuilder.append(player.checkInventory(set.getKey())).append(" ").append(set.getKey());
            if(set.getValue() > 1){
                stringBuilder.append("s");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }
    protected void setPlayer(Player p){
        this.player = p;
    }
}
