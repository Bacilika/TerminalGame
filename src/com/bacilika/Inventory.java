package com.bacilika;

public class Inventory {
    int[] inventoryList = new int[200];
    String[] blockTypes = {"wood","stone","wood plank","crafting table","stick","iron","furnace","boat"};

    public void addBlockToInventory(String block, int amount) {
        for (int i = 1; i <= amount; i++) {
            ++inventoryList[convertBlockToID(block)];
        }
    }
    public void removeItemFromInventory(String block, int blocks) {
        for (int i = 0; i < blocks; i++) {
            --inventoryList[convertBlockToID(block)];
        }
    }
    public int convertBlockToID(String block){
        int blockID = 0;
        switch (block){
            case "wood" ->
                    blockID = 0;

            case "stone" ->
                    blockID = 1;

            case "wood plank" ->
                    blockID = 2;

            case "crafting table" ->
                    blockID = 3;

            case "stick" ->
                    blockID = 4;

            case "iron" ->
                    blockID = 5;
            case "furnace" ->
                    blockID = 6;
            case "boat" ->
                    blockID = 7;
        }
        return blockID;
    }

}



