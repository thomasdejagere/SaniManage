/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import service.ProductService;

/**
 *
 * @author Thomas
 */
public class ProductenBeheer extends ModelObservable<Product>{
    private List<Product> producten;
    
    private ProductService productService = new ProductService();
    public ProductenBeheer() throws SQLException, IllegalStateException, ClassNotFoundException{
        producten = productService.findAll();
    }
    @Override
    public int aantal() {
        return producten.size();
    }

    @Override
    public Product getAtIndex(int index) {
        return producten.get(index);
    }
    
    public List<Product> geefOvereenkomstigeProducten(Map<Long, Integer> pd){
        List<Product> resulterendeProducten = new ArrayList<Product>();
        Set<Long> keySet = pd.keySet();
        
        for(Product p : producten){
            for(Long l : keySet){
                if(Objects.equals(p.getOnzeBarcode(), l)){
                    p.setAantalTeBestellen(pd.get(l));
                    resulterendeProducten.add(p);
                    pd.remove(l);
                    break;
                }
            }
        }
        //CONTROLEREN OF EEN BARCODE NIET GEKEND IS IN HET SYSTEEM:
        if(!(pd.isEmpty())){
            System.out.printf("BARCODE NIET GEKEND");
        }
        return resulterendeProducten;
    }
    public void pasProductAan(Product p) throws SQLException, IllegalStateException, ClassNotFoundException{
        productService.update(p);
    }
//    public String[] geefLeveranciers(Long barcode){
//        for(Product product : producten){
//            if(barcode.equals(product.getOnzeBarcode()))
//                return product.getLeveranciers();
//        }
//        return null;
//    }

    public void verwijderLeverancier(Leverancier lev) {
        for(Product product : producten){
            product.verwijderLeverancier(lev);
        }
    }

    public Product geefProduct(Long onzeBarcode) {
        for(Product product : producten){
            if(Objects.equals(product.getOnzeBarcode(), onzeBarcode)) {
                return product;
            } else {
                //qfdqsdf
            }
        }
        return null;
    }

    public boolean add(Product nieuwProduct) throws ClassNotFoundException, SQLException {
        boolean exists = true;
        for(Product product : producten){
            if(product.getOnzeBarcode().compareTo(nieuwProduct.getOnzeBarcode()) == 0){
                exists = false;
                break;
            }
        }
        if(exists){
            productService.add(nieuwProduct);
        }
        return exists;
    }

    public void voegAantallenAan(Product p) throws SQLException {
        productService.voegAantallenAan(p.getId(), p.getAantalTeBestellen());
    }
}
