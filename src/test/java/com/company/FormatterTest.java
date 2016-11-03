package com.company;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test for Formatter
 */
public class FormatterTest {

    private Formatter f;

    @Before
    public void setUp() throws IOException {
        f = new Formatter();
    }

    @Test
    public void format() throws Exception {
        f.format("input", "output");
        BufferedReader template = new BufferedReader(new FileReader("template"));
        BufferedReader result = new BufferedReader(new FileReader("output"));
        StringBuilder s2 = new StringBuilder();
        StringBuilder s1 = new StringBuilder();
        while (template.ready()) {
            s1.append(template.read());
        }
        while (result.ready()) {
            s2.append(result.read());
        }
        assertEquals("Format", s1.toString(), s2.toString());
    }

}