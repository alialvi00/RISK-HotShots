import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//this controller is only used for the adjacent Jlist
public class AdjListController implements ListSelectionListener{
    
    private RiskView view;

    public AdjListController(RiskView view){
        this.view = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent e){
        JList adjList = (JList) e.getSource();
        if(!adjList.isSelectionEmpty()){
            view.enableAttack();
        }
    }
}