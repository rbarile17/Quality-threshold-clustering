import data.Data;
import data.EmptyDatasetException;
import keyboardinput.Keyboard;
import mining.ClusteringRadiusException;
import mining.QTMiner;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		String answer="";
		Data data = new Data();
		System.out.println(data);
		double radius = 2.0;
		
		do {
			do {
				System.out.println("Type radius (> 0): ");
				radius = Keyboard.readDouble();
			} while(radius <= 0);
			QTMiner qt = new QTMiner(radius);
			try {
				try {
					int numIter = qt.compute(data);	
					System.out.println("Number of clusters:"+numIter);
					System.out.println(qt.getC().toString(data));
				}
				catch(EmptyDatasetException e) {
					System.out.println("Empty dataset.");
				}
			}
			catch (ClusteringRadiusException e) {
				System.out.println("Found only one cluster, try smaller radius.");
			}
			System.out.println("New execution? (yes / no)");
			answer = Keyboard.readWord();	
			while(!answer.equals("yes") && !answer.equals("no")) {	
				System.out.println("Allowed values: yes - no");
				answer = Keyboard.readWord();	
			} 
		} while (answer.equals("yes"));
	}

}
