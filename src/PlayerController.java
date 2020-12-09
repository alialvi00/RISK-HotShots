import java.awt.event.*;
import java.io.Serializable;

class PlayerController implements ItemListener, Serializable {

    RiskView mainView;
    public PlayerController(RiskView view){
        mainView = view;
    }

    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            if(mainView.getPlayerCount() == 2){
                mainView.setNameFields(2,true,false);
            }
            else if(mainView.getPlayerCount() == 3){
                mainView.setNameFields(3,true,false);
            }
            else if(mainView.getPlayerCount() == 4){
                mainView.setNameFields(4,true,false);
            }
            else if(mainView.getPlayerCount() == 5){
                mainView.setNameFields(5,true,false);
            }
            else if(mainView.getPlayerCount() == 6){
                mainView.setNameFields(6,true,false);
            }
        }
        else if(e.getStateChange() == ItemEvent.DESELECTED){
            if(mainView.getPlayerCount() == 2){
                mainView.setNameFields(2,false,true);
            }
            else if(mainView.getPlayerCount() == 3){
                mainView.setNameFields(3,false,true);
            }
            else if(mainView.getPlayerCount() == 4){
                mainView.setNameFields(4,false,true);
            }
            else if(mainView.getPlayerCount() == 5){
                mainView.setNameFields(5,false,true);
            }
            else if(mainView.getPlayerCount() == 6){
                mainView.setNameFields(6,false,true);
            }
        }
    }

}

