package com.bhama.MemoryCardGameServer;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

// @author Bhama Bahadoor
public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        int numPlayers = 0;
        
        if (numPlayers < 5) {
            numPlayers++;
            Server board = new Server();
            UserThread gs = new UserThread();
            gs.start();
        } else {
            UIManager um = new UIManager();
            um.put("OptionPane.background", Color.MAGENTA);
            um.put("Panel.background", Color.MAGENTA);
            JOptionPane.showMessageDialog(null, "Maximum Players Reached. Please wait for your turn.", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

}