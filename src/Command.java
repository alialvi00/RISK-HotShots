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
    private CommandWords commandWord;
    private String secondWord;
    private HashMap<String, CommandWords> validCommands;

    /**
     * The constructor for the Command Class.
     * Purpose: Initialize the commandWord and secondWord fields.
     *
     * @param  commandWord This is the Command Word (first)
     * @param  secondWord This is the second word (2nd)
     */
    public Command(CommandWords commandWord, String secondWord){
        this.commandWord = commandWord;
        this.secondWord = secondWord;

        validCommands = new HashMap<>();
        for(CommandWords cmd: CommandWords.values()){
            validCommands.put(cmd.toString(), cmd);
        }

    }

    /**
     * This method will check to see if there is a second word.
     * Return true if there is a second word.
     *
     */
    public boolean checkSecondWord(){
        if (secondWord != null){
            return true;
        }
        return false;
    }

    /**
     * Return the command word
     */
    public CommandWords getCommandWord() {
        return commandWord;
    }

    /**
     * Return the second word
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * Return true if the command is valid
     */
    public boolean checkIfCommand(String cmd) {
        if (validCommands.containsKey(cmd)){
            return true;
        }
            return false;

    }




}

