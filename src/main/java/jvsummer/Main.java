package jvsummer;

import jvsummer.domain.SummerFactory;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide one String argument");
            System.exit(1);
        }

        SummerFactory summerFactory = new SummerFactory();
        ArrayList<String> suggestions = summerFactory
            .createSummerSuggestion()
            .suggest(args[0]);

        System.out.println(suggestions.toString());
    }
}
