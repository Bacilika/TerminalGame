package com.bacilika.Action.GatherActions;

import com.bacilika.Action.AbstractAction;
import com.bacilika.Action.ActionType;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;
import com.bacilika.Player;

public class MineStone extends AbstractAction {

    public MineStone(ObjectType objectType, int amount, InputAnalyzer analyzer) {
        super(ActionType.MINE, objectType, ObjectType.PICKAXE,amount,analyzer);
    }

    @Override
    public int performAction(int amount, ObjectType objectType, ObjectType tool) {
        if (tool == ObjectType.PICKAXE) {
            return Math.min(amount, 5);
        } else {
            return 0;
        }
    }

    @Override
    public void printHelp() {
        System.out.println("you need a pickaxe to mine");
    }
}
