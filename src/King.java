public class King extends Piece{
    public King(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int rowDiff = Math.abs(position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(position.getColumn() - newPosition.getColumn());

        boolean isOneSquareMove = rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0);

        if (isOneSquareMove) {
            Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
            return destinationPiece == null || destinationPiece.getColor() != this.getColor();
        }

        if (rowDiff == 0 && colDiff == 2) {
            boolean isKingsideCastling = newPosition.getColumn() > position.getColumn();
            boolean isQueensideCastling = newPosition.getColumn() < position.getColumn();

            if (isKingsideCastling &&
                    isCastlingAllowed("kingside",board) &&
                    isPathClear(position, new Position(position.getRow(), position.getColumn() + 3), board) &&
                    !isUnderAttack(position, this.getColor(), board) &&
                    !isUnderAttack(new Position(position.getRow(), position.getColumn() + 1), this.getColor(), board) &&
                    !isUnderAttack(newPosition, this.getColor(), board)) {
                return true;
            }

            if (isQueensideCastling &&
                    isCastlingAllowed("queenside",board) &&
                    isPathClear(position, new Position(position.getRow(), position.getColumn() - 4), board) &&
                    !isUnderAttack(position, this.getColor(), board) &&
                    !isUnderAttack(new Position(position.getRow(), position.getColumn() - 1), this.getColor(), board) &&
                    !isUnderAttack(newPosition, this.getColor(), board)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCastlingAllowed(String side,Piece[][] board) {
        if (this.hasMoved) {
            return false;
        }

        if (side.equals("kingside")) {
            Piece kingsideRook = board[position.getRow()][7];
            return kingsideRook instanceof Rook && !kingsideRook.hasMoved();
        } else if (side.equals("queenside")) {
            Piece queensideRook = board[position.getRow()][0];
            return queensideRook instanceof Rook && !queensideRook.hasMoved();
        }

        return false;
    }

    private boolean isPathClear(Position start, Position end, Piece[][] board) {
        int row = start.getRow();
        int colStart = Math.min(start.getColumn(), end.getColumn()) + 1;
        int colEnd = Math.max(start.getColumn(), end.getColumn());

        for (int col = colStart; col < colEnd; col++) {
            if (board[row][col] != null) {
                return false;
            }
        }

        return true;
    }


    private boolean isUnderAttack(Position position, PieceColor color, Piece[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.getColor() != color) {
                    if (piece.isValidMove(position, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
