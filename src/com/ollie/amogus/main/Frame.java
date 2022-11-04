package com.ollie.amogus.main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class Frame {

    private static Game g;

    private static JFrame mainFrame = new JFrame();

    public Frame(){
        try{
            g = new Game();
        } catch (IOException e){
            e.printStackTrace();
        }

        mainFrame.setTitle("Amog");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(g);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        try {
            g.start();
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    public static Game getG() {
        return g;
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Frame::new);

    }

}
