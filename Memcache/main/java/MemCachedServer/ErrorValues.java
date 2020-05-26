package MemCachedServer;

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
public enum ErrorValues {ERROR, SERVER_ERROR, CLIENT_ERROR};
