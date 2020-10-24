/**
 * Enum class of all valid command words for the RISK game
 * This phrase will perform the tasks that the words are assigned to.
 * @author (Areeb Haq, Ali Alvi, Hassan Jallad, Raj Sandhu)
 * @version (1.0)
 */

public enum CommandWords
{
    PLAY("play"), ATTACK("attack"), DRAFT("draft"), REINFORCE ("reinforce"), QUIT("quit");

    private String command;

    CommandWords(String command){
        this.command = command;
    }

    public String toString()
    {
        return command;
    }
}

