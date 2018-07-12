package com.geekbrains.weather;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DataBase {
    private static DataBase instance;
    private ArrayList<String> arrayList;

    private DataBase(){
        createArray();
    }

    public static DataBase getInstance(){
        if (instance == null){
            instance = new DataBase();
        }
        return instance;
    }

    private void createArray(){
        arrayList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            arrayList.add(ThreadLocalRandom.current().nextInt(-30, 30) + "\u00B0C");
        }
    }

    public ArrayList getTempHistory(){
        return arrayList;
    }
}
