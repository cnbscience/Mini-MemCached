package MemCachedServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MemCachedTask extends Thread{
    private Socket socket;
    private MemCache memCache;
    OutputStream output;
    PrintWriter outputStream;
    Scanner inputStream;

    /**
     * This method is used to check if a player has won the game
     * after placing a disc, this method checks 4 possible directions
     * after every move by either players.
     *  1) Horizontal verification (check all the rows)
     *  2) Vertical verifaction (check all the columns)
     *  3)Left diagonal
     *  4)Right diagonal.
     * @param
     * @return true/false
     * @exception
     * @see
     */
    public MemCachedTask(Socket socket, MemCache memCache){
        this.socket = socket;
        this.memCache = memCache;
    }


    /**
     * This method is used to check if a player has won the game
     * after placing a disc, this method checks 4 possible directions
     * after every move by either players.
     *  1) Horizontal verification (check all the rows)
     *  2) Vertical verifaction (check all the columns)
     *  3)Left diagonal
     *  4)Right diagonal.
     * @param
     * @return true/false
     * @exception
     * @see
     */

    @Override
    public void run() {
        //System.out.println("Connected: " + socket);
        try {
            output = socket.getOutputStream();
            inputStream = new Scanner(socket.getInputStream());
            outputStream = new PrintWriter(socket.getOutputStream(), true);
            ParserAndDispatcher pd = new ParserAndDispatcher();
            String input = null;
            while (inputStream.hasNextLine()) {
                input = inputStream.nextLine();
                pd.protocolParserAndDispatch(memCache, input, inputStream, output);
                //protocolParserAndDispatcher(input);
            }
            //return;
        } catch (Exception e) {
            System.out.println("Error : " +  e.toString()+ socket);
        } finally {
            try {
                socket.close();
                inputStream.close();
                output.close();
                outputStream.close();
            } catch (IOException e) {
                System.out.println("IOEXCEPTION: " + socket);
            }
            System.out.println("Closed: " + socket);
        }

    }
}
