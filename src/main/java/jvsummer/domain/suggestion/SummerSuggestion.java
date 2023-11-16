package jvsummer.domain.suggestion;

import jvsummer.domain.SummerConfig;
import jvsummer.domain.transfer.TrieNodeTransfer;
import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class SummerSuggestion implements SummerSuggestionImpl {
    @Override
    public ArrayList<String> suggest(String arg) {
        TrieNodeTransfer root = new TrieNodeTransfer();
        Optional<JSONArray> wordsOptional = getWords();
        if (wordsOptional.isEmpty()) {
            System.out.println("Internal error: Failed to parse dictionary");
            System.exit(1);
        }

        JSONArray words = wordsOptional.get();
        try {
            for (int i = 0; i < words.length(); i++) {
                this.insertToTrie(root, (String) words.get(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return this.takeNodesStartingWith(root, arg);
    }

    private ArrayList<String> takeNodesStartingWith(TrieNodeTransfer node, String input) {
        for (int i = 0; i < input.length(); i++) {
            char letter = input.charAt(i);
            int index = this.getIndexForLetter(letter);
            node = node.children[index];
        }
        if (node == null) {
            System.out.println("Internal error: Failed to find letters provided in the dictionary");
            System.exit(1);
        }

        ArrayList<String> result = new ArrayList<String>();
        this.takeWordsFromNode("", node, result);
        for (int i = 0; i < result.size(); i++) {
            result.set(i, input + result.get(i));
        }

        return result;
    }

    private ArrayList<String> takeWordsFromNode(String prefix, TrieNodeTransfer node, ArrayList<String> result) {
        if (node.isEndOfWord) {
            result.add(prefix);
            prefix = "";
        }

        for (int i = 0; i < SummerConfig.ALPHABET_SIZE; ++i) {
            if (node.children[i] == null) {
                continue;
            }
            String next = Character.toString((char) (i + 97));
            TrieNodeTransfer pChild = node.children[i];
            prefix += next;
            this.takeWordsFromNode(prefix, pChild, result);
            prefix = prefix.substring(0, prefix.length() - 1);
        }

        return result;
    }

    private void takeWordsFromNode(TrieNodeTransfer node, Map<String, String> result) {
        String letterKey = String.valueOf(node.letter);
        result.putIfAbsent(letterKey, letterKey);

        System.out.print(node.letter);
        for (int i = 0; i < SummerConfig.ALPHABET_SIZE; i++) {
            if (node.children[i] == null) {
                continue;
            }
            printTrie(node.children[i]);
        }
    }

    private void printTrie(TrieNodeTransfer node) {
        System.out.print(node.letter);
        for (int i = 0; i < SummerConfig.ALPHABET_SIZE; i++) {
            if (node.children[i] == null) {
                continue;
            }
            printTrie(node.children[i]);
        }
    }

    private static Optional<JSONArray> getWords() {
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

    private void insertToTrie(TrieNodeTransfer root, String key) {
        for (int level = 0; level < key.length(); level++) {
            char currentLetter = key.charAt(level);
            int index = this.getIndexForLetter(currentLetter);
            if (index < 0) {
                continue;
            }
            if (root.children[index] == null) {
                root.children[index] = new TrieNodeTransfer();
                root.children[index].letter = currentLetter;
            }
            root = root.children[index];
        }

        root.isEndOfWord = true;
    }

    private int getIndexForLetter(char letter) {
        int index = letter;
        if (index < 'a') {
            return -1;
        }

        return index - 'a';
    }
}
