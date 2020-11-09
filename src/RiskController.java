import java.awt.event.*;


public class RiskController implements ActionListener{

    private RiskModel mainModel;
    private RiskView mainView;

    public RiskController(RiskModel model, RiskView view){
        mainView = view;
        mainModel = model;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        mainView.setPlayerNames();
        mainView.switchView();       
        mainView.startNewGame();
        mainModel.setUpPlayers(mainView.getPlayerNames(), mainView.getPlayerCount());
    }

}
