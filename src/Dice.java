import java.io.Serializable;
import java.util.Random;

/**
 * This class is part of the "RISK: GLOBAL DOMINATION" application
 *
 * This class represents one die rolled. A random integer is generated
 * using the Random class. Different Dice class objects can be used to represent
 * dices in the game
 *
 * @author Ali, Areeb, Hassan, Raj
 * @version Date: 2020-10-24
 */

public class Dice implements Serializable {

    //the value that one die rolls
    private int diceValue;
    private static final int MINVALUE = 1;
    private static final int MAXVALUE = 6;

    public Dice(){

        Random num = new Random(); //instance of Random class
        diceValue = num.nextInt(MAXVALUE - MINVALUE + 1) + MINVALUE; //generate random integers from 1-6
    }

    public int getDiceValue(){
        return diceValue;
    }
}


