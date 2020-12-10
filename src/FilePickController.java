import org.json.simple.parser.ParseException;

import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;

public class FilePickController implements ActionListener, Serializable {

    RiskView view;
    RiskModel model;

    public FilePickController(RiskModel model, RiskView view) {
        this.view = view;
        this.model = model;
    }

    public void changeModel(RiskModel model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String fileName = view.pickFile();

        try {
            if(!model.initiateMap(fileName)){
                view.fileError();
                return;
            } else{
                if(!model.validateMap()){
                 view.fileError();
                 return;
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        view.setMapType(true);
    }
    
}