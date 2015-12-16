/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Thomas
 */
public class OnzeBarcodeVerifier extends InputVerifier{

    @Override
    public boolean verify(JComponent input) {
        try {
            JTextField field = (JTextField) input;
            String text = field.getText();
            if(text.isEmpty() || text.length() != 13 || !(text.startsWith("540"))) {
                return false;
            }
            if(!(text.substring(3,4).equals("0") || text.substring(3,4).equals("1"))){
                return false;
            }
            if(!text.endsWith("00")){
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
}
