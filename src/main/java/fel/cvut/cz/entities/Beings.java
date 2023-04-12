package fel.cvut.cz.entities;

public abstract class Beings extends Entity{
    int speed; //speed of movement
    public Beings(int x, int y) {
        super(x, y);
        speed = 20; //CHNAGE THIS LATER
    }
}
