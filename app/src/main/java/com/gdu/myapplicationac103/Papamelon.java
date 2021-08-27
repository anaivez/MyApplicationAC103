package com.gdu.myapplicationac103;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by Czm on 2021/8/25 09:25.
 */
public class Papamelon {

    static Map<String, Integer> map = new HashMap<>();
    String[] list = new String[]{"papamelonhello",
            "papahellomelonpapahellomelon",
            "hellopapamelonhellopapa"};

    public static void main(String[] args) {
        map.put("h", 1);
        map.put("e", 2);
        map.put("l", 3);
        map.put("o", 2);
        map.put("p", 2);
        map.put("a", 2);
        map.put("m", 1);
        map.put("n", 1);

    }

    private String getPrint() {
        String def = "Oh,no!";
        for (String str : list) {
            for (String key : map.keySet()) {
                int size = str.split(key).length;
                if (map.get(key) != size) {
                    return "Oh,no!";
                }
            }
        }
        return "Hello Papamelon!";
    }


}
