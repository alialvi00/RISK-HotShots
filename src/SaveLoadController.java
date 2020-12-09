import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SaveLoadController implements ActionListener, Serializable {

    RiskModel model;
    RiskView view;
    SaveLoad savedGame;

    public SaveLoadController(RiskModel model, RiskView view){

        this.model = model;
        this.view = view;
    }

    public void changeModel(RiskModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        savedGame = new SaveLoad(model,view);

        if(e.getActionCommand().equals("save")){

            try {
                savedGame.saveCurrentPlayer();
                savedGame.saveModel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("load")){

            try {
                savedGame.loadPlayer();
                savedGame.loadModel();
            } catch (IOException | ClassNotFoundException | InterruptedException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
