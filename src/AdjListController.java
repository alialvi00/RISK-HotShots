import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.Serializable;

//this controller is only used for the adjacent Jlist
public class AdjListController implements ListSelectionListener, Serializable {
    
    private RiskView view;

    public AdjListController(RiskView view){
        this.view = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent e){
        JList adjList = (JList) e.getSource();
        if(!adjList.isSelectionEmpty()){
            if(view.getMode()){
                view.enableConfirmButton();
            } else{
                view.enableAttack();   
            }           
        }
    }
}