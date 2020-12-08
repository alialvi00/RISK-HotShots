import java.util.ArrayList;

/**
 * Continent Class of the Continents in RISK. This class provides information about the continent's name,
 * amount of reinforcement troops it is eligible for and how many countries it contains.
 * @author Ali Alvi Areeb Haq Hassan Jallad Raj Sandhu
 * @version 1.0
 */


public class Continent{

    private String continentName;
    private int numCountries;
    private int extraTroops;
    private ArrayList<Country> countryList;


    public Continent(String continentName, int numCountries, int extraTroops){
        countryList = new ArrayList<Country>();
        this.continentName = continentName;
        this.numCountries = numCountries;
        this.extraTroops = extraTroops;
    }

    public int getNumCountries(){

        return numCountries;
    }

    public boolean isContinentFull(int amount){
        if(amount == numCountries){
            return true;
        }
        return false;
    }

    public int getExtraTroops(){
        return extraTroops;
    }

    public String getName() {
        return continentName;
    }

    public void addCountry(Country country){
        countryList.add(country);
    }

    public ArrayList<Country> getCountryList(){
        return countryList;
    }

}
