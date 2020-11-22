public interface RiskListener {

    void handleInitialMap(MapEvent m);
    void handleMapChange(MapEvent m);
    void handleAdjacentList(ListEvent l);
    void handleEndGame(MapEvent m);
}
