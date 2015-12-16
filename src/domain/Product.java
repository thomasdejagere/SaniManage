/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 *
 * @author Thomas
 */
@Entity
//@NamedQuery(name = "Product.findAll", query = "SELECT p FROM product p")
public class Product implements Serializable {
    @Id 
    @GeneratedValue
    private int id;
    @ElementCollection
    @CollectionTable(name="barcode_leverancier")
    @MapKeyColumn(name = "artikel_leverancier")
    private Map<Leverancier, Long> barcodes;
    private Long onzeBarcode;
    private String omschrijving;
    @ManyToOne
    private Leverancier standaardLeverancier;
    @Transient
    private Leverancier bestelLeverancier;
    @Transient
    private Integer aantalTeBestellen;
    

    public Product() {
        barcodes = new HashMap<>();
    }

    public Product(Long onzeBarcode, String omschrijving) {
        this.onzeBarcode = onzeBarcode;
        this.omschrijving = omschrijving;
        barcodes = new HashMap<>();
    }

    public void setStandaardLeverancier(Leverancier lev) {
        //eerst controleren of leverancier in de set zit!
        standaardLeverancier = lev;
        if (bestelLeverancier == null) {
            bestelLeverancier = lev;
        }

    }

    //geef barcode van bestelleverancier

    public String geefBarcodeVanBestelLeverancier() {
        return barcodes.get(bestelLeverancier).toString();
    }

    public void setBestelLeverancier(Leverancier lev) {
        bestelLeverancier = lev;
    }

    public String getBestelLeverancier() {
        if (bestelLeverancier == null) {
            return "";
        } else {
            return bestelLeverancier.getNaam();
        }

    }

    public void voegBarcodeToe(Leverancier lev, Long code) {
        if(standaardLeverancier == null)
            setStandaardLeverancier(lev);
        barcodes.put(lev, code);
    }
    public void voegBarcodeToe(Leverancier leverancier) {
        if(standaardLeverancier == null)
            setStandaardLeverancier(leverancier);
        barcodes.put(leverancier, new Long(0));
    }
    public Long getOnzeBarcode() {
        return onzeBarcode;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public String geefBarcodePerLeverancier(Leverancier lev) {
        //HIER KAN EEN NULLEXCEPTION ONSTAAN OF EEN OF ANDERE EXCEPTIE ALS HIJ EEN ONGEKENDE LEVERANCIER OPZOEKT 
        Long barcode = barcodes.get(lev);
        //HIER NIET ZEKER OF IK MOET CONTROLLEREN OF HET EEN 0 OF EEN NULL IS ALS HIJ DE LEVERANCIER NIET KENT!
        return (barcode == 0) ? "N.V.T." : barcode.toString();
    }

    public boolean heeftGeenLeveranciers() {
        return (barcodes.isEmpty());
    }

    public String[] getLeveranciers() {
        Set<Leverancier> leveranciers = barcodes.keySet();
        String[] leveranciersString = new String[leveranciers.size()];
        int index = 0;
        for (Leverancier l : leveranciers) {
            leveranciersString[index] = l.getNaam() + " : " + barcodes.get(l).toString();
            index++;
        }
        return leveranciersString;
    }

    public boolean heeftMeerdereLeveranciers() {
        return (barcodes.size() >= 2);
    }

    public Integer getAantalTeBestellen() {
        return aantalTeBestellen;
    }

    public void setAantalTeBestellen(Integer aantalTeBestellen) {
        this.aantalTeBestellen = aantalTeBestellen;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public void setOnzeBarcode(String nieuweBarcode) {
        this.onzeBarcode = Long.parseLong(nieuweBarcode);
    }

    public void verwijderLeverancier(Leverancier lev) {
        for (Iterator<Map.Entry<Leverancier, Long>> it = barcodes.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Leverancier, Long> entry = it.next();
            if (entry.getKey().getNaam().equals(lev.getNaam())) {
                if(entry.getKey().getNaam().equals(standaardLeverancier.getNaam())){
                    setBestelLeverancier(null);
                    setStandaardLeverancier(null);
                }
                it.remove();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStandaardLeverancier() {
        return standaardLeverancier.getNaam();
        
    }

    public void verwijderBarcode(Leverancier l) {
        Set<Leverancier> lev = barcodes.keySet();
        for(Leverancier la : lev){
            if(la.getCode() == l.getCode()){
                barcodes.remove(la);
                break;
            }
        }
        if (bestelLeverancier.getCode() == l.getCode() && standaardLeverancier.getCode() != l.getCode()) {
            bestelLeverancier = standaardLeverancier;
        }
        if (standaardLeverancier.getCode() == l.getCode() && barcodes.isEmpty()) {
            setStandaardLeverancier(null);
            setBestelLeverancier(null);
        } else {
            if (standaardLeverancier.getCode() == l.getCode() && !(barcodes.isEmpty())) {
                Set<Leverancier> levs = barcodes.keySet();
                if(bestelLeverancier.getCode() == l.getCode()){
                    setBestelLeverancier(null);
                }
                setStandaardLeverancier(barcodes.keySet().iterator().next());
            }
        }

    }

}
