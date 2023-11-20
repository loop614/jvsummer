package jvsummer.domain.suggestion;

import jvsummer.domain.SummerConfig;
import jvsummer.domain.transfer.TrieNodeTransfer;

import java.util.ArrayList;

public class SummerTrieHandler implements SummerTrieHandlerImpl {
    public ArrayList<String> getMatchingStrings(TrieNodeTransfer node, String input) {
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

    public void insertToTrie(TrieNodeTransfer root, String key) {
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

    private void takeWordsFromNode(String prefix, TrieNodeTransfer node, ArrayList<String> result) {
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
    }

    private int getIndexForLetter(char letter) {
        int index = letter;
        if (index < 'a') {
            return -1;
        }

        return index - 'a';
    }
}
