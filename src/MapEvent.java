import java.util.ArrayList;
import java.util.*;

public class MapEvent extends EventObject{
    
    private ArrayList<Player> players;

    public MapEvent(RiskModel model, ArrayList<Player> players){
        super(model);
        this.players = players;
    }

    public ArrayList<Player> getPlayerList() {
        return players;
    }
}
