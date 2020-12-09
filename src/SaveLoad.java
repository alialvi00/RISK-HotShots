import java.io.*;

public class SaveLoad implements Serializable {

    RiskModel model;
    RiskView view;
    Player currentPlayer;

    public SaveLoad(RiskModel model, RiskView view){

        this.model = model;
        this.view = view;
        currentPlayer = model.getCurrentPlayer();
    }

    public void saveModel() throws IOException {
        FileOutputStream saveFile = new FileOutputStream("ModelClass.ser");
        ObjectOutputStream save = new ObjectOutputStream(saveFile);
        save.writeObject(model);
        save.close();
    }

    public void saveCurrentPlayer() throws IOException {
        FileOutputStream saveFile = new FileOutputStream("PlayerClass.ser");
        ObjectOutputStream save = new ObjectOutputStream(saveFile);
        save.writeObject(currentPlayer);
        save.close();
    }

    public void loadPlayer() throws IOException, ClassNotFoundException {

        FileInputStream saveFile = new FileInputStream("PlayerClass.ser");
        ObjectInputStream save = new ObjectInputStream(saveFile);
        currentPlayer = (Player) save.readObject();
        save.close();
    }

    public void loadModel() throws IOException, ClassNotFoundException, InterruptedException {

        FileInputStream saveFile = new FileInputStream("ModelClass.ser");
        ObjectInputStream save = new ObjectInputStream(saveFile);
        model = (RiskModel) save.readObject();
        save.close();
        view.changeModel(model);
        model.addView(view);
        model.updateCurrentPlayer(currentPlayer);
        view.switchView();
        view.startNewGame();
        System.out.println("Saved game has been loaded");
        System.out.println();
        System.out.println();
        view.handleInitialMap(new MapEvent(model, model.showPlayerList()));
        if(!model.ifFortify()){
            view.setNormalMode();
            view.addAdjListener();
        }
        if (currentPlayer.getIsAI()) {
            model.playAI();
            model.nextTurn();
        }
    }
}
