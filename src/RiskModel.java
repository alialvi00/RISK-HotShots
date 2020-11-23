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
    private Boolean bonusTroops = true;
    private Boolean attackPhase = false;
    private Boolean maneuverPhase = false;

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
     * @param defendingPlayer   player being attacked
     */
    public void attack(Country attackingCountry, Country defendingCountry, int attackingTroops, int defendingTroops, Player defendingPlayer) {

        attackPhase = true;
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
    public void playAI(){

        //Bonus Troops Placement
        if(bonusTroops == true){

        }

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
        bonusTroops();
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
        bonusTroops();
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
     * @param defendingPlayer   Player
     */
    public void initiateAttack(Country attackingCountry, Country defendingCountry, int attackingTroops, int defendingTroops, Player defendingPlayer){
        //attack actually occurs, map gets updated
        attack(attackingCountry, defendingCountry, attackingTroops, defendingTroops, defendingPlayer);

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
     * @param country   country bring modified
     */
    public void fortify(int troops, Country country){
        currentPlayer.updateCountry(country, troops);
        currentPlayer.updateEnforcements(-troops);
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
            bonusTroops = (int) tempNum;    //this removes the decimel places (rounding down)
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
     * @param country   country bring modified
     */
    public void manuever(int troops, Country originCountry, Country destinationCountry){
        currentPlayer.updateCountry(originCountry, -troops);
        currentPlayer.updateCountry(destinationCountry, troops);
        for (RiskView rV : viewList) {
            rV.handleMapChange(new MapEvent(this, playerList));
        }
    }

}