
import java.util.*;

public class RiskMap {
    private Continent northAmerica;
    private Continent europe;
    private Continent africa;
    private Continent asia;
    private Continent australia;
    private Continent southAmerica;

    //Arraylists of countries
    private ArrayList<Country> northAmericaList;
    private ArrayList<Country> europeList;
    private ArrayList<Country> africaList;
    private ArrayList<Country> asiaList;
    private ArrayList<Country> australiaList;
    private ArrayList<Country> southAmericaList;

    //North America
    private Country alaska;
    private Country alberta;
    private Country centralAmerica;
    private Country easternUS;
    private Country greenland;
    private Country northwestT;
    private Country ontario;
    private Country quebec;
    private Country westernUS;

    //South America
    private Country argentina;
    private Country brazil;
    private Country peru;
    private Country venezuela;

    //Africa
    private Country congo;
    private Country eastAfrica;
    private Country egypt;
    private Country madagascar;
    private Country northAfrica;
    private Country southAfrica;

    //ASIA
    private Country afghanistan;
    private Country china;
    private Country india;
    private Country irkutsk;
    private Country japan;
    private Country kamchatka;
    private Country middleEast;
    private Country mongolia;
    private Country siam;
    private Country siberia;
    private Country ural;
    private Country yakutsk;

    //EUROPE
    private Country greatBritain;
    private Country iceland;
    private Country northernEurope;
    private Country scandinavia;
    private Country southernEurope;
    private Country ukraine;
    private Country westernEurope;

    //AUSTRALIA
    private Country indonesia;
    private Country newGuinea;
    private Country westernAustralia;
    private Country easternAustralia;

    ArrayList<Country> countryList;
    private ArrayList<Continent> continentList;


    public RiskMap(){

        continentList = new ArrayList<Continent>();

        northAmerica = new Continent("North America", 9, 5);
        europe = new Continent("Europe", 7, 5);
        africa = new Continent("Africa", 6, 3);
        asia = new Continent("Asia", 12, 7);
        australia = new Continent("Australia", 4, 2);
        southAmerica = new Continent("South America", 4, 2);

        initializeMap();
        createContinentList();
        countryList = createCountryList();        
    }

    public void createContinentList() {
        continentList.add(northAmerica);
        continentList.add(europe);
        continentList.add(africa);
        continentList.add(asia);
        continentList.add(australia);
        continentList.add(southAmerica);
    }

    public ArrayList<Country> createCountryList() {

        ArrayList<Country> list = new ArrayList<Country>();

        for(Country c: northAmericaList){
            list.add(c);
        }
        for(Country c: europeList){
            list.add(c);
        }
        for(Country c: asiaList){
            list.add(c);
        }
        for(Country c: africaList){
            list.add(c);
        }
        for(Country c: australiaList){
            list.add(c);
        }
        for(Country c: southAmericaList){
            list.add(c);
        }

        return list;
    }

    public boolean isValidCountry(String countryName){
        ArrayList<Country> list = createCountryList();
        for(Country c: list){
            if(c.toString().toLowerCase().equals(countryName)){
                return true;
            }
        }
        return false;
    }


    public void initializeMap() {


        //countries in north america
        alaska = new Country("Alaska", northAmerica);
        String[] alaskaAdj = new String[]{"Kamchatka", "Alberta", "Northwest Territory"};
        alaska.setBorders(alaskaAdj);

        alberta = new Country("Alberta", northAmerica);
        String[] albertaAdj = new String[]{"Alaska", "Northwest Territory", "Ontario", "Western United States"};
        alberta.setBorders(albertaAdj);

        centralAmerica = new Country("Central America", northAmerica);
        String[] centralAdj = new String[]{"Western United States", "Eastern United States", "Venezuela"};
        centralAmerica.setBorders(centralAdj);

        easternUS = new Country("Eastern United States", northAmerica);
        String[] easternAdj = new String[]{"Western United States", "Central America", "Ontario", "Quebec"};
        easternUS.setBorders(easternAdj);

        greenland = new Country("Greenland", northAmerica);
        String[] greenlandAdj = new String[]{"Quebec", "Ontario", "Northwest Territory", "Iceland"};
        greenland.setBorders(greenlandAdj);

        northwestT = new Country("Northwest Territory", northAmerica);
        String[] northwestAdj = new String[]{"Alaska", "Alberta", "Greenland", "Ontario"};
        northwestT.setBorders(northwestAdj);

        ontario = new Country("Ontario", northAmerica);
        String[] ontarioAdj = new String[]{"Greenland", "Alberta", "Northwest Territory", "Quebec", "Western United States", "Eastern United States"};
        ontario.setBorders(ontarioAdj);

        quebec = new Country("Quebec", northAmerica);
        String[] quebecAdj = new String[]{"Ontario", "Greenland", "Eastern United States"};
        quebec.setBorders(quebecAdj);

        westernUS =  new Country("Western United States", northAmerica);
        String[] westernAdj = new String[]{"Alberta", "Ontario", "Eastern United States", "Central America"};
        westernUS.setBorders(westernAdj);

        northAmericaList = new ArrayList<Country>();
        northAmericaList.add(ontario);
        northAmericaList.add(westernUS);
        northAmericaList.add(alaska);
        northAmericaList.add(greenland);
        northAmericaList.add(northwestT);
        northAmericaList.add(easternUS);
        northAmericaList.add(quebec);
        northAmericaList.add(centralAmerica);
        northAmericaList.add(alberta);

        //countries in south america
        argentina = new Country("Argentina", southAmerica);
        String[] argentinaAdj = new String[]{"Venezuela", "Brazil", "Peru"};
        argentina.setBorders(argentinaAdj);

        brazil = new Country("Brazil", southAmerica);
        String[] brazilAdj = new String[]{"Venezuela", "Argentina", "Peru", "North Africa"};
        brazil.setBorders(brazilAdj);

        venezuela = new Country("Venezuela", southAmerica);
        String[] venezuelaAdj = new String[]{"Brazil", "Peru"};
        venezuela.setBorders(venezuelaAdj);

        peru = new Country("Peru", southAmerica);
        String[] peruAdj = new String[]{"Brazil", "Argentina", "Venezuela"};
        peru.setBorders(peruAdj);

        southAmericaList = new ArrayList<Country>();
        southAmericaList.add(argentina);
        southAmericaList.add(brazil);
        southAmericaList.add(peru);
        southAmericaList.add(venezuela);

        //countries in africa
        congo = new Country("Congo", africa);
        String[] congoAdj = new String[]{"North Africa", "East Africa", "South Africa"};
        congo.setBorders(congoAdj);

        eastAfrica = new Country("East Africa", africa);
        String[] eastAfricaAdj = new String[]{"Egypt", "North Africa", "Congo", "South Africa", "Madagascar", "Middle East"};
        eastAfrica.setBorders(eastAfricaAdj);

        egypt = new Country("Egypt", africa);
        String[] egyptAdj = new String[]{"Middle East", "East Africa", "North Africa", "Southern Europe"};
        egypt.setBorders(egyptAdj);

        madagascar = new Country("Madagascar", africa);
        String[] madagascarAdj = new String[]{"East Africa", "South Africa"};
        madagascar.setBorders(madagascarAdj);

        northAfrica = new Country("North Africa", africa);
        String[] northAfricaAdj = new String[]{"Egypt", "East Africa", "Congo", "Brazil", "Southern Europe", "Western Europe"};
        northAfrica.setBorders(northAfricaAdj);

        southAfrica = new Country("South Africa", africa);
        String[] southAfricaAdj = new String[]{"Madagascar", "Congo", "East Africa"};
        southAfrica.setBorders(southAfricaAdj);

        africaList = new ArrayList<Country>();
        africaList.add(congo);
        africaList.add(eastAfrica);
        africaList.add(egypt);
        africaList.add(madagascar);
        africaList.add(northAfrica);
        africaList.add(southAfrica);


        //ASIA
        afghanistan = new Country("Afghanistan", asia);
        String[] afghanistanAdj = new String[]{"Afghanistan","Ukraine","Ural","Siberia","China","India","Middle East"};
        afghanistan.setBorders(afghanistanAdj);


        china = new Country("China", asia);
        String[] chinaAdj = new String[]{"Ukraine","Ural","Siberia","India","Middle East"};
        china.setBorders(chinaAdj);


        india = new Country("India", asia);
        String[] indiaAdj = new String[]{"Middle East" ,"Afghanistan","China","Siam"};
        india.setBorders(indiaAdj);


        irkutsk = new Country("Irkutsk", asia);
        String[] irkutskAdj = new String[]{"Siberia","Yakutsk","Kamchatka","Mongolia"};
        irkutsk.setBorders(irkutskAdj);

        japan = new Country("Japan", asia);
        String[] japanAdj = new String[]{"Kamchatka","Mongolia"};
        japan.setBorders(japanAdj);


        kamchatka = new Country("Kamchatka", asia);
        String[] kamchatkaAdj = new String[]{"Yakutsk","Alaska","Japan","Mongolia","Irkutsk"};
        kamchatka.setBorders(kamchatkaAdj);


        middleEast = new Country("Middle East", asia);
        String[] middleEastAdj = new String[]{"Ukraine","Afghanistan","India","Egypt"};
        middleEast.setBorders(middleEastAdj);


        mongolia = new Country("Mongolia", asia);
        String[] mongoliaAdj = new String[]{"Siberia","Irkutsk","Kamchatka","Japan","China"};
        mongolia.setBorders(mongoliaAdj);


        siam = new Country("siam", asia);
        String[] siamAdj = new String[]{"India","China","Indonesia"};
        siam.setBorders(siamAdj);


        siberia = new Country("Siberia", asia);
        String[] siberiaAdj = new String[]{"Ural","Yakutsk","Irkutsk","Mongolia","China","Afghanistan"};
        siberia.setBorders(siberiaAdj);


        ural = new Country("Ural", asia);
        String[] uralAdj = new String[]{"Ukraine","Siberia","Afghanistan"};
        ural.setBorders(uralAdj);

        yakutsk = new Country("Yakutsk", asia);
        String[] yakutskAdj = new String[]{"Siberia","Kamchatka","Irkutsk"};
        yakutsk.setBorders(yakutskAdj);

        asiaList = new ArrayList<Country>();
        asiaList.add(afghanistan);
        asiaList.add(china);
        asiaList.add(india);
        asiaList.add(irkutsk);
        asiaList.add(japan);
        asiaList.add(kamchatka);
        asiaList.add(middleEast);
        asiaList.add(mongolia);
        asiaList.add(siam);
        asiaList.add(siberia);
        asiaList.add(ural);
        asiaList.add(yakutsk);


        // EUROPE
        greatBritain = new Country("Great Britain", europe);
        String[] greatBritainAdj = new String[]{"Iceland","Scandinavia","Northern Europe","Western Europe"};
        greatBritain.setBorders(greatBritainAdj);

        iceland = new Country("Iceland", europe);
        String[] icelandAdj = new String[]{"Greenland","Great Britain"};
        iceland.setBorders(icelandAdj);

        northernEurope = new Country("Northern Europe", europe);
        String[] northernEuropeAdj = new String[]{"Great Britain","Ukraine","Southern Europe", "Western Europe"};
        northernEurope.setBorders(northernEuropeAdj);

        scandinavia = new Country("Scandinavia", europe);
        String[] scandinaviaAdj = new String[]{"Great Britain", "Ukraine"};
        scandinavia.setBorders(scandinaviaAdj);

        southernEurope = new Country("Southern Europe", europe);
        String[] southernEuropeAdj = new String[]{"Western Europe","Northern Europe","Ukraine,Egypt","North Africa"};
        southernEurope.setBorders(southernEuropeAdj);

        ukraine = new Country("Ukraine", europe);
        String[] ukraineAdj = new String[]{"Scandinavia","Ural","Afghanistan","Middle East","Southern Europe","Northern Europe"};
        ukraine.setBorders(ukraineAdj);

        westernEurope = new Country("Western Europe", europe);
        String[] westernEuropeAdj = new String[]{"Great Britain","Northern Europe","Southern Europe","North Africa"};
        westernEurope.setBorders(westernEuropeAdj);

        europeList = new ArrayList<Country>();
        europeList.add(greatBritain);
        europeList.add(iceland);
        europeList.add(northernEurope);
        europeList.add(scandinavia);
        europeList.add(southernEurope);
        europeList.add(ukraine);
        europeList.add(westernEurope);

        //Australia
        easternAustralia = new Country("Eastern Australia", australia);
        String[] easternAustraliaAdj = new String[]{"New Guinea","Western Australia"};
        easternAustralia.setBorders(easternAustraliaAdj);

        indonesia = new Country("Indonesia", australia);
        String[] indonesiaAdj = new String[]{"New Guinea","Western Australia"};
        indonesia.setBorders(indonesiaAdj);

        newGuinea = new Country("New Guinea", australia);
        String[] newGuineaAdj = new String[]{"Indonesia","Eastern Australia","Western Australia"};
        newGuinea.setBorders(newGuineaAdj);

        westernAustralia = new Country("Western Australia", australia);
        String[] westernAustraliaAdj = new String[]{"Indonesia","New Guinea","Eastern Australia"};
        westernAustralia.setBorders(westernAustraliaAdj);

        australiaList = new ArrayList<Country>();
        australiaList.add(easternAustralia);
        australiaList.add(indonesia);
        australiaList.add(newGuinea);
        australiaList.add(westernAustralia);
    }

    public ArrayList<Country> getCountryList() {
        return countryList;
    }

    public ArrayList<Continent> getContinentList() {
        return continentList;
    }


}
