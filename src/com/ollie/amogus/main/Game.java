package com.ollie.amogus.main;

import com.ollie.amogus.gameobjects.Map;
import com.ollie.amogus.gameobjects.Wall;
import com.ollie.amogus.gameobjects.entities.Crewmate;
import com.ollie.amogus.gameobjects.entities.Directions;
import com.ollie.amogus.gameobjects.entities.MPCrewMate;
import com.ollie.amogus.networking.GameClient;
import com.ollie.amogus.networking.GameServer;
import com.ollie.amogus.networking.LoginPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    private Crewmate crew;
    private Map b;

    private final ArrayList<Wall> walls;

    private boolean playing;
    private boolean left,right,up,down;

    private Thread thread;

    private GameClient client;
    private GameServer server;

    public Game() throws IOException {

        setFocusable(true);

        walls = new ArrayList<>();

        playing = false;
        left = false;
        right = false;
        up = false;
        down = false;

        setPreferredSize(new Dimension(1024, 1024));
        setVisible(true);

    }

    public void init() throws IOException {

        crew = new MPCrewMate(512, 600, JOptionPane.showInputDialog(this, "Please enter a username"), null, -1);

        b = new Map(-1400, -70);
        b.addCrewMate(crew);

        addKeyListener(new KeyInput());

        LoginPacket lP = new LoginPacket(crew.getUserName(), crew.getX(), crew.getY());
        if(server != null) {
            server.addConnection((MPCrewMate) crew, lP);

        }

        lP.writeData(client);

        initWalls();

        playing = true;

    }

    private synchronized void initWalls(){

        walls.add(new Wall(390, 333, 260, 200));

    }

    public synchronized void start() throws UnknownHostException {

        thread = new Thread(this);
        thread.start();

        if(JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0){
            server = new GameServer(this);
            server.start();
        }

        client = new GameClient(this, InetAddress.getLocalHost().getHostAddress());
        client.start();
        System.out.println("client started");
    }

    public synchronized void kill(){

        try{
            thread.join();
            playing = false;
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public synchronized void run(){

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int targetUpdates = 6;
        final double targetUpdateTime = 1000000000/targetUpdates;

        final int targetFPS = 60;
        final double targetTime = 1000000000/targetFPS;

        long lastTime = System.nanoTime();
        double deltaF = 0, deltaU = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while(playing){

            long now = System.nanoTime();

            deltaU += (now - lastTime) / targetUpdateTime;
            deltaF += (now - lastTime) / targetTime;

            lastTime = now;

            if(deltaU >= 1){
                //input
                animate();

                tick();
                ticks++;
                deltaU--;
            }

            if(deltaF >= 1){
                try {
                    render();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                move();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - timer > 1000){
                System.out.printf("UPS: %s, FPS: %s%n", ticks, frames);
                frames = 0;
                ticks = 0;
                timer += 1000;

                System.out.println(b.getCrewmates());
            }

        }

        kill();

    }

    private synchronized void move(){

        if(up && left) {
            if(crew.checkCollisions(-1, -1)) return;
            crew.updatePos(1, 1);
            b.updatePos(-1, -1);
        }
        else if(up && right) {
            if(crew.checkCollisions(1, -1)) return;
            crew.updatePos(-1, 1);
            b.updatePos(1, -1);
        }
        else if(down && left) {
            if(crew.checkCollisions(-1, 1)) return;
            crew.updatePos(1, -1);
            b.updatePos(-1, 1);
        }
        else if(down && right) {
            if(crew.checkCollisions(1, 1)) return;
            crew.updatePos(-1, -1);
            b.updatePos(1, 1);
        } else if(up) {
            if(crew.checkCollisions(0, -2)) return;
            crew.updatePos(0, 2);
            b.updatePos(0, -2);
        }

        else if(down) b.updatePos(0, 2);
        else if(left) b.updatePos(-2, 0);
        else if(right) b.updatePos(2, 0);

        if(right) crew.changeDirection(Directions.LEFT);
        else if(left) crew.changeDirection(Directions.RIGHT);

        crew.setMoving(up || left || right || down);

    }

    private void render() throws AWTException {

        BufferCapabilities ge = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBufferCapabilities();

        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            this.createBufferStrategy(3, ge);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        b.drawImage(g);
        b.render(g);

        walls.iterator().forEachRemaining(w -> w.drawImage(g));

        g.dispose();
        bs.show();

    }

    private void animate(){
        crew.addSpriteNum();
    }

    private void tick(){

    }

    public Map getB() {
        return b;
    }

    public Crewmate getCrew() {
        return crew;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    private class KeyInput extends KeyAdapter{

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A) right = false;
            if (e.getKeyCode() == KeyEvent.VK_D) left = false;
            if (e.getKeyCode() == KeyEvent.VK_W) down = false;
            if (e.getKeyCode() == KeyEvent.VK_S) up = false;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A) right = true;
            if (e.getKeyCode() == KeyEvent.VK_D) left = true;
            if (e.getKeyCode() == KeyEvent.VK_W) down = true;
            if (e.getKeyCode() == KeyEvent.VK_S) up = true;
        }
    }

    public GameClient getClient() {
        return client;
    }

    public GameServer getServer() {
        return server;
    }
}
