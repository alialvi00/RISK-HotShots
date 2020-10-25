import java.util.HashMap;

public class ProcessCommand {

    private HashMap<String,CommandWords> listOfCommands;

    public ProcessCommand(){

        listOfCommands = new HashMap<>();
        for(CommandWords cmd: CommandWords.values()) {
            if (cmd != cmd.INCORRECT)
                listOfCommands.put(cmd.toString(), cmd);
        }
    }



    public CommandWords getCommandWords(String userCommand) {

        CommandWords cmd = listOfCommands.get(userCommand);
        if(cmd != null)
            return cmd;
        else
            return CommandWords.INCORRECT;
    }


    public boolean isCommand(String userCommand) {
        return listOfCommands.containsKey(userCommand);
    }



    public void printCommands() {

        for(String cmd : listOfCommands.keySet()){
            System.out.println(cmd + " ");
        }
        System.out.println();
    }
}
