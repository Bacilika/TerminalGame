package com.bacilika;

import com.bacilika.Action.ActionType;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String name;
    private int energy;
    private final Map<ObjectType,Integer> inventory;

    public Player(String name){
        this.name= name;
        inventory = new HashMap<>();
        System.out.println("Hello "+ name);
        energy = 20;
    }
    public int checkInventory(ObjectType item){

        return inventory.getOrDefault(item, 0);
    }
    public void viewInventory(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<ObjectType, Integer> set :
                inventory.entrySet()) {
            stringBuilder.append(set.getKey()).append(" = ").append(set.getValue());
            stringBuilder.append('\n');
        }
        System.out.println(stringBuilder);

    }
    public void addToInventory(ObjectType objectType,int amount){
        if (inventory.get(objectType) == null)
            inventory.put(objectType,amount);
        else
            inventory.replace(objectType,inventory.get(objectType)+amount);
    }
    public void removeFromInventory(ObjectType type, int amount){
        inventory.replace(type,inventory.get(type)-amount);
        if (inventory.get(type) == 0){
            inventory.remove(type);
        }
    }
    public void sleep(){
        System.out.println("Player went to bed");
        System.exit(0);
    }
    public void die(){
        System.out.println("player died");
        System.exit(0);
    }

    void printPlayerUI(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.copyValueOf(new char[]{'-'},0,20));
        System.out.println(stringBuilder);
    }


}
