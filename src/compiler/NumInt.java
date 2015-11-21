package compiler;

public class NumInt extends Token{

    public final int value;
    
    public NumInt(int value) {
        super(Tag.NUM);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
    
}
