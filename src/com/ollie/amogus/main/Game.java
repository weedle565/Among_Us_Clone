package com.ollie.amogus.main;

import com.ollie.amogus.gameobjects.Background;
import com.ollie.amogus.gameobjects.Wall;
import com.ollie.amogus.gameobjects.entities.Crewmate;
import com.ollie.amogus.gameobjects.entities.Directions;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    private final Crewmate crew;
    private final Background b;

    private final ArrayList<Wall> walls;

    private boolean playing;
    private boolean left,right,up,down;

    private Thread thread;

    public Game() throws IOException {

        setFocusable(true);

        walls = new ArrayList<>();

        playing = false;
        left = false;
        right = false;
        up = false;
        down = false;

        crew = new Crewmate(512, 600, "Test");
        b = new Background(-1400, -70);

        setPreferredSize(new Dimension(1024, 1024));
        setVisible(true);

    }

    public void init(){

        addKeyListener(new KeyInput());

        initWalls();

        start();
        playing = true;

    }

    private synchronized void initWalls(){

        walls.add(new Wall(390, 333, 260, 200));

    }

    public synchronized void start(){

        thread = new Thread(this);
        thread.start();
    }

    public synchronized void kill(){

        try{
            thread.join();
            playing = false;
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void run(){

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

            }

        }

        kill();

    }

    private synchronized void move(){

        if(up && left) {
            if(crew.checkCollisions(-1, -1)) return;
            System.out.println(crew.checkCollisions(-1,  -1));
            b.updatePos(-1, -1);
        }
        else if(up && right) {
            if(crew.checkCollisions(1, -1)) return;
            b.updatePos(1, -1);
        }
        else if(down && left) {
            if(crew.checkCollisions(-1, 1)) return;
            b.updatePos(-1, 1);
        }
        else if(down && right) {
            if(crew.checkCollisions(1, 1)) return;
            b.updatePos(1, 1);
        } else if(up) {
            if(crew.checkCollisions(0, -2)) return;
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
        crew.drawImage(g);

        walls.iterator().forEachRemaining(w -> w.drawImage(g));

        g.dispose();
        bs.show();

    }

    private void animate(){
        crew.addSpriteNum();
    }

    private void tick(){

    }

    public Background getB() {
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

}
