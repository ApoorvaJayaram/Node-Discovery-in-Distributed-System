import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Client1 {

	//private Socket socket;
	private Server server = null;
	private ObjectInputStream inputStream = null;

	private boolean isConnected = false;
	// private String hostname;
	private int cport;
	private int sport;
	private int id;
	private int port;
	private static HashMap<String, ArrayList<String>> knowledgeMap = new HashMap<String, ArrayList<String>>();
	private knowledgeTable table;
	private static requestMSG nodeREQ;
	private static int identifier;
	private static String hostname;
	private static int clientPort;
	private static int serverPort;
	private static String DestName;

	public Client1(int port) {
		this.port = port;
	}

	public void communicate() {

		ObjectOutputStream outputStream = null;
		ObjectInputStream inStream1 = null;
		ObjectInputStream inStream2 = null;
		PrintWriter write;

		try {
			
			if(server == null)
			{
			server= new Server(port);
			
			server.communicate();
			}
			if (nodeREQ != null) {
				

					System.out.println("Destnation Node::" + nodeREQ.DestNodeName
							+ "  port:::" + nodeREQ.serverPort);
					
					Socket socket = new Socket(nodeREQ.DestNodeName, nodeREQ.serverPort);
					
					/*//system.out.println("socket port:::" + socket.getPort()
							+ " local port::::" + socket.getLocalPort());*/
					
				
				// isConnected = true;
				// sending req

				outputStream = new ObjectOutputStream(socket.getOutputStream());

				ArrayList<requestMSG> msgList = new ArrayList<>();

				// requestMSG msg = new requestMSG(0, "localhost",4448,4445);
				msgList.add(nodeREQ);

				for (int i = 0; i < msgList.size(); i++) {

					System.out.println("Object to be written = "
							+ msgList.get(i));

					// outputStream.flush();
				}
				outputStream.writeObject(msgList);
				outputStream.flush();
				inStream1 = new ObjectInputStream(socket.getInputStream());
				// inStream2 = new ObjectInputStream(socket.getInputStream());
				// reading ack
				String ack = (String) inStream1.readObject();
				//system.out.println("Receiving Acknowledgement...." + ack);
				
				//this.wait(2*1000);
				
				table = (knowledgeTable) inStream1.readObject();
				//inStream1.close();
				
				//system.out.println("Receiving msg" + table);
				//WriteToFile(table,nodeREQ.hostname);
				PrintNodesDiscovered(table);
			}
			/*outputStream.close();
			inStream1.close();
			socket.close();*/

		} catch (SocketException se) {
			//system.out.println("Socket Exception");
			//se.printStackTrace();
			
			// //system.exit(0);

		} catch (IOException e) {
			//system.out.println("IO Exception");
			//e.printStackTrace();

		} catch (ClassNotFoundException e) {
			//system.out.println("class not found");
			//e.printStackTrace();
		} 

	}

	public static void readFile(String line) throws Exception {

		String[] items = line.split("	");

		identifier = Integer.parseInt(items[0]);

		DestName = items[1];

		//clientPort = Integer.parseInt(items[2]);
		serverPort = Integer.parseInt(items[2]);
		//system.out.println(items[0] + items[1] + items[2]);
		// Object
		// creation

	}

	/*public synchronized void WriteToFile(knowledgeTable table,String File) {
		try {

			File file = new File("config"+File + ".txt");

			if (!file.exists()) {
				//system.out.println("config"+ File + ".txt"+" does not exist");
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			 Collection<requestMSG> Values = table.getValues();
		        for(requestMSG value: Values){
		            //system.out.println("Value of "+value+" is: "+value.toString());
		            //String str = value.prepareMsg();
		            bw.append(value.identifier+"	"+value.hostname+"	"+value.serverPort+"\n");
		        }
			
			
			
			bw.close();

			//system.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/

	public synchronized void PrintNodesDiscovered(knowledgeTable table)
	{
		Collection<requestMSG> Values = table.getValues();
		Collection<String> keys;
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> l1 = new ArrayList<String>();
		String host = nodeREQ.hostname;
		for(requestMSG value: Values){
            //system.out.println("New node discovered by "+nodeREQ.hostname + " is: "+value.DestNodeName);
            
            if(knowledgeMap!=null && !knowledgeMap.entrySet().isEmpty())
            {
            	////system.out.println("knowledgemap is not null"+knowledgeMap.keySet());
    			keys = knowledgeMap.keySet(); 
    			for(String key : keys)
    			{
    				//system.out.println("key"+key);
    				if(key==nodeREQ.hostname)
    				{
    					//system.out.println("if");

    					list=knowledgeMap.get(key);
    					list.add(value.hostname);
    					list.add(value.DestNodeName);
    					//system.out.println(list);
    				}
    				else
    				{
    					//system.out.println("else");
    					l1.add(value.hostname);
    					l1.add(value.DestNodeName);
    					knowledgeMap.put(host, l1);
    				}
    			}
            }
            else
            {
            	//system.out.println("final else");
            	l1.add(value.hostname);
            	l1.add(value.DestNodeName);
				knowledgeMap.put(host, l1);	
            }
            
		}
		for(String entry : knowledgeMap.keySet())
		{
			//system.out.println("inside print for");
			
		System.out.println("Node::"+entry.toString()+" Neighbour nodes:::"+knowledgeMap.get(entry));
		}
	}
	
	public static void main(String[] args) {

		Client1 client;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("config"
					+ args[0] + ".txt"));
			String line = null;
			clientPort = Integer.parseInt(args[1]);
			hostname = args[0];
			client = new Client1(clientPort);

			
			while ((line = reader.readLine()) != null) {
				//system.out.println(line);
				readFile(line);
				nodeREQ = new requestMSG(identifier, hostname, clientPort,
						serverPort,DestName);
				client.communicate();
			}
			
			
		} catch (Exception e) {
			//system.out.println("Exception inn the main method");
			//e.printStackTrace();
		}

	}

	

}