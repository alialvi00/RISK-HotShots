import java.util.*;

/**
 * This class is part of "RISK: GLOBAL DOMINATION" application.
 *
 *
 */

public class ProcessInput {

    private ProcessCommand userCommands;
    private Scanner readInput;

    public ProcessInput(){

        userCommands = new ProcessCommand();
        readInput = new Scanner(System.in);
    }

    public Command getCommand(){

        Command cmd;
        String scanInput;
        String firstWord = null;
        String secondWord = null;

        System.out.print("Choose a command: ");

        scanInput = readInput.nextLine();

        Scanner tokenizer = new Scanner(scanInput);
        if(tokenizer.hasNext()) {
            firstWord = tokenizer.next();
            if(tokenizer.hasNext()){
                secondWord = tokenizer.next();
            }
        }
        return cmd = new Command(userCommands.getCommandWords(firstWord),secondWord);
    }

    public void printAllCommands(){
        userCommands.printCommands();
    }
}
