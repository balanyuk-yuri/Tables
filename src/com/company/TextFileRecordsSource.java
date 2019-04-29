package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class TextFileRecordsSource implements RecordsSource {
    List<Record> records = new ArrayList<>();
    boolean isOk = true;

    TextFileRecordsSource(String filepath) {
        try (Scanner scanner = new Scanner(new FileInputStream(filepath))) {
            for (long lineNumber = 1; scanner.hasNextLine(); ++lineNumber) {
                String str = scanner.nextLine();
                StringBuilder error = new StringBuilder();
                Optional<Record> newRec = parseRecord(str, error);
                if (newRec.isEmpty()){
                    System.out.println("Не удалось прочитать строку номер №" + lineNumber + " : " + error.toString());
                    continue;
                }

                records.add(newRec.get());
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Не удалось открыть файл %s на чтение%n", filepath);
            isOk = false;
        }

    }

    @Override
    public Stream<Record> records() {
        return records.stream();
    }

    @Override
    public boolean isOk() {
        return isOk;
    }

    public static Optional<Record> parseRecord(String str, StringBuilder error) {
        String[] strList = str.split("\t");
        if (strList.length != 2) {
            error.append("Строка должна содержать одну табуляцию");
            return Optional.empty();
        }

        int id = 0;
        try {
            id = Integer.parseInt(strList[0]);
            if (id < 0){
                throw new Exception();
            }
        } catch (Exception e){
            error.append("Id должен быть целым неотрицательным числом");
            return Optional.empty();
        }

        String value = strList[1];
        if (value.isEmpty()){
            error.append("Поле значения пусто");
            return Optional.empty();
        }

        return Optional.of(new Record(id, value));
    }
}