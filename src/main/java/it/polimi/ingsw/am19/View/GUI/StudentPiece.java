package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import javafx.scene.shape.Circle;

public class StudentPiece extends Circle {
    PieceColor color;

    public StudentPiece(int radius) {
        super(radius);
    }

    public void setColor(PieceColor pieceColor) {
        this.color = pieceColor;
    }

    public PieceColor getColor() {
        return color;
    }
}
