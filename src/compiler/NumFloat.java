package compiler;

public class NumFloat extends Token{
     public final float value;
    
    public NumFloat(float value) {
        super(Tag.NUM);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
}
