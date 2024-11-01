public class Queen extends Piece{
    public Queen(PieceColor color,Position position){
        super(color,position);
    }
    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        if (newPosition.equals(this.position)) {
            return false;
        }

        int rowDiff = Math.abs(newPosition.getRow() - this.position.getRow());
        int colDiff = Math.abs(newPosition.getColumn() - this.position.getColumn());

        boolean straightLine = this.position.getRow() == newPosition.getRow()
                || this.position.getColumn() == newPosition.getColumn();

        boolean diagonal = rowDiff == colDiff;
        boolean knightMove = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);


        if (!straightLine && !diagonal && !knightMove) {
            return false;
        }
        if (knightMove) {
            Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
            return destinationPiece == null || destinationPiece.getColor() != this.getColor();
        }


        int rowDirection = Integer.compare(newPosition.getRow(), this.position.getRow());
        int colDirection = Integer.compare(newPosition.getColumn(), this.position.getColumn());

        int currentRow = this.position.getRow() + rowDirection;
        int currentCol = this.position.getColumn() + colDirection;
        while (currentRow != newPosition.getRow() || currentCol != newPosition.getColumn()) {
            if (board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];
        return destinationPiece == null || destinationPiece.getColor() != this.getColor();
    }
}
