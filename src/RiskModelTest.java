import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RiskModelTest {

    RiskModel risk;
    private ArrayList<Player> playerList;
    Player player;
    Player player1;
    ArrayList<String> playerNames;
    Country alaska;
    Country alberta;
    Country centralAmerica;
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


    protected void setUp(){
        risk = new RiskModel();
        player = new Player("Areeb", 3);
        conqueredCountries = new HashMap<Country, Integer>();
        conqueredCountries2 = new HashMap<Country, Integer>();
        alaska = new Country("alaska", new Continent("North America", 9, 5));
        alberta = new Country("alberta", new Continent("North America", 9, 5));


    }

    protected void tearDown(){

    }

    @org.junit.Test
    public void testIsValidNumber() { ;
        setUp();
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
        setUp();
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
        setUp();
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player);

        assertEquals(null, risk.getDefendingPlayer("alaska") ); // this is the case because there is no countries assigned.
    }


    @org.junit.Test
    public void testCorrectInitialTroops() {
        setUp();
        int check = risk.getInitialTroops(3);
        assertEquals(check, 35);
    }


    @org.junit.Test
    public void checkCurrentPlayerDesignation() {
        setUp();
        risk.setCurrentPlayer(player);

        Player check = risk.getCurrentPlayer();

        assertEquals(player, check);

    }


    @org.junit.Test
    public void checkFortify() {
        setUp();

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
        setUp();
        conqueredCountries.put(alaska, 3);
        conqueredCountries.put(alberta, 3);
        player.setConqueredCountries(conqueredCountries);
        player.updateCountry(alberta, 3);
        player.updateCountry(alaska,-3);
        risk.setCurrentPlayer(player);
        risk.maneuver(3, alaska, alberta);
        //if the country origin lost troops and country final gained troops then the manuever was succesful.
        assertEquals(risk.getCurrentPlayer().getConqueredCountries(), player.getConqueredCountries());
    }

    @org.junit.Test
    public void checkBonusTroopsAllocation() {
        setUp();
        Player player = new Player("Areeb", 2);
        Player player1 = new Player("Hassan", 6);
        conqueredCountries.put(alaska, 2);
        conqueredCountries.put(centralAmerica,3);
        conqueredCountries.put(greenland, 2);
        conqueredCountries.put(northwestT,3);
        conqueredCountries.put(ontario, 2);
        conqueredCountries.put(quebec,3);
        conqueredCountries.put(westernUS, 2);
        conqueredCountries.put(argentina,3);
        conqueredCountries.put(brazil, 2);
        conqueredCountries.put(peru,3);
        player1.setConqueredCountries(conqueredCountries);
        player.updateEnforcements(1);
        risk.setCurrentPlayer(player);
        risk.bonusTroops();
        assertEquals(risk.getCurrentPlayer().getAvailableEnforcement(), player1.getAvailableEnforcement());

    }

    @org.junit.Test
    public void testAttack() {
        setUp();
        player1 = new Player("Hassan", 2);
        risk.addPlayer(player1);
        conqueredCountries.put(alaska, 3);
        conqueredCountries2.put(alberta, 3);
        player.setConqueredCountries(conqueredCountries);
        player1.setConqueredCountries(conqueredCountries2);
        risk.setCurrentPlayer(player);
        risk.attack(alaska, alberta, 3,3);

        assertEquals(true, risk.getCheckAttack());
    }


}