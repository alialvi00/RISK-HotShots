import java.util.ArrayList;

import static org.junit.Assert.*;

public class RiskModelTest {

    RiskModel risk;
    private ArrayList<Player> playerList;
    Player player;
    ArrayList<String> playerNames;
    Country alaska;
    Country alberta;


    @org.junit.Test
    public void testAttack(){
        RiskModel risk = new RiskModel();
        Player player = new Player("Areeb", 3);
        risk.attack(alaska, alberta, 2, 3, player);
        boolean check = risk.getCheckAttack();
        assertEquals(true, check);

    }

    @org.junit.Test
    public void testGetDefendingPlayer() {
        Player player = new Player("AREEB", 5);
        RiskModel risk = new RiskModel();
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player);

        assertEquals(null, risk.getDefendingPlayer("alaska") ); // this is the case because there is no countries assigned.
    }


    @org.junit.Test
    public void testIsValidNumber() {
        RiskModel risk = new RiskModel();
        assertEquals(true, risk.isValidNumber("3"));
    }


    @org.junit.Test
    public void testIfPlayerSet() {
        ArrayList<String> playerNames = new ArrayList<>();
        RiskModel risk = new RiskModel();
        risk.setPlayerCount(2);
        playerNames.add("Areeb");
        playerNames.add("Hassan");
        risk.setPlayerNames(playerNames);

        ArrayList<Player> playerObjects = new ArrayList<>();
        risk.setPlayers();
        assertEquals(risk.showPlayerList().size(), 2);

    }



    @org.junit.Test
    public void testCorrectInitialTroops() {
        RiskModel risk = new RiskModel();
        int check = risk.getInitialTroops(3);
        assertEquals(check, 35);
    }

    @org.junit.Test
    public void testIfWon() {
        Player player = new Player("Areeb", 5);
        ArrayList<String> playerNames = new ArrayList<>();

        RiskModel risk = new RiskModel();
        playerNames.add("Areeb");
        playerNames.add("Hassan");
        risk.setPlayerNames(playerNames);
        risk.setPlayerCount(2);
        risk.setPlayers();

        assertEquals(false, risk.ifWon());


        //hypothetical case as when a player loses they are removed from the playerList

    }







}