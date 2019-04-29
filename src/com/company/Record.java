package com.company;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Record implements Comparable<Record> {
    private int id = 0;
    private List<String> values;

    Record(int id, String value){
        this.id = id;
        this.values = new ArrayList<String>();
        this.values.add(value);
    }

    Record(int id, Collection<String> values){
        this.id = id;
        this.values = values.stream().collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public Stream<String> getValues() {
        return values.stream();
    }

    public void print(PrintStream printer){
        printer.printf("%5d", id);
        values.forEach(val->printer.printf("%5s", val));
        printer.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return id == record.id &&
                values.equals(record.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, values);
    }

    @Override
    public int compareTo(Record o) {
        return Integer.compare(id, o.id);
    }
}
