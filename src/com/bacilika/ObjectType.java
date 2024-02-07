package com.bacilika;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ObjectType {
    MOUNTAINS,
    FOREST,
    BEACH,
    CAVE,
    WOOD,
    STONE,
    HELP,
    ALL,
    AXE(new HashMap<>() {{
        put(ObjectType.WOOD, 2);
    }},null),
    HOUSE(new HashMap<>() {{
        put(ObjectType.WOOD, 50);
        put(ObjectType.STONE,20);
    }},null),
    PICKAXE(new HashMap<>() {{
        put(ObjectType.WOOD, 5);
    }},null);
    private ObjectType requiredTool;
    private HashMap<ObjectType,Integer> crafting;
    ObjectType(HashMap<ObjectType, Integer> crafting, ObjectType requiredTool){
        this.crafting = crafting;
        this.requiredTool = requiredTool;

    }
    ObjectType(){

    }
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public ObjectType getRequiredTool() {
        return requiredTool;
    }

    public HashMap<ObjectType, Integer> getCrafting() {
        return crafting;
    }
}
