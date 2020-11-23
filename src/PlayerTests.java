import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PlayerTests {

    private Country alaska;
    private Country quebec;
    private Country alberta;
    Player player;

    private HashMap <Country, Integer> conqueredCountries = new HashMap<Country, Integer>();


    protected void setUp(){
        player = new Player("Areeb", 5);
    }
    @org.junit.Test
    public void testUpdateReinforcements() {
        setUp();
        player.updateEnforcements(3);
        int x = player.getAvailableEnforcement();
        assertEquals(x, 8);
    }

    @Test
    public void testAttackCountry() {
        setUp();
        //just check if an arraylist of size 2 is made if the argument given is 2
        int size = player.attackCountry(2).size();
        assertEquals(2, size);
    }

    @org.junit.Test
    public void testGetCountryByName() {
        setUp();
        assertEquals(player.getCountryByName("alaska"),alaska);
    }

    @Test
    public void testGetCountryTroops() {
        HashMap <Country, Integer> conqueredCountries = new HashMap<Country, Integer>();
        conqueredCountries.put(alaska, 1);
        conqueredCountries.put(quebec, 2);
        conqueredCountries.put(alberta,0);

        setUp();
        long x =  player.getCountryTroops("alberta");
        assertEquals(0, x);
    }


    @Test
    public void testHasCountries() {
        setUp();
        conqueredCountries.put(alaska, 1);
        conqueredCountries.put(quebec, 2);

        assertEquals(false, player.hasCountry("alaska"));
    }


}