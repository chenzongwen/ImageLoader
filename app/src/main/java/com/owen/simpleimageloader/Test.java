package com.owen.simpleimageloader;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Owen Chan
 * On 2017-10-25.
 */

public class Test {

    public static void main(String[] args) {

        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>(10, 0.75f, true);

        linkedHashMap.put(0, 0);
        linkedHashMap.put(1, 1);
        linkedHashMap.put(2, 2);
        linkedHashMap.put(3, 3);
        linkedHashMap.put(4, 4);
        linkedHashMap.get(1);
        linkedHashMap.get(2);
        linkedHashMap.put(5, 5);

        for (Map.Entry<Integer, Integer> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }


}
