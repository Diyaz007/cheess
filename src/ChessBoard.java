import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ChessBoard {
    private Piece[][] board;
    public int whiteQueen = 1;
    public int blackQueen = 1;

    public ChessBoard() {
        this.board = new Piece[8][8]; // Chessboard is 8x8
        setupPieces();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Piece getPiece(int row, int column) {
        return board[row][column];
    }

    public void setPiece(int row, int column, Piece piece) {
        board[row][column] = piece;
        if (piece != null) {
            piece.setPosition(new Position(row, column));
        }
    }

    private void setupPieces() {
        board[0][0] = new Rook(PieceColor.BLACK, new Position(0, 0));
        board[0][7] = new Rook(PieceColor.BLACK, new Position(0, 7));
        board[7][0] = new Rook(PieceColor.WHITE, new Position(7, 0));
        board[7][7] = new Rook(PieceColor.WHITE, new Position(7, 7));

        board[0][1] = new Knight(PieceColor.BLACK, new Position(0, 1));
        board[0][6] = new Knight(PieceColor.BLACK, new Position(0, 6));
        board[7][1] = new Knight(PieceColor.WHITE, new Position(7, 1));
        board[7][6] = new Knight(PieceColor.WHITE, new Position(7, 6));

        board[0][2] = new Bishop(PieceColor.BLACK, new Position(0, 2));
        board[0][5] = new Bishop(PieceColor.BLACK, new Position(0, 5));
        board[7][2] = new Bishop(PieceColor.WHITE, new Position(7, 2));
        board[7][5] = new Bishop(PieceColor.WHITE, new Position(7, 5));

        board[0][3] = new Queen(PieceColor.BLACK, new Position(0, 3));
        board[7][3] = new Queen(PieceColor.WHITE, new Position(7, 3));

        board[0][4] = new King(PieceColor.BLACK, new Position(0, 4));
        board[7][4] = new King(PieceColor.WHITE, new Position(7, 4));

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(PieceColor.BLACK, new Position(1, i));
            board[6][i] = new Pawn(PieceColor.WHITE, new Position(6, i));
        }
    }

    public void movePiece(Position start, Position end) {
        Random random = new Random();

        Piece movingPiece = board[start.getRow()][start.getColumn()];

        if (movingPiece instanceof King) {
            if (Math.abs(start.getColumn() - end.getColumn()) == 2) {
                boolean isKingside = end.getColumn() > start.getColumn();
                Position rookStartPos = isKingside ? new Position(start.getRow(), 7) : new Position(start.getRow(), 0);
                Position rookEndPos = isKingside ? new Position(start.getRow(), 5) : new Position(start.getRow(), 3);

                Piece rook = board[rookStartPos.getRow()][rookStartPos.getColumn()];

                board[end.getRow()][end.getColumn()] = movingPiece;
                board[end.getRow()][end.getColumn()].setPosition(end);
                board[start.getRow()][start.getColumn()] = null;

                board[rookEndPos.getRow()][rookEndPos.getColumn()] = rook;
                board[rookEndPos.getRow()][rookEndPos.getColumn()].setPosition(rookEndPos);
                board[rookStartPos.getRow()][rookStartPos.getColumn()] = null;

                return;
            }
        }

        if (movingPiece instanceof Bishop) {
            Position position = new Position(end.getRow() - start.getRow(), end.getColumn() - start.getColumn());
            if (movingPiece.isValidMove(end, board)) {
                if (position.getRow() == 0) {
                    position = new Position(start.getRow(), start.getColumn() + 1);
                } else if (position.getColumn() == 0 && start.getRow() > end.getRow()) {
                    position = new Position(start.getRow() - 1, start.getColumn());
                } else if (position.getColumn() == 0 && start.getRow() < end.getRow()) {
                    position = new Position(start.getRow() + 1, start.getColumn());
                }
                if (board[position.getRow()][position.getColumn()] != null &&
                        movingPiece.getColor() == board[position.getRow()][position.getColumn()].getColor()) {
                    int random1 = random.nextInt(1, 6);
                    showDiceRoll(random1);
                    if (random1 % 2 == 0) {
                        board[position.getRow()][position.getColumn()] = null;
                    }
                }
            }
            board[end.getRow()][end.getColumn()] = movingPiece;
            board[end.getRow()][end.getColumn()].setPosition(end);
            board[start.getRow()][start.getColumn()] = null;
        } else if (movingPiece instanceof Queen) {
            if (movingPiece.getColor() == PieceColor.WHITE) {
                if (whiteQueen < 5) {
                    board[end.getRow()][end.getColumn()] = movingPiece;
                    board[end.getRow()][end.getColumn()].setPosition(end);
                    board[start.getRow()][start.getColumn()] = null;
                    whiteQueen++;
                    showQueenSteps(whiteQueen, PieceColor.WHITE);
                } else if (whiteQueen == 5) {
                    board[end.getRow()][end.getColumn()] = null;
                    board[start.getRow()][start.getColumn()] = null;
                    QueenDelete();
                }
            } else if (movingPiece.getColor() == PieceColor.BLACK) {
                if (blackQueen < 5) {
                    board[end.getRow()][end.getColumn()] = movingPiece;
                    board[end.getRow()][end.getColumn()].setPosition(end);
                    board[start.getRow()][start.getColumn()] = null;
                    blackQueen++;
                    showQueenSteps(blackQueen, PieceColor.BLACK);
                } else if (blackQueen == 5) {
                    board[end.getRow()][end.getColumn()] = null;
                    board[start.getRow()][start.getColumn()] = null;
                    QueenDelete();
                }
            }
        } else {
            board[end.getRow()][end.getColumn()] = movingPiece;
            board[end.getRow()][end.getColumn()].setPosition(end);
            board[start.getRow()][start.getColumn()] = null;
        }
    }


    private void showDiceRoll(int diceRoll) {
        JFrame frame = new JFrame("Результат броска кубика");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);
        JLabel label = new JLabel("Выпало число: " + diceRoll, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        frame.add(label);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void QueenDelete() {
        JFrame frame = new JFrame("Бык исчезает!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);
        JLabel label = new JLabel("Бык исчезает!", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        frame.add(label);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void showQueenSteps(int diceRoll,PieceColor color) {
        JFrame frame = new JFrame("Ходы Королевы");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);
            JLabel label;
        if(color == PieceColor.BLACK) {
             label = new JLabel("Осталось ходов: " + (6-diceRoll) + " у Синего", SwingConstants.CENTER);
        }else {
             label = new JLabel("Осталось ходов: " + (6-diceRoll) + " у Красного", SwingConstants.CENTER);
        }
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        frame.add(label);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}