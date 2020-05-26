package MemCachedServer;
/**
 * @author  Chiddu bhat
 * @version 1.0
 * @since   May-21-2020
 */

import java.util.Random;

public class Util {

    /**
     * This method is a helper method to check if a string is
     * a valid integer. used in validating memcached text protocol
     * @param str
     * @return true
     * @exception
     * @see
     */
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * This method is a helper method to generate random strings of given
     * input length, used highly in testing
     * @param length
     * @return String
     * @exception
     * @see
     */
    public static String getRandomString(int length){
        Random rand = new Random();
        StringBuffer sb  = new StringBuffer();
        for(int i =0; i< length; i++) {
            sb.append((char) (rand.nextInt(26) + 'a'));
        }
        return sb.toString();
    }
}
