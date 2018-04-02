package projectMain;

import java.util.*;

import java.util.LinkedList;
import java.util.List;


public class MainClass {

			//public List<Students> studenti = new LinkedList<>();
	
	
	public static void main(String[] args) {
		
		Kurzy course = new Kurzy();
		
		
			//----------------------------------------------------------
			/*
			for (int i = 0; i < 100; i++) {
				studenti.add(student[i]);
			}
			
			int n = 0;
			
			Iterator<Students> iter = studenti.iterator();  
			while(iter.hasNext()) {
				System.out.print(n + "	");
				iter.next().vypis(0); 
				n++;
			}
			*/
			//--------------------------------------------------------------

		
		
		course.inicializacia();
		
		course.vypisLektor();
		
		System.out.println("\n\n");
		
		course.vypisStudent(0);
		
		System.out.println("\n\n");
				
		course.beh(1);
		course.vypisStudent(1);
		
		System.out.println("\n\n");
		
		course.beh(2);
		course.vypisStudent(2);
		
		System.out.println("\n\n");
		
		course.beh(3);
		course.vypisStudent(3);
/*
		//skuska hesla
		System.out.println("\n\n\nheslo: ");
		student[2].setPassword();
		System.out.print(student[2].getPassword());
		
		System.out.println("\n\n\n");
		*/
		
	
		
	}

}
