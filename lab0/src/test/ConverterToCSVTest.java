package test;

import main.ConverterToCSV;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConverterToCSVTest {

    @Test
    void convert_EmptyInput_ShouldWriteNothing() throws IOException {
        Reader reader = new StringReader("");
        StringWriter writer = new StringWriter();

        ConverterToCSV converter = new ConverterToCSV(reader, writer);
        converter.convert();

        assertEquals("", writer.toString());
    }

    @Test
    void convert_RepeatedWords_ShouldCountCorrectly() throws IOException {
        Reader reader = new StringReader("a a bb");
        StringWriter writer = new StringWriter();

        ConverterToCSV converter = new ConverterToCSV(reader, writer);
        converter.convert();

        String result = writer.toString();

        assertTrue(result.contains("bb;1;0,3333"));
        assertTrue(result.contains("a;2;0,6667"));
    }

    @Test
    void convert_LastWordWithoutSeparator_ShouldBeProcessed() throws IOException {
        Reader reader = new StringReader("one two two");
        StringWriter writer = new StringWriter();

        ConverterToCSV converter = new ConverterToCSV(reader, writer);
        converter.convert();

        String result = writer.toString();

        assertTrue(result.contains("one;1;0,3333"));
        assertTrue(result.contains("two;2;0,6667"));
    }
}
