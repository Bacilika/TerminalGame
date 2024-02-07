package com.bacilika.Action.GatherActions;

import com.bacilika.Action.AbstractAction;
import com.bacilika.Action.ActionType;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;
import com.bacilika.Player;

public class ChopWood extends AbstractAction {

    public ChopWood(int amount, InputAnalyzer analyzer){
        super(ActionType.CHOP, ObjectType.WOOD,  ObjectType.AXE, amount,analyzer);
    }

    @Override
    public int performAction(int amount, ObjectType collectedObject,ObjectType requiredTool) {
        if (requiredTool == ObjectType.AXE){
            return Math.min(amount,5);
        }
        else{
            return 1;
        }
    }

    @Override
    public void printHelp() {
        System.out.println("use an axe to chop more wood at a time");
    }
}
