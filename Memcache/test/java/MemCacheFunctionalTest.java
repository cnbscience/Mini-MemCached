import MemCachedServer.MemCache;
import MemCachedServer.Node;
import MemCachedServer.MemCacheController;
import MemCachedServer.ParserAndDispatcher;
import MemCachedServer.Util;
import org.junit.Assert;
import org.junit.Test;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MemCacheFunctionalTest {

    Scanner inputStream;

    @Test
    public void TestMemCachePutRequest() {
        MemCache m = new MemCache(100);

        String key = Util.getRandomString(5);
        String value = Util.getRandomString(5);
        m.put(key,value,0,0,value.length());

        Assert.assertEquals(value, m.getValue(key));
        System.out.println("****************Score**********************");
        System.out.println("MemCached put request test case successfully passed");
        System.out.println("*******************************************");
    }

    @Test
    public void TestMemCacheWrongKeyGetRequest() {
        MemCache m = new MemCache(100);

        String key = Util.getRandomString(5);
        String value = Util.getRandomString(5);
        m.put(key,value,0,0,value.length());

        Assert.assertEquals(null, m.getValue("wrongKey"));
        System.out.println("****************Score**********************");
        System.out.println("MemCached get request test case successfully passed");
        System.out.println("*******************************************");
    }


    //LRU EVICTION TEST
    @Test
    public void LRUEvictionTest() {
        MemCache m = new MemCache(5);

        String key = Util.getRandomString(5);
        String value = Util.getRandomString(5);
        m.put(key, value, 0, 0, value.length());

        String keyToBeEvicted = key;

        for(int i =0; i < 4; i++) {
            key = Util.getRandomString(5);
            value = Util.getRandomString(5);
            m.put(key, value, 0, 0, value.length());
        }
        //now the cache has 5 elements and will run the eviction policy
        key = Util.getRandomString(5);
        value = Util.getRandomString(5);
        m.put(key, value, 0, 0, value.length());

        Assert.assertEquals(null, m.getValue(keyToBeEvicted));

        System.out.println("****************Score**********************");
        System.out.println("MemCached Eviction test complete case successfully passed");
        System.out.println("*******************************************");
    }


    @Test
    public void MemCacheCapacityTest() {
        MemCache m = new MemCache(5);
        String key;
        String value;
        for(int i =0; i < 5; i++) {
            key = Util.getRandomString(5);
            value = Util.getRandomString(5);
            m.put(key, value, 0, 0, value.length());
        }
        //now the cache is full capacity, we should be doing eviction and
        //store the new object.
        key = Util.getRandomString(5);
        value = Util.getRandomString(5);

        m.put(key, value, 0, 0, value.length());
        m.put(key, value, 0, 0, value.length());

        Assert.assertEquals(value, m.getValue(key));

        System.out.println("****************Score**********************");
        System.out.println("MemCached Eviction test complete case successfully passed");
        System.out.println("*******************************************");
    }


}
