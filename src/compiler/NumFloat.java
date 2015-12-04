package compiler;

public class NumFloat extends Token{
     public final float value;
    
    public NumFloat(float value) {
        super(Tag.FLOAT);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
}
