import java.awt.event.*;

public class PassController implements ActionListener{

    RiskView view;
    RiskModel model;

    public PassController(RiskView view, RiskModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e){

        view.playSoundEffect("library/PassButtonSound.wav");
        view.clearSelection();
        model.nextTurn();
        view.updateCountriesJlist(model.getCurrentPlayer());
        view.setFortifyMode();
        System.out.println("\nIt is " + model.getCurrentPlayer().getName() + "'s turn \n");
    }
    
}