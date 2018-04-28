package projectMain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Exception;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
*Trieda Kurzy zabezpeèuje hlavné metódy pre prácu so študentami, lektormi, naèítanie a zápis vısledkov. 
*@author Denisa Mensatorisová  
*/
public class Kurzy {
	
	/** student je pole 100 inštancií triedy Students */
	Students[] student = new Students[100];
	/** lektor je pole 5 inštancií triedy Teachers */
	Teachers[] lektor = new Teachers[5];
	
	public LinkedList<Students>[] studenti = new LinkedList[4]; 
	public LinkedList<Students> studentivysl = new LinkedList<Students>();
	LinkedList<Teachers> lektori = new LinkedList<Teachers>();

	/** zoznsub je inštancia Triedy ArrayList a slúi pre ukladanie názvov súborov s koneènımi vısledkami kurzu	 */
	public List<String> zoznsub = new ArrayList<String>();
	
	/**
	 * Metóda naplní generovanımi náhodnımi údajmi pole 100 študentov a pole 5 lektorov.</br>
	 * Môe nasta vınimka IndexOutOfBoundsException ak je index po¾a mimo rozsah. 
	 * @throws Exception
	 */
	public void inicializacia() throws Exception {
		try {
			for (int i = 0; i < 4; i++) {
				 studenti[i] = new LinkedList<Students>();
			}
			
			/** inicializácia študentov bez maturity z matematiky a informatiky */
			for (int i = 0; i < 40; i++) {
				student[i] = new Students(new Matematika(false), new Informatika(false));
				student[i].naplnBody();
				student[i].setSuma(0);
				student[i].setMeno(i+1);
				studenti[0].add(student[i]);
			}
			
			/**inicializácia študentov s maturitov z matematiky a informatiky */
			for (int i = 40; i < 100; i++) {
				student[i] = new Students(new Matematika(true), new Informatika(true));
				student[i].naplnBody();		
				student[i].setSuma(0);
				student[i].setMeno(i+1);
				studenti[0].add(student[i]);
			}
					
			/**inicializácia uèite¾ov */
			for (int i = 0; i < 5; i++) {
				lektor[i] = new Teachers(new Matematika(), new Informatika());
				lektor[i].naplnBody();
				lektori.add(lektor[i]);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * Metóda <b>beh</b> realizuje kurzy študentov s náhodnım vıberom lektora, 
	 * študenti dostávajú za kadı kurz náhodne body z Matematiky a Informatiky,
	 * po 3 kurze sa celkové dosiahnuté body zapíšu do súboru cez serializáciu objektu
	 * @param kurz - poradie kurzu 1 a 3
	 */
	public void beh(int kurz) {
		
		int mat = (int) (Math.random() * 5 + 0);		//nahodny vyber lektora na dany kurz
		int inf = (int) (Math.random() * 5 + 0);
		double bodyMat = lektor[mat].mat.getMarks(lektor[mat]);	//zistenie bodov lektora z daneho predmetu
		double bodyInf = lektor[inf].inf.getMarks(lektor[inf]);
						
		for (int i = 0; i < 100; i++) {
			student[i].addBody(kurz, bodyMat, bodyInf);
			student[i].setSuma(kurz);
			studenti[kurz].add(student[i]);
		}	
			
		// zotriedi podla celkoveho poctu bodov Suma = Mat+Inf	
		Collections.sort(studenti[kurz], Collections.reverseOrder(new SortbySuma(kurz)));
		
		// zapíše do súboru celkové vısledky cez serializáciu objektu
		if (kurz == 3) {
			try {
				zapisObjekt(kurz);				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			// prezrie aktuálny adresár na súbory s vısledkami a naèíta ich názvy
			try {
			    najdiSuboryVysled();
			} 
			catch (Exception e) {
			  	e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Metóda vyh¾adá súbory starších vısledkov a názvy súborov uloí do listu <i>zoznsub</i>,
	 * preh¾adáva aktuálny adresár ".", názvy súborov v liste zotriedi pod¾a aktuálnosti	
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 * @throws IndexOutOfBoundsException
	 */
	public void najdiSuboryVysled() throws IndexOutOfBoundsException, FileNotFoundException, IOException {
	try {			
			File folder = new File(".");     // moe by aj "." , "c:\Users\dede\eclipse-worskpace\"
			File[] listOfFiles = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File folder, String name) {
					return (name.endsWith(".txt") && name.startsWith("Vysl"));  // filter pre súbory s vısledkami
				}
			});

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		             zoznsub.add(listOfFiles[i].getName());
		      } 
		    }
		    
	        Collections.sort(zoznsub, Collections.reverseOrder());
		    
		}
		catch (IndexOutOfBoundsException eind) {
			throw new IndexOutOfBoundsException("Index mimo rozsah. Nie je iadny súbor s vısledkami.");			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	  
	}
	
	
	
	/**
	 * Metóda zapisuje celkové vısledky študentov po poslednom kurze do súboru s vyzuitím serializácie objektu,
	 * údaje sú zapisované do súboru s generovanım názvom pod¾a aktuálneho dátumu a èasu v tvare "Vysledok_yyyyMMddHHmm.txt",	 
	 * @param kurz - èíslo kurzu
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 * @throws java.io.InvalidObjectException  
	 */
	public void zapisObjekt(int kurz) throws FileNotFoundException, IOException, InvalidObjectException {
		try {
			DateFormat formatData = new SimpleDateFormat("yyyyMMddHHmm");
			Date datum = new Date();
			String datumtxt = formatData.format(datum);
			String menosub = "Vysledok" + "_" + datumtxt + ".txt";		// vyrobenie nazvu suboru

			System.out.println("Nazov suboru je : " + menosub);

			ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(menosub));
			s.writeObject(studenti[kurz]);
			s.flush();
			System.out.println("Vısledok 3.kurzu uloenı do súboru - serializácia.");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metóda naèíta zo súboru s názvom v parametri celkové vısledky kurzu študentov do objektu typu List(Students) cez deserializáciu objektu,
	 * pri èítaní objektu deserializáciou môe nasta vınimka ak objekt v súbore je staršieho typu,
	 * to znamená, e medzi zápisom objektu do súboru a aktuálnym stavom objektu došlo k zmene vo vlastnostiach objektu
	 *  a tım nesedí serial èíslo objektu nové a staré uloené v súbore. 
	 * @param s - názov súboru
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 * @throws java.io.InvalidClassException
	 * @throws java.lang.ClassNotFoundException
	 */
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
		catch (FileNotFoundException ef) {
			throw new FileNotFoundException("Súbor sa nenašiel.");			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metóda umoòuje vypísa vısledky študentov po kadom kurze na konzolu. 
	 * @param kurz - poradie kurzu
	 */
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
	
	/** Metóda vypisuje na konzolu hodnotenie lektorov.	 */
	public void vypisLektor() {
		
		int n = 0;
		Iterator<Teachers> iter = lektori.iterator();  
		
		while(iter.hasNext()) {
			System.out.print("Lektor[" + n + "]	");
			iter.next().vypis(); 
			n++;
		}
	}
	
	/**
	 * Metóda umoòuje vygenerova a prideli kadému študentovi heslo.
	 * Nevyuíva sa - viï popis {@link Students#setPassword() setPassword} metódy. 
	 * @call Students.setPassword()
	 */
	public void hesla() {
		
		Iterator<Students> iter = studenti[0].iterator();  
		
		while(iter.hasNext()) {			
			iter.next().setPassword();	// tieto hesla zatial nepouzite v GUI 
		}
	}
	
	/** 
	 * Metóda vypisuje na konzolu študentom pridelené náhodne generované heslá. 
	 * Nevyuíva sa - pozri {@linkplain Students#setPassword() setPassword} metódu.
	 */
	public void vypisHesla() {
		
		int n = 0;
		Iterator<Students> iter = studenti[0].iterator();  
		
		while(iter.hasNext()) {
			System.out.print("Heslo[" + n + "]	"); 
			System.out.println(iter.next().getPassword());	// tieto hesla zatial nepouzite v GUI 
			n++;
		}		
	}
	
}
