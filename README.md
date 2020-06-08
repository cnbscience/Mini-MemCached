**Mini-MemCached:**
generic in nature, but intended for use in speeding up dynamic web applications by 
alleviating database load. Memcached is an in-memory key-value store for small chunks of arbitrary 
data (strings, objects) from results of database calls, API calls, or page rendering.

**High Level Overview:**
The Mini-memcached server is comprised of three main components :
-Controller
-Protocol parser and Dispatcher
-MemCache service.

**Controller:** The controller is responsible for all the configurations of the memcache server. 
It is also responsible for maintaining dynamic thread pool to throttle cache performance based on the external demand.

**Protocol parser:** This module is responsible for parsing the incoming request from the client. 
It is responsible for verifying if the incoming request adheres to memcached text protocol. 
It also checks the parameters of the request for their validity. 
It responds to clients following standards of memcached text protocol.

**Dispatcher:** Once the request is parsed by the protocol parser and verified. The dispatcher forwards the request to 
the concerned service provider(SET or GET). once the service is completed, results are sent back to clients using 
standard memcached text protocol.

**Cache service:** This is the core module of the server, responsible for serving requests sent from the dispatcher. 
Internally it uses LRU caching policy to keep the cache updated. It’s capacity is initialized during the server 
configuration by the Controller.

More info : https://memcached.org/

**Getting Started:**
1. Download IntelliJ IDEA or eclipse based on the underlying OS, make sure you have
2. open JDK-14 installed. 
3. Install telnet if you don't have one.
4. Install JUnit and configure it's class path.

**Prerequisites:**
1. This project is built with IntelliJ Idea IDE with openJDK14.
2. Also have Junit installed in your class path to run the test cases.
3. Telnet
4. Git installed.

**Installing and running**

To deploy using command line and git.

1. Make a directory : “mkdir memcached”
2. type “cd memcached”
3. git clone https://github.com/cnbscience/Mini-MemCached.git
4. cd /path/to/Mini-MemCached/Memcache/main/java/MemCachedServer
5. javac -d /path/to/MemcacheD/target/classes/ *.java (Not compulsory if the class files are present)
6. java -classpath /path/to/memcached/MemcacheD/target/classes/ MemCachedServer.MemcachedServerDriver
7. If the server is up you will see this message.
*************Memcached Server started************** 
      Waiting for clients to connect
8. Open one more terminal and type:
   a. “telnet localhost <port_no>” (defualt pot_no is 11211).
9. Start sending memcached text protocol requests.

To deploy Using IDE like IntelliJ:
1. Download the project from https://github.com/cnbscience/Mini-MemCached.
2. Import the project into IntelliJ IDEA and run build and run MemcachedServerDriver.
  

**Until finished**

The server will keep responding until the client closes it's session. To close the server, 
just stop the server.


**Running unit tests:**
In the test folder there are two types of tests
1. Memcached core functionality test - which validates and verifies core functionality like set,get and cache eviction         mechanisms.
2. Memcached protocol test: which verifies the protocol validation module of this project.
To run the tests, have you Junit configure with your class path.

**Running performance benchmarking using memtier_benchmark** 

1. Download and install memtier_benchmark as per the instructions mentioned in the README.
2. Start the memcached server either through the command line or IDE as mentioned above.
3. Use various tuning parameters to check the performance of this memcached.
4. Here is one such performance benchmark which was captured.

Built With
ItelliJ IDEA 

Versioning
Mini-Memcached- version 1.0

**Authors
Chiddu Bhat**

**License
Free to use**


