package jvsummer.domain.suggestion;

import jvsummer.domain.SummerConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class SummerWordParserImpl implements SummerWordParser {
    public JSONArray getWords() {
        Optional<JSONArray> wordsOptional = this.getJsonWords();
        if (wordsOptional.isEmpty()) {
            System.out.println("Internal error: Failed to parse dictionary");
            System.exit(1);
        }

        return wordsOptional.get();
    }

    private Optional<JSONArray> getJsonWords() {
        Path fileName = Path.of(SummerConfig.WORDS_PATH);
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
