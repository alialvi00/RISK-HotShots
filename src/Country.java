import java.util.*;

/**
 * The following Country Class holds the Country names in the Game RISK, that players can attack or defend.
 * The class also sets the borders between countries with a simple setter method.
 * @author Ali Alvi Areeb Haq Hassan Jallad Raj Sandhu
 * @version 1.0
 */

public class Country {

    private String countryName;
    private ArrayList<String> adjacentCountries;
    private Continent continent;


    public Country(String countryName, Continent continent){

        this.countryName = countryName;
        adjacentCountries = new ArrayList<String>();
        this.continent = continent;
    }

    public void setBorders(ArrayList<String> border){
        adjacentCountries = border;
    }

    public ArrayList<String> getAdjacentCountries(){
        return adjacentCountries;
    }

    public boolean equals(String country) {
        if (country.equals(countryName)) {
            return true;
        }
        return false;
    }

    public String toString(){

        return countryName;
    }
    
    public Continent getContinent(){
        return continent;
    }
    
}
