import java.util.ArrayList;
import java.util.*;

public class ListEvent extends EventObject{
    
    private ArrayList<String> adjacentList;

    public ListEvent(RiskModel model, ArrayList<String> list){
        super(model);
        adjacentList = list;
    }

    public ArrayList<String> getAdjacentList(){
        return adjacentList;
    }

}
