package com.bacilika.Action;

import com.bacilika.ObjectType;
import com.bacilika.Player;

public interface Action {

    ActionType getName();
    ObjectType getObject();

    int getAmount();
    void setAmount(int amount);
    void startAction(Player player);

    void setObject(ObjectType type);

    int performAction(int amount, ObjectType tool);
}
