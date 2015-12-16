package domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ListModel;

/**
 *
 * @author Thomas
 */
public class BarcodeController {
    private ProductenBeheer productenBeheer;
    private LeveranciersBeheer leveranciersBeheer;
    private GescandeProductenBeheer gescandeProductenBeheer;
    
    public BarcodeController() throws SQLException, IllegalStateException, ClassNotFoundException, IOException{
        productenBeheer = new ProductenBeheer();
        leveranciersBeheer = new LeveranciersBeheer();
        gescandeProductenBeheer = new GescandeProductenBeheer(leesIngescandeProducten(), productenBeheer);
    }
    
    public Map<Long, Integer> leesIngescandeProducten() throws IOException{
        List<String> lines = Files.readAllLines(Paths.get("C:/Users/Kantoor/Documents/vm_list/vm_list.csv"));
        ///Users/Kantoor/Documents/vm_list
        Map<Long, Integer> result = new HashMap<>();
        for(int i = 1; i < lines.size(); i++){
            String line = lines.get(i).replaceAll("\"", "");
            int index = line.indexOf(";");
            String barcode = line.substring(0,index);
            String echteBarcode = barcode.substring(0, barcode.length() - 2);
            echteBarcode += "00";
            Long l = Long.parseLong(echteBarcode);
            int aantal = Integer.parseInt(line.substring(index+1));
            result.put(l, aantal);
        }
        return result;
    }
    public String[] geefLeveranciers(String barcode){
        //return productenBeheer.geefLeveranciers(barcode);
        return gescandeProductenBeheer.geefLeveranciers(barcode);
    }
    
    public ListModel geefIngescandeBarcodes() throws SQLException, IllegalStateException, ClassNotFoundException{
        return gescandeProductenBeheer.geefBarcodeVanGescandeProducten();
    }
    
    public ListModel geefOmschrijvingenBarcodes(){
        return gescandeProductenBeheer.geefOmschrijvingVanGescandeProducten();
    }
    public ListModel geefStandaardLeverancierPerProduct(){
        return gescandeProductenBeheer.geefStandaardLeverancierPerProduct();
    }
    public List<Integer> geefAantallenPerProduct(){
        return gescandeProductenBeheer.geefAantallenPerProduct();
    }
    public void voegLeverancierToe(String naam) throws SQLException, ClassNotFoundException{
        leveranciersBeheer.add(naam);
    }
    public void pasProductAan(String barcode, String omschrijving, String leverancier) throws SQLException, IllegalStateException, ClassNotFoundException{
        Leverancier lev = leveranciersBeheer.geefLeverancier(leverancier);
        //productenBeheer.pasProductAan(barcode, omschrijving, lev);
    }
    public void kiesGewensteLeverancier(String barcode, String leverancier, boolean standaardLeverancier) throws SQLException, IllegalStateException, ClassNotFoundException{
        Leverancier lev = leveranciersBeheer.geefLeverancier(leverancier);
        gescandeProductenBeheer.kiesGewensteLeverancier(barcode, lev, standaardLeverancier);
    }

    public boolean heeftBarcodes(String barcode) {
        return gescandeProductenBeheer.heeftBarcodes(barcode);
    }

    public String[] geefLeveranciers() {
       return leveranciersBeheer.geefAlleLeveranciers();
    }

    public void voegBarcodeToe(String onzeBarcode, String nieuweBarcode, String lev, boolean standaardLev) throws SQLException, IllegalStateException, ClassNotFoundException {
        Leverancier leverancier = leveranciersBeheer.geefLeverancier(lev);
        gescandeProductenBeheer.voegBarcodeToe(onzeBarcode, nieuweBarcode, leverancier, standaardLev);
    }

    public boolean heeftMeerdereBarcodes(String barcode) {
        return gescandeProductenBeheer.heeftMeerdereBarcodes(barcode);
    }

    public Map<String, List<Product>> geefProductenPerBestelLeverancier() {
        return gescandeProductenBeheer.geefProductenPerBestelLeverancier();
    }

    public void pasAantalAan(String onzeBarcode, String nieuwAantal) {
        gescandeProductenBeheer.pasAantalAan(onzeBarcode, nieuwAantal);
    }

    public String geefOmschrijving(String onzeBarcode) {
        return gescandeProductenBeheer.geefOmschrijving(onzeBarcode);
    }

    public void pasProductAan(String oudeBarcode, String nieuweBarcode, String omschrijving, String standaardLeverancier) throws SQLException, IllegalStateException, ClassNotFoundException {
        Leverancier leverancier = leveranciersBeheer.geefLeverancier(standaardLeverancier);
        gescandeProductenBeheer.pasProductAan(oudeBarcode, nieuweBarcode, omschrijving, leverancier);
    }

//    public void verwijderLeverancier(String leverancier) throws SQLException, ClassNotFoundException {
//        Leverancier lev = leveranciersBeheer.remove(leverancier);
//        gescandeProductenBeheer.verwijderLeverancier(lev);
//        productenBeheer.verwijderLeverancier(lev);
//    }

    public boolean voegBestaandProductToe(String onzeBarcode, String aantal) {
            Product p = productenBeheer.geefProduct(Long.parseLong(onzeBarcode));
            if (p == null) {
                return false;
            }else{
                p.setAantalTeBestellen(Integer.parseInt(aantal));
                gescandeProductenBeheer.add(p);
                return true;
            }
    }

    public boolean voegNieuwProductToe(String onzeBarcode, String omschrijving, String aantal) throws ClassNotFoundException, SQLException {
        Product nieuwProduct = new Product(Long.parseLong(onzeBarcode), omschrijving);
        nieuwProduct.setAantalTeBestellen(Integer.parseInt(aantal));
        if(Integer.parseInt(aantal) > 0)
            gescandeProductenBeheer.add(nieuwProduct);
        return productenBeheer.add(nieuwProduct);
    }

    public void verwijderProductUitLijst(String onzeBarcode) {
        gescandeProductenBeheer.verwijderProductUitLijst(Long.parseLong(onzeBarcode));
    }

    public void voegAantallenAan() throws SQLException {
        gescandeProductenBeheer.voegAantallenAan();
    }

    public String geefStandaardLeverancier(String barcode) {
        return gescandeProductenBeheer.geefStandaardLeverancier(barcode);
    }

    public void verwijderBarcode(String onzeBarcode, String leverancier) throws SQLException, IllegalStateException, ClassNotFoundException {
        Leverancier l = leveranciersBeheer.geefLeverancier(leverancier);
        gescandeProductenBeheer.verwijderBarcode(onzeBarcode, l);
    }

    public String geefBestelLeverancier(String barcode) {
        return gescandeProductenBeheer.geefBestelLeverancier(barcode);
    }
}
