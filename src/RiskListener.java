public interface RiskListener {

    void handleInitialMap(MapEvent m);
    void handleAttack(MapEvent m);
    void handleAdjacentList(ListEvent l);
    void handleEndGame(MapEvent m);
}
