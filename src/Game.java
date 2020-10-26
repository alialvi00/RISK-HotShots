import java.util.*;

public class Game{

    private Map map;
    private ProcessInput input;
    private Random randInt;
    private Random forCountryList;

    int playerCount;
    private ArrayList<String> playerNames;
    private ArrayList<Player> playerList;

    public Game(){

        playerNames = new ArrayList<>();
        playerList = new ArrayList<>();
        map = new Map();
        initializeGame();
    }


   public void initializeGame() {
        welcome();
        setPlayerCount();
        checkPlayerCount();
        setPlayerNames();
        setPlayers();
        setCountry();
        distributeTroops(playerCount);
   }

    public void welcome(){

        System.out.println("Welcome to RISK: Global Domination!");
        System.out.println("RISK is a classic strategy game filled with conquest and intrigue.\n"
        + "This game can be played with 2-6 players." +
        "To win, you must strive to capture all of the continents and countries in the game, while eliminating other players.\n" +
        "The last man standing is the winner!");
    }


    public Country randomCountry(){
        forCountryList = new Random();
        int value = forCountryList.nextInt(map.getCountryList().size());
        Country selectedCountry = map.getCountryList().get(value);

        map.getCountryList().remove(selectedCountry);
        return selectedCountry;
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


    public static void main(String[] args) {
        Game game1 = new Game();
        Country s = game1.randomCountry();
        System.out.print(game1.map.getCountryList());


    }


}