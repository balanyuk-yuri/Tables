package com.company;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        if (args.length != 3){
            System.out.println("В аргументах укажите пути до 2 входных и одного выходного файла");
            return;
        }

        RecordsSource firstSource = new TextFileRecordsSource(args[0]);
        if (!firstSource.isOk()){
            return;
        }

        RecordsSource secondSource = new TextFileRecordsSource(args[1]);
        if (!secondSource.isOk()){
            return;
        }

        Joiner joiner1 = new ArrayListJoiner(new ArrayList<Record>(firstSource.records().collect(Collectors.toList())),
                new ArrayList<Record>(secondSource.records().collect(Collectors.toList())));

        Joiner joiner2 = new LinkedListJoiner(new LinkedList<Record>(firstSource.records().collect(Collectors.toList())),
                new LinkedList<Record>(secondSource.records().collect(Collectors.toList())));

        Joiner joiner3 = new HashMapJoiner(hashFromStream(firstSource.records()), hashFromStream(secondSource.records()));

        List<Record> list1 = joiner1.join().collect(Collectors.toList());
        list1.sort(Record::compareTo);
        List<Record> list2 = joiner2.join().collect(Collectors.toList());
        list2.sort(Record::compareTo);
        List<Record> list3 = joiner3.join().collect(Collectors.toList());
        list3.sort(Record::compareTo);

        if (!list1.equals(list2) || !list2.equals(list3)){
            System.out.println("Результаты соединений не равны");
            return;
        }

        try {
            PrintStream printer = new PrintStream(args[2]);
            printer.printf("%5s\t%s.value\t%s.value%n", "ID", args[0], args[1]);
            list1.forEach(rec->rec.print(printer));
        } catch (FileNotFoundException e) {
            System.out.printf("Не удается открыть файл %s для записи%n", args[2]);
            return;
        }
    }

    public static HashMap<Integer, List<Record>> hashFromStream(Stream<Record> stream){
        HashMap<Integer, List<Record>> res = new HashMap<>();
        stream.forEach(rec -> {
            if (!res.containsKey(rec.getId())) {
                res.put(rec.getId(), List.of(rec));
            }
            else {
                List<Record> oldList = res.get(rec.getId());
                List<Record> newList = new ArrayList<>(oldList);
                newList.add(rec);
                res.put(rec.getId(), newList);
            }
        });

        return res;
    }
}
