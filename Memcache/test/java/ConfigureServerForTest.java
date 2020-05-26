import MemCachedServer.MemCacheController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConfigureServerForTest extends Thread{


    @Override
    public void run(){
        MemCacheController m = new MemCacheController();
        m.controller();

    }


}
