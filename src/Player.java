import java.util.*;

public class Player{

    private String playerName;
    private int availableEnforcement;
    private Boolean isAI = false;
    private HashMap<Country, Integer> conqueredCountries;
    private Boolean isDefending;
    private Dice die;
    private RiskListener updateChanges;
    /**
     * constructor for the player class
     * the number of troops they each contain
     * @param name of type String 
     */
    public Player(String name, int availableEnforcement) {
        conqueredCountries = new HashMap<>();
        playerName = name;
        this.availableEnforcement = availableEnforcement;

    }

    /**
     * update the number of troops that can be used for reinforcement
     * @param newTroops of type int
     */
    public void updateEnforcements(int newTroops){
        availableEnforcement += newTroops;
    }

    /**
     * returns an ArrayList of countries that the player controls
     * @return an ArrayList of Country object
     */
    public ArrayList<Country> getCountries() {
        Set<Country> countrySet = conqueredCountries.keySet();
        ArrayList<Country> countryList = new ArrayList<>(countrySet);
        return countryList;


    }

    /**
     * returns the number of available enforcements
     * @return available enforcements of type int
     */
    public int getAvailableEnforcement() {
        return availableEnforcement;
    }

    /**
     * returns the amount of troops in a country
     * @param country of type Country
     * @return number of troops in int
     */
    public int getCountryTroops(String country) {
        for(Country c: getCountries()){
            if(c.toString().toLowerCase().equals(country)){
                return conqueredCountries.get(c);
            }
        }
        return 0;
    }
    
    /**
     * returns an arraylist containing the "rolled" dice value
     * @param troops number of troops used to defend of type int
     * @return arraylist of int object(s)
     */
    public ArrayList<Integer> defendCountry(int troops) {
        ArrayList<Integer> diceArray = new ArrayList<>();
        
        if (troops == 1){
            Dice die = new Dice();
            diceArray.add(die.getDiceValue());
            return diceArray;
        }
        else{
            Dice die1 = new Dice();
            Dice die2 = new Dice();
            diceArray.add(die1.getDiceValue());
            diceArray.add(die2.getDiceValue());
            return diceArray;
        }
    }

    /**
     * returns an arraylist containing the "rolled" dice value
     * @param troops number of troops attacking with of type int
     * @return arraylist int object(s)
     */
    public ArrayList<Integer> attackCountry(int troops) {
        ArrayList<Integer> diceArray = new ArrayList<>();
        
        if (troops == 1){
            Dice die = new Dice();
            diceArray.add(die.getDiceValue());
            return diceArray;
        }
        if (troops == 2){
            Dice die1 = new Dice();
            Dice die2 = new Dice();
            diceArray.add(die1.getDiceValue());
            diceArray.add(die2.getDiceValue());
            return diceArray;
        }

        else{
            Dice die1 = new Dice();
            Dice die2 = new Dice();
            Dice die3 = new Dice();
            diceArray.add(die1.getDiceValue());
            diceArray.add(die2.getDiceValue());
            diceArray.add(die3.getDiceValue());
            return diceArray;
        }
    }

    /**
     * adds a conquered country
     * @param country of type Country
     * @param troops of type int
     */
    public void addCountry(Country country, Integer troops) {
        conqueredCountries.put(country, troops);
    }

    /**
     * deletes a country 
     * @param country of type Country
     */
    public void deleteCountry(Country country) {
        conqueredCountries.remove(country);
    }

    /**
     * moves troops from the origin country to a destination country
     * @param origin country that troops are being moved from of type Country
     * @param destination country troops are moving to of type Country
     * @param troops number of troops moving of type int
     * @return returns true if it is successful or false if not
     */
   // public Boolean maneuverTroops(Country origin, Country destination, int troops){
     //   if(troops < conqueredCountries.get(origin)){
     //       conqueredCountries.put(origin, conqueredCountries.get(origin) - troops);
     //       conqueredCountries.put(destination, conqueredCountries.get(destination) + troops);
      //      return true;
    //    }
     //
    //    return false;
    //}

    /**
     * updates the number of troops in a country after an attack
     * @param country being updated of type Country
     * @param troops of type int
     */
    public void updateCountry(Country country, int troops){
        conqueredCountries.put(country, conqueredCountries.get(country) + troops);
    }

    public HashMap<Country, Integer> getPlayerData(){
        return conqueredCountries;
    }

    public String getName() {
        return playerName;
    }


    public Boolean hasCountry(String country){
        for(Country c: getCountries()){
            if(country.toLowerCase().equals(c.toString().toLowerCase())){
                return true;  
            }            
        }
        return false;
    }

    public Country getCountryByName(String countryName){
        for(Country c: getCountries()){
            if(countryName.toLowerCase().equals(c.toString().toLowerCase())){
                return c;  
            }            
        }
        return null;
    }

    public void setConqueredCountries(HashMap<Country, Integer> conqueredCountries) {
        this.conqueredCountries = conqueredCountries;
    }

    public HashMap<Country, Integer> getConqueredCountries(){
        return conqueredCountries;
    }

    public void setAsAI(Boolean isAI){
        this.isAI = isAI;
    }

    public Boolean getIsAI(){
        return isAI;
    }

}