import java.awt.event.*;

public class AttackController implements ActionListener{
    private RiskModel mainModel;
    private RiskView mainView;

    public AttackController(RiskModel model, RiskView view){
        mainView = view;
        mainModel = model;
    }

    @Override
    public void actionPerformed(ActionEvent e){

        mainView.playSoundEffect(getClass().getResource("AttackButtonSound.wav"));
        Country originCountry = mainView.getOriginCountry();
        Player defendingPlayer = mainModel.getDefendingPlayer(mainView.getDestinationCountry());

        //checks if the attacking country has 1 player
        if(mainModel.getCurrentPlayer().getPlayerData().get(originCountry) == 1){
            System.out.println("\n" + "You must leave at least 1 troop in your country, please try a different attack \n");
            return;
        }

        Country destinationCountry = defendingPlayer.getCountryByName(mainView.getDestinationCountry());

        int maxAttackingTroops = mainModel.getMaxAttackingTroops(originCountry);
        int attackingTroops = mainView.getAttackingTroops(maxAttackingTroops);

        int defendingTroops = 0;
        int maxDefendingTroops = mainModel.getMaxDefendingTroops(destinationCountry, defendingPlayer);

        if(defendingPlayer.getIsAI()){
            defendingTroops = mainModel.defendAI(maxDefendingTroops);
        }
        else {
            defendingTroops = mainView.getDefendingTroops(maxDefendingTroops, defendingPlayer);
        }

        mainModel.initiateAttack(originCountry, destinationCountry, attackingTroops, defendingTroops);
    }
}
