package com.bacilika.Action.GatherActions;

import com.bacilika.Action.ActionType;
import com.bacilika.ObjectType;
import com.bacilika.Player;

public class ChopWood extends Gather{

    public ChopWood(){

        super(ActionType.CHOP, ObjectType.WOOD, ObjectType.AXE);
    }

    @Override
    public int performAction(int amount,ObjectType tool) {
        if (tool == ObjectType.AXE){
            return Math.min(amount,5);
        }
        else{
            return 1;
        }
    }
}
