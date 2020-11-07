import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RiskController {

    private RiskModel mainModel;
    private RiskView mainView;

    public RiskController(RiskView mainView){

        this.mainView = mainView;

        mainView.setPlayerCount(new PlayerNames());
        mainView.setStartGame(new StartGame());
    }

    class PlayerNames implements ItemListener {


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

    class StartGame implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.switchView();
            mainView.startNewGame();
        }
    }

}
