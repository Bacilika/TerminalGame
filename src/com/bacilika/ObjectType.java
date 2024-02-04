package com.bacilika;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ObjectType {
    WOOD, STONE, AXE, PICKAXE, HOUSE;
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
