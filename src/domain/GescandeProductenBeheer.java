/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ListModel;

/**
 *
 * @author Thomas
 */
public class GescandeProductenBeheer extends ModelObservable<Product>{
    private List<Product> gescandeProducten;
    private ProductenBeheer productenBeheer;
    
    public GescandeProductenBeheer(Map<Long, Integer> barcodes, ProductenBeheer beheer) throws SQLException, IllegalStateException, ClassNotFoundException{
        productenBeheer = beheer;
        gescandeProducten = productenBeheer.geefOvereenkomstigeProducten(barcodes);
        
    }
    @Override
    public int aantal() {
        return gescandeProducten.size();
    }

    @Override
    public Product getAtIndex(int index) {
        return gescandeProducten.get(index);
    }
    public ListModel geefBarcodeVanGescandeProducten(){
        return new BarcodeListModel(this);
    }
    public ListModel geefOmschrijvingVanGescandeProducten(){
        return new OmschrijvingListModel(this);
    }
    public void kiesGewensteLeverancier(String barcode, Leverancier lev, boolean standaardLeverancier) throws SQLException, IllegalStateException, ClassNotFoundException {
        //MOET NOG ALLEMAAL EENS GOED BEKEKEN WORDEN IN VERBAND MET ZO VEEL NAAR DE DATABANK GAAN
        //DIT IS OOK RAAR DAT JE DAN UITEINDELIJK NOOIT NAAR DE LIJST VAN PRODUCTEN VERWIJST MAAR WEL ALTIJD DIRECT NAAR DE MAPPER
        if(standaardLeverancier){
        geefProduct(barcode).setStandaardLeverancier(lev);
        productenBeheer.pasProductAan(geefProduct(barcode));
        }
        else{
            geefProduct(barcode).setBestelLeverancier(lev);
        }
        setChanged();
    }

    public ListModel geefStandaardLeverancierPerProduct() {
        return new LeverancierPerProductListModel(this);
    }

    public boolean heeftBarcodes(String barcode) {
        return !(geefProduct(barcode).heeftGeenLeveranciers());
    }
    public Product geefProduct(String barcode){
        for(Product p : gescandeProducten){
            if(p.getOnzeBarcode().toString().equals(barcode)){
                return p; 
            }
        }
        return null;
    }

    public void voegBarcodeToe(String onzeBarcode, String nieuweBarcode, Leverancier leverancier, boolean standaardLev) throws SQLException, IllegalStateException, ClassNotFoundException {
        if(!(nieuweBarcode.isEmpty()))
            geefProduct(onzeBarcode).voegBarcodeToe(leverancier, Long.parseLong(nieuweBarcode));
        else
            geefProduct(onzeBarcode).voegBarcodeToe(leverancier);
        
        if(standaardLev)
        geefProduct(onzeBarcode).setStandaardLeverancier(leverancier);
        productenBeheer.pasProductAan(geefProduct(onzeBarcode));
    }

    public boolean heeftMeerdereBarcodes(String barcode) {
        return geefProduct(barcode).heeftMeerdereLeveranciers();
    }

    public List<Integer> geefAantallenPerProduct() {
        List<Integer> result = new ArrayList<>();
        gescandeProducten.stream().forEach((p) -> {
            result.add(p.getAantalTeBestellen());
        });
        return result;
    }

    public Map<String, List<Product>> geefProductenPerBestelLeverancier() {
        Map<String, List<Product>> productenPerLeverancier = new HashMap<>();
        for(Product p : gescandeProducten){
            if(productenPerLeverancier.containsKey(p.getBestelLeverancier())){
                productenPerLeverancier.get(p.getBestelLeverancier()).add(p);
            }
            else{
                List<Product> pr = new ArrayList<>();
                pr.add(p);
                productenPerLeverancier.put(p.getBestelLeverancier(), pr);
            }
        }
        return productenPerLeverancier;
    }

    public void pasAantalAan(String onzeBarcode, String nieuwAantal) {
        geefProduct(onzeBarcode).setAantalTeBestellen(Integer.parseInt(nieuwAantal));
    }

    public String geefOmschrijving(String onzeBarcode) {
        return geefProduct(onzeBarcode).getOmschrijving();
    }

    public void pasProductAan(String oudeBarcode, String nieuweBarcode, String omschrijving, Leverancier leverancier) throws SQLException, IllegalStateException, ClassNotFoundException {
        Product p = geefProduct(oudeBarcode);
        geefProduct(oudeBarcode).setOmschrijving(omschrijving);
        geefProduct(oudeBarcode).setStandaardLeverancier(leverancier);
        geefProduct(oudeBarcode).setOnzeBarcode(nieuweBarcode);
        productenBeheer.pasProductAan(p);
    }
    public void verwijderLeverancier(Leverancier lev) {
        for(Product p : gescandeProducten){
            p.verwijderLeverancier(lev);
        }
    }
    public void add(Product p) {
        gescandeProducten.add(p);
    }

    public void verwijderProductUitLijst(Long onzeBarcode) {
        for(Product p : gescandeProducten){
            if(Objects.equals(p.getOnzeBarcode(), onzeBarcode)){
                gescandeProducten.remove(p);
                break;
            }
        }
    }

    public void voegAantallenAan() throws SQLException {
        for(Product p : gescandeProducten){
            productenBeheer.voegAantallenAan(p);
        }
    }

    public String[] geefLeveranciers(String barcode) {
        return geefProduct(barcode).getLeveranciers();
    }

    public String geefStandaardLeverancier(String barcode) {
        Product p = geefProduct(barcode);
        return geefProduct(barcode).getStandaardLeverancier();
    }

    public void verwijderBarcode(String onzeBarcode, Leverancier l) throws SQLException, IllegalStateException, ClassNotFoundException {
        geefProduct(onzeBarcode).verwijderBarcode(l);
        productenBeheer.pasProductAan(geefProduct(onzeBarcode));
    }

    public String geefBestelLeverancier(String barcode) {
        Product p = geefProduct(barcode);
        return p.getBestelLeverancier();
    }
}
