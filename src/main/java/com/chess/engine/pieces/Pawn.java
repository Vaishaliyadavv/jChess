package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.MajorMove;

public class Pawn extends Piece {
    private final int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};

    public Pawn(final Alliance pieceAlliance, final int piecePosition) {

        super(PieceType.PAWN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                //TODO more work to do here(deal with promotions)
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

            } else if (currentCandidateOffset == 16 && this.isFirstMove() && (BoardUtils.SEVENTH_RANK[this.piecePosition]) && this.getPieceAlliance().isBlack() || (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                }
            } else if (currentCandidateOffset == 7 && !(BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() || (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {

                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //TODO more work here
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                    }
                }

            } else if (currentCandidateOffset == 9 && !(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() || (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //TODO more work here
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                    }
                }


            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance() ,
                move.getDestinationCoordinate());
    }

    public String toString() {
        return PieceType.PAWN.toString();
    }

}
