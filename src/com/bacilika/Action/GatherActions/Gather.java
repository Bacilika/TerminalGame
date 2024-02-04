package com.bacilika.Action.GatherActions;

import com.bacilika.Action.Action;
import com.bacilika.Action.ActionType;
import com.bacilika.Game;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;
import com.bacilika.Player;

public abstract class Gather implements Action {
    private ActionType actionType;
    private ObjectType gatheredObject;
    private ObjectType desiredTool;

    private int wantedAmount;

    protected Gather(ActionType type, ObjectType gatheredObject, ObjectType desiredTool){
        this.actionType = type;
        this.gatheredObject = gatheredObject;
        this.desiredTool = desiredTool;
    }
    @Override
    public int getAmount(){
        return wantedAmount;
    }
    @Override
    public void setAmount(int amount){
        wantedAmount = amount;
    }
    @Override
    public void setObject(ObjectType type){
        gatheredObject = type;
    }

    @Override
    public ActionType getName() {
        return actionType;
    }
    @Override
    public void startAction(Player player){

        int retrivedItems = -1;
        Action a =null;

        // if no object specified
        if(gatheredObject == null) {
            System.out.println("what would you like to " + actionType.toString().toLowerCase());

            a = InputAnalyzer.analyzeInput(Game.scanner.nextLine(),actionType,null,wantedAmount);

            while (a.getObject() == null) {
                System.out.println("invalid object");
                a = InputAnalyzer.analyzeInput(Game.scanner.nextLine(),actionType,null,wantedAmount);
            }
            gatheredObject = a.getObject();
        }

        // if no amount specified
        if(wantedAmount == -1){
            if(a == null){
                System.out.println("how many " + gatheredObject.toString() + " would you like?");
                a = InputAnalyzer.analyzeInput(Game.scanner.nextLine(),actionType,gatheredObject,wantedAmount);
            }

            if(a.getAmount() == -1){
                System.out.println("how many " + gatheredObject.toString() + " would you like?");
                wantedAmount = Game.getValidInput(Integer::parseInt, (line) -> {
                    System.err.printf("Please enter an integer, not \"%s\"\n", line);
                });
            }
            else{
                wantedAmount = a.getAmount();
            }
        }
        //player has correct tool
        if(player.checkInventory(desiredTool) > 0){
            retrivedItems = performAction(wantedAmount,desiredTool);
            //got less than expected
            if(retrivedItems < wantedAmount){
                System.out.println("you can only " + actionType.toString() +
                        " " + retrivedItems +" " + gatheredObject.toString() + " at a time.");
            }

        }
        else{
            retrivedItems = performAction(wantedAmount,null);

            if(retrivedItems < wantedAmount){
                System.out.println("You don't have a " + desiredTool + " so you could only get " +retrivedItems + " " + gatheredObject.toString() );
            }
            //got 0
            else if (retrivedItems == 0){
                System.out.println("You don't have a " + desiredTool.toString() + " so you cannot " + actionType.toString());
                return;
            }
        }
        System.out.println("Added " + retrivedItems +" "+ gatheredObject.toString()+ " to inventory!");
        player.addToInventory(gatheredObject,retrivedItems);
    }

    @Override
    public ObjectType getObject() {
        return gatheredObject;
    }

    @Override
    public String toString() {
        return "Gather{" +
                "actionType=" + actionType +
                ", gatheredObject=" + gatheredObject +
                ", desiredTool=" + desiredTool +
                ", wantedAmount=" + wantedAmount +
                '}';
    }
}
