/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author Thomas
 */
@Entity
@NamedQuery(name = "Leverancier.findAll", query = "SELECT l FROM Leverancier l")
public class Leverancier implements Serializable {
      private String naam;
      @Id
      @GeneratedValue
      private int code;

    public Leverancier() {
    }
      
      public Leverancier(String naam){
          this.naam = naam;
      }

    public String getNaam() {
        return naam;
    }
    //MOET WEG ALS DATABANK WERKT!
    public Leverancier(String naam, int code){
        this.naam = naam;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public String toString(){
        return getCode() + " " + getNaam();
    }
}
