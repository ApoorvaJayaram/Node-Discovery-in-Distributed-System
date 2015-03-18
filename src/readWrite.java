import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class readWrite {

	private requestMSG nodeREQ;
	
	public readWrite()
	{
		
	}

	public synchronized String readFile(String line) throws Exception {

		int identifier;
		String hostname;
		//int clientPort;
		int serverPort;

		String[] items = line.split("	");

		identifier = Integer.parseInt(items[0]);

		hostname = items[1];

		//clientPort = Integer.parseInt(items[2]);
		serverPort = Integer.parseInt(items[2]);
		 //system.out.println("read file ::"+items[0]+items[1]+items[2]);
		//nodeREQ = new requestMSG(identifier, hostname, clientPort, serverPort); // Object creation
		this.notify();
		return hostname;

	}

	public synchronized void WriteToFile(requestMSG msg) {
		try {
			////system.out.println("write to file");
			this.wait(5000);
			String filename ="configserv"+msg.DestNodeName+".txt";
			File file = new File(filename);

			if (!file.exists()) {
			//system.out.println("Config File for the node "+msg.DestNodeName+" not found");
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			String entry = msg.identifier+"	"+msg.hostname+"	"+msg.clientPort;
			//bw.append(entry);
			//bw.close();
			out.println();
			out.print(entry);
			out.close();
			bw.close();
			fw.close();

			////system.out.println("Done");
		
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public synchronized requestMSG WriteToTable(String line,String hostname,int port,int i)
	{
try{
		this.wait(5000);
}
catch ( InterruptedException e) {
			e.printStackTrace();
		}

		int identifier;
		String destName;
		//int clientPort;
		int serverPort;
		knowledgeTable table =new knowledgeTable();
		requestMSG msg;

		String[] items = line.split("	");

		identifier = Integer.parseInt(items[0]);

		destName = items[1];
		
		//clientPort = Integer.parseInt(items[2]);
		serverPort = Integer.parseInt(items[2]);
		msg = new requestMSG(identifier, hostname, port, serverPort, destName);
		//table.add(msg, "Client" + i);
		////system.out.println("table::::\n" + table);
		 //system.out.println("write to table::::"+items[0]+items[1]+items[2]);
		 this.notify();
		 return msg;
	}


}
