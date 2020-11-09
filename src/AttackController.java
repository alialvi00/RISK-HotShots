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

        Country originCountry = mainView.getOriginCountry();
        Player defendingPlayer = mainModel.getDefendingPlayer(mainView.getDestinationCountry());
        Country destinationCountry = defendingPlayer.getCountryByName(mainView.getDestinationCountry());

        int maxAttackingTroops = mainModel.getMaxAttackingTroops(originCountry);
        int attackingTroops = mainView.getAttackingTroops(maxAttackingTroops);

        int maxDefendingTroops = mainModel.getMaxDefendingTroops(destinationCountry, defendingPlayer);
        int defendingTroops = mainView.getDefendingTroops(maxDefendingTroops, defendingPlayer);

        mainModel.initiateAttack(originCountry, destinationCountry, attackingTroops, defendingTroops, defendingPlayer);
    }
}
