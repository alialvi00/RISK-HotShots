import com.intellij.json.JsonParser;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class RiskMap implements Serializable {

    ArrayList<Country> countryList;
    private ArrayList<Continent> continentList;

   
    public RiskMap(){
        continentList = new ArrayList<>();
        countryList = new ArrayList<>();
    }

    /**
     * this method parses the JSON map into continents and countries
     */
    public boolean parseMapJson(String filePath) throws IOException, ParseException {
        String mapPath = "";
        JSONParser parser = new JSONParser();
        Object readFile;
        try{
            InputStream readLine;
            readLine = getClass().getResourceAsStream("DefaultMap.json");
            if(filePath != null) {
                readLine = getClass().getResourceAsStream("/" + filePath);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(readLine));
            readFile = parser.parse(reader);
        }
        catch(Exception e){
            if(filePath == null){
                mapPath = "library\\Maps\\DefaultMap.json"; //defaultPath
                FileReader fileReader = new FileReader(mapPath);
                readFile = parser.parse(fileReader);
            } else{
                mapPath = "library\\Maps\\" + filePath;
                FileReader fileReader = new FileReader(mapPath);
                readFile = parser.parse(fileReader);
            }
        }

        JSONObject o = (JSONObject)readFile;

        try {



            //to iterate over the continents
            Iterator continents = o.keySet().iterator();
            while(continents.hasNext()){
                 String continentKey = (String) continents.next();
                 JSONObject continent = (JSONObject) o.get(continentKey);
                 //add each continent to the list of continents
                 int numOfCountries = ((Long)continent.get("numberOfCountries")).intValue();
                 int extraTroops = ((Long)continent.get("extraTroops")).intValue();
                 Continent continentObject = createContinent(continentKey, numOfCountries, extraTroops);
                //to iterate over countries
                JSONObject allCountries = (JSONObject)continent.get("countries");
                Iterator countries = allCountries.keySet().iterator();

                while(countries.hasNext()){
                     String countryName = (String) countries.next();
                     JSONObject country = (JSONObject) ((JSONObject) continent.get("countries")).get(countryName);
                     JSONArray adjCountries = (JSONArray) country.get("adjacentCountries");
                     ArrayList<String> adjCountriesList = new ArrayList<>();
                    for(int i = 0; i < adjCountries.size(); i++){
                        adjCountriesList.add(adjCountries.get(i).toString());
                    }
                    Country newCountry = createCountry(countryName, continentObject, adjCountriesList);
                    continentObject.addCountry(newCountry);
                }
                continentList.add(continentObject);
            }
            return true;
        }catch (Exception e) {
              countryList.clear();
              continentList.clear();
              e.printStackTrace();
              return false;

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
            System.out.println(country.toString());
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

    /**public static void main(String[] args){
        RiskMap m = new RiskMap();
        m.parseMapJson("custom1.json");
        m.validateMap();
     }
    **/
}
