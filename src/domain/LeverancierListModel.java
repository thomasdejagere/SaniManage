/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;

/**
 *
 * @author Thomas
 */
public class LeverancierListModel extends AbstractListModel<String> implements Observer{

    private ModelObservable<Leverancier> model;
    
    public LeverancierListModel(LeveranciersBeheer model) 
    {
        this.model = model;
        this.model.addObserver(this);
    }
    
    @Override
    public int getSize() {
        return model.aantal();
    }

    @Override
    public String getElementAt(int index) {
        return model.getAtIndex(index).getNaam();
    }
    //NOG EENS GOED BEKIJKEN!
    @Override
    public void update(Observable arg0, Object object) {
               if(arg0.equals("add"))
       {
           int size=getSize();
           fireIntervalRemoved(this, size, size);
       }
       else
       {
           int index=(Integer)object;
           fireIntervalRemoved(this, index, index);
       }
    }
    
}
