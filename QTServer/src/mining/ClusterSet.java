package mining;

import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;
import data.Data;
import java.io.Serializable;

public class ClusterSet implements Iterable<Cluster>,Serializable {
	private Set<Cluster> C = new TreeSet<Cluster>();

	
	ClusterSet() {}
	
	void add(Cluster c){
		C.add(c);
	}
	
	public String toString() {
		String out = "";
		Iterator<Cluster> it = C.iterator();
		int i = 1;
		while(it.hasNext()) {
			out += i+":"+it.next().toString()+'\n';
			i++;
		}
		return out;
	}
	
	public Iterator<Cluster> iterator() {
		return C.iterator();
	}
	
	public String toString(Data data){
		String str="";
		Iterator<Cluster> it = C.iterator();
		int i = 1;
		while(it.hasNext()) {
			Cluster c = it.next();
			if (c !=null){
				str+= i + ":" + c.toString(data)+"\n";
			}
			i++;
		}
		return str;
	}

}
