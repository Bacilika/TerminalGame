package com.bacilika.Action;

import java.util.List;

public enum ActionType {
    CHOP, MINE, CRAFT, VIEWINVENTORY, SLEEP, HELP, UNKNOWN, DIE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
