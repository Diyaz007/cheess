public class Bishop extends Piece {
    public Bishop(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        if (newPosition.equals(this.position)) {
            return false;
        }

        boolean isValidLMove = false;
        int rowDiff = this.position.getRow() - newPosition.getRow();
        int colDiff = this.position.getColumn() - newPosition.getColumn();
        isValidLMove = (rowDiff == 2 && colDiff == 0) || (rowDiff == -2 && colDiff == 0) || (rowDiff == 0 && colDiff == 2) || (rowDiff == 0 && colDiff == -2) || (rowDiff == 1 && colDiff == 1) || (rowDiff == -1 && colDiff == -1) || (rowDiff == 1 && colDiff == -1) || (rowDiff == -1 && colDiff == 1);


        if (!isValidLMove) {
            return false;
        }

        Piece targetPiece = board[newPosition.getRow()][newPosition.getColumn()];
        if (targetPiece == null) {
            return true;
        } else {
            return targetPiece.getColor() != this.getColor();
        }
    }
}
