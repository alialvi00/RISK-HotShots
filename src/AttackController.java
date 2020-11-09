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
        String destinationCountry = mainView.getDestinationCountry();
        Player defendingPlayer = mainModel.getDefendingPlayer(destinationCountry);

        int maxAttackingTroops = mainModel.getMaxAttackingTroops(originCountry);
        int attackingTroops = mainView.getAttackingTroops(maxAttackingTroops);

        int maxDefendingTroops = mainModel.getMaxDefendingTroops(destinationCountry);
        int defendingTroops = mainView.getDefendingTroops(maxDefendingTroops, defendingPlayer);

        mainModel.initiateAttack(originCountry, destinationCountry, attackingTroops, defendingTroops, defendingPlayer);
    }
}
