/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Product;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author Thomas
 */
public class BestelFrame extends JFrame {

    private JTextArea result;

    public BestelFrame(String leverancier, List<Product> productenPerLeverancier) {
        result = new JTextArea();
        for (Product p : productenPerLeverancier) {
            String barcode = p.geefBarcodeVanBestelLeverancier();
            if (barcode.equals("0")) {
                result.append(p.getOmschrijving() + " : aantal: " + p.getAantalTeBestellen());
            } else {
                result.append(p.geefBarcodeVanBestelLeverancier() + " : " + p.getOmschrijving() + " : aantal: " + p.getAantalTeBestellen());
            }
            result.append("\n");
        }
        add(result);
    }
}
