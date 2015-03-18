import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Accept_clients implements Runnable{
	
	 private ServerSocket socketserver;
     private Socket socket = null;
     private ObjectInputStream inStream = null;
     private ObjectOutputStream outputStream = null;
     private Server2Connection s2c = null;
     
     public Accept_clients(ServerSocket s)
     {
          this.socketserver = s;
          
     }
	
	public Accept_clients(Socket clientSocket) {
		this.socket = clientSocket;
	}

	public void run() {
		
		try {
			int i = 0;

            while(true)
             {
            	i++;
            	
               this.socket = socketserver.accept(); 
               
               //system.out.println("client Connected......"+"port::::"+ this.socket);
               
               ////system.out.println(this.socket.getPort());
              
               //A new server thread is created for every client request made to the server
               this.s2c =new Server2Connection(this.socket,i,new Server());
               
               new Thread(s2c,"Thread"+i).start();
              /// //system.out.println("Thread name::::"+Thread.currentThread().getName());
               Thread.sleep(20 * 1000);
               

              }
	}
		catch (IOException | InterruptedException e) {
            
            e.getMessage();
        }

}
	
	/* public synchronized void read() throws ClassNotFoundException, IOException
     {  
		 requestMSG msg;
		 this.inStream = new ObjectInputStream(socket.getInputStream());
         msg = (requestMSG) inStream.readObject();
         //system.out.println("Object received = " + msg);
         this.notify();
     }
	 
	 public synchronized void write()
     {  

		 String ack;
         try {
            
             this.wait(10000);
             //system.out.println(socket.getPort());
             this.outputStream = new ObjectOutputStream(socket.getOutputStream());
             ack = "Request Received";
             this.outputStream.writeObject(ack);
             this.outputStream.flush();
             this.notify();
        } 
         catch (IOException e) {
            e.getMessage();
        } 
        catch (InterruptedException e) {
            e.getMessage();
        }

     }*/
	 
}