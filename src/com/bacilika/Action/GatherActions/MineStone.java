package com.bacilika.Action.GatherActions;

import com.bacilika.Action.ActionType;
import com.bacilika.ObjectType;
import com.bacilika.Player;

public class MineStone extends Gather {

    public MineStone() {
        super(ActionType.MINE, ObjectType.STONE, ObjectType.PICKAXE);
    }

    @Override
    public int performAction(int amount, ObjectType tool) {
        if (tool == ObjectType.PICKAXE) {
            return Math.min(amount, 5);
        } else {
            return 0;
        }
    }
}
