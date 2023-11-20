package jvsummer;

import jvsummer.domain.SummerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class SummerTest {
    @Test
    public void testAbExample() {
        SummerFactory summerFactory = new SummerFactory();
        ArrayList<String> suggestions = summerFactory
                .createSummerSuggestion()
                .suggest("ab");

        ArrayList<String> expected = this.getExpectedWordsFromFile();
        assertTrue(
    suggestions.size() == expected.size() &&
            suggestions.containsAll(expected) &&
            expected.containsAll(suggestions)
        );
    }

    private ArrayList<String> getExpectedWordsFromFile() {
        ArrayList<String> expected = new ArrayList<String>();
        Optional<JSONArray> wordsOptional = getWords();
        if (wordsOptional.isEmpty()) {
            System.out.println("Internal error: Failed to parse dictionary");
            System.exit(1);
        }

        JSONArray words = wordsOptional.get();
        try {
            for (int i = 0; i < words.length(); i++) {
                expected.add(words.get(i).toString());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return expected;
    }

    private static Optional<JSONArray> getWords() {
        String path = System.getProperty("user.dir") + "/src/test/java/jvsummer/data/ab_words.json";
        Path fileName = Path.of(path);
        String jsonString = "";
        try {
            jsonString = Files.readString(fileName);
        } catch (IOException e) {
            return Optional.empty();
        }

        JSONArray arr;
        try {
            JSONObject obj = new JSONObject(jsonString);
            arr = obj.getJSONArray("words");
        } catch (JSONException e) {
            return Optional.empty();
        }

        return Optional.of(arr);
    }
}
