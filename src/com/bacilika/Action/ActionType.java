package com.bacilika.Action;

import java.util.List;

public enum ActionType {
    CHOP, MINE, CRAFT, INVENTORY, SLEEP, UNKNOWN, DIE, RECIPE, EXPLORE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
