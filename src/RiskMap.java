import org.json.*;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class RiskMap implements Serializable {

    ArrayList<Country> countryList;
    private ArrayList<Continent> continentList;

   
    public RiskMap(){
        continentList = new ArrayList<Continent>();
        countryList = new ArrayList<Country>();       
    }

    /**
     * this method parses the JSON map into continents and countries
     */
    public boolean parseMapJson(String filePath){
        String mapPath = "";
        if(filePath == null){
            System.out.println("nuldfsfdsfdsf");
            mapPath = "C:\\Users\\hassa\\OneDrive\\University\\SYSC3110\\Project\\RISK-HotShots\\library\\Maps\\DefaultMap.json"; //defaultPath
        } else{
            mapPath = "library\\Maps\\" + filePath;
        }
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
                    Country newCountry = createCountry(countryName, continentObject, adjCountriesList);
                    continentObject.addCountry(newCountry);
                }
                continentList.add(continentObject);
            }
            return true;
              } catch (Exception e) {
                  countryList.clear();
                  continentList.clear();
                  return false;
                  //e.printStackTrace();
              }
    }

    public Continent createContinent(String name, int numCountries, int extraTroops) {
        Continent continent = new Continent(name, numCountries, extraTroops);
        return continent;
    }

    public Country createCountry(String name, Continent continent, ArrayList<String> border) {
        Country country = new Country(name, continent);
        country.setBorders(border);
        countryList.add(country);
        return country;
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

    public Country getCountryByName(String name){
        for(Country c: countryList){
            if(name.toLowerCase().equals(c.toString().toLowerCase())){
                return c;  
            }            
        }
        return null;
    }

    /**
     * validates the json map
     * @return
     */
    public boolean validateMap(){
        for(Continent continent: continentList){
            if(!validateContinents(continent)){
                countryList.clear();
                continentList.clear();
                return false;
            }
        }
        for(Country country: countryList){
            if(!validateCountryLink(country)){
                countryList.clear();
                continentList.clear();
                return false;
            }
        }
        return true;
    }

    /**
     * this function checks if all areas (continents) are accessible 
     * @return boolean true if accessible false if not
     */
    public boolean validateContinents(Continent continent){
        for(Country country: continent.getCountryList()){
            for(String adj: country.getAdjacentCountries()){
                Country adjCountry = getCountryByName(adj);
                if(adjCountry.getContinent() != country.getContinent()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * validates the links between countries, i.e a country
     * should appear in the list of adjacent countries of its bordering countries
     * @param country
     * @return
     */
    public boolean validateCountryLink(Country country){
        for(String adj: country.getAdjacentCountries()){
            Country adjCountry = getCountryByName(adj);
            if(!adjCountry.getAdjacentCountries().contains(country.toString())){
                return false;
            }
        }
        return true;
    }

}
