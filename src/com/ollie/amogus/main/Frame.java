package com.ollie.amogus.main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Frame {

    private JFrame mainFrame;
    private static Game g;

    public Frame(){
        try{
            g = new Game();
        } catch (IOException e){
        e.printStackTrace();
        }

        mainFrame = new JFrame();
        mainFrame.setTitle("Amog");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(g);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        g.init();
    }

    public static Game getG() {
        return g;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Frame::new);

    }

}
