import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SaveLoadController implements ActionListener, Serializable {

    RiskModel model;
    RiskView view;
    MainClass newGame = new MainClass();

    public SaveLoadController(RiskModel model, RiskView view){

        this.model = model;
        this.view = view;
    }

    public void changeModel(RiskModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("save")){
            try {
                writeToFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("load")){
            try {
                FileInputStream saveFile = new FileInputStream("savegame.ser");
                ObjectInputStream save = new ObjectInputStream(saveFile);
                model = (RiskModel ) save.readObject();
                //System.out.println(gameElements.r_flag);
                save.close();
                view.changeModel(model);
                model.addView(view);
                view.switchView();
                view.startNewGame();
                view.handleInitialMap(new MapEvent(model,model.showPlayerList()));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void writeToFile() throws IOException {
       FileOutputStream saveFile = new FileOutputStream("savegame.ser");
       ObjectOutputStream save = new ObjectOutputStream(saveFile);
       save.writeObject(model);
       save.close();
    }
}
