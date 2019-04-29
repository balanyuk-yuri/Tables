package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListJoiner extends Joiner{
    ArrayListJoiner(ArrayList<Record> first, ArrayList<Record> second){
        for (Record firstRec : first){
            for (Record secondRec : second){
                if (firstRec.getId() == secondRec.getId()){
                    result.add(new Record(firstRec.getId(), Stream.concat(firstRec.getValues(),
                            secondRec.getValues()).collect(Collectors.toList())));
                }
            }
        }
    }
}
