package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {
    private GameHandler gameHandler;
    private Player player;
    private ArrayList<Entity> GhostList;

    private ArrayList<Entity> ExplosionList;

    public EntityManager(GameHandler gameHandler, Player player){
        this.gameHandler = gameHandler;
        this.player = player;
        GhostList = new ArrayList<Entity>();
        ExplosionList = new ArrayList<Entity>();
    }

    public void addGhostEntity(Entity e){
        GhostList.add(e);
    }
    public void addExplosionEntity(Entity e){
        ExplosionList.add(e);
    }


    public void tick(){
        for (int i = 0; i < GhostList.size(); i++){
            Entity e = GhostList.get(i);
            e.tick();
        }
        for (int i = 0; i < ExplosionList.size(); i++){
            Entity e = ExplosionList.get(i);
            e.tick();
        }
        player.tick();
    }

    public void render(Graphics g){
        for (int i = 0; i < GhostList.size();){
            Entity e = GhostList.get(i);
            e.render(g);
            //todo zabití ducha
            i++;
        }
        for (int i = 0; i < ExplosionList.size();){
            Explosion e = (Explosion) ExplosionList.get(i);
            e.render(g);
            if (e.getLifeSpan() == 0){
                ExplosionList.remove(e);
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

    public ArrayList<Entity> getGhostList() {
        return GhostList;
    }
    public ArrayList<Entity> getExplosionList() {
        return ExplosionList;
    }

    public void setGhostList(ArrayList<Entity> ghostList) {
        this.GhostList = ghostList;
    }
}
