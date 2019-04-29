package com.company.test;

import com.company.Record;
import com.company.TextFileRecordsSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileRecordsSourceTest {
    StringBuilder error;

    @Before
    public void testInit(){
        error = new StringBuilder();
    }

    @Test
    public void parseOkRecord() {
        String test = "5\tA";
        Optional<Record> result = TextFileRecordsSource.parseRecord(test, error);
        assertTrue(result.isPresent());
    }

    @Test
    public void parseWrongRecord() {
        String test = "5A";
        Optional<Record> result = TextFileRecordsSource.parseRecord(test, error);
        assertTrue(result.isEmpty());
    }

    @Test
    public void parseWrongIdRecord() {
        String test = "-4\tA";
        Optional<Record> result = TextFileRecordsSource.parseRecord(test, error);
        assertTrue(result.isEmpty());
    }

    @Test
    public void parseWrongValueRecord() {
        String test = "-4\t";
        Optional<Record> result = TextFileRecordsSource.parseRecord(test, error);
        assertTrue(result.isEmpty());
    }
}