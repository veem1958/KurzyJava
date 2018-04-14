package projectMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Exception;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Kurzy {
	
	Students[] student = new Students[100];
	Teachers[] lektor = new Teachers[5];
	
	public LinkedList<Students>[] studenti = new LinkedList[4];
	public LinkedList<Students> studentivysl = new LinkedList();
	LinkedList<Teachers> lektori = new LinkedList<Teachers>();

	public List<String> zoznsub = new ArrayList<String>();
	
	
	public void inicializacia() throws Exception {
		
		for (int i = 0; i < 4; i++) {
			 studenti[i] = new LinkedList<Students>();
		}
		
		//inicializacia studentov bez maturity z matematiky a informatiky
		for (int i = 0; i < 40; i++) {
			student[i] = new Students(new Matematika(false), new Informatika(false));
			student[i].naplnBody();
			student[i].setSuma(0);
			student[i].setMeno(i+1);
			studenti[0].add(student[i]);
		}
		
		//inicializacia studentov s maturitov z matematiky a informatiky
		for (int i = 40; i < 100; i++) {
			student[i] = new Students(new Matematika(true), new Informatika(true));
			student[i].naplnBody();		
			student[i].setSuma(0);
			student[i].setMeno(i+1);
			studenti[0].add(student[i]);
		}
				
		//inicializacia ucitelov
		for (int i = 0; i < 5; i++) {
			lektor[i] = new Teachers(new Matematika(), new Informatika());
			lektor[i].naplnBody();
			lektori.add(lektor[i]);
		}
		
		
	}
	
	
	
	public void beh(int kurz) {
		
		int mat = (int) (Math.random() * 5 + 0);		//nahodny vyber lektora na dany kurz
		int inf = (int) (Math.random() * 5 + 0);
		double bodyMat = lektor[mat].mat.getMarks(lektor[mat]);	//zistenie bodov lektora z daneho predmetu
		double bodyInf = lektor[inf].inf.getMarks(lektor[inf]);
		System.out.println("Mat: ucitel: " + mat + " - " + bodyMat);
		System.out.println("Inf: ucitel: " + inf + " - " + bodyInf + "\n\n");
						
		for (int i = 0; i < 100; i++) {
			student[i].addBody(kurz, bodyMat, bodyInf);
			student[i].setSuma(kurz);
			studenti[kurz].add(student[i]);
		}	
			
		// zotriedi podla celkoveho poctu bodov Suma = Mat+Inf	
		Collections.sort(studenti[kurz], Collections.reverseOrder(new SortbySuma(kurz)));
		//vypisStudent(kurz);  // konzola
		
		// zapise do suboru celkove vysledky cez serializaciu objektu
		if (kurz == 3) {
			try {
				zapisObjekt(kurz);				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			// --- doplnene 14.4.2018 ---
			try {
			    najdiSuboryVysled();
			} 
			catch (Exception e) {
			  	e.printStackTrace();
			}
		}
	}
	
	
	// vyhlada subory vysledkov a da ich do listu ---  doplnene 14.4.2018
	
	public void najdiSuboryVysled() throws FileNotFoundException, IOException {
		try {
			
			File folder = new File(".");     // može by aj "." , "d:\\down\\" , "..\\KurzyJava\\"
			File[] listOfFiles = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File folder, String name) {
					return (name.endsWith(".txt") && name.startsWith("Vysl"));
				}
			});

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		             zoznsub.add(listOfFiles[i].getName());
		      } 
		    }
		    
	        Collections.sort(zoznsub, Collections.reverseOrder());
	        //System.out.println(zoznsub);		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	// zapise vysledky kurzu studentov do suboru cez serializaciu objektu
	public void zapisObjekt(int kurz) throws FileNotFoundException, IOException, InvalidObjectException {
		try {
			DateFormat formatData = new SimpleDateFormat("yyyyMMddHHmm");
			Date datum = new Date();
			String datumtxt = formatData.format(datum);
			String menosub = "Vysledok" + "_" + datumtxt + ".txt";

			System.out.println("nazov suboru bude : " + menosub);

			ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(menosub));
			s.writeObject(studenti[kurz]);
			s.flush();
			System.out.println("Výsledok 3.kurzu uložený do súboru - serializácia.");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// nacita vysledky kurzu studentov zo suboru do objektu cez serializaciu
	public void nacitajObjekt(String s) throws FileNotFoundException, IOException, InvalidClassException, ClassNotFoundException {
		try {
			studentivysl = new LinkedList<Students>();
			String menosub = s;
			ObjectInputStream v = new ObjectInputStream(new FileInputStream(menosub));
			studentivysl = (LinkedList<Students>) v.readObject();
			v.close();
			System.out.println("Poèet študentov v naèítanom objekte : " + studentivysl.size());
			
		}
		catch (InvalidClassException eobj) {
			throw new InvalidClassException("Chyba objektu v súbore.");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//vypis konzola
	public void vypisStudent(int kurz) {
		
			int n = 0;
			Iterator<Students> iter = studenti[kurz].iterator();  
			
			System.out.println(kurz + ".kurz: ");	
			while(iter.hasNext()) {
				System.out.print("Student[" + n + "]	");
				iter.next().vypis(kurz);
				n++;
			}
	}
	
	
	//vypis konzola
	public void vypisLektor() {
		
		int n = 0;
		Iterator<Teachers> iter = lektori.iterator();  
		
		while(iter.hasNext()) {
			System.out.print("Lektor[" + n + "]	");
			iter.next().vypis(); 
			n++;
		}
	}
	
	//hesla
	public void hesla() {
		
		Iterator<Students> iter = studenti[0].iterator();  
		
		while(iter.hasNext()) {			
			iter.next().setPassword();	//tieto hesla zatial nepouzite v GUI 
		}
	}
	
	public void vypisHesla() {
		
		int n = 0;
		Iterator<Students> iter = studenti[0].iterator();  
		
		while(iter.hasNext()) {
			System.out.print("Heslo[" + n + "]	"); 
			System.out.println(iter.next().getPassword());
			n++;
		}		
	}
	
}
