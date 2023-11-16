package jvsummer;

import jvsummer.domain.SummerFactory;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class SummerTest {
    @Test
    public void testAbExample() {
        SummerFactory summerFactory = new SummerFactory();
        ArrayList<String> suggestions = summerFactory
                .createSummerSuggestion()
                .suggest("ab");

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("abolish");
        expected.add("abortion");
        expected.add("absence");
        expected.add("absent");
        expected.add("absorb");
        expected.add("abstract");
        expected.add("absurd");
        expected.add("abundance");
        expected.add("abuse");

        assertTrue(
                suggestions.size() == expected.size() &&
                        suggestions.containsAll(expected) && expected.containsAll(suggestions)
        );
    }
}
