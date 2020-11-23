import javax.swing.*;
import java.util.*;

public class RiskModel{

    private RiskMap map;
    private Dice rollToBegin;
    private int playerCount;
    private Player currentPlayer;
    private ArrayList<String> playerNames;
    private ArrayList<Player> playerList;
    private ArrayList<Integer> rolls;
    private List<RiskView> viewList;
    private Boolean gameEnded;
    private int checkAttack;
    private Boolean firstAITurn;

    public RiskModel() {
        playerNames = new ArrayList<>();
        playerList = new ArrayList<>();
        gameEnded = false;
        map = new RiskMap();
        viewList = new ArrayList<>();
        checkAttack = 0;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * does everything needed to attack. rolls the dice, updates countries, removes players if needed
     * @param attackingCountry  country attacking
     * @param defendingCountry  country defending
     * @param attackingTroops   number of troops attacking
     * @param defendingTroops   number of troops defending
     */
    public void attack(Country attackingCountry, Country defendingCountry, int attackingTroops, int defendingTroops) {

        int defendingTroopsLost = 0;
        int attackingTroopsLost = 0;
        int winningTroops = 0;
        Player defendingPlayer = getDefendingPlayer(defendingCountry.toString());


        ArrayList<Integer> attackingDice = currentPlayer.attackCountry(attackingTroops);
        ArrayList<Integer> defendingDice = defendingPlayer.defendCountry(defendingTroops);

        while (!(attackingDice.isEmpty() || defendingDice.isEmpty())) {
            if (Collections.max(attackingDice) > Collections.max(defendingDice)) {
                attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                defendingPlayer.updateCountry(defendingCountry, -1);
                defendingTroopsLost++;
                winningTroops++;
                checkAttack++;

            } else if (Collections.max(attackingDice) <= Collections.max(defendingDice)) {
                attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                currentPlayer.updateCountry(attackingCountry, -1);

                attackingTroopsLost++;
                checkAttack++;

            }
        }
        System.out.println();
        System.out.println(defendingCountry + " lost " + defendingTroopsLost + " troops");
        System.out.println(attackingCountry + " lost " + attackingTroopsLost + " troops");

        if (defendingPlayer.getPlayerData().get(defendingCountry) == 0) {
            currentPlayer.addCountry(defendingCountry, winningTroops);
            currentPlayer.updateCountry(attackingCountry, -winningTroops);
            defendingPlayer.deleteCountry(defendingCountry);
            checkAttack++;
            System.out.println();
            System.out.println(defendingCountry + " has lost all troops and is now conquered by " + currentPlayer.getName());

            if(checkPlayerLost(defendingPlayer)){
                playerList.remove(defendingPlayer);
                if(checkGameOver(playerList)){
                    endGame();
                }
            }
        }
    }


    /**
     * This method performs the AI actions depending on
     * on the current state of Board.
     * This method assumes that the current player
     * is an AI. Only call this method after checking
     * if current player is an AI
     * Maximized expected utility was calculated and
     * applied to this method to create the best
     * possible outcome.
     */
    public void playAI() {

        ArrayList<Country> countries;
        ArrayList<Country> attackCountry = new ArrayList<>();
        ArrayList<Country> defendingCountry = new ArrayList<>();
        ArrayList<Country> originCountry = new ArrayList<>();
        ArrayList<Country> destinationCountry = new ArrayList<>();

        Player defendingPlayer = null; //Initial assignment as dummy

        int troops; //bonusTroops available
        int attackTroops; //troops to attack with
        int defendTroops; //troops to defend with
        int moveTroops; //Troops to maneuver
        int currentTroops = 0;

        Random generatePick = new Random(); //generate first pick
        Random generateSecondPick = new Random(); //generate second pick
        Random generatePercent = new Random(); //generate the chance percent
        Random generateMoveTroops = new Random();

        int pick;
        int secondPick;
        int percent;

        boolean attack = false;
        boolean hasEnemy = false;
        boolean isOwned = false;

        countries = currentPlayer.getCountries();

        //Bonus Troops Placement
        pick = generatePick.nextInt(countries.size());
        troops = currentPlayer.getAvailableEnforcement();
        if (currentPlayer.getCountryTroops(currentPlayer.getCountries().get(pick).toString()) < 6) {
            fortify(troops, currentPlayer.getCountries().get(pick));
        } else {
            fortify(troops, currentPlayer.getCountries().get(generatePick.nextInt(countries.size())));
        }

        //Generate the correct calculated percent
        percent = generatePercent.nextInt(99);

        //AI now performs attack depending on its chances
        //Attack has 50% of chance to execute, this analysis was done using Bernoulli Equation square root of weighted utility
        while (percent >= 49) {

            for (int i = 0; i < currentPlayer.getCountries().size(); i++) {
                for (int j = 0; j < currentPlayer.getCountries().get(i).getAdjacentCountries().length; j++) {

                    boolean hasAdjacentConquered = currentPlayer.hasCountry(currentPlayer.getCountries().get(i).getAdjacentCountries()[j]);
                    int num = currentPlayer.getPlayerData().get(currentPlayer.getCountries().get(i));

                    if (num > 1 && !hasAdjacentConquered) {
                        attack = true;
                    } else {
                        attack = false;
                    }
                }
                if (attack) {
                    attackCountry.add(currentPlayer.getCountries().get(i));
                }
            }

            //If AI made a choice to attack from a country
            if (attackCountry.size() > 0) {

                pick = generatePick.nextInt(attackCountry.size());

                for (int i = 0; i < attackCountry.get(pick).getAdjacentCountries().length; i++) {

                    boolean hasAdjacentConquered = currentPlayer.hasCountry(attackCountry.get(pick).getAdjacentCountries()[i]);

                    if (!hasAdjacentConquered) {

                        defendingPlayer = getDefendingPlayer(attackCountry.get(pick).getAdjacentCountries()[i]);
                        defendingCountry.add(defendingPlayer.getCountryByName(attackCountry.get(pick).getAdjacentCountries()[i]));
                    }
                }
                //When AI has chosen defending countries
                if (defendingCountry.size() > 0) {

                    secondPick = generateSecondPick.nextInt(defendingCountry.size());
                    attackTroops = getMaxAttackingTroops(attackCountry.get(pick));

                    if (defendingPlayer.hasCountry(defendingCountry.get(secondPick).toString())) {
                        defendTroops = getMaxDefendingTroops(defendingCountry.get(secondPick), defendingPlayer);
                        initiateAttack(attackCountry.get(pick), defendingCountry.get(secondPick), attackTroops, defendTroops);
                        percent = generatePercent.nextInt(99);
                    }
                }
            }
        }

        //percent = generatePercent.nextInt(99);

        //75 percent of chance to maneuver troops
        //while(percent >= 24) {
        //Now we maneuver troops, make sure we maneuver to country that has enemy at its border so we can strengthen our country
        for (int i = 0; i < currentPlayer.getCountries().size(); i++) {
            for (int j = 0; j < currentPlayer.getCountries().get(i).getAdjacentCountries().length; j++) {

                boolean hasAdjacentConquered = currentPlayer.hasCountry(currentPlayer.getCountries().get(i).getAdjacentCountries()[j]);

                //At least one of the destination country is owned by the current AI player
                if (hasAdjacentConquered)
                    isOwned = true;
                    //Destination country definitely has at least one enemy country bordering it
                else
                    hasEnemy = true;
            }
            if (isOwned && hasEnemy) {
                destinationCountry.add(currentPlayer.getCountries().get(i));
            }
        }

        //AI Algorithm has successfully chosen countries to transfer troops
        if (!destinationCountry.isEmpty()) {

            pick = generatePick.nextInt(destinationCountry.size());

            for (int i = 0; i < destinationCountry.get(pick).getAdjacentCountries().length; i++) {

                boolean hasAdjacentConquered = currentPlayer.hasCountry(destinationCountry.get(pick).getAdjacentCountries()[i]);

                if (hasAdjacentConquered) {

                    currentTroops = currentPlayer.getPlayerData().get(currentPlayer.getCountryByName(destinationCountry.get(pick).getAdjacentCountries()[i]));

                    if(currentTroops>1)
                        originCountry.add(currentPlayer.getCountryByName(destinationCountry.get(pick).getAdjacentCountries()[i]));
                }
            }
            //AI Algorithm has successfully chosen countries to transfer troops from
            if (!originCountry.isEmpty()) {

                secondPick = generateSecondPick.nextInt(originCountry.size());
                troops = currentPlayer.getPlayerData().get(currentPlayer.getCountryByName(originCountry.get(secondPick).toString()));
                int troopsToMove = generateMoveTroops.nextInt(troops)+1;
                maneuver(troopsToMove, originCountry.get(secondPick), destinationCountry.get(pick));
            }
        }
    //}
    }

    public boolean getCheckAttack() {
        if (checkAttack > 0) {
            return true;
        }
        return false;
    }

        /**
         * checks if a player lost all his countries
         * @param defendingPlayer
         * @return
         */
        private boolean checkPlayerLost(Player defendingPlayer) {
            if(defendingPlayer.getCountries().size() == 0){
                return true;
            } 
            return false;
        }

        /**
         * prints out a welcome message
         */
    public void welcome() {

        System.out.println("Welcome to RISK: Global Domination!");
        System.out.println();
        System.out.println("RISK is a classic strategy game filled with conquest and intrigue.\n"
                + "This game can be played with 2-6 players." +
                "To win, you must strive to capture all of the continents and countries in the game, while eliminating other players.\n" +
                "The last man standing is the winner!\n");
    }

    /**
     * distributes countries to players
     */
    public void setCountry() {
        while (!(map.getCountryList().isEmpty())) {
            for (Player p : playerList) {
                p.addCountry(randomCountry(), 1);
                p.updateEnforcements(-1);
                if(map.getCountryList().isEmpty()){
                    break;
                }
            }
        }
        distributeTroops();
    }

    /**
     * sets the number of players
     * @param playerCount int
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * adds player names to a list 
     * @param players String
     */
    public void setPlayerNames(ArrayList<String> players) {
        for (String p: players){
            playerNames.add(p);
        }
    }

    /**
     * initiates player objects using the list of names
     */
    public void setPlayers(ArrayList<Boolean> playerType) {
        for (int i = 0; i < playerCount; i++) {
            playerList.add(new Player(playerNames.get(i), getInitialTroops(playerCount)));
        }
        for(int j = 0; j < playerCount; j++){
            playerList.get(j).setAsAI(playerType.get(j));
        }
    }

  
    public ArrayList<Player> showPlayerList(){
        return playerList;
    }
  
    /**
     * distributes the troops to each country of each player
     */

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

    /**
     * picks a random country from the list of countries
     * @return Country
     */
    public Country randomCountry() {
        Random inCountryList = new Random();
        int value = inCountryList.nextInt(map.getCountryList().size());
        Country temp = map.getCountryList().get(value);

        map.getCountryList().remove(temp);
        return temp;
    }


    /**
     * depending how many players picks the starting amount of troops
     * @param playerCount int
     * @return int
     */
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
        rolls = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            rolls.add(rollDice());
        }

        int maxValue = Collections.max(rolls);
        for (int i = 0; i < rolls.size(); i++) {
            if (rolls.get(i) == maxValue) {
                currentPlayer = playerList.get(i);
            }
        }
        bonusTroops();

        SwingUtilities.invokeLater(() -> {
            try {
                if(currentPlayer.getIsAI()){
                    playAI();
                    nextTurn();

                }
                else{
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void ifAIStarts(){

        while(currentPlayer.getIsAI()){
            playAI();
            nextTurn();
        }
    }

    public void nextTurn() {
        if (playerList.indexOf(currentPlayer) < (playerList.size() - 1)) {
            currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
        } else {
            currentPlayer = playerList.get(0);
        }
        bonusTroops();

        SwingUtilities.invokeLater(() -> {
            try {
                if(currentPlayer.getIsAI()){
                    playAI();
                    nextTurn();

                }
                else{
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public int rollDice() {
        rollToBegin = new Dice();
        return rollToBegin.getDiceValue();
    }


    public Player getDefendingPlayer(String country) {
        for (Player p : playerList) {
            if (p.hasCountry(country)) {
                return p;
            }
        }
        return null;       //added this or else error since not returning anything
    }

    public boolean checkGameOver(ArrayList players) {
        if(players.size() == 1){
            return true;
        }
        return false;
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

    /**
     * adds observers
     * @param view
     */
    public void addView(RiskView view) {
        viewList.add(view);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * returns the maximum amount of troops for an attacking country
     * @param country of type Country
     * @return int
     */
    public int getMaxAttackingTroops(Country country){
        int troops = currentPlayer.getPlayerData().get(country) - 1;
        if(troops > 3){
            return 3;
        } else{return troops;}
    }

    /**
     * returns the maximum amount of troops for a defending country 
     * @param country Country
     * @param player
     * @return int
     */
    public int getMaxDefendingTroops(Country country, Player player){
        if(player==null){
            System.out.println("Null Country");
        }
        int troops = player.getPlayerData().get(country);
        if(troops > 2){
            return 2;
        }
        else{return troops;}
    }

    /**
     * sets up the players given by the observer and updates the observers
     * @param playerNames ArrayList<String>
     * @param playerCount int
     */
    public void setUpPlayers(ArrayList<String> playerNames, int playerCount,ArrayList<Boolean> playerType) {
        setPlayerCount(playerCount);
        setPlayerNames(playerNames);
        setPlayers(playerType);
        setCountry();
        whoStarts();
        for (RiskView rV : viewList) {
            rV.handleInitialMap(new MapEvent(this, playerList));
        }
    }

    /**
     * initiates the attack from the information given by the controller
     * @param attackingCountry  Country
     * @param defendingCountry  Country
     * @param attackingTroops   int
     * @param defendingTroops   int
     */
    public void initiateAttack(Country attackingCountry, Country defendingCountry, int attackingTroops, int defendingTroops){
        //attack actually occurs, map gets updated
        attack(attackingCountry, defendingCountry, attackingTroops, defendingTroops);

        for (RiskView rV : viewList) {
            rV.handleMapChange(new MapEvent(this, playerList));
        }
    }

    /**
     * updates the adjacent list for a country, only displayes the enemies countries
     * @param country Country
     */
    public void updateEnemyAdjacentCountries(Country country){
        String[] allAdj = country.getAdjacentCountries();
        ArrayList<String> enemyCountries = new ArrayList<>();
        //here we will remove the countries that the player already owns
        for (String s : allAdj) {
            if(!currentPlayer.hasCountry(s)){
                enemyCountries.add(s);
            }
        }
        for (RiskView v : viewList) {
            v.handleAdjacentList(new ListEvent(this, enemyCountries));
        }
    }

    /**
     * updates the adjacent list for a country, only displayes the owned countries
     * @param country Country
     */
    public void updateOwnedAdjacentCountries(Country country){
        String[] allAdj = country.getAdjacentCountries();
        ArrayList<String> enemyCountries = new ArrayList<>();
        //here we will remove the countries that the player already owns
        for (String s : allAdj) {
            if(currentPlayer.hasCountry(s)){
                enemyCountries.add(s);
            }
        }
        for (RiskView v : viewList) {
            v.handleAdjacentList(new ListEvent(this, enemyCountries));
        }
    }

    /**
     * ends the game once there is only one player left
     */
    public void endGame(){
        for (RiskView v : viewList) {
            v.handleEndGame(new MapEvent(this, playerList));
        }
    }

    /**
     * returns the current player's available enforcements 
     */
    public int getAvailableEnforcement(){
        return currentPlayer.getAvailableEnforcement();
    }

    /**
     * fortifies the player's selected country
     * @param troops of type int
     * @param country   country being modified
     */
    public void fortify(int troops, Country country){

        System.out.println();
        System.out.println(currentPlayer.getName() + " is now placing bonus troops \n");
        currentPlayer.updateCountry(country, troops);
        currentPlayer.updateEnforcements(-troops);
        System.out.println("\n" + currentPlayer.getName() + " has now placed " + troops + " bonus troops in " + country.toString() + "\n");
        for (RiskView rV : viewList) {
            rV.handleMapChange(new MapEvent(this, playerList));
        }
    }

    /**
     * calculates and adds the bonus troops to the current player
     */
    public void bonusTroops(){

        int bonusTroops = 3;
        ArrayList<Country> countries = currentPlayer.getCountries();
        if(countries.size() > 11){
            double tempNum = countries.size()/3;
            bonusTroops = (int) tempNum;    //this removes the decimal places (rounding down)
        }
        //calculating continent bonuses
        for(Continent continent: map.getContinentList()){
            int numCountries = 0;   //number of countries in the continent
            for(Country country: countries){
                if(country.getContinent() == continent){
                    numCountries++;
                }
            }
            if(numCountries == continent.getNumCountries()){
                bonusTroops += continent.getExtraTroops();
            }
        }
        currentPlayer.updateEnforcements(bonusTroops);
    }

    /**
     * Maneuvers troops between countries
     * @param troops of type int
     * @param originCountry   country that troops leave
     * @param destinationCountry country that troops go to
     */
    public void maneuver(int troops, Country originCountry, Country destinationCountry){
        System.out.println();
        System.out.println(currentPlayer.getName() + " has now maneuvered " + troops + " troops from " + originCountry + " to " + destinationCountry + "\n");
        System.out.println();
        currentPlayer.updateCountry(originCountry, -troops);
        currentPlayer.updateCountry(destinationCountry, troops);
        for (RiskView rV : viewList) {
            rV.handleMapChange(new MapEvent(this, playerList));
        }
    }

}