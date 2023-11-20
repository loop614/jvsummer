package jvsummer.domain.suggestion;

import jvsummer.domain.transfer.TrieNodeTransfer;

import java.util.ArrayList;

public interface SummerTrieHandlerImpl {
    public ArrayList<String> getStringsContaining(TrieNodeTransfer node, String input);

    public void insertToTrie(TrieNodeTransfer root, String key);
}
