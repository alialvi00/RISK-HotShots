import java.awt.event.*;

public class FilePickController implements ActionListener{

    RiskView view;
    RiskModel model;

    public FilePickController(RiskModel model, RiskView view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String fileName = view.pickFile();
       
       if(!model.initiateMap(fileName)){
           view.fileError();
           return;
       } else{
           if(!model.validateMap()){
            view.fileError();
            return;
           }
       }
       view.setMapType(true);
    }
    
}