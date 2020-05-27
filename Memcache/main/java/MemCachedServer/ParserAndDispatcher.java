package MemCachedServer;

/**
 * @author  Chiddu bhat
 * @version 1.0
 * @since   May-21-2020
 */

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class ParserAndDispatcher {

    public ParserAndDispatcher() {

    }

    /**
     * This method verifies the request from client in the first step,
     * if request is valid it dispatches request to concerned interface methods
     * to serve the request.
     * Here is a valid set of request and response supported by this memcache.
     *
     * Valid set request :
     *  Request:  set <key> <flags> <ttl> <bytes>
     *          <datachunk>
     *  Response: STORED
     *  Valid get request :
     *  Request:  get <key>
     *  Response: VALUE <key> <flags> <bytes>
     *            <data chunk>
     *            END
     * @param
     * @return true/false
     * @exception
     * @see
     */
    public void protocolParserAndDispatch(MemCache memCache, String request,
                                          Scanner inputStream, OutputStream output) {
        //validate if the request is valid.
        if(!validateRequestProtocol(request)){
            return;
        }

        String[] token = request.split(" ");
        System.out.println("OPERATION RECIEVED =  "+ token[0]);
        switch (token[0]) {
            case "set":
                performSetOperation(memCache, token, inputStream, output);
                break;
            case "get":
                token = request.split(" ");
                //key is stored in the second argument;
                performGetOperation(memCache, token[1], output);
                break;
            default:
                System.out.println("Invalid request from client "+ token[0]);
                sendToClient(ErrorValues.ERROR.toString(),output);
        }

    }

    /**
     * This is a helper method to send response back to client , we convert the
     * response to raw data and use OutputStream to write those bytes.
     * We also append \r\n at the end to comply with Memcached text protocol stanadards.
     * @param s, outputStream
     * @return none
     * @exception
     * @see
     */

    private static void sendToClient(String s, OutputStream outputStream) {
        String temp = s + "\r\n";
        byte[] bytes = temp.getBytes();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the interface method between controller and memcached to perform
     * get operation for a given key. This operation assumes the initial request
     * is processed by the parser.
     *  If Key is not found just return "END\r\n"
     *  If Key is found we respond
     *       VALUE <key><flags><bytes>
     *       <data>
     *       <END>
     * @param memCache,key,outputStream
     * @return none
     * @exception
     * @see
     */
    private static void performGetOperation(MemCache memCache, Object key,
                                           OutputStream outputStream) {
        //System.out.println("Received GET command : ");
        Node n = memCache.get(key);
        if (n == null) {
            sendToClient("END",outputStream);
            return;
        }
        String o = "VALUE " + key + " " + n.getFlags() + " " + n.getBytes();
        sendToClient(o, outputStream);
        sendToClient("" + n.getVal(), outputStream);
        sendToClient("END", outputStream);
        return;
    }

    /**
     * This method is used validate request that are sent to Memcached, this design
     * only supports get and set requests as of now. The key length is restricted to 150.
     * This also checks if length of set request is 5 along with numeric validity for
     * flags, ttl and bytes parameters.
     *
     * @param request
     * @return true/false
     * @exception
     * @see
     */
    public boolean validateRequestProtocol(String request){
        String[] temp = request.split(" ");
        System.out.println("OPERATION RECIEVED =  "+ temp[0]);
        switch (temp[0]) {
            case "set":
                if (temp.length != 5 || !Util.isNumeric(temp[2]) ||
                        !Util.isNumeric(temp[3]) || !Util.isNumeric(temp[4])
                        || (temp[1].length() >= 150)) {
                    return false;
                }else{
                    return true;
                }
            case "get":
                    return true;
            default:
                return false;
        }
    }


    /**
     * This is the interface method between controller and memcached to perform
     * set operation with given key and value. This operation assumes the initial request
     * is processed by the parser.
     *  If Key is exists update the new data and metadata and return "STORED\r\n"
     *  If Key doesn't exist add the new key and value and return "STORED\r\n"
     * @param memCache,key,outputStream
     * @return none
     * @exception
     * @see
     */
    public static void performSetOperation(MemCache memCache, String[] token,
                                           Scanner inputStream, OutputStream outputStream) {
        String error;
        String data = inputStream.nextLine();

        System.out.println("DATA CHUNK : "+ data);
        if (data.length() != Integer.parseInt(token[4])) {
            error = ErrorValues.CLIENT_ERROR.toString() + " bad data chunk";
            sendToClient(error,outputStream);
            return;
        }
        try {
            memCache.put(token[1], data, Integer.parseInt(token[2]),
                    Integer.parseInt(token[3]), Integer.parseInt(token[4]));
        }catch(Exception e){
            System.out.println("Server error "+ e );
            sendToClient(ErrorValues.SERVER_ERROR.toString(), outputStream);
            return;
        }
        //System.out.println("SUCCESSFULLY STORED , SENDING RESPONSE BACK TO CLIENT");
        sendToClient("STORED",outputStream);
    }
}
