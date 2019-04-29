package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedListJoiner extends Joiner {
    LinkedListJoiner(LinkedList<Record> first, LinkedList<Record> second){
        if (first.isEmpty() || second.isEmpty()){
            return;
        }

        first.sort(Record::compareTo);
        second.sort(Record::compareTo);

        ListIterator<Record> fit = first.listIterator();
        ListIterator<Record> sit = second.listIterator();
        Record firstRec = fit.next();
        Record secondRec = sit.next();

        do {
            switch (firstRec.compareTo(secondRec)){
                case -1:
                    if (fit.hasNext()){
                        firstRec = fit.next();
                    } else {
                        return;
                    }
                    break;
                case 1:
                    if (sit.hasNext()){
                        secondRec = sit.next();
                    } else {
                        return;
                    }
                    break;
                case 0:
                default:
                    List<Record> firstSameRecords = new ArrayList<>();
                    Record oldFirstRec = firstRec;
                    while (firstRec.compareTo(oldFirstRec) == 0){
                        firstSameRecords.add(firstRec);
                        oldFirstRec = firstRec;
                        if (!fit.hasNext()){
                            break;
                        }
                        firstRec = fit.next();
                    }

                    List<Record> secondSameRecords = new ArrayList<>();
                    Record oldSecondRec = secondRec;
                    while (secondRec.compareTo(oldSecondRec) == 0){
                        secondSameRecords.add(secondRec);
                        oldSecondRec = secondRec;
                        if (!sit.hasNext()){
                            break;
                        }
                        secondRec = sit.next();
                    }

                    processTheSame(firstSameRecords, secondSameRecords);

                    break;
            }
        } while (fit.hasNext() || sit.hasNext());
    }

    private void processTheSame(List<Record> first, List<Record> second){
        for (Record firstRec : first){
            for (Record secondRec : second){
                    result.add(new Record(firstRec.getId(), Stream.concat(firstRec.getValues(),
                            secondRec.getValues()).collect(Collectors.toList())));
            }
        }
    }
}
