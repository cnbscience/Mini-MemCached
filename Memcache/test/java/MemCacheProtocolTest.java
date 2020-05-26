import MemCachedServer.MemCache;
import MemCachedServer.ParserAndDispatcher;
import MemCachedServer.Util;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class MemCacheProtocolTest {


    @Test
    public void TestMemCacheProtocolSet() {
        String request = "";

        String key = Util.getRandomString(5);
        StringBuffer sb = new StringBuffer();
        request = "set"+" "+key+" "+0+" "+1234+" "+5;

        ParserAndDispatcher pd = new ParserAndDispatcher();
        pd.validateRequestProtocol(request);
        Assert.assertEquals(true, pd.validateRequestProtocol(request));

        System.out.println("****************Score**********************");
        System.out.println("MemCached put request test case successfully passed");
        System.out.println("*******************************************");
    }


    @Test
    public void TestMemCacheProtocolGet() {
        String request = "";
        OutputStream output = null;

        String key = Util.getRandomString(5);
        ParserAndDispatcher pd = new ParserAndDispatcher();

        request = "get"+" "+key;
        Assert.assertEquals(true, pd.validateRequestProtocol(request));

        System.out.println("****************Score**********************");
        System.out.println("MemCached get request test case successfully passed");
        System.out.println("*******************************************");
    }


    @Test
    public void TestMemCacheProtocolBadRequest() {
        String request = "";
        OutputStream output = null;

        String key = Util.getRandomString(5);
        ParserAndDispatcher pd = new ParserAndDispatcher();

        //Not giving length of data chunk
        request = "set"+" "+key+" "+0+" "+1234+" ";
        pd.validateRequestProtocol(request);
        Assert.assertEquals(false, pd.validateRequestProtocol(request));

        //No command given
        request = "adsa"+ " "+key+" "+0+" "+1234+" ";
        pd.validateRequestProtocol(request);
        Assert.assertEquals(false, pd.validateRequestProtocol(request));

        //Key length > 150
        key = Util.getRandomString(200);
        request = "set"+" "+key+" "+0+" "+1234+" ";
        pd.validateRequestProtocol(request);
        Assert.assertEquals(false, pd.validateRequestProtocol(request));

        System.out.println("****************Score**********************");
        System.out.println("MemCache Protocol BadRequest test case successfully passed");
        System.out.println("*******************************************");
    }

}
