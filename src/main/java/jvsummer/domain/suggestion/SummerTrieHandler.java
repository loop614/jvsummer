package jvsummer.domain.suggestion;

import jvsummer.domain.SummerConfig;
import jvsummer.domain.transfer.NodeStringBuilderTransfer;
import jvsummer.domain.transfer.TrieNodeTransfer;

import java.util.ArrayList;

public class SummerTrieHandler implements SummerTrieHandlerImpl {
    public void insertToTrie(TrieNodeTransfer root, String word) {
        for (int letterIndex = 0; letterIndex < word.length(); letterIndex++) {
            char currentLetter = word.charAt(letterIndex);
            int index = this.getIndexForLetter(currentLetter);
            if (index < 0) {
                continue;
            }
            if (root.children[index] == null) {
                root.children[index] = new TrieNodeTransfer();
                root.children[index].isEndOfWord = letterIndex == word.length() - 1;
                root.children[index].letter = currentLetter;
            }
            root = root.children[index];
        }
    }

    public ArrayList<String> getStringsContaining(TrieNodeTransfer node, String input) {
        ArrayList<String> suggestions = new ArrayList<String>();
        this.takeOutSuggestions(
            node,
            new NodeStringBuilderTransfer(),
            false,
            suggestions
        );

        return suggestions;
    }

    private int getIndexForLetter(char letter) {
        int index = letter;
        if (index == ' ') {
            return SummerConfig.ALPHABET_SIZE - 1;
        }

        if (index < 'a') {
            return -1;
        }

        return index - 'a';
    }

    private void takeOutSuggestions(
        TrieNodeTransfer node,
        NodeStringBuilderTransfer nodeBuilder,
        boolean wasA,
        ArrayList<String> suggestions
    ) {
        for (TrieNodeTransfer child : node.children) {
            if (child == null) continue;
            NodeStringBuilderTransfer nodeBuilder2 = new NodeStringBuilderTransfer();
            nodeBuilder2.builder.append(nodeBuilder.builder.toString());
            nodeBuilder2.containsAb = nodeBuilder.containsAb;
            nodeBuilder2.builder.append(child.letter);
            if (child.letter == 'b' && wasA) {
                nodeBuilder2.containsAb = true;
            }
            if (child.isEndOfWord && nodeBuilder2.containsAb) {
                suggestions.add(nodeBuilder2.builder.toString());
                if (child.children.length > 0) {
                    this.takeOutSuggestions(child, nodeBuilder2, child.letter == 'a', suggestions);
                }
                continue;
            }

            this.takeOutSuggestions(child, nodeBuilder2, child.letter == 'a', suggestions);
        }
    }
}
