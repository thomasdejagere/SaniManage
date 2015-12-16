/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.LeverancierDAO;
import domain.Leverancier;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Thomas
 */
public class LeverancierService {
    private static LeverancierDAO leverancierDAO;

    public LeverancierService() {
        leverancierDAO = new LeverancierDAO();
    }
    
    public void add(Leverancier leverancier) {
        leverancierDAO.startTransaction();
        Leverancier l = leverancierDAO.findBy(leverancier.getCode());
        if(l==null){
        leverancierDAO.addLeverancier(leverancier);
        leverancierDAO.saveChanges();
        } else{
            leverancierDAO.stopTransaction();
            throw new IllegalArgumentException("Leverancier bestaat al.");
        }
    }

    public List<Leverancier> findAll() {
        leverancierDAO.startTransaction();
        List<Leverancier> leveranciers = leverancierDAO.findAll();
        leverancierDAO.stopTransaction();
        return leveranciers;
    }
    public void update(Leverancier leverancier) {
        leverancierDAO.startTransaction();
        leverancierDAO.update(leverancier);
        leverancierDAO.saveChanges();
    }

    public void delete(Leverancier leverancier) {
        leverancierDAO.startTransaction();
        leverancierDAO.delete(leverancier);
        leverancierDAO.saveChanges();
    }
}
