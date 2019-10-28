package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {


    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffSet:CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffSet;
          if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

              if(isFirstColumnExclusions(this.piecePosition,currentCandidateOffSet) ||
                      (isSecondColumnExlcusion(this.piecePosition, currentCandidateOffSet)) ||
                      (isSeventhColumnExclusions(this.piecePosition, currentCandidateOffSet))||
                      (isEightColumnExlusions(this.piecePosition,currentCandidateOffSet))){
                  continue;
              }
              final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
              if(!candidateDestinationTile.isTileOccupied()){
                  legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
              }else{
                  final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                  final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                  if (this.pieceAlliance != pieceAlliance){
                      legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                  }
              }
          }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusions(final int currentPosition, final int candidateOffSet){

        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffSet == -17 || candidateOffSet == -10 ||
                candidateOffSet == 6 || candidateOffSet == 15);

    }

    private static boolean isSecondColumnExlcusion(final int currentPosition, final int candidateOffSet){
        return  BoardUtils.SECOND_COLUMN[currentPosition] &&(candidateOffSet == -10 || candidateOffSet == 6);
    }

    private static boolean isSeventhColumnExclusions(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }

    private static boolean isEightColumnExlusions(final int currentPosition, final int candidateOffSet){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == -15 || candidateOffSet == -6 ||
                candidateOffSet == 10 || candidateOffSet == 17);
    }

}
