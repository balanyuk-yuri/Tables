package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class Joiner {
    protected List<Record> result = new ArrayList<>();

    public Stream<Record> join(){
        return result.stream();
    }
}
