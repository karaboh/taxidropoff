package com.example.peter.taxidropoff;

import java.util.HashMap;

/**
 * Created by Peter on 2016-04-27.
 */
public class TaxiDBDAO {


public static HashMap<String,String[]> login(String username){
boolean userExists = false;

userExists=true;
    return userExists;
}

    public static HashMap<String,String[]> findChildTelephoneNumbers(String username){

        HashMap<String,String[]> childDetMap = new HashMap<String,String[]>();
        //id ->> {child name, tel}
        childDetMap.put("1",new String[]{"Zodwa","715752619"});
        childDetMap.put("2",new String[]{"Abel","715752619"});
        childDetMap.put("3",new String[]{"Peter","715752619"});
        childDetMap.put("4",new String[]{"Nathi","715752619"});
        childDetMap.put("5",new String[]{"Andile Zulu","847716671"});
        childDetMap.put("6",new String[]{"Karabo Hlongwane","847716671"});
        childDetMap.put("7",new String[]{"Paless Dikole","847716671"});
        childDetMap.put("8",new String[]{"Jim van der Merwe","847716671"});
        childDetMap.put("9",new String[]{"Thibos","847716671"});


        return childDetMap;
    }
}
