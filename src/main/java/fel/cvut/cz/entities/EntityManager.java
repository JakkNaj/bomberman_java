package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;

import java.awt.*;
import java.util.ArrayList;

/** Class managing all entities in game */
public class EntityManager {
    private GameHandler gameHandler;
    private Player player;
    private ArrayList<Entity> GhostList;

    private ArrayList<Entity> ExplosionList;

    public EntityManager(GameHandler gameHandler, Player player){
        this.gameHandler = gameHandler;
        this.player = player;
        GhostList = new ArrayList<>();
        ExplosionList = new ArrayList<>();
    }

    public void addGhostEntity(Entity e){
        GhostList.add(e);
    }
    public void addExplosionEntity(Entity e){
        ExplosionList.add(e);
    }


    public void tick(){
        for (int i = 0; i < GhostList.size();){
            Ghost e =(Ghost) GhostList.get(i);
            e.tick();
            if (e.health <= 0){
                GhostList.remove(e);
            } else {
                i++;
            }
        }
        for (int i = 0; i < ExplosionList.size();){
            Explosion e = (Explosion) ExplosionList.get(i);
            e.tick();
            if (e.getLifeSpan() <= 0){
                ExplosionList.remove(e);
            } else {
                i++;
            }
        }
        player.tick();
    }

    public void render(Graphics g){
        for (Entity entity : GhostList) {
            Ghost e = (Ghost) entity;
            e.render(g);
        }
        for (Entity entity : ExplosionList) {
            Explosion e = (Explosion) entity;
            e.render(g);
        }
        player.render(g);
    }

    /** Returns the right string to write to file do save ghosts */
    public String saveGhostsToFile(){
        String result = "";
        for (Entity g: GhostList){
            result += g.getX() +" "+ g.getY() +" " + ((Ghost) g).getXmovement() +" "+((Ghost) g).getYmovement() +"\n";
        }
        return result;
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
