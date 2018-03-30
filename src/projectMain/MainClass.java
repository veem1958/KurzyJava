package projectMain;

import java.util.*;

import java.util.LinkedList;
import java.util.List;


public class MainClass {

			public List<Students> studenti = new LinkedList<>();
	

	public static void main(String[] args) {
		
		Students[] student = new Students[100];
		Teachers[] lektor = new Teachers[5];

				List<Students> studenti = new LinkedList<>();
		 
		//inicializacia studentov bez maturity z matematiky a informatiky
		for (int i = 0; i < 40; i++) {

				studenti.add(student[i] = new Students(new Matematika(false), new Informatika(false)));
			/*
			student[i] = new Students(new Matematika(false), new Informatika(false));
			*/
			System.out.print("Student[" + i + "]	");
			student[i].naplnBody();
			student[i].vypis(0);	
		}
		
		//inicializacia studentov s maturitov z matematiky a informatiky
		for (int i = 40; i < 100; i++) {
			student[i] = new Students(new Matematika(true), new Informatika(true));
			System.out.print("Student[" + i + "]	");
			student[i].naplnBody();
			student[i].vypis(0);			
		}
		
		for (int i = 0; i < 5; i++) {
			lektor[i] = new Teachers(new Matematika(), new Informatika());
			System.out.print("Lektor[" + i + "]	");
			lektor[i].naplnBody();
			lektor[i].vypis();			
		}
		
		System.out.println("\n\n");
		
		//list studentov
		
			//----------------------------------------------------------
			/*
			for (int i = 0; i < 100; i++) {
				studenti.add(student[i]);
			}
			*/
			int n = 0;
			
			Iterator<Students> iter = studenti.iterator();  
			while(iter.hasNext()) {
				System.out.print(n + "	");
				iter.next().vypis(0); 
				n++;
			}
			
			//--------------------------------------------------------------

		
			
		//-----------------------------------------------------------------
		//pomocny vypis
		/*
		for (int i = 0; i < 100; i++) {
			System.out.print("Student[" + i + "]	");
			student[i].vypis(0);
		}
		
		for (int i = 0; i < 5; i++) {
			System.out.print("Lektor[" + i + "]	");
			lektor[i].vypis();
		}*/
		//-----------------------------------------------------------------
		
		System.out.println("\n\n\n1.kurz: ");		
		//1.kurz
		for (int i = 0; i < 100; i++) {
			System.out.print("Student[" + i + "]	");
			student[i].addBody(1);
			student[i].vypis(1);
			
		}
		
		System.out.println("\n\n\n2.kurz: ");
		//2.kurz
		for (int i = 0; i < 100; i++) {
			System.out.print("Student[" + i + "]	");
			student[i].addBody(2);
			student[i].vypis(2);
			
		}
		
		System.out.println("\n\n\n3.kurz: ");
		//3.kurz
		for (int i = 0; i < 100; i++) {
			System.out.print("Student[" + i + "]	");
			student[i].addBody(3);
			student[i].vypis(3);
			
		}
		System.out.println("\n\n\nheslo: ");
		student[2].setPassword();
		System.out.print(student[2].getPassword());
		
		System.out.println("\n\n\n");
		
		
		//random body za aktivitu
		for (int i = 0; i < 10; i++) {
			int cisloStudenta = (int) (Math.random() * 99 + 0);
			int cisloKurzu = (int) (Math.random() * 3 + 1);
			System.out.print("Student[" + cisloStudenta + "]	" + "Kurz[" + cisloKurzu + "]	");
			student[cisloStudenta].randomBody(cisloKurzu);
			student[cisloStudenta].vypis(cisloKurzu);
		}
		
	}

}
