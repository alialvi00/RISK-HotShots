import java.awt.event.*;

public class ManeuverController implements ActionListener{

    RiskView view;
    RiskModel model;

    public ManeuverController(RiskModel model, RiskView view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("maneuverEnable")){
            view.removeAdjListener();
            view.disableAttack();
            view.disablePassButton();
            view.clearSelection();
            view.changeManeuverMode(true);
        } else if(e.getActionCommand().equals("exitManeuver")){
            view.changeManeuverMode(false);
            view.clearSelection();
            view.setNormalMode();
        } else{
            Country originCountry = view.getOriginCountry();
            int maxCountryTroops = model.getCurrentPlayer().getPlayerData().get(originCountry) - 1;
            if(maxCountryTroops == 0){
                System.out.println("You must leave at least one troop in any of your countries.");
                return;
            }
            Country destination = model.getCurrentPlayer().getCountryByName(view.getDestinationCountry());
            int maneuveringTroops = view.getManeuverTroops(maxCountryTroops);
            model.maneuver(maneuveringTroops, originCountry, destination);
            view.disableConfirmButton();
        }
    }
    
}