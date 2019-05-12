package mining;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import data.Data;

public class ClusterSet implements Iterable<Cluster>{
	private Set<Cluster> C = new TreeSet<Cluster>();
	
	void add(Cluster c) {
		C.add(c);
	}
	
	public String toString() {
		String str = "";
		
		for(Cluster c : C) {
			str += c;
		}
		
		return str;
	}
	
	public String toString(Data data) {
		String str = "";
		
		int i=0;
		for(Cluster c : C) {
			if (c != null){
				str += (i++) +":"+c.toString(data)+"\n";
			}
			str += "\n";
		}
		return str;
	}
	
	
	@Override
	public Iterator<Cluster> iterator() {
		return this.C.iterator();
	}
}