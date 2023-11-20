package jvsummer.domain.suggestion;

import jvsummer.domain.SummerConfig;
import jvsummer.domain.transfer.NodeStringBuilderTransfer;
import jvsummer.domain.transfer.TrieNodeTransfer;

import java.util.ArrayList;

public class SummerTrieHandlerImpl implements SummerTrieHandler {
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
            input,
            node,
            new NodeStringBuilderTransfer("", 0, false),
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
        String input,
        TrieNodeTransfer node,
        NodeStringBuilderTransfer nodeBuilder,
        ArrayList<String> suggestions
    ) {
        for (TrieNodeTransfer child : node.children) {
            if (child == null) continue;
            NodeStringBuilderTransfer nodeBuilder2 = this.createNodeStringBuilderTransfer(nodeBuilder);
            nodeBuilder2.builder.append(child.letter);
            if (child.letter == input.charAt(nodeBuilder2.inputPosition))
            {
                if (nodeBuilder2.inputPosition == input.length() - 1) {
                    nodeBuilder2.containsInput = true;
                }
                if (nodeBuilder2.inputPosition < input.length() - 1) {
                    nodeBuilder2.inputPosition++;
                }
            }
            else {
                nodeBuilder2.inputPosition = 0;
            }

            if (child.isEndOfWord && nodeBuilder2.containsInput) {
                suggestions.add(nodeBuilder2.builder.toString());
                if (child.children.length > 0) {
                    this.takeOutSuggestions(input, child, nodeBuilder2, suggestions);
                }
                continue;
            }

            this.takeOutSuggestions(input, child, nodeBuilder2, suggestions);
        }
    }

    private NodeStringBuilderTransfer createNodeStringBuilderTransfer(NodeStringBuilderTransfer object)
    {
        return new NodeStringBuilderTransfer(
            object.builder.toString(),
            object.inputPosition,
            object.containsInput
        );
    }
}
