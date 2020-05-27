package MemCachedServer;

/**
 * @author  Chiddu bhat
 * @version 1.0
 * @since   May-21-2020
 *
 * This is the worker thread class and has following methods
 * public MemCachedTask(Socket socket, MemCache memCache)
 *  public void run()
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MemCachedTask extends Thread{
    private Socket socket;
    private MemCache memCache;
    OutputStream outputStream;
    Scanner inputStream;

    /**
     * This is the constructor for a worker thread. Initializes worker thread
     * with Memcache Instance and ServerSocket.
     * @param socket,memCache
     * @return
     * @exception
     * @see
     */
    public MemCachedTask(Socket socket, MemCache memCache){
        this.socket = socket;
        this.memCache = memCache;
    }


    /**
     * This is the run method of worker thread. Each worker thread does the following after being spawned
     * by the controller. Sends request sent by client to  parser which validates the request itself
     * then the dispatcher forwards it to appropriate memcached service interface. While this is beign done
     * the worker thread holds connection with client. Once there are no more requests, worker thread closes
     * socket, inputstream and outputstream.
     * @param
     * @return
     * @exception
     * @see
     */

    @Override
    public void run() {
        //System.out.println("Connected: " + socket);
        try {
            outputStream = socket.getOutputStream();
            inputStream = new Scanner(socket.getInputStream());
            ParserAndDispatcher pd = new ParserAndDispatcher();
            String input = null;
            while (inputStream.hasNextLine()) {
                input = inputStream.nextLine();
                pd.protocolParserAndDispatch(memCache, input, inputStream, outputStream);
                //protocolParserAndDispatcher(input);
            }
            //return;
        } catch (Exception e) {
            System.out.println("Error : " +  e.toString()+ socket);
        } finally {
            try {
                socket.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                System.out.println("IOEXCEPTION: " + socket);
            }
            System.out.println("Closed: " + socket);
        }

    }
}
