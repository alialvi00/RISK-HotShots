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
        view.setFortifyMode();
        model.nextTurn();
    }
    
}