import java.util.*;

public class PlayerEvent extends EventObject{

    ArrayList<Country> playerCountries;
    ArrayList<String> countryNames;
    List<String> adjacentCountries;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PlayerEvent(Object source) {
        super(source);
        Player p1 = (Player) source;
        adjacentCountries = new ArrayList<String>();
        countryNames = new ArrayList<>();
        this.playerCountries = p1.getCountries();
    }

    public ArrayList<String> getPlayerCountries(){

        for(Country eachC : playerCountries){

            countryNames.add(eachC.toString());
        }
        return countryNames;
    }

    /**
     * Always call getPlayerCountries before calling this method
     * @param countryName
     * @return
     */
    public List<String> getAdjacentCountries(String countryName){

        for(Country eachCountry : playerCountries){
            if(eachCountry.toString().equals(countryName)){
                Collections.addAll(adjacentCountries,eachCountry.getAdjacentCountries());
            }
        }
        return adjacentCountries;
    }
}
