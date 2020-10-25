import java.util.*;

public class Game{

    private Map map;

    int playerCount;

    ArrayList<Country> countryList;

    private ProcessInput input;

    private Random randInt;
    private Random forCountryList;


    public Game(){
        map = new Map();
        initializeGame();
    }

   public void initializeGame() {
        welcome();
        setPlayerCount();
        checkPlayerCount();
        distributeTroops(playerCount);
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

    public void welcome(){
        System.out.println("Welcome to RISK: Global Domination!");
        System.out.println("RISK is a classic strategy game filled with conquest and intrigue.\n"
        + "This game can be played with 2-6 players." + 
        "To win, you must strive to capture all of the continents and countries in the game, while eliminating other players.\n" +
        "The last man standing is the winner!");
        
    }

    public void distributeTroops(int playerCount) {
        randInt = new Random();
        int initialTroops = randInt.nextInt(getInitialTroops(playerCount) + 1);

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