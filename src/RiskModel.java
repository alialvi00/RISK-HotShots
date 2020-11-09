import javax.swing.*;
import java.util.*;

public class RiskModel extends DefaultListModel {

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

    public void attack() {
        if (userCommand.ifSecondCommand()) {
            System.out.println("Attack what?");
        } else {
            Scanner attackInput = new Scanner(System.in);
            System.out.println("Which country to launch an attack from?");
            String attackingCountry = attackInput.nextLine();
            attackingCountry = attackingCountry.toLowerCase();
            while(!checkPlayerCountry(attackingCountry, currentPlayer)){
                System.out.println("Please enter a valid country that you rule: ");
                attackingCountry = attackInput.nextLine();
                attackingCountry = attackingCountry.toLowerCase();
            }


            System.out.println("Following countries are bordering " + attackingCountry + ", choose an eligible country to attack: \n");
            for (String s : currentPlayer.getCountryByName(attackingCountry).getAdjacentCountries()) {
                System.out.println(s);
            }
            System.out.println("Which country would you like to attack?");
            String defendingCountry = attackInput.nextLine();
            defendingCountry = defendingCountry.toLowerCase();
            while(!(!checkPlayerCountry(defendingCountry, currentPlayer) && checkAdjacency(attackingCountry, defendingCountry) && map.isValidCountry(defendingCountry))){
                System.out.println("Please enter a VALID country you DO NOT rule and IS bordering your country: ");
                defendingCountry = attackInput.nextLine();
                defendingCountry = defendingCountry.toLowerCase();
            }


            System.out.println("How many troops would you like to attack with?");
            int attackingTroops = 0;
            String attackTroopsInput = attackInput.nextLine();
            if(isValidNumber(attackTroopsInput)){
                attackingTroops = Integer.parseInt(attackTroopsInput);
            }
            while(!checkAttackingTroops(attackingCountry, currentPlayer, attackingTroops)){
                System.out.println("Invalid. You can attack with maximum of 3 troops and must leave at least 1 troop in the country.\n" +
                "Please enter another amount: ");
                attackTroopsInput = attackInput.nextLine();
                attackingTroops = getValidNumber(attackTroopsInput, attackInput);
            }


            Player defendingPlayer = getDefendingPlayer(defendingCountry);

            System.out.println(defendingPlayer.getName() + ", " + defendingCountry + " is being attacked!\n" +
                    "How many troops would you like to defend with?");
            int defendingTroops = 0;
            String defendingTroopsInput = attackInput.nextLine();
            if(isValidNumber(defendingTroopsInput)){
                defendingTroops = Integer.parseInt(defendingTroopsInput);
            }
            
            while(!checkDefendingTroops(defendingCountry, defendingPlayer, defendingTroops)){
                System.out.println("Invalid, you can only use a maximum of 2 troops to defend and not exceed the amount of troops in your country\n" 
                + "Please enter another amount: ");
                defendingTroopsInput = attackInput.nextLine();
                defendingTroops = getValidNumber(attackTroopsInput, attackInput);
            }

            int defendingTroopsLost = 0;
            int attackingTroopsLost = 0;
            int attackSize = 0;

            ArrayList<Integer> attackingDice = currentPlayer.attackCountry(attackingTroops);
            ArrayList<Integer> defendingDice = defendingPlayer.defendCountry(defendingTroops);

            while (!(attackingDice.isEmpty() || defendingDice.isEmpty())) {
                if (Collections.max(attackingDice) > Collections.max(defendingDice)) {
                    attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                    defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                    defendingPlayer.updateCountry(defendingPlayer.getCountryByName(defendingCountry), -1);
                    currentPlayer.updateCountry(currentPlayer.getCountryByName(attackingCountry), -1);
                    attackSize++;
                    defendingTroopsLost++;
                } else if (Collections.max(attackingDice) <= Collections.max(defendingDice)) {
                    attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                    defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                    currentPlayer.updateCountry(currentPlayer.getCountryByName(attackingCountry), -1);
                    attackingTroopsLost++;
                }
            }
            System.out.println();
            System.out.println(defendingCountry + " lost " + defendingTroopsLost + " troops");
            System.out.println(attackingCountry + " lost " + attackingTroopsLost + " troops");

            if (defendingPlayer.getCountryTroops(defendingCountry) == 0) {
                currentPlayer.addCountry(defendingPlayer.getCountryByName(defendingCountry), attackSize);
                defendingPlayer.deleteCountry(defendingPlayer.getCountryByName(defendingCountry));

                System.out.println();
                System.out.println(defendingCountry + " has lost all troops and is now conquered by " + currentPlayer.getName());
            }
            checkGameOver();
            startPlayerTurn();
            stateOfMap();
        }
    }


    public void play() {
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
    }

    public void startPlayerTurn() {

        System.out.println(currentPlayer.getName() + " has the choice to pass or attack");
        System.out.println("If attacking, refer to the map given and choose an enemy country");
        while (!gameEnded) {

            userCommand = readInput.getCommand();
            gameEnded = processCommand();
        }
        System.out.println("Game over");
    }


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
        startPlayerTurn();
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

    public void goBack() {
        if (!userCommand.ifSecondCommand()) {
            System.out.println("Where would you like to go? (go back)? \n");
        } else {
            boolean check = userCommand.getSecondCommand().equals("back");
            if (check) {
                System.out.println("Changing your mind? You are being redirected...");
                startPlayerTurn();
            } else {
                System.out.println("Do you mean 'go back' to your options? \n");
            }
        }
    }

    public boolean processCommand() {

        boolean quitGame = false;
        CommandWords commandWord = userCommand.getFirstCommand();

        switch (commandWord) {

            case INCORRECT:
                System.out.println("Wrong command, please enter a command from the available commands");
                break;
            case PASS:
                passTurn();
                break;
            case ATTACK:
                attack();
                break;
            case QUIT:
                quitGame = quit();
                break;
            case MAP:
                stateOfMap();
                break;
            case RETURN:
                goBack();
                break;

        }
        return quitGame;
    }

    public Boolean checkPlayerCountry(String country, Player player) {
        return player.hasCountry(country);
    }

    public Boolean checkAttackingTroops(String country, Player player, int troops) {
      
        if(troops <= 0 || troops > 3){
            return false;
        } else if (troops < player.getCountryTroops(country)) {
            return true;
        }
        return false;
    }

    public Boolean checkDefendingTroops(String country, Player player, int troops) {

        if(troops <= 0 || troops > 2){
            return false;
        } else return troops <= player.getCountryTroops(country);
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
}