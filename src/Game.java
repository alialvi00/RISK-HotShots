import java.util.*;

public class Game{

    private Map map;
    private ProcessInput input;
    private Random randInt;
    private Random forCountryList;
    private Dice rollToBegin;
    private Player starter;
    private Command userCommand;
    private int playerCount;
    private int currentPlayer;
    private int attackingTroops;
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
        map = new Map();
        initializeGame();
    }

    public void attack() {
        if(userCommand.ifSecondCommand()){
            System.out.println("attack what?");
        }
        else{
            Scanner attackInput = new Scanner(System.in);
            System.out.println("Which continent to launch an attack from? \n");
            String continentOrigin = attackInput.nextLine();
            continentOrigin.toLowerCase();
            Continent currentCont = map.getContinent(continentOrigin);

            if(currentCont != null) {

                System.out.println("Among the countries that you rule in" + continentOrigin + ", where do you want to launch the attack from? \n");
                String countryOrigin = attackInput.nextLine();
                if(map.checkCountry(countryOrigin)) {
                    Country originCountry = new Country(countryOrigin, currentCont);
                }
                else{
                    System.out.println("Not a valid country in " + continentOrigin);
                    attack();
                }
            }
        }
    }

    public void play(){


        welcome();
        setPlayerCount();
        checkPlayerCount();
        setPlayerNames();
        setPlayers();
        setCountry();
        System.out.println("The game will now start");
        System.out.println("The current state of map is: ");
        stateOfMap();
        System.out.println("Dice is now being rolled for all" + playerCount + "players.");
        whoStarts();
        System.out.println("Player" + currentPlayer + "has the highest number rolled so they will start");
        System.out.println("Player" + currentPlayer + "has the choice to pass turn or attack");
        System.out.println("If attacking, refer to the map given and choose an enemy country");
        boolean gameEnded = false;
        while(!gameEnded){
            Command command = readInput.getCommand();
            userCommand = command;
            gameEnded = processCommand();
        }
        System.out.println("Game over");
    }

   public void initializeGame() {
        map.initializeMap();
        play();
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
            for(int i = 0; i < playerCount; i++){

                int x = distributeTroops(playerCount);
                if(x != -1)
                    playerList.get(i).addCountry(randomCountry(), x);
            }
        }
    }

    public void checkPlayerCount() {
       if(playerCount < 2){
            System.out.println("This game requires atleast 2 players");
            setPlayerCount();
        }
        else if(playerCount > 6){
            System.out.println("The maximum amount of players allowed is 6");
            setPlayerCount();
        }
   }

   public void setPlayerCount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the amount of player: ");
        playerCount = Integer.parseInt(input.nextLine());
        input.close();
   }

   public void setPlayerNames(){
       Scanner input = new Scanner(System.in);
       for(int i = 1; i < playerCount; i++ ) {
           System.out.println("Please enter the name for Player" + (i+1));
           playerNames.add(input.nextLine());
       }
       input.close();
   }

    public void setPlayers(){

        for(int i = 0; i < playerCount; i++){

            playerList.add(new Player(playerNames.get(i)));
        }
    }

    public int distributeTroops(int playerCount) {

        randInt = new Random();

        int totalTroops = getInitialTroops(playerCount);
        int randomTroops = randInt.nextInt(getInitialTroops(playerCount))+1;
        int currentTroops = totalTroops - randomTroops;
        if (currentTroops > 0 && (!(currentTroops < 3))){
            return randomTroops;
        }
        return -1;

    }

    public Country randomCountry(){
        forCountryList = new Random();
        int value = forCountryList.nextInt(map.getCountryList().size());
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
                currentPlayer = i;
            }
        }
    }

    public int nextTurn() {
        if(currentPlayer < playerCount && currentPlayer != 5) {
            currentPlayer += 1;
        }
        else if(currentPlayer == 5){
            currentPlayer = 0;
        }
        return currentPlayer;
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
            System.out.println("Player" + currentPlayer + "has skipped its turn");
            currentPlayer += 1;
        }
    }


    public void stateOfMap(){

        for (int i = 0; i < playerCount; i++) {

            for (Object eachCountry : playerList.get(i).getPlayerData().keySet()) {

                String key = eachCountry.toString();
                String numTroops = playerList.get(i).getPlayerData().get(key).toString();
                System.out.println("Player" + i + "has");
                System.out.println(key + " " + numTroops);
            }
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



}