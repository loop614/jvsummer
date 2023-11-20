package jvsummer.domain.suggestion;

import jvsummer.domain.transfer.TrieNodeTransfer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SummerSuggestionImpl implements SummerSuggestion {
    private final SummerWordParser summerWordParser;
    private final SummerTrieHandler summerTrieHandler;

    public SummerSuggestionImpl(
        SummerWordParser summerWordParser,
        SummerTrieHandler summerTrieHandler
    ) {
        this.summerWordParser = summerWordParser;
        this.summerTrieHandler = summerTrieHandler;
    }

    @Override
    public ArrayList<String> suggest(String arg) {
        TrieNodeTransfer root = new TrieNodeTransfer();
        JSONArray words = this.summerWordParser.getWords();
        try {
            for (int i = 0; i < words.length(); i++) {
                this.summerTrieHandler.insertToTrie(root, words.get(i).toString());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return this.summerTrieHandler.getStringsContaining(root, arg);
    }
}
