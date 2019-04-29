package com.company;

import java.util.stream.Stream;

public interface RecordsSource {
    Stream<Record> records();
    boolean isOk();
}
