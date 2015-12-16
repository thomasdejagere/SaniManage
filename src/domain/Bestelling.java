/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;

/**
 *
 * @author Thomas
 */
public class Bestelling {
    private Leverancier leverancier;
    private List<Product> producten;
    
    public void addProduct(Product product){
        producten.add(product);
    }
    public void removeProduct(Product product){
        producten.add(product);
    }
}
