tcp-stream-router
=========================

A simple TCP data stream broadcasting router based on Akka actors that connects as client to a socket outputing data and exposes a server socket for others to connect to.


Usage:

`java -jar tcp-stream-router.jar  (source streaming host) (source streaming port) (destination server host) (destination server port)`

