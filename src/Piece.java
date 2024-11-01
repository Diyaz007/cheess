public abstract class Piece {
    protected Position position;
    protected PieceColor color;
    protected boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved() {
        this.hasMoved = true;
    }

    public Piece(PieceColor color,Position position) {
        this.position = position;
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PieceColor getColor() {
        return color;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }
    public abstract boolean isValidMove(Position newPosition, Piece[][] board);
}
