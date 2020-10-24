import java.util.*;

public class Player{

    private String playerName;
    private int availableEnforcement;
    private HashMap<Country, Integer> conqueredCountries;
    private Boolean isDefending;

    /**
     * constructor for the player class
     * @param playerCountries of type HashMap<Country, Integer> country the player owns and
     * the number of troops they each contain
     * @param name of type String 
     */
    public Player(HashMap<Country, Integer> playerCountries, String name) {
        conqueredCountries = playerCountries;
        playerName = name;
        availableEnforcement = 0;
        isDefending = false;
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
        ArrayList<Country> countryList = new ArrayList<Country>(countrySet);
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
    public int getCountryTroops(Country country) {
        return conqueredCountries.get(country);
    }
    
    /**
     * returns an array containing the "rolled" dice value 
     * @param countryName name of the country being attacked of type String
     * @param troops number of troops used to defend of type int
     * @return array of int object(s)
     */
    public int[] defendCountry(String countryName, int troops) {
        int[] diceArray = new int[troops];
        
        if (troops == 1){
            Dice die = new Dice();
            diceArray[0] = die.getDiceValue();
            return diceArray;
        }
        else{
            Dice die1 = new Dice();
            Dice die2 = new Dice();
            diceArray[0] = die1.getDiceValue();
            diceArray[1] = die2.getDiceValue();
            return diceArray;
        }
        
    }

    /**
     * returns an array containing the "rolled" dice value
     * @param countryName county attacking from of type String
     * @param troops number of troops attacking with of type int
     * @return array int object(s)
     */
    public int[] attackCountry(String countryName, int troops) {
        int[] diceArray = new int[troops];
        
        if (troops == 1){
            Dice die = new Dice();
            diceArray[0] = die.getDiceValue();
            return diceArray;
        }
        if (troops == 2){
            Dice die1 = new Dice();
            Dice die2 = new Dice();
            diceArray[0] = die1.getDiceValue();
            diceArray[1] = die2.getDiceValue();
            return diceArray;
        }

        else{
            Dice die1 = new Dice();
            Dice die2 = new Dice();
            Dice die3 = new Dice();
            diceArray[0] = die1.getDiceValue();
            diceArray[1] = die2.getDiceValue();
            diceArray[1] = die3.getDiceValue();
            return diceArray;
        }
    }

    /**
     * adds a conquered country
     * @param country of type Country
     * @param troops of type int
     */
    public void addCountry(Country country, int troops) {
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
    public Boolean maneuverTroops(Country origin, Country destination, int troops){
        if(troops < conqueredCountries.get(origin)){
            conqueredCountries.put(origin, conqueredCountries.get(origin) - troops);
            conqueredCountries.put(destination, conqueredCountries.get(destination) + troops);
            return true;
        }
        
        return false;
    }

    /**
     * updates the number of troops in a country after an attack
     * @param country being updated of type Country
     * @param troops of type int
     */
    public void updateCountry(Country country, int troops){
        conqueredCountries.put(country, conqueredCountries.get(country) + troops);
    }
}