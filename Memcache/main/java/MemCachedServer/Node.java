package MemCachedServer;
/**
 * @author  Chiddu bhat
 * @version 1.0
 * @since   May-21-2020
 *
 * This is the node class which hold key, value and metadata.
 * There are the methods provided by this class
 * getter and setters of all attributes
 * public Node(K key, V val, int flags, int ttl, int bytes)
 *
 */

public class Node<K,V>{
   private K key;
   private V val;
   private int bytes;
   private int flags;
   private int ttl;

    private Node prev;
    private Node next;

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }



    /**
     * This constructor takes in key and data along with other metadata.
     * flags and ttl are not implemented as part of this design.
     * @param key,val,flags,ttl,bytes
     * @return
     * @exception
     * @see
     */
    public Node(K key, V val, int flags, int ttl, int bytes){

        this.val = val;
        this.key = key;
        this.flags = flags;
        this.ttl = ttl;
        this.bytes = bytes;

    }

    public Node(){

    }
}