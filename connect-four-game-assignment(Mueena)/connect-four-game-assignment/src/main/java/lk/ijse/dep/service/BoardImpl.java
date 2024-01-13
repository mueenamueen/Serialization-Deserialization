package lk.ijse.dep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardImpl implements Board {
    private Piece[][] pieces;
    private BoardUi boardUI;
    public Piece player;

    public int col;

    public BoardImpl(BoardUi boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                pieces[i][j] = Piece.EMPTY;

            }
        }
    }

    public BoardImpl(Piece[][] pieces, BoardUi boardUI) {
        this.pieces=new Piece[6][5];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                this.pieces[i][j]=pieces[i][j];
            }
        }
        this.boardUI = boardUI;
    }


    @Override
    public BoardUi getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for (int i = 0; i < 5; i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        int findbol = findNextAvailableSpot(col);
        if (findbol != -1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean existLegalMoves() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public void updateMove(int col,Piece move) {

        this.col = col;
        this.player = move;

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                pieces[col][i] = move;
                break;
            }
        }
    }



    @Override
    public Winner findWinner() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                Piece currentP = pieces[i][j];
                if (currentP != Piece.EMPTY) {
                    if (j + 3 < NUM_OF_ROWS &&
                            currentP == pieces[i][j + 1] &&
                            currentP == pieces[i][j + 2] &&
                            currentP == pieces[i][j + 3]) {
                        return new Winner(currentP, i, j, i, j + 3);
                    }
                    if (i + 3 < NUM_OF_COLS &&
                            currentP == pieces[i + 1][j] &&
                            currentP == pieces[i + 2][j] &&
                            currentP == pieces[i + 3][j]) {
                        return new Winner(currentP, i, j, i + 3, j);
                    }
                }

            }

        }
        return new Winner(Piece.EMPTY);
    }

    @Override
    public BoardImpl getBoardImpl() {
        return this;
    }

    public boolean getStatus(){
        if (!existLegalMoves()){
            return false;
        }

        Winner winner=findWinner();
        if (winner.getWinningPiece() != Piece.EMPTY){

            return false;
        }
        return true;
    }

    public List<BoardImpl> getAllLegalNextMoves() {

        Piece nextPiece = player == Piece.BLUE?Piece.GREEN:Piece.BLUE;

        List<BoardImpl> nextMoves = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            int raw=findNextAvailableSpot(i);
            if (raw!=-1){
                BoardImpl legalMove=new BoardImpl(this.pieces,this.boardUI);
                legalMove.updateMove(i,nextPiece);
                nextMoves.add(legalMove);
            }
        }
        return  nextMoves;
    }

    public BoardImpl getRandomLeagalNextMove() {
        final List<BoardImpl> legalMoves = getAllLegalNextMoves();

        if (legalMoves.isEmpty()) {
            return null;
        }

        final int random= new Random().nextInt(legalMoves.size());
        return legalMoves.get(random);
    }

    public Piece getPlayer() {
        return player;
    }

    public Piece[][] getPieces() {
        return pieces;
    }
}


