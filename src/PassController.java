import java.awt.event.*;
import java.io.Serializable;

public class PassController implements ActionListener, Serializable {

    RiskView view;
    RiskModel model;

    public PassController(RiskView view, RiskModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e){

        view.playSoundEffect(getClass().getResource("PassButtonSound.wav"));
        view.clearSelection();
        view.setFortifyMode();
        model.nextTurn();
    }
    
}
