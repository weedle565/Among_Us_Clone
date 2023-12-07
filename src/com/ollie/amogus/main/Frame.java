package com.ollie.amogus.main;

import com.ollie.amogus.networking.DisconnectPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class Frame {

    private static Game g;

    private static final JFrame mainFrame = new JFrame();

    public Frame(){
        try{
            g = new Game();
        } catch (IOException e){
            e.printStackTrace();
        }


        mainFrame.setTitle("Amog");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                DisconnectPacket disconnectPacket = new DisconnectPacket(g.getCrew().getUserName());
                g.getClient().sendData(disconnectPacket.getData());

                System.exit(0);

            }
        };

        mainFrame.addWindowListener(exitListener);
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

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static Game getG() {
        return g;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Frame::new);

    }

}
