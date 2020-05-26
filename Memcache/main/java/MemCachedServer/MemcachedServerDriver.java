package MemCachedServer;

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
