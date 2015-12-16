/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import domain.Leverancier;
import domain.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistentie.JPAUtil;

/**
 *
 * @author Thomas
 */
public class ProductDAO {
    private static EntityManagerFactory emf;
    private EntityManager em;

    public ProductDAO() {
        emf = JPAUtil.getEntityManagerFactory();
    }

    public void addProduct(Product product) {
        em.persist(product);
    }

    public List<Product> findAll() throws SQLException {
        List<Product> producten = new ArrayList();
        Connection con = DriverManager.getConnection("jdbc:sqlserver://192.168.1.2:1433;databaseName=BarcodeSystemSV", "sanivervaeck", "d56jhx023");
        Statement statement = con.createStatement();
        String s = "SELECT * FROM PRODUCT";
        String barcodes = "SELECT * FROM BarcodeSystemSV.dbo.barcode_leverancier WHERE Product_ID = ?";
        String leveranciers = "SELECT * FROM LEVERANCIER";
        ResultSet leveranciersResult = statement.executeQuery(leveranciers);
        List<Leverancier> leveranciersList = new ArrayList();
        while(leveranciersResult.next()){
            Leverancier l = new Leverancier();
            l.setCode(leveranciersResult.getInt("CODE"));
            l.setNaam(leveranciersResult.getString("NAAM"));
            leveranciersList.add(l);
        }
        ResultSet rs = statement.executeQuery(s);
        while(rs.next()){
            Product p = new Product();
            p.setId(rs.getInt("ID"));
            p.setOmschrijving(rs.getString("OMSCHRIJVING"));
            p.setOnzeBarcode(rs.getString("ONZEBARCODE"));
            if(rs.getInt("STANDAARDLEVERANCIER_CODE") != 0){
                 p.setStandaardLeverancier(leveranciersList.get(rs.getInt("STANDAARDLEVERANCIER_CODE")-1));
            }
            
            PreparedStatement stat = con.prepareStatement(barcodes);
            stat.setString(1, Integer.toString(p.getId()));
            ResultSet barcodesLeverancier = stat.executeQuery();
            while(barcodesLeverancier.next()){
                p.voegBarcodeToe(leveranciersList.get(barcodesLeverancier.getInt("barcodes_KEY")-1), barcodesLeverancier.getLong("BARCODES"));
            }
            producten.add(p);
        }
        try{
            
        }catch(Exception e){
            
        }
        return producten;
        
    }

    public void update(Product product) {
        em.merge(product);
    }

    public void delete(Product product) {
        em.remove(product);
    }

    public void startTransaction() {
        if (em==null||!em.isOpen()) {
            em = emf.createEntityManager();
        }
        em.getTransaction().begin();
    }

    public void saveChanges() {
        em.getTransaction().commit();
        em.close();
    }

    public void stopTransaction() {
        em.close();
    }

    public void voegAantallenAan(int id, int aantalTeBestellen) throws SQLException {
       Connection con = DriverManager.getConnection("jdbc:sqlserver://192.168.1.2:1433;databaseName=BarcodeSystemSV", "sanivervaeck", "d56jhx023");
       Statement statement = con.createStatement();
       PreparedStatement stat = con.prepareStatement("SELECT AANTAL_KEER_BESTELD FROM PRODUCT WHERE ID = ?");
       stat.setInt(1, id);
       ResultSet result = stat.executeQuery();
       int aantalBestellingen = 0;
       if(result.next())
       aantalBestellingen = result.getInt("AANTAL_KEER_BESTELD");
       PreparedStatement voegAantalToe = con.prepareStatement("UPDATE PRODUCT SET AANTAL_KEER_BESTELD = ? WHERE ID = ?");
       voegAantalToe.setInt(1, aantalTeBestellen + aantalBestellingen);
       voegAantalToe.setInt(2, id);
       voegAantalToe.execute();
    }
}
