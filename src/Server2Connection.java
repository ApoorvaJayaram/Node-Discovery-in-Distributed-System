import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Server2Connection implements Runnable {

	Socket addr ;
	ObjectOutputStream os ;
	ObjectInputStream is ;
	String ack1 = null;
	String ack2 = null;
	//readWrite[] rw = new readWrite[5];
	knowledgeTable table = new knowledgeTable();
	private requestMSG nodeREQ;
	
	private static ArrayList<requestMSG> updatedList = new ArrayList<requestMSG>();

	int i;

	Socket clientSocket;
	Server server;

	public Server2Connection(Socket clientSocket, int i, Server server) {

		this.clientSocket = clientSocket;
		this.server = server;
		addr = clientSocket;
		this.i = i;

		try {
			is = new ObjectInputStream(addr.getInputStream());
			os = new ObjectOutputStream(addr.getOutputStream());

		} catch (IOException e) {
			//system.out.println(e);
		}
	}

	public void run() {
		// Socket clientSocket = null;
		ArrayList<requestMSG> msgList = null;
		requestMSG msg = null;
		String ack = null;
		boolean updated = false;
		// int j=0;
		Socket s1;
		boolean serverStop = false;
		BufferedReader reader;
		readWrite rw;
		requestMSG newMSG;
		String host;
		
		try {
			
			while (true) {
				////system.out.println("Thread.currentThread().getName()"+Thread.currentThread().getName());
				////system.out.println("Value of i : " + i);

				////system.out.println("message received from client" + i);
				
				msgList = (ArrayList<requestMSG>) is.readObject();
				for (int j = 0; j < msgList.size(); j++) {
					msg = msgList.get(j);
					
					//system.out.println("Object received = " + msg +"\n from client node"+msg.hostname);
					////system.out.println("read" + j + " completed");
					//msg.clientPort=clientSocket.getPort();
				
				 reader = new BufferedReader(new FileReader(
							"configserv"+msg.getDestNodeName()+".txt"));
					//system.out.println("Destnation::::"+msg.getDestNodeName());
					rw = new readWrite();
				
				try {
					
					String line = null;
					
					while ((line=reader.readLine()) != null && !line.isEmpty()) {
					
						//system.out.println(line);
						newMSG=rw.WriteToTable(line,msg.DestNodeName,msg.serverPort,i);
						table.add(newMSG, msg.hostname);
						host = rw.readFile(line);

					
				
	
			
				
					Thread.sleep(2 * 1000);

					if (!host.equals(msg.hostname) && updated == false) {
						
						
						updated = true;
						/*rw.WriteToFile(msg);
						addToList(msg);*/

					}
					
					
					//system.out.println("write completed");
					//s1= new Socket(msg.hostname, addr[i].getPort());
				}
				
				if (updated) {
					rw.WriteToFile(msg);
					addToList(msg);
					ack = "Request Received....Destination File updated with new value";
				} else {
					ack = "Request Received....Destination File not updated with new value";
				}

				os.writeObject(ack);
				// os[i].flush();
				
				Thread.sleep(2 * 1000);
				sentToAll(updatedList);
				//system.out.println(addr.getPort());
				
				
				
				

			
			}catch (Exception e) {
					//system.out.println(e.getMessage());
					e.printStackTrace();
				}

				}
				
			
				if (is.read() == -1) {
					is.close();
					os.close();
					
					//system.out.println("EOF------");
					break;
					//server.stop();
					// serverStop = true;
					//sentToAll(updatedList);
					//break;
				}
				
			}
			
			/*
			 * if(serverStop == true) { server.stopServer(); }
			 */
		} catch (IOException  | ClassNotFoundException e) {
			//system.out.println("exception......");
			//server.stop();
			//e.printStackTrace();
			//system.out.println(e);
		}
	}

	/*public void printOutput() {
		//system.out.println("printing the output");
		//system.out.println("the nodes discovered by node" + i + " are:\n"
				+ table.getnodes());
	}
*/
	public void addToList(requestMSG msg) {
		//system.out.println("Adding new updated msg to the list" + msg);
		updatedList.add(msg);
	}

	public void sentToAll(ArrayList<requestMSG> list) {
		////system.out.println("inside sentToAll" + list.size());
		////system.out.println(list);
		Socket s;
		for (int j = 0; j < list.size(); j++) {
			////system.out.println("table contents to be sent.... ");
			try {
				////system.out.println("table::::1\n" + table);
				os.writeObject(table);
				////system.out.println("table::::\n" + list.get(j).serverPort);
				
				//s = new Socket(list.get(j).getHostname(),list.get(j).clientPort);
				//s.communicate();*/

				/*//system.out.println("Hii");
				//system.out.println("table::::\n" + table);*/

				////system.out.println("Hii2");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//system.out.println("exception in sent to all");
				//e.printStackTrace();
			}
			// /new Server2Connection(list.get(i).clientPort, i, new Server());
		}
	}

}
