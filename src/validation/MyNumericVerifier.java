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
public class MyNumericVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        try {
            JTextField field = (JTextField) input;
            String text = field.getText();
            if(field.getText().isEmpty()) {
                return false;
            } else {
            }
            Long.parseLong(((JTextField) input).getText());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

}
