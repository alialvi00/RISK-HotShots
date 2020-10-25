import java.util.HashMap;

/**
 * Enum class of all valid command words for the RISK game
 * This phrase will perform the tasks that the words are assigned to.
 * @author (Areeb Haq, Ali Alvi, Hassan Jallad, Raj Sandhu)
 * @version (1.0)
 */

public enum CommandWords
{

    PLAY("play"), ATTACK("attack"), PASS("pass turn"), INCORRECT("incorrect"), QUIT("quit");

    private String userCommand;

    CommandWords(String userCommand){
        this.userCommand = userCommand;
    }

    public String toString()
    {
        return userCommand;
    }
}

