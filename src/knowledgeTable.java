import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

public class knowledgeTable  implements Serializable{

	private Hashtable<String, requestMSG> table = new Hashtable<String, requestMSG>();

	public knowledgeTable() {

	}

	public boolean contains(requestMSG msg) {

		if (table.containsValue(msg)) {
			System.out.println("req msg already present in the table");

			return true;
		}
		return false;

	}

	public void add(requestMSG msg, String string) {

		table.put(string, msg);

	}

	public String toString() {

		return "Client connected = " + table.keySet() + " ; details = "
				+ table.values() + "\n";

	}

	public Collection<requestMSG> getValues()
	{
		 Collection<requestMSG> Values = table.values();
	        for(requestMSG value: Values){
	            System.out.println("Value of "+value+" is: "+value.toString());
	        }
		
		return table.values();
	}
	
	public Set<String> getnodes() {
		return table.keySet();
	}
}
