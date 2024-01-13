package lk.ijse.dep.service;

public interface Board {
    int NUM_OF_ROWS=5;
    int NUM_OF_COLS=6;
    BoardUi getBoardUI();
    int findNextAvailableSpot(int col);
    boolean isLegalMove(int col);
    boolean existLegalMoves();
    void updateMove (int col,Piece move);
    Winner findWinner();

    BoardImpl getBoardImpl();


}
