import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FortifyController implements ListSelectionListener{
    private RiskModel model;
    private RiskView view;

    public FortifyController(RiskModel model, RiskView view){
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
            int availableTroops = model.getAvailableEnforcement();
            int usedTroops = view.getEnforcementAmount(availableTroops);
            availableTroops = availableTroops - usedTroops;
            if(availableTroops == 0){
                view.setNormalMode();
                view.addAdjListener();
            }
            model.fortify(usedTroops, selectedCountry);
        }
    }
}