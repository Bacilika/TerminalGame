package com.bacilika.Action;

import com.bacilika.Game;
import com.bacilika.InputAnalyzer;
import com.bacilika.ObjectType;

import java.sql.SQLOutput;

public class Explore extends AbstractAction {
    private ObjectType location;
    private InputAnalyzer analyzer;
    public Explore(ObjectType location, InputAnalyzer analyzer){
        super(ActionType.EXPLORE,location,location== null ? null : location.getRequiredTool() ,0,analyzer);
        this.location = location;
        this.analyzer = analyzer;

    }
    public void determineLocation(){
        if(location == null){
            System.out.println("What/where would you like to explore");

        }

    }


    @Override
    public int performAction(int amount, ObjectType objectType, ObjectType tool) {
        return 0;
    }

    @Override
    public void printHelp() {

    }
}
