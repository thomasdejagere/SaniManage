/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import service.LeverancierService;

/**
 *
 * @author Thomas
 */
public class LeveranciersBeheer extends ModelObservable<Leverancier>{

    private List<Leverancier> leveranciers = new ArrayList<Leverancier>();
    //private Mapper<Leverancier> leverancierMapper = MapperFactory.getInstance().getMapper(Leverancier.class);
    private LeverancierService leverancierService = new LeverancierService();
    public LeveranciersBeheer() throws SQLException, IllegalStateException, ClassNotFoundException{
        leveranciers = leverancierService.findAll();
    }
    @Override
    public int aantal() {
        return leveranciers.size();
    }

    @Override
    public Leverancier getAtIndex(int index) {
        return leveranciers.get(index);
    }
    public ListModel geefLeveranciers() throws SQLException, IllegalStateException, ClassNotFoundException{
        leveranciers = leverancierService.findAll();
        return new LeverancierListModel(this);
    }
    public String[] geefAlleLeveranciers(){
        String[] result = new String[leveranciers.size()];
        int index = 0;
        for(Leverancier l : leveranciers){
            result[index] = l.getNaam();
            index++;
        }
        return result;
    }
    public void add(String naam) throws SQLException, ClassNotFoundException{
        Leverancier leverancier = new Leverancier(naam);
        leverancierService.add(leverancier);
        leveranciers = leverancierService.findAll();
    }

    public Leverancier geefLeverancier(String leverancier) {
        if(leverancier.contains(":")){
        int index = leverancier.indexOf(":");
        leverancier = leverancier.substring(0, index - 1);
        }
        for(Leverancier lev : leveranciers){
            String leveranciersNaam = lev.getNaam();
            if(leveranciersNaam.equals(leverancier))
                return lev;
        }
        return null;
        
    }

    public Leverancier remove(String lev) throws SQLException, ClassNotFoundException {
        
        for(Leverancier leverancier : leveranciers){
            if(leverancier.getNaam().equals(lev)){
                leverancierService.delete(leverancier);
                return leverancier;
            }
            
        }
        return null;
    }
}
