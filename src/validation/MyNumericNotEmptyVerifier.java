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
public class MyNumericNotEmptyVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        try {
            if(!(((JTextField) input).getText().isEmpty()))
                Long.parseLong(((JTextField) input).getText());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

}
