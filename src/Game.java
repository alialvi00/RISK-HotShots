import java.lang.reflect.Array;
import java.util.*;

public class Game{

    private riskMap map;
    private ProcessInput input;
    private Dice rollToBegin;
    private Command userCommand;
    private int playerCount;
    private Player currentPlayer;
    private ProcessInput readInput;
    private ArrayList<String> playerNames;
    private ArrayList<Player> playerList;
    private ArrayList<Integer> rolls;
    private Boolean isGameOver;

    public Game(){

        readInput = new ProcessInput();
        playerNames = new ArrayList<>();
        playerList = new ArrayList<>();
        isGameOver = false;
        map = new riskMap();
        play();
    }

    public void attack() {
        if(userCommand.ifSecondCommand()){
            System.out.println("attack what?");
        }
        else{
            Scanner attackInput = new Scanner(System.in);
            System.out.println("Which country to launch an attack from?");      
            String attackingCountry = attackInput.nextLine();
            attackingCountry.toLowerCase();
            while(!checkCountry(attackingCountry, currentPlayer)){
                System.out.println("Please enter a country you rule: ");
                attackingCountry = attackInput.nextLine();
                attackingCountry.toLowerCase();
            }
            

            System.out.println("You can attack the following countries from " + attackingCountry + ":\n");
            for(String s: currentPlayer.getCountryByName(attackingCountry).getAdjacentCountries()){
                System.out.println(s);
            }
            System.out.println("Which country would you like to attack?");
            String defendingCountry = attackInput.nextLine();
            defendingCountry.toLowerCase();
            while(checkCountry(defendingCountry, currentPlayer) && !checkAdjacency(attackingCountry, defendingCountry)){
                System.out.println("Please enter a country you DO NOT rule and IS bordering your country: ");
                defendingCountry = attackInput.nextLine();
                defendingCountry.toLowerCase();
            }


            System.out.println("How many troops would you like to attack with?");
            int attackingTroops = Integer.parseInt(attackInput.nextLine());
            while(!checkAttackingTroops(attackingCountry, currentPlayer, attackingTroops)){
                System.out.println("Invalid. You can attack with maximum of 3 troops and must leave atleast 1 troop in the country.\n" + 
                "Please enter another amount: ");
                attackingTroops = Integer.parseInt(attackInput.nextLine());
            }

            Player defendingPlayer = getDefendingPlayer(defendingCountry);

            System.out.println(defendingPlayer.getName() + ", " + defendingCountry + " is being attacked!\n" + 
            "How many troops would you like to defend with?");
            int defendingTroops = Integer.parseInt(attackInput.nextLine());
            while(!checkDefendingTroops(defendingCountry, defendingPlayer, defendingTroops)){
                System.out.println("Invalid, you can only use a maximum of 2 troops to defend and not exceed the amount of troops in your country\n" 
                + "Please enter another amount: ");
                defendingTroops = Integer.parseInt(attackInput.nextLine());
            }

            ArrayList<Integer> attackingDice = currentPlayer.attackCountry(attackingTroops);
            ArrayList<Integer> defendingDice = defendingPlayer.attackCountry(defendingTroops);

            while (!(attackingDice.isEmpty() && defendingDice.isEmpty())){
                if(Collections.max(attackingDice) > Collections.max(defendingDice)){
                    attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                    defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                    defendingPlayer.updateCountry(defendingPlayer.getCountryByName(defendingCountry), -1);
                }
                else if(Collections.max(attackingDice) <= Collections.max(defendingDice)){
                    attackingDice.remove(attackingDice.indexOf(Collections.max(attackingDice)));
                    defendingDice.remove(defendingDice.indexOf(Collections.max(defendingDice)));
                    currentPlayer.updateCountry(currentPlayer.getCountryByName(attackingCountry), -1);
                }
            }

            if(defendingPlayer.getCountryTroops(defendingCountry) == 0){
                defendingPlayer.deleteCountry(defendingPlayer.getCountryByName(defendingCountry));
                currentPlayer.addCountry(currentPlayer.getCountryByName(defendingCountry), attackingDice.size());
            }

            stateOfMap();

        }
    }

    public void play(){
        welcome();
        setPlayerCount();
        setPlayerNames();
        setPlayers();
        setCountry();
        System.out.println("The game will now start");
        System.out.println("The current state of map is: ");
        stateOfMap();
        System.out.println("Dice is now being rolled for all " + playerCount + " players.");
        whoStarts();
        System.out.println(currentPlayer.getName() + " has the highest number rolled so they will start");
        System.out.println(currentPlayer.getName() + " has the choice to pass or attack");
        System.out.println("If attacking, refer to the map given and choose an enemy country");
        boolean gameEnded = false;
        while(!gameEnded){
            Command command = readInput.getCommand();
            userCommand = command;
            gameEnded = processCommand();
        }
        System.out.println("Game over");
    }


    public void welcome(){

        System.out.println("Welcome to RISK: Global Domination!");
        System.out.println("RISK is a classic strategy game filled with conquest and intrigue.\n"
        + "This game can be played with 2-6 players." +
        "To win, you must strive to capture all of the continents and countries in the game, while eliminating other players.\n" +
        "The last man standing is the winner!");
        System.out.println("Your commands are: play, attack, quit, pass turn");
    }


    public void setCountry(){
        while(!(map.getCountryList().isEmpty())){
            for(Player p: playerList){
                    p.addCountry(randomCountry(), 1);
                    p.updateEnforcements(-1);
            }
        }
        distributeTroops();
        
    }

   public void setPlayerCount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the amount of player: ");
        playerCount = Integer.parseInt(input.nextLine());  
        while(playerCount > 6 || playerCount < 2){
            System.out.println("Invalid player count, only 2-6 players are allowed \n" + "please enter the amount of players again: ");
            playerCount = Integer.parseInt(input.nextLine());
        }

   }

   public void setPlayerNames(){
       Scanner input = new Scanner(System.in);
       for(int i = 0; i < playerCount; i++ ) {
           System.out.println("Please enter the name for Player" + (i+1));
           playerNames.add(input.nextLine());
       }
   }

    public void setPlayers(){
        for(int i = 0; i < playerCount; i++){
            playerList.add(new Player(playerNames.get(i), getInitialTroops(playerCount)));
        }
    }

    public void distributeTroops() {

        Random randInt = new Random();

        for(Player p: playerList){
            while(!(p.getAvailableEnforcement() <= 0)){
            int randomTroops = randInt.nextInt(2);
            for(Country c: p.getCountries()){
                if((p.getAvailableEnforcement() - randomTroops) >= 0){
                        p.updateCountry(c, randomTroops);
                        p.updateEnforcements(-randomTroops);
                    }
            } 
            }
        }
    }

    public Country randomCountry(){
        Random inCountryList = new Random();
        int value = inCountryList.nextInt(map.getCountryList().size());
        Country temp = map.getCountryList().get(value);

        map.getCountryList().remove(temp);
        return temp;
    }

    public int getInitialTroops(int playerCount) {
        switch (playerCount){
            case 2: return 50;
            case 3: return 35;
            case 4: return 30;
            case 5: return 25;
        }
        return 20;
    }

    public void whoStarts(){
        rolls = new ArrayList<Integer>();
        for(int i =0; i < playerList.size(); i++) {
            rolls.add(rollDice());
        }

        int maxValue = Collections.max(rolls);
        for (int i = 0; i < rolls.size(); i++){
            if(rolls.get(i) == maxValue){
                currentPlayer = playerList.get(i);
            }
        }
    }

    public void nextTurn() {
        if(playerList.indexOf(currentPlayer) < (playerList.size() - 1)) {
            currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + 1);
        }
        else{
            currentPlayer = playerList.get(0);
        }
    }


    public int rollDice(){
        rollToBegin = new Dice();
        return rollToBegin.getDiceValue();
    }

    public void passTurn(){
        if(!userCommand.ifSecondCommand()){
            System.out.println("Pass what?");
        }
        else {
            System.out.println(currentPlayer.getName() + " has skipped its turn");
            nextTurn();
        }
    }


    public void stateOfMap(){
        for(Player p: playerList){
            System.out.println(p.getName() + "'s Countries: ");
            for(Country c: p.getPlayerData().keySet()){
                System.out.println(c + ": " + p.getPlayerData().get(c));
            }
            System.out.println("\n");
        }
    }

    public boolean quit(){
        if(userCommand.ifSecondCommand()){
            System.out.println("Quit what?");
            return false;
        }
        else{
            return true;
        }
    }

    public boolean processCommand(){

        boolean quitGame = false;
        CommandWords commandWord = userCommand.getFirstCommand();

        switch(commandWord){

            case INCORRECT:
                System.out.println("Wrong command, please enter a command from the available commands");
                break;
            case PASS:
                passTurn();
                System.out.println("Next player turn");
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
        }
        return quitGame;
    }

    public Boolean checkCountry(String country, Player player) {
        return player.hasCountry(country);
    }

    public Boolean checkAttackingTroops(String country, Player player, int troops) {
        if(troops == 0 || troops > 3){
            return false;
        }        
        else if(troops < player.getCountryTroops(country)){
            return true;
        }
        return false;
    }

    public Boolean checkDefendingTroops(String country, Player player, int troops) {
        if(troops == 0 || troops > 2){
            return false;
        } 
        else if(troops <= player.getCountryTroops(country)){
            return true;
        }
        return false;
    }

    public Player getDefendingPlayer(String country) {
        for(Player p: playerList){
            if(p.hasCountry(country)){
                return p;
            }
        }
        return null;       //added this or else error since not returning anything
    }

    public Boolean checkAdjacency(String attackingCountry, String defendingCountry) {
        for(String s: currentPlayer.getCountryByName(attackingCountry).getAdjacentCountries()){
            if(defendingCountry.equals(s.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Game game1 = new Game();
    }



}