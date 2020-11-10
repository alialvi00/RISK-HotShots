import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ListController implements ListSelectionListener{
    
    private RiskModel model;
    private RiskView view;

    public ListController(RiskModel model, RiskView view){
        this.model = model;
        this.view = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent e){
        //disables the attack button
        view.disableAttack();
        //this gets the the country the player selects from the jlist
        Country selectedCountry = view.getOriginCountry();

        //this should update the adjacent countries when someone clicks on their country
        if(selectedCountry == null){
            view.clearSelection();
        }
        else
        {
        model.updateAdjacentCountries(selectedCountry);
        }
    }
}
