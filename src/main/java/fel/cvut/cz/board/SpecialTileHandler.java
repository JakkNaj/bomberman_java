package fel.cvut.cz.board;

import java.io.FileWriter;

/** Class to hold coords of boosts in Gameboard */
public class SpecialTileHandler {
    private int xBombB, yBombB;
    private int xExploB, yExploB;
    private int xRunB, yRunB;

    private int xGate, yGate;

    SpecialTileHandler(){
        xBombB = -1;
        yBombB = -1;
        xExploB = -1;
        yExploB = -1;
        xRunB = -1;
        yRunB = -1;
        xGate = -1;
        yGate = -1;
    }

    public boolean collideWithGate(int x, int y){
        return xGate == x && yGate == y;
    }
    public boolean collideWithExploBoost(int x, int y){
        return xExploB == x && yExploB == y;
    }
    public boolean collideWithRunBoost(int x, int y){
        return xRunB == x && yRunB == y;
    }
    public boolean collideWithBombBoost(int x, int y){
        return xBombB == x && yBombB == y;
    }

    public String saveToFile(){
        String result = "";
        if (yExploB != -1 && xExploB != -1) result += "0\n";
        else
            result += "1\n";
        if (xBombB != -1 && yBombB != -1) result += "0\n";
        else
            result += "1\n";
        if (xRunB != -1 && yRunB != -1) result += "0\n";
        else
            result += "1\n";
        return result;
    }

    //Getters and Setters
    public int getxBombB() {
        return xBombB;
    }

    public void setxBombB(int xBombB) {
        if(xGate != xBombB)
            this.xBombB = xBombB;
    }

    public int getyBombB() {
        return yBombB;
    }

    public void setyBombB(int yBombB) {
        if(yGate != yBombB)
            this.yBombB = yBombB;
    }

    public int getxExploB() {
        return xExploB;
    }

    public void setxExploB(int xExploB) {
        if (xGate != yExploB)
            this.xExploB = xExploB;
    }

    public int getyExploB() {
        return yExploB;
    }

    public void setyExploB(int yExploB) {
        if (yGate != yExploB)
            this.yExploB = yExploB;
    }

    public int getxRunB() {
        return xRunB;
    }

    public void setxRunB(int xRunB) {
        if (xGate != xRunB)
            this.xRunB = xRunB;
    }

    public int getyRunB() {
        return yRunB;
    }

    public void setyRunB(int yRunB) {
        if (yGate != yRunB)
            this.yRunB = yRunB;
    }

    public int getxGate() {
        return xGate;
    }

    public void setxGate(int xGate) {
        this.xGate = xGate;
    }

    public int getyGate() {
        return yGate;
    }

    public void setyGate(int yGate) {
        this.yGate = yGate;
    }
}
