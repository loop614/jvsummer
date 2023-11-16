package jvsummer.domain;

import jvsummer.domain.suggestion.SummerSuggestion;
import jvsummer.domain.suggestion.SummerSuggestionImpl;

public class SummerFactory {
    public SummerSuggestionImpl createSummerSuggestion() {
        return new SummerSuggestion();
    }
}
