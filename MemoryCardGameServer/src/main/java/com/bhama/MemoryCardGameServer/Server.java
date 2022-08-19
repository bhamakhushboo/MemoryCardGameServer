package com.bhama.MemoryCardGameServer;


import java.awt.*;
import java.util.*;
import javax.swing.*;

// @author Bhama Bahadoor
public class Server {

    public static JFrame frame;
    public static JLabel jl;
    public JPanel panel;
    public JLabel display1;

    //Constructor creating a Jframe on Server and displaying jlabels
    public Server() {
        frame = new JFrame("MEMORY MATCHING GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.blue);

        panel = (JPanel) frame.getContentPane();
        panel.setLayout(null);

        JLabel ListPlayers = new JLabel("LIST OF PLAYERS CONNECTED");
        jl = new JLabel();
        panel.add(ListPlayers);
        panel.add(jl);

        ListPlayers.setFont(new Font("Serif", Font.BOLD, 25));
        Dimension size = ListPlayers.getPreferredSize();
        ListPlayers.setBounds(15, 15, size.width, size.height);
        ListPlayers.setForeground(Color.WHITE);
        ListPlayers.setBackground(Color.GRAY);

        Dimension size1 = ListPlayers.getPreferredSize();
        jl.setBounds(15, 35, size1.width, size1.height);
        jl.setFont(new Font("Serif", Font.BOLD, 20));
        jl.setForeground(Color.RED);
        jl.setBackground(Color.ORANGE);
        
        display1 = new JLabel("The chat server is running...");
         Dimension size2 = ListPlayers.getPreferredSize();
        display1.setBounds(15, 415, size2.width, size2.height);
        display1.setFont(new Font("Serif", Font.BOLD, 15));
        display1.setForeground(Color.WHITE);
        
        panel.add(display1);

        frame.setSize(500, 500);
        frame.setVisible(true);

    }

   

}
