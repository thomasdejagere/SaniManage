/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.ProductDAO;
import domain.Leverancier;
import domain.Product;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class ProductService {
        private static ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }
    
    public void add(Product product) throws SQLException {
        productDAO.startTransaction();
        productDAO.addProduct(product);
        productDAO.saveChanges();
    }

    public List<Product > findAll() throws SQLException {
        productDAO.startTransaction();
        List<Product> producten = productDAO.findAll();
        productDAO.stopTransaction();
        return producten;
    }
    public void update(Product p) {
        productDAO.startTransaction();
        productDAO.update(p);
        productDAO.saveChanges();
    }

    public void delete(Product product) {
        productDAO.startTransaction();
        productDAO.delete(product);
        productDAO.saveChanges();
    }

    public void voegAantallenAan(int id, int aantalTeBestellen) throws SQLException {
        productDAO.voegAantallenAan(id, aantalTeBestellen);
    }
}
