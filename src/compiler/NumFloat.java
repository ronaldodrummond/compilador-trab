/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author pedrohbnp
 */
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
