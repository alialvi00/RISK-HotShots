import org.json.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;

public class RiskMap {
    ArrayList<Country> countryList;
    private ArrayList<Continent> continentList;

   
    public RiskMap(){
        continentList = new ArrayList<Continent>();
        countryList = new ArrayList<Country>(); 
        parseMapJson();       
    }

    public void parseMapJson(){
        //Path of the JSON file
        String mapPath = "C:\\Users\\hassa\\OneDrive\\University\\SYSC3110\\Project\\RISK-HotShots\\src\\DefaultMap.json";
        try {
             String jsonContents = new String((Files.readAllBytes(Paths.get(mapPath))));
            JSONObject o = new JSONObject(jsonContents);

            //to iterate over the continents
            Iterator<String> continents = o.keys();
            while(continents.hasNext()){
                 String continentKey = continents.next();
                 JSONObject continent = o.getJSONObject(continentKey);
                 //add each continent to the list of continents 
                 int numOfCountries = continent.getInt("numberOfCountries");
                 int extraTroops = continent.getInt("extraTroops");
                 Continent continentObject = createContinent(continentKey, numOfCountries, extraTroops);
                //to itterate over countries
                Iterator<String> countries = continent.getJSONObject("countries").keys();
                while(countries.hasNext()){                   
                     String countryName = countries.next();
                     JSONObject country = continent.getJSONObject("countries").getJSONObject(countryName);
                     JSONArray adjCountries = country.getJSONArray("adjacentCountries");
                     ArrayList<String> adjCountriesList = new ArrayList<String>();
                    for(int i = 0; i < adjCountries.length(); i++){
                        adjCountriesList.add(adjCountries.get(i).toString());
                    }
                    createCountry(countryName, continentObject, adjCountriesList);
                }
            }
              } catch (Exception e) {
                  e.printStackTrace();
              }
    }

    public Continent createContinent(String name, int numCountries, int extraTroops) {
        Continent continent = new Continent(name, numCountries, extraTroops);
        continentList.add(continent);
        return continent;
    }

    public void createCountry(String name, Continent continent, ArrayList<String> border) {
        Country country = new Country(name, continent);
        country.setBorders(border);
        countryList.add(country);
    }

    public boolean isValidCountry(String countryName){
        ArrayList<Country> list = getCountryList();
        for(Country c: list){
            if(c.toString().toLowerCase().equals(countryName)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Country> getCountryList() {
        ArrayList<Country> copyCountryList = countryList;
        return copyCountryList;
    }

    public ArrayList<Continent> getContinentList() {
        return continentList;
    }

}
