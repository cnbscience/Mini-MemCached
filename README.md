Mini-MemCached:
generic in nature, but intended for use in speeding up dynamic web applications by 
alleviating database load. Memcached is an in-memory key-value store for small chunks of arbitrary 
data (strings, objects) from results of database calls, API calls, or page rendering.

More info : https://memcached.org/

Getting Started:
Download IntelliJ IDEA or eclipse based on the underlying OS, make sure you have
open JDK-14 installed. 
Install telnet if you don't have one.
Install JUnit and configure it's class path.

Prerequisites:
This project is built with IntelliJ Idea IDE with openJDK14.
Also have Junit installed in your class path to run the test cases.

Installing and running 
Command line : Compile the project with standard Javac command and run
/path/to/java/ -classpath /path/to/classes MemCachedServer.MemcachedServerDriver
GUI: import the project into IntelliJ IDEA and run build and run MemcachedServerDriver.
you should be able to see this below message when the server has started successfully.
********Memcached Server started**********
Waiting for clients to connect

Open another terminal and run 
telnet localhost 11211 (make sure you have telnet installed)
You can run memcached commnds from this terminal.


until finished
The server will keep responding until the client closes it's session. To close the server, 
just stop the server.

Running the tests
In the test folder there are two types of tests 
Memcached core functionality test - which validates and verifies core functionality like
set , get and cache eviction mechanisms.
Memcached protocol test: which verifies the protocol validation module of this project.
To run the tests, have you Junit configure with your class path. 

Built With
Maven - Dependency Management

Versioning
Connect four - version 2.0

Authors
Chiddu Bhat

License
Free to use

Acknowledgments
Thank you CarbonBlack for providing me an opportunity to work on a cool project. 
it was fun filled 5 hours of work.
