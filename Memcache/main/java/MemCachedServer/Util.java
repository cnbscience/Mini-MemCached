package MemCachedServer;

import java.util.Random;

public class Util {

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
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
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
    public static String getRandomString(int length){
        Random rand = new Random();
        StringBuffer sb  = new StringBuffer();
        for(int i =0; i< length; i++) {
            sb.append((char) (rand.nextInt(26) + 'a'));
        }
        return sb.toString();
    }
}
