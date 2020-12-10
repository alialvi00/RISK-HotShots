import javax.swing.*;
import java.io.Serializable;
import java.util.*;

public class RiskModel implements Serializable {

    private RiskMap map;
    private int playerCount;
    private Player currentPlayer;
    private ArrayList<String> playerNames;
    private ArrayList<Player> playerList;
    private final List<RiskView> viewList;
    private int checkAttack;
    private Boolean fortifyPhase;
    private Boolean attackPhase;
    private Boolean maneuverPhase;

    public RiskModel() {
        playerNames = new ArrayList<>();
        playerList = new ArrayList<>();
        map = new RiskMap();
        viewList = new ArrayList<>();
        checkAttack = 0;
        fortifyPhase = true;
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

        fortifyPhase = false;
        int defendingTroopsLost = 0;
        int attackingTroopsLost = 0;
        int winningTroops = 0;
        Player defendingPlayer = getDefendingPlayer(defendingCountry.toString());


        ArrayList<Integer> attackingDice = currentPlayer.attackCountry(attackingTroops);
        ArrayList<Integer> defendingDice = defendingPlayer.defendCountry(defendingTroops);

        while (!(attackingDice.isEmpty() || defendingDice.isEmpty())) {
            if (Collections.max(attackingDice) > Collections.max(defendingDice)) {
                attackingDice.remove(Collections.max(attackingDice));
                defendingDice.remove(Collections.max(defendingDice));
                defendingPlayer.updateCountry(defendingCountry, -1);
                defendingTroopsLost++;
                winningTroops++;
                checkAttack++;

            } else if (Collections.max(attackingDice) <= Collections.max(defendingDice)) {
                attackingDice.remove(Collections.max(attackingDice));
                defendingDice.remove(Collections.max(defendingDice));
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
    public void playAI() throws InterruptedException {

        ArrayList<Country> countries;
        Random generatePick = new Random(); //generate first pick
        Random generatePercent = new Random(); //generate the chance percent

        int pick;
        int percent;
        int troops; //bonusTroops available

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

            attackAI();
            percent = generatePercent.nextInt(99); //percent = generatePercent.nextInt(99);
        }

        //Now we maneuver troops, make sure we maneuver to country that has enemy at its border so we can strengthen our country
        maneuverAI();

        Thread.sleep(200);
    }

    public int defendAI(int maxTroops){

        Random generateTroops = new Random();

        return generateTroops.nextInt(maxTroops)+1;
    }

    public void attackAI(){

        boolean attack = false;
        int attackTroops; //troops to attack with
        int defendTroops; //troops to defend with
        int troopsDefending;
        ArrayList<Country> attackCountry = new ArrayList<>();
        ArrayList<Country> defendingCountry = new ArrayList<>();
        Player defendingPlayer = null; //Initial assignment as dummy

        Random generatePick = new Random(); //generate first pick
        Random generateSecondPick = new Random(); //generate second pick

        int pick;
        int secondPick;

        for (int i = 0; i < currentPlayer.getCountries().size(); i++) {
            for (int j = 0; j < currentPlayer.getCountries().get(i).getAdjacentCountries().size(); j++) {

                boolean hasAdjacentConquered = currentPlayer.hasCountry(currentPlayer.getCountries().get(i).getAdjacentCountries().get(j));
                int num = currentPlayer.getPlayerData().get(currentPlayer.getCountries().get(i));

                attack = num > 1 && !hasAdjacentConquered;
            }
            if (attack) {
                attackCountry.add(currentPlayer.getCountries().get(i));
            }
        }

        //If AI made a choice to attack from a country
        if (attackCountry.size() > 0) {

            pick = generatePick.nextInt(attackCountry.size());

            for (int i = 0; i < attackCountry.get(pick).getAdjacentCountries().size(); i++) {

                boolean hasAdjacentConquered = currentPlayer.hasCountry(attackCountry.get(pick).getAdjacentCountries().get(i));

                if (!hasAdjacentConquered) {

                    defendingPlayer = getDefendingPlayer(attackCountry.get(pick).getAdjacentCountries().get(i));
                    defendingCountry.add(defendingPlayer.getCountryByName(attackCountry.get(pick).getAdjacentCountries().get(i)));
                }
            }
            //When AI has chosen defending countries
            if (defendingCountry.size() > 0) {

                secondPick = generateSecondPick.nextInt(defendingCountry.size());
                attackTroops = getMaxAttackingTroops(attackCountry.get(pick));

                if(attackTroops <= 0){
                    return;
                }

                if (defendingPlayer.hasCountry(defendingCountry.get(secondPick).toString())) {

                    defendTroops = getMaxDefendingTroops(defendingCountry.get(secondPick), defendingPlayer);

                    if(defendTroops>0) {

                        if (!defendingPlayer.getIsAI()) {
                            for (RiskView rv : viewList) {
                                defendTroops = rv.getDefendingTroops(defendTroops, defendingPlayer,defendingCountry.get(secondPick));
                                initiateAttack(attackCountry.get(pick), defendingCountry.get(secondPick), attackTroops, defendTroops);
                            }
                        } else {

                            troopsDefending = defendAI(defendTroops);
                            initiateAttack(attackCountry.get(pick), defendingCountry.get(secondPick), attackTroops, troopsDefending);
                        }
                    }
                }
            }
        }
    }

    public void maneuverAI(){

        fortifyPhase = false;
        boolean hasEnemy = false;
        boolean isOwned = false;
        int currentTroops;
        int troops; //bonusTroops available
        int pick;
        int secondPick;

        Random generateMoveTroops = new Random();
        Random generatePick = new Random(); //generate first pick
        Random generateSecondPick = new Random(); //generate second pick

        ArrayList<Country> originCountry = new ArrayList<>();
        ArrayList<Country> destinationCountry = new ArrayList<>();

        for (int i = 0; i < currentPlayer.getCountries().size(); i++) {
            for (int j = 0; j < currentPlayer.getCountries().get(i).getAdjacentCountries().size(); j++) {

                boolean hasAdjacentConquered = currentPlayer.hasCountry(currentPlayer.getCountries().get(i).getAdjacentCountries().get(j));

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

            for (int i = 0; i < destinationCountry.get(pick).getAdjacentCountries().size(); i++) {

                boolean hasAdjacentConquered = currentPlayer.hasCountry(destinationCountry.get(pick).getAdjacentCountries().get(i));

                if (hasAdjacentConquered) {

                    currentTroops = currentPlayer.getPlayerData().get(currentPlayer.getCountryByName(destinationCountry.get(pick).getAdjacentCountries().get(i)));

                    if(currentTroops>1)
                        originCountry.add(currentPlayer.getCountryByName(destinationCountry.get(pick).getAdjacentCountries().get(i)));
                }
            }
            //AI Algorithm has successfully chosen countries to transfer troops from
            if (!originCountry.isEmpty()) {

                secondPick = generateSecondPick.nextInt(originCountry.size());
                troops = currentPlayer.getPlayerData().get(currentPlayer.getCountryByName(originCountry.get(secondPick).toString()));
                int troopsToMove = generateMoveTroops.nextInt(troops)+1;

                if(troops-troopsToMove>0){
                    maneuver(troopsToMove, originCountry.get(secondPick), destinationCountry.get(pick));
                }
            }
        }
    }

    public boolean ifFortify(){
        return fortifyPhase;
    }

    public void setFortifyPhase(Boolean value){
        fortifyPhase = value;
    }

    public boolean getCheckAttack() {
        return checkAttack > 0;
    }

        /**
         * checks if a player lost all his countries
         * @param defendingPlayer is the player defending
         * @return boolean of whether player owns any country
         */
        private boolean checkPlayerLost(Player defendingPlayer) {
            return defendingPlayer.getCountries().size() == 0;
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
        playerNames.addAll(players);
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

    public void addPlayer(Player player){
        playerList.add(player);
    }

    /**
     * depending how many players picks the starting amount of troops
     * @param playerCount int
     * @return int
     */
    public int getInitialTroops(int playerCount) {
        return switch (playerCount) {
            case 2 -> 50;
            case 3 -> 35;
            case 4 -> 30;
            case 5 -> 25;
            default -> 20;
        };
    }

    public void whoStarts() {
        ArrayList<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            rolls.add(rollDice());
        }

        int maxValue = Collections.max(rolls);
        for (int i = 0; i < rolls.size(); i++) {
            if (rolls.get(i) == maxValue) {
                currentPlayer = playerList.get(i);
            }
        }

        fortifyPhase = true;
        bonusTroops();

        SwingUtilities.invokeLater(() -> {
            try {
                if(currentPlayer.getIsAI()){
                    playAI();
                    nextTurn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void ifAIStarts() throws InterruptedException {

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

        fortifyPhase = true;
        bonusTroops();

        for (RiskView rV : viewList) {
            rV.handleNewTurn(new MapEvent(this, playerList));
        }

        SwingUtilities.invokeLater(() -> {
            try {
                if(currentPlayer.getIsAI()){
                    playAI();
                    nextTurn();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public int rollDice() {
        Dice rollToBegin = new Dice();
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
        return players.size() == 1;
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
     * @param view adds view
     */
    public void addView(RiskView view) {
        viewList.add(view);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void updateCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    /**
     * returns the maximum amount of troops for an attacking country
     * @param country of type Country
     * @return int
     */
    public int getMaxAttackingTroops(Country country){
        int troops = currentPlayer.getPlayerData().get(country) - 1;
        return Math.min(troops, 3);
    }

    /**
     * returns the maximum amount of troops for a defending country 
     * @param country Country
     * @param player is the defending player
     * @return int
     */
    public int getMaxDefendingTroops(Country country, Player player){
        if(player==null){
            System.out.println("Null Country");
        }
        assert player != null;
        int troops = player.getPlayerData().get(country);
        return Math.min(troops, 2);
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
     * updates the adjacent list for a country, only displays the enemies countries
     * @param country Country
     */
    public void updateEnemyAdjacentCountries(Country country){
        ArrayList<String> allAdj = country.getAdjacentCountries();
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
     * updates the adjacent list for a country, only displays the owned countries
     * @param country Country
     */
    public void updateOwnedAdjacentCountries(Country country){
        ArrayList<String> allAdj = country.getAdjacentCountries();
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

        fortifyPhase = false;

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
     * @param originCountry   beginning country
     * @param destinationCountry final country to move to.

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

    /**
     * initiates the map 
     * @param fileName name of json file
     * @return true if parsing is successful, false otherwise
     */
    public boolean initiateMap(String fileName){
       return map.parseMapJson(fileName);
    }

    /**
     * validates the created map
     * @return true if valid
     */
    public boolean validateMap(){
        return map.validateMap();
    }

    /**
     * return list of continents
     * @return
     */
    public ArrayList<Continent> getContinents(){
        return map.getContinentList();
    }

}