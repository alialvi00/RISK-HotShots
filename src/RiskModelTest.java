import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RiskModelTest {

    RiskModel risk;
    private ArrayList<Player> playerList;
    Player player;
    ArrayList<String> playerNames;
    Country alaska;
    Country alberta;
    Country centralAmerica;
    Country easternUS;
    Country greenland;
    Country northwestT;
    Country ontario;
    Country quebec;
    Country westernUS;
    Country argentina;
    Country brazil;
    Country peru;


    private HashMap<Country, Integer> conqueredCountries = new HashMap<Country, Integer>();
    private HashMap<Country, Integer> conqueredCountries2 = new HashMap<Country, Integer>();



    @org.junit.Test
    public void testIsValidNumber() {
        RiskModel risk = new RiskModel();
        assertEquals(true, risk.isValidNumber("3"));
    }


    @org.junit.Test
    public void testIfPlayerSet() {
        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<Boolean> playerType = new ArrayList<>();
        playerType.add(true);
        playerType.add(true);
        playerType.add(true);
        playerType.add(true);
        playerType.add(true);
        playerType.add(true);
        RiskModel risk = new RiskModel();
        risk.setPlayerCount(2);
        playerNames.add("Areeb");
        playerNames.add("Hassan");
        risk.setPlayerNames(playerNames);

        ArrayList<Player> playerObjects = new ArrayList<>();
        risk.setPlayers(playerType);
        assertEquals(risk.showPlayerList().size(), 2);

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
    public void testAttack(){
        RiskModel risk = new RiskModel();
        Player player = new Player("Areeb", 3);
        Player player2 = new Player("Hassan", 2);
        conqueredCountries.put(alaska, 3);
        conqueredCountries2.put(alberta, 2);
        player.setConqueredCountries(conqueredCountries);
        risk.setCurrentPlayer(player);
        player2.setConqueredCountries(conqueredCountries2);
        player.updateCountry(alaska, 3);
        player2.updateCountry(alberta, 2);

        risk.attack(alberta, alaska, 3, 2);
        assertEquals(true, risk.getCheckAttack());

    }

    @org.junit.Test
    public void testCorrectInitialTroops() {
        RiskModel risk = new RiskModel();
        int check = risk.getInitialTroops(3);
        assertEquals(check, 35);
    }


    @org.junit.Test
    public void checkCurrentPlayerDesignation() {
        RiskModel risk = new RiskModel();
        Player player = new Player("Areeb", 3);
        risk.setCurrentPlayer(player);

        Player check = risk.getCurrentPlayer();

        assertEquals(player, check);

    }

    @org.junit.Test
    public void checkFortify() {
        RiskModel risk = new RiskModel();
        Player player = new Player("Areeb", 3);
        conqueredCountries.put(alaska, 3);
        player.setConqueredCountries(conqueredCountries);
        player.updateCountry(alaska, 3);
        player.updateEnforcements(-3);
        risk.setCurrentPlayer(player);
        risk.fortify(3, alaska);
        assertEquals(risk.getCurrentPlayer().getConqueredCountries(), player.getConqueredCountries());
    }

    @org.junit.Test
    public void checkManeuver() {
        RiskModel risk = new RiskModel();
        Player player = new Player("Areeb", 3);
        conqueredCountries.put(alaska, 3);
        player.setConqueredCountries(conqueredCountries);
        player.updateCountry(alaska, 3);
        player.updateEnforcements(-3);
        risk.setCurrentPlayer(player);
        risk.manuever(3, alaska, alberta);
        //if the country origin lost troops and country final gained troops then the manuever was succesful.
        assertEquals(risk.getCurrentPlayer().getConqueredCountries(), player.getConqueredCountries());
    }

    @org.junit.Test
    public void checkBonusTroopsAllocation() {
        RiskModel risk = new RiskModel();
        Player player = new Player("Areeb", 0);
        Player player1 = new Player("Hassan", 4);
        conqueredCountries.put(alaska, 2);
        player1.setConqueredCountries(conqueredCountries);
        player.updateEnforcements(1);
        risk.setCurrentPlayer(player);
        risk.bonusTroops();
        assertEquals(risk.getCurrentPlayer().getAvailableEnforcement(), player1.getAvailableEnforcement());

    }










}