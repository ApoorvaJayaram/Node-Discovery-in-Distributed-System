import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;

public class Server implements Runnable {
    private static ServerSocket serverSocket = null;
   private int serverport;
private Thread runningThread;
private boolean isStopped = false;
private SocketImpl serverPort;
    
    public Server(int port) {
    	this.serverport = port;
    	
    }

    public Server() {
		
	}

	public void communicate() {
        try {
        	//system.out.println("communicate...");
        	
        	if(this.serverSocket == null)
    			this.serverSocket = new ServerSocket(serverport);
    		
           System.out.println("waiting for the clients...");
             new Thread(new Accept_clients(serverSocket)).start();
             //Thread.sleep(5000);
            
            Thread.sleep(20 * 1000);

        } catch (InterruptedException | IOException e) {
        	//system.err.println("Error wile starting Server.");
			e.printStackTrace();
		}
    }

   /* public static void main(String[] args) {
    	
        Server server = new Server(Integer.parseInt(args[0]));
        Thread t = new Thread(server);
        t.start();
        server.communicate();
       
    }*/

	
	public void stopServer() {
		
		    //system.out.println( "Server cleaning up." );
		
		    //system.exit(0);
		
		    }

	 public void run(){
	        synchronized(this){
	            this.runningThread = Thread.currentThread();
	        }
	        openServerSocket();
	        while(! isStopped()){
	            Socket clientSocket = null;
	            try {
	                clientSocket = this.serverSocket.accept();
	            } catch (IOException e) {
	                if(isStopped()) {
	                    //system.out.println("Server Stopped.") ;
	                    return;
	                }
	                throw new RuntimeException(
	                    "Error accepting client connection", e);
	            }
	            //system.out.println("waiting for the clients...");
	            new Thread(
	                new  Accept_clients(
	                    clientSocket)
	            ).start();
	        }
	        //system.out.println("Server Stopped.") ;
	    }
	 
	 private synchronized boolean isStopped() {
	        return this.isStopped;
	    }

	    public synchronized void stop(){
	        this.isStopped = true;
	        try {
	            this.serverSocket.close();
	        } catch (IOException e) {
	            throw new RuntimeException("Error closing server", e);
	        }
	    }

	    private void openServerSocket() {
	        try {
	            this.serverSocket = new ServerSocket(serverport);
	        } catch (IOException e) {
	            throw new RuntimeException("Cannot open port "+serverport, e);
	        }
	    }

	}

