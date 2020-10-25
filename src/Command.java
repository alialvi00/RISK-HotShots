import java.util.*;
import java.util.HashMap;


/**
 * The command class is a vital part of the RISK Game as it holds key words that are
 * used by the player(s) to make their desired moves.
 * The Command class consists of command words joined with a second word to complete a phrase
 * This phrase will perform the tasks that the words are assigned to.
 * @author (Areeb Haq, Ali Alvi, Hassan Jallad, Raj Sandhu)
 * @version (1.0)
 */


public class Command
{
    private CommandWords firstCommand;
    private String secondCommand;

    /**
     * The constructor for the Command Class.
     * Purpose: Initialize the firstCommand object and secondCommand field.
     *
     * @param  firstCommand This is the first Command (first)
     * @param  secondCommand This is the second Command (2nd)
     */
    public Command(CommandWords firstCommand, String secondCommand) {

        this.firstCommand = firstCommand;
        this.secondCommand = secondCommand;
    }

    public CommandWords getFirstCommand(){
        return firstCommand;
    }

    /**
     * Determines if the input has a second word
     * @return Boolean to determine if secondWord null
     */
    public boolean ifSecondCommand(){
        return !(secondCommand == null);
    }

    public boolean wrongCommand(){
        return (firstCommand == CommandWords.PASS);
    }

    public String getSecondCommand(){
        return secondCommand;
    }

}

