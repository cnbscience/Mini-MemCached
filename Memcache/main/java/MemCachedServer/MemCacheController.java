package MemCachedServer;

/**
 * @author  Chiddu bhat
 * @version 1.0
 * @since   May-21-2020
 */

import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemCacheController {
    private ServerSocket serverSocket = null;
    private int port;
    private int capacity;
    private MemCache memCache;
    private Properties config;

    public MemCacheController() {

    }


    /**
     * This method is responsible for creating server socket based on the
     * port that is configured in Configuratios.properties file , it also intializes the
     * memcached to capacity specified in config file.
     * It is responsible for hosting executor service, which is a neat way of managing threads
     * java.
     * Design choice : ExecutorServicePool vs regular Thread :
     *  Executor service makes it extremely easy to manage threads. If regular threads are
     *  spawned with each request the system is not effectively scalable, we would be limited
     *  by the max number of threads that can be spawned and new requests would be dropped, if all
     *  threads are busy serving requests.In Executor service threads return to thread pool after
     *  the service, waiting to serve the next request. If all the threads are already busy serving
     *  requests, new requests are queued until threads are available.
     *
     *  CachedThreadPool()  vs fixedThreadPool() :
     *  CachedThreadPool helps to dynamically scale threads required as per the request traffic.
     *  It automatically scales up or scales down on number of threads that are required to serve
     *  requests at any given point in time based on request traffic. With FixedThreadPool we are bound
     *  by number of threads that can serve requests at any given point in time. This will be a huge
     *  bottleneck when I/O through sockets.
     *  memtier_benchmark - The system was not able to scale with fixedThreadPool of 32
     *  when memtier_benchmark was configured with  8 threads having 10 connections each
     *  with 100 requests per thread and data length of 5.
     *
     * @param
     * @return
     * @exception
     * @see
     */
    public void controller () {
        //reading properties from configuration file
       loadMemCacheConfigurations();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("********Memcached Server started**********");
            System.out.println("Waiting for clients to connect");
            memCache = new MemCache(capacity);
        } catch (IOException e) {
            System.out.println("Port is already taken by someone else, please free that up.");
           return;
        }

        ExecutorService pool = Executors.newCachedThreadPool();
        while (true) {
            try {
                pool.execute(new MemCachedTask(serverSocket.accept(),memCache));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method is used to load configurations stored in
     * "Configurations.properties" file. Please edit this file
     * for changing existing configuration or adding new one.
     * This files contains following configurations
     *      -port(default : 11211)
     *      -capacity(default: 10000000)
     * @param
     * @return
     * @exception
     * @see
     */
    private void loadMemCacheConfigurations(){
        try {
            FileInputStream fileInputStream = new FileInputStream("Configuration.properties");
            config = new Properties();
            config.load(fileInputStream);
            port = Integer.parseInt(config.getProperty("port"));
            capacity = Integer.parseInt(config.getProperty("capacity"));
        } catch (IOException e) {
            System.out.println("Failed to load server configurations "+ e );
            e.printStackTrace();
            return;
        }
    }

}
