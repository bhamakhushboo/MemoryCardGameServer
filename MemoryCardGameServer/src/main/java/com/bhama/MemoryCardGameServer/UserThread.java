package com.bhama.MemoryCardGameServer;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// @author Bhama Bahadoor
public class UserThread extends Thread {

    private static DataInputStream dis;
    private static ObjectOutputStream oos;

    ArrayList<String> playersList = new ArrayList<>();
    private Object[] playerArray;
    private String[] images = {"apple.png", "apple.png", "circle.png", "circle.png", "heart1.png", "heart1.png",
        "heart2.png", "heart2.png", "diamond.png", "diamond.png", "clover.png", "clover.png",
        "question.png", "question.png", "star.png", "star.png"};

    //Using an arraylist to store the cards
    private ArrayList<String> CardsGrid = new ArrayList<>(Arrays.asList(images));
    private Object[] objects;

    // All client names, so we can check for duplicates upon registration.
    private static Set<String> names = new HashSet<>();

    // The set of all the print writers for all the clients, used for broadcast.
    private static Set<PrintWriter> writers = new HashSet<>();

    @Override
    public void run() {
        NewUserProcess();
    }

    //Method to create a new Socket connection each time a user connects
    public void NewUserProcess() {
        try {
            //creating a ServerSocker
            ServerSocket ss = new ServerSocket(6666);
            ExecutorService pool = Executors.newFixedThreadPool(500);
            Socket s = ss.accept();

            //Creating all output and input streams that are needed for the game
            oos = new ObjectOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());

            //reading the player name
            String playername = dis.readUTF();

            //starting a thread for user chat
            pool.execute(new UserThread.Handler(playername, s));

            //Adding players name to the array list
            playersList.add(playername);
            playerArray = playersList.toArray();

            //displaying the names of connected players to the JFrame
            if (playerArray != null) {
                for (Object player : playerArray) {
                    Server.jl.setText(player.toString() + " is playing");
                }
            }

            //Shuffling the cards randomly
            ShuffleCard();

            //Writing the randomlist of cards as an object to the output stream
            oos.writeObject(returnList());

            
            dis.readInt();
            ss.close();
            
        } catch (IOException e) {

            System.out.println(e);
            System.exit(1);
        }
    }

    //Method to shuffle cards for players
    public void ShuffleCard() {

        Collections.shuffle(CardsGrid);

        //converting the arraylist to array
        objects = CardsGrid.toArray();
    }

    //method to get the List
    public Object[] returnList() {
        return objects;
    }

    //The user chat handler class   
    private static class Handler implements Runnable {

        private String name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;

        public Handler(String name, Socket socket) {
            this.name = name;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                if (!name.isEmpty() && !names.contains(name)) {
                    names.add(name);
                }

                out.println("NAMEACCEPTED " + name);
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + name + " has joined");
                }
                writers.add(out);

                // Accept messages from this client and broadcast them.
                while (true) {
                    String input = in.nextLine();
                    if (input.toLowerCase().startsWith("/quit")) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                }
                if (name != null) {
                    System.out.println(name + " is leaving");
                    names.remove(name);
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + " has left");
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

    }

   
}
