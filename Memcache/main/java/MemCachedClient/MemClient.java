package MemCachedClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MemClient {

    // initialize socket and input output streams
    private Socket socket = null;
    private Scanner scanner;
    Scanner inputStream;
    PrintWriter outputStream;
    private String prompt = ">>";


    public void dispatchToServer(String command) {
        //replace all extra white spaces
        command.replaceAll("\\s+", " ").trim();
        String[] tokens = command.split(" ");
        switch (tokens[0]) {
            case "set":
                sendSetRequestToServer(command);
                break;

            case "get":
                sendGetRequestToServer(command);
                break;

            case "quit":
                sendGoodByeToServer(command);
                break;

            default:
                System.out.println("Unknown command");
                return;
        }

    }

    // assuming integer is in decimal number system
    static boolean isValidInteger(String s) {
        for (int a = 0; a < s.length(); a++) {
            if (a == 0 && s.charAt(a) == '-') return false;
            if (!Character.isDigit(s.charAt(a))) return false;
        }
        return true;
    }

    public void sendSetRequestToServer(String command) {
        outputStream.println(command);
        String data = scanner.nextLine();
        outputStream.println(data);
        System.out.println(inputStream.nextLine());
        showPrompt();
        return;
    }

    public void sendGetRequestToServer(String command) {
        outputStream.println(command);
        while(true) {
            String response = inputStream.nextLine();
            if(response.equals("END")){
                break;
            }
            System.out.println(response);
        }
        showPrompt();
    }

    public void printCommandMenu(){
        System.out.println("HERE ARE SOME USEFUL COMMANDS TO TRY : ");
        System.out.println();
        System.out.println("Storage commands : ");
        System.out.println();
        System.out.println("set <key><flag><bytes>");
        System.out.println("<data>");
        System.out.println();
        System.out.println("Retrieval commands : ");
        System.out.println("get <key>");
        System.out.println();
        System.out.println("Exit command : ");
        System.out.println("quit");
        System.out.println();



    }

    public void welcomeMessage(){
        System.out.println("********************************************************");
        System.out.println("********WELCOME TO MINI MEMCACHED SERVER!!!*************");
        System.out.println("********************************************************");
        System.out.println();
        System.out.println();
        printCommandMenu();
    }

    public void sendGoodByeToServer(String command){
        outputStream.println(command);
        System.out.println("********THANK YOU FOR USING MINI MEMCACHED!!!*************");
    }

    public boolean isValidRequest(String line) {
        line.replaceAll("\\s+", " ").trim();
        String tokens[] = line.split(" ");
        if (tokens[0].equals("set")) {
            //check if the user has provided key, flags and bytes;
            if (tokens.length == 4 && isValidInteger(tokens[2]) && isValidInteger(tokens[2])) {
                return true;
            } else {
                return false;
            }
        } else if (tokens[0].equals("get") && (tokens.length == 2)) {
            return true;
        }
        return false;
    }

    public void showPrompt() {
        System.out.print(prompt);
    }

    public MemClient(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            welcomeMessage();

            // takes input from terminal
            scanner = new Scanner(System.in);
            inputStream = new Scanner(socket.getInputStream());
            outputStream = new PrintWriter(socket.getOutputStream(), true);

            // sends output to the socket
            boolean addDataBlock = false;
            String line = "";
            String preData = "";
            //scanner.useDelimiter("[\\r\\n]+");
            scanner.useDelimiter("\\n");
            showPrompt();
            while (true) {
                line = scanner.nextLine();

                if (line.equals("quit")) {
                    dispatchToServer(line);
                    break;
                }

                if (!isValidRequest(line)) {
                    System.out.println("ERROR STRING");
                    showPrompt();
                    continue;
                }
                dispatchToServer(line);
            }

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // close the connection
        try {
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

}
