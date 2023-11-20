package jvsummer.domain.transfer;

public class NodeStringBuilderTransfer {
    public StringBuilder builder;
    public boolean containsInput;
    public int inputPosition;


    public NodeStringBuilderTransfer(
        String builder,
        int inputPosition,
        boolean containsInput
    ) {
        this.builder = new StringBuilder().append(builder);
        this.inputPosition = inputPosition;
        this.containsInput = containsInput;
    }
}
