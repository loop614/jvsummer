package jvsummer.domain.transfer;

import jvsummer.domain.SummerConfig;

public class TrieNodeTransfer
{
    public TrieNodeTransfer[] children = new TrieNodeTransfer[SummerConfig.ALPHABET_SIZE];

    public char letter = '!';

    public boolean isEndOfWord;
}
