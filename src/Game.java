import java.util.*;

public class Game{

    private Map map;
    private ProcessInput input;
    private Random randInt;
    private Random forCountryList;
    private Dice rollToBegin;
    private Player starter;
    private int playerCount;
    private int firstPlayer;
    private int currentPlayer;
    private int attackingTroops;
    private ProcessInput readInput;
    private ArrayList<String> playerNames;
    private ArrayList<Player> playerList;
    private ArrayList<Integer> rolls;
    private Boolean isGameOver;

    public Game(){

        playerNames = new ArrayList<>();
        playerList = new ArrayList<>();
        isGameOver = false;
        map = new Map();
        initializeGame();
    }

    public void attack(Command command) {
        if(command.ifSecondCommand()){
            System.out.println("attack what?");
        }
        else{
            playerList.get(playerTurn()).attackCountry(attackingTroops);
        }
    }

    public void play(){

        boolean gameEnded = false;
        while(!gameEnded){
            Command command = readInput.getCommand();
            gameEnded = processCommand(command);
        }
        System.out.println("Game over");
    }

   public void initializeGame() {
        welcome();
        setPlayerCount();
        checkPlayerCount();
        setPlayerNames();
        setPlayers();
        setCountry();
        distributeTroops(playerCount);
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

    public int whoStarts(){
        rolls = new ArrayList<Integer>();
        for(int i =0; i < playerList.size(); i++) {
            rolls.add(rollDice());
        }

        int maxValue = Collections.max(rolls);
        for (int i = 0; i < rolls.size(); i++){
            if(rolls.get(i) == maxValue){
                firstPlayer = i;
            }
        }
        return firstPlayer;
    }

    public int playerTurn() {
        currentPlayer = whoStarts();
        return currentPlayer;
    }


    public int rollDice(){
        rollToBegin = new Dice();
        return rollToBegin.getDiceValue();
    }

    public void passTurn(Command command){
        if(!command.ifSecondCommand()){
            System.out.println("Play what?");
        }
        else {
            System.out.println("Player" + playerTurn() + "has skipped its turn");
            currentPlayer += 1;
        }
    }

    public void stateOfMap(Command command){

        if(command.ifSecondCommand()){
            System.out.println("Map what?");
        }
        else {
            for (int i = 0; i < playerCount; i++) {

                for (Object eachCountry : playerList.get(i).getPlayerData().keySet()) {

                    String key = eachCountry.toString();
                    String numTroops = playerList.get(i).getPlayerData().get(key).toString();
                    System.out.println("Player" + i + "has");
                    System.out.println(key + " " + numTroops);
                }
            }
        }
    }

    public boolean quit(Command command){
        if(command.ifSecondCommand()){
            System.out.println("Quit what?");
            return false;
        }
        else{
            return true;
        }
    }

    public boolean processCommand(Command userCommand){

        boolean quitGame = false;
        CommandWords commandWord = userCommand.getFirstCommand();

        switch(commandWord){

            case INCORRECT:
                System.out.println("Wrong command, please enter a command from the available commands");
            case PLAY:
                play();
            case PASS:
                passTurn(userCommand);
                System.out.println("Next player turn");
            case ATTACK:
                Scanner input = new Scanner(System.in);
                System.out.println("With how many troops to attack with?");
                int num = Integer.parseInt(input.nextLine());
                attackingTroops = num;
                attack(userCommand);
            case QUIT:
                quitGame = quit(userCommand);
            case MAP:
                stateOfMap(userCommand);
        }
        return quitGame;
    }



}