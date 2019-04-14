package com.tdt4240.catchgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameModes {

    private HashMap<String, ArrayList<Integer>> gameModeData = new HashMap<>();

    public GameModes(String difficulty, int baseSpeed, int baseFrequency) {
        ArrayList<Integer> difficultyStats = new ArrayList<>(Arrays.asList(baseSpeed, baseFrequency));
        gameModeData.put(difficulty, difficultyStats);
    }

    public ArrayList<Integer> getGameModeData(String difficulty) {
        ArrayList<Integer> difficultyStats = gameModeData.get(difficulty);
        return difficultyStats;
    }
}
