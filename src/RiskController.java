import java.awt.event.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class RiskController implements ActionListener, Serializable {

    private RiskModel mainModel;
    private RiskView mainView;

    public RiskController(RiskModel model, RiskView view){
        mainView = view;
        mainModel = model;
    }

    public void changeModel(RiskModel mainModel){
        this.mainModel = mainModel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("quit")){
            System.exit(0);
        }


        mainView.setPlayerNames();
        mainView.setPlayerType();
        if(!mainView.checkStartGame()){
            mainView.startGameError();
            return;
        }
        mainView.checkStartGame();
        mainView.switchView();       
        mainView.startNewGame();
        mainModel.welcome();
        if(!mainView.mapType()){
            mainModel.initiateMap(null);
        }
        mainModel.setUpPlayers(mainView.getPlayerNames(), mainView.getPlayerCount(),mainView.getPlayerType());
    }



}
