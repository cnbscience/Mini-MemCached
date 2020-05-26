package MemCachedServer;

/**
 * @author  Chiddu bhat
 * @version 1.0
 * @since   May-21-2020
 */



public class MemcachedServerDriver {

    /**
     * This is the driver class, start the controller which in turn configures
     * the server and created thread pool.
     * @param args
     * @return
     * @exception
     * @see
     */
    public static void main(String[] args) {
      MemCacheController memCacheController = new MemCacheController();
      memCacheController.controller();
    }
}
