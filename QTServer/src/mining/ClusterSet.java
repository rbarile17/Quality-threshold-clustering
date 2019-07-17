package mining;

import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import data.Data;
import java.io.Serializable;

public class ClusterSet implements Iterable<Cluster>,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6423140876509792857L;
	private Set<Cluster> C = new TreeSet<Cluster>();
	
	ClusterSet() {}
	
	void add(Cluster c){
		C.add(c);
	}
	
	public String toString() {
		String str = "";
		
		for(Cluster c : C) 
			str += c + "\n";
		
		return str;
	}
	
	public String toString(Data data) {
		String str = "";
		
		int i=0;
		for(Cluster c : C) {
			if (c != null)
				str += (i++) +":"+c.toString(data)+"\n";
			str += "\n";
		}
		return str;
	}
	
	public List<List<String>> toList() {
		List<List<String>> l = new LinkedList<List<String>>();
		
		for(Cluster c : this.C) {
			l.add(c.getCentroid().toList());
		}
		
		return l;
	}
	
	public Iterator<Cluster> iterator() {
		return C.iterator();
	}
}
