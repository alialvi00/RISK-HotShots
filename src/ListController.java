import java.awt.event.*;

import javax.swing.JList;
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
        //hopefully this gets the the country the player selects from the jlist, i am unable to test
        Country selectedCountry = view.getOriginCountry();

        //this should update the adjacent countries when someone clicks on their country
        view.updateAdjacentJList(selectedCountry.getAdjacentCountries());
    }
}
