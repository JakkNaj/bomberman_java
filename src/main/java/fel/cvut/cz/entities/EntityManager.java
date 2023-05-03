package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {
    private GameHandler gameHandler;
    private Player player;
    private ArrayList<Entity> entityList;

    public EntityManager(GameHandler gameHandler, Player player){
        this.gameHandler = gameHandler;
        this.player = player;
        entityList = new ArrayList<Entity>();
    }

    public void addEntity(Entity e){
        entityList.add(e);
    }

    public void tick(){
        for (int i = 0; i < entityList.size(); i++){
            Entity e = entityList.get(i);
            e.tick();
        }
        player.tick();
    }

    public void render(Graphics g){
        for (int i = 0; i < entityList.size();){
            Entity e = entityList.get(i);
            e.render(g);
            if (e instanceof ExplodedBomb && ((ExplodedBomb) e).getLifeSpan() == 0){
                entityList.remove(e);
            } else {
                i++;
            }
        }
        player.render(g);
    }


    //GETTERS AND SETTERS
    public GameHandler getHandler() {
        return gameHandler;
    }

    public void setHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
        this.entityList = entityList;
    }
}
