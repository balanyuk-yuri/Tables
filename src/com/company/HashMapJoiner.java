package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashMapJoiner extends Joiner {
    HashMapJoiner(HashMap<Integer, List<Record>> first, HashMap<Integer, List<Record>> second){
        for (Map.Entry<Integer, List<Record>> firstEntry : first.entrySet()){
            if (second.containsKey(firstEntry.getKey())){
                for (Record fRec : firstEntry.getValue()){
                    for (Record sRec : second.get(firstEntry.getKey())){
                        result.add(new Record(fRec.getId(), Stream.concat(fRec.getValues(),
                                sRec.getValues()).collect(Collectors.toList())));
                    }
                }
            }
        }
    }
}
