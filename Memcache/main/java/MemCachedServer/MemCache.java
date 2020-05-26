package MemCachedServer;
/**
 * @author  Chiddu bhat
 * @version 1.0
 * @since   May-21-2020
 */


import java.util.HashMap;

/**
 * High Level Design:
 * This Memcached is built with two important components:
 * HashMap and a Doubly linked list. HashMap is used for constant time lookups af keys.
 * We also maintain a doubly linked list which are mapped to hashmap. The reason for mapping
 * Doubly Linked List Nodes to maps is for achieving constant time insert and delete.
 * This doubly linked list is arranged based on  elements which are most
 * recently used. Least recently used elements will be at the end of
 * the list. When we hit MAX_CAPACITY we will start removing elements from the
 * tail of the list which represents LRC cache eviction policy. Since adding and removing
 * elements from Doubly linked list also.
 *
 */


public class MemCache<K,V>{
    int capacity;
    Node LRUhead;
    Node LRUtail;

    HashMap<K,Node> memcached;


    /**
     * This constructor method is used initialize the memcache with a given capacity
     * the capacity is loaded from Configuration.properties file by Controller.
     * @param capacity
     * @return none
     * @exception
     * @see
     */

    public MemCache(int capacity) {
        memcached = new HashMap<>(capacity);
        this.capacity = capacity;
        LRUhead = new Node();
        LRUtail = new Node();
        LRUhead.setNext(LRUtail);
        LRUtail.setPrev(LRUhead);
    }

    /**
     * This method is built specifically for unit testing
     * Since we don't want to expose Node class, we have this bridge method
     * to return just the value for a given key.
     * Warning : This method is does not support concurrency and doesn't manipulate
     * LRU cache.
     * @param key<K>
     * @return value<V>
     * @exception
     * @see
     */
    public V getValue(K key){
        if(memcached.containsKey(key)) {
            Node n = memcached.get(key);
            return (V) n.getVal();
        }else{
            return null;
        }
    }

    /**
     * This is a helper method to add elements  which are recently used to
     * at the head of the doubly linked list.This only manipulates the LRU list and not
     * the MemCached itself.
     * This method is designed to support concurrency while manipulating LRU List.
     * @param node
     * @return none
     * @exception
     * @see
     */
     private synchronized void add(Node node){
        Node first = LRUhead.getNext();
        LRUhead.setNext(node);
        node.setNext(first);
        first.setPrev(node);
        node.setPrev(LRUhead);
    }

    /**
     * This is a helper method that was used during development and debugging
     * this method provides most upto date cacpacity of the MemCached.
     * @param
     * @return capacity
     * @exception
     * @see
     */
    public int getCurrentUsage(){
        return memcached.size();
    }

    /**
     * This is a helper method to remove elements which are least recently used from the
     * tail of the doubly linked list. This only manipulates the LRU list and not
     * the MemCached itself.
     * This method is designed to support concurrency while manipulating LRU List.
     * @param node
     * @return none
     * @exception
     * @see
     */

    private synchronized void remove(Node node) {

        Node prev = node.getPrev();
        Node next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);
    }

    /**
     * This is a public method to get elements based on the key provided. If the element
     * exists we will return the value and modify the LRU list by removing element from
     * its current position and adding it to the front of the list (representing most recently used).
     * This method is designed to support concurrency while manipulating LRU List.
     * This method return Node which contains key, value, flags, ttl, bytes stored.
     * This method is called from dispatcher when it receives  a get request.
     * @param key
     * @return Node
     * @exception
     * @see
     */
    public  synchronized Node get(K key) {
        if(!memcached.containsKey(key)){
            return null;
        }else{
            Node rem = memcached.get(key);
            remove(rem);
            add(rem);
            return rem;
        }
    }

    /**
     * This is a public method to put data based on the key provided along with
     * flags, timetolive(TTL) and length of data(flags and TTL are not supported in this design).
     * Three conditions to consider in this method :
     * 1) If the key exists we will modify the data and other metadata to the latest data and metadata
     * as per the request. We will modify the LRU list by removing element from its current position and adding
     * it to the front of the list (representing most recently used).
     * 2) If the key doesn't exist we will create
     * a node and store all the metadata information and add it to the front of the LRU list.
     * 3) If we hit the max capacity of the MemCache,
     *          -we will evict items from the MemCache by following least recently used cache eviction policy.
     *          Which means we will remove elements from the tail of LRU list and it's entry in the hashmap.
     *          -Create new node and add it to the front of LRU list and add that entry into out hashmap.
     * This method is designed to support concurrency while manipulating
     * LRU List. This method return Node which contains key, value, flags, ttl, bytes stored.
     * This method is called from dispatcher when it receives  a get request.
     * @param key,value,flags,ttl,bytes
     * @return
     * @exception
     * @see
     */
    public synchronized void put(K key, V value, int flags ,int ttl, int bytes) {
        //System.out.println("IN PUT MEMCACHE with value "+ value);
        if(memcached.containsKey(key)) {
            Node node = memcached.get(key);
            if (node != null) {
                node.setVal(value);
                node.setFlags(flags);
                node.setBytes(bytes);
                node.setTtl(ttl);
                remove(node);
                add(node);
            }
            //System.out.println("BEFORE RETURN FROM LEVEL1");
            return;
        }
        if(this.capacity== memcached.size()){
            //System.out.println("CAPACITY FULL, DOING EVICTION NOW");
            Node n = new Node(key, value, flags, ttl, bytes);
            memcached.remove(LRUtail.getPrev().getKey());
            remove(LRUtail.getPrev());
            memcached.put(key,n);
            add(n);
        }else{
            Node n = new Node(key, value, flags, ttl, bytes);
            memcached.put(key,n);
            add(n);
            //System.out.println("SUCCESSFULLY ADDED ITEM :" + value);
        }
    }

}
