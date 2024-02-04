package com.bacilika.Action;

import com.bacilika.ObjectType;
import com.bacilika.Player;

public class PlayerAction implements Action {
    private ActionType actionName;

    public PlayerAction(ActionType actionName){
        this.actionName = actionName;

    }
    @Override
    public ActionType getName() {
        return actionName;
    }
    @Override
    public ObjectType getObject() {
        return null;
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

        switch (actionName){
            case VIEWINVENTORY -> player.viewInventory();
            case SLEEP -> player.sleep();
            case DIE -> player.die();
            case HELP -> player.help();
        }
    }
    @Override
    public void setObject(ObjectType type) {

    }

    @Override
    public int performAction(int amount, ObjectType tool) {
        return 0;
    }
}
