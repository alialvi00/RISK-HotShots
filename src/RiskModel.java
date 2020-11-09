import javax.swing.*;
import java.util.*;

public class RiskModel{

    private RiskMap map;
    private Dice rollToBegin;
    private Command userCommand;
    private int playerCount;
    private Player currentPlayer;
    private ProcessInput readInput;
    private ArrayList<String> playerNames;
    private ArrayList<Player> playerList;
    private ArrayList<Integer> rolls;
    private List<RiskView> viewList;
    private Boolean gameEnded;

    public RiskModel() {
        playerNames = new ArrayList<>();
        playerList = new ArrayList<>();
        gameEnded = false;
        map = new RiskMap();
        viewList = new ArrayList<>();
    }

    public void attack(Country attackingCountry, Country defendingCountry, int attackingTroops, int defendingTroops, Player defendingPlayer) {
            int defendingTroopsLost = 0;
            int attackingTroopsLost = 0;
            int winningTroops = 0;

            ArrayList<Integer> attackingDice = currentPlayer.attackCountry(attackingTroops);
            ArrayList<Integer> defendingDice = defendingPlayer.defendCountry(defendingTroops);

            while (!(attackingDice.isEmpty() || defendingDice.isEmpty())) {
                if (Collections.max(attackingDice) > Collections.max(defendingDice)) {
                    attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                    defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                    defendingPlayer.updateCountry(defendingCountry, -1);
                    defendingTroopsLost++;
                    winningTroops++;
                } else if (Collections.max(attackingDice) <= Collections.max(defendingDice)) {
                    attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                    defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                    currentPlayer.updateCountry(attackingCountry, -1);
                    attackingTroopsLost++;
                }
            }
            System.out.println();
            System.out.println(defendingCountry + " lost " + defendingTroopsLost + " troops");
            System.out.println(attackingCountry + " lost " + attackingTroopsLost + " troops");

            if (defendingPlayer.getPlayerData().get(defendingCountry) == 0) {
                currentPlayer.addCountry(defendingCountry, winningTroops);
                defendingPlayer.deleteCountry(defendingCountry);

                System.out.println();
                System.out.println(defendingCountry + " has lost all troops and is now conquered by " + currentPlayer.getName());
            }
        }



    /**public void play() {
        welcome();
        setPlayers();
        setCountry();
        System.out.println();
        System.out.println("The game will now start");
        System.out.println();
        System.out.println("The current state of map is: ");
        stateOfMap();
        System.out.println("Dice is now being rolled for all " + playerCount + " players.");
        whoStarts();
        System.out.println(currentPlayer.getName() + " has the highest number rolled so they will start");
        startPlayerTurn();
    }*/

    public void welcome() {

        System.out.println("Welcome to RISK: Global Domination!");
        System.out.println();
        System.out.println("RISK is a classic strategy game filled with conquest and intrigue.\n"
                + "This game can be played with 2-6 players." +
                "To win, you must strive to capture all of the continents and countries in the game, while eliminating other players.\n" +
                "The last man standing is the winner!");
    }


    public void setCountry() {
        while (!(map.getCountryList().isEmpty())) {
            for (Player p : playerList) {
                p.addCountry(randomCountry(), 1);
                p.updateEnforcements(-1);
            }
        }
        distributeTroops();

    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setPlayerNames(ArrayList<String> players) {
        for (String p: players){
            playerNames.add(p);
        }
    }

    public void setPlayers() {
        for (int i = 0; i < playerCount; i++) {
            playerList.add(new Player(playerNames.get(i), getInitialTroops(playerCount)));
        }
    }

    public void distributeTroops() {

        Random randInt = new Random();

        for (Player p : playerList) {
            while (!(p.getAvailableEnforcement() <= 0)) {
                int randomTroops = randInt.nextInt(2);
                for (Country c : p.getCountries()) {
                    if ((p.getAvailableEnforcement() - randomTroops) >= 0) {
                        p.updateCountry(c, randomTroops);
                        p.updateEnforcements(-randomTroops);
                    }
                }
            }
        }
    }

    public Country randomCountry() {
        Random inCountryList = new Random();
        int value = inCountryList.nextInt(map.getCountryList().size());
        Country temp = map.getCountryList().get(value);

        map.getCountryList().remove(temp);
        return temp;
    }

    public int getInitialTroops(int playerCount) {
        switch (playerCount) {
            case 2:
                return 50;
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
        }
        return 20;
    }

    public void whoStarts() {
        rolls = new ArrayList<Integer>();
        for (int i = 0; i < playerList.size(); i++) {
            rolls.add(rollDice());
        }

        int maxValue = Collections.max(rolls);
        for (int i = 0; i < rolls.size(); i++) {
            if (rolls.get(i) == maxValue) {
                currentPlayer = playerList.get(i);
            }
        }
    }

    public void nextTurn() {
        if (playerList.indexOf(currentPlayer) < (playerList.size() - 1)) {
            currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
        } else {
            currentPlayer = playerList.get(0);
        }
    }


    public int rollDice() {
        rollToBegin = new Dice();
        return rollToBegin.getDiceValue();
    }

    public void passTurn() {
        if (!userCommand.ifSecondCommand()) {
            System.out.println("Do you mean pass turn? \n");
        } else {
            String skipTurn = userCommand.getSecondCommand();
            if (skipTurn.equals("turn")) {
                System.out.println(currentPlayer.getName() + " has skipped its turn");
                System.out.println("Next player turn");
                nextTurn();
            } else {
                System.out.println("Do you mean pass turn? \n");
            }
        }
    }


    public void stateOfMap() {
        for (Player p : playerList) {
            System.out.println();
            System.out.println(p.getName() + "'s Countries: ");
            System.out.println();
            for (Country c : p.getPlayerData().keySet()) {
                System.out.println(c + ": " + p.getPlayerData().get(c));
            }
        }
    }

    public boolean quit() {
        if (userCommand.ifSecondCommand()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkPlayerCountry(String country, Player player) {
        return player.hasCountry(country);
    }

    public Player getDefendingPlayer(String country) {
        for (Player p : playerList) {
            if (p.hasCountry(country)) {
                return p;
            }
        }
        return null;       //added this or else error since not returning anything
    }

    public Boolean checkAdjacency(String attackingCountry, String defendingCountry) {
        for (String s : currentPlayer.getCountryByName(attackingCountry).getAdjacentCountries()) {
            if (defendingCountry.equals(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    public void checkGameOver() {

        for (Player p : playerList) {
            if (p.getCountries().size() == 42) { //Checks if a Player has all countries.
                gameEnded = true;
                System.out.println("Game Over. The winner is: " + p.getName());
            }

        }
    }

    public int getValidNumber(String troops, Scanner input) {
        String troopNum = troops;
        while(!isValidNumber(troopNum)){
            System.out.println("Please enter a number: ");
            troopNum = input.nextLine();
        }
        return Integer.parseInt(troopNum);
    }

    public Boolean isValidNumber(String num){
        try
        {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    public void addView(RiskView view) {
        viewList.add(view);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMaxAttackingTroops(Country country){
        int troops = currentPlayer.getPlayerData().get(country) - 1;
        if(troops > 3){
            return 3;
        } else{return troops;}
    }

    public int getMaxDefendingTroops(Country country, Player player){
        int troops = player.getPlayerData().get(country);
        if(troops > 2){
            return 2;
        }
        else{return troops;}
    }

    public void setUpPlayers(ArrayList<String> playerNames, int playerCount) {
        setPlayerCount(playerCount);
        setPlayerNames(playerNames);
        setPlayers();
        setCountry();
        whoStarts();
        for (RiskView rV : viewList) {
            rV.handleInitialMap(new MapEvent(this, playerList));
        }
    }

    public void initiateAttack(Country attackingCountry, Country defendingCountry, int attackingTroops, int defendingTroops, Player defendingPlayer){
        //attack actually occurs, map gets updated
        attack(attackingCountry, defendingCountry, attackingTroops, defendingTroops, defendingPlayer);

        for (RiskView rV : viewList) {
            rV.handleAttack(new MapEvent(this, playerList));
        }
    }
}