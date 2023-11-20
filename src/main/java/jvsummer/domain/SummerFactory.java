package jvsummer.domain;

import jvsummer.domain.suggestion.*;

public class SummerFactory {
    public SummerSuggestion createSummerSuggestion() {
        return new SummerSuggestionImpl(
            this.createSummerWordParser(),
            this.createSummerTrieHandler()
        );
    }

    private SummerWordParser createSummerWordParser() {
        return new SummerWordParserImpl();
    }

    private SummerTrieHandler createSummerTrieHandler() {
        return new SummerTrieHandlerImpl();
    }
}
