package jvsummer.domain;

import jvsummer.domain.suggestion.*;

public class SummerFactory {
    public SummerSuggestionImpl createSummerSuggestion() {
        return new SummerSuggestion(
            this.createSummerWordParser(),
            this.createSummerTrieHandler()
        );
    }

    private SummerWordParserImpl createSummerWordParser() {
        return new SummerWordParser();
    }

    private SummerTrieHandlerImpl createSummerTrieHandler() {
        return new SummerTrieHandler();
    }
}
