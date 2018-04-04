package projectMain;

import java.util.*;
import java.lang.Exception;
import java.util.LinkedList;
import java.util.List;


public class MainClass {
	
	public static void main(String[] args) {
		
		Kurzy course = new Kurzy();
		
		try {
		      course.inicializacia();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
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

		
	/*	skuska hesla
		course.hesla();
		course.vypisHesla();
	*/
		
	
		
	}

}
