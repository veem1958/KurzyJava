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

/**
*Trieda Kurzy zabezpeËuje hlavnÈ metÛdy pre pr·cu so ötudentami, lektormi, naËÌtanie a z·pis v˝sledkov. 
*@author Denisa Mensatorisov·  
*/
public class Kurzy {
	
	Students[] student = new Students[100];
	Teachers[] lektor = new Teachers[5];
	
	public LinkedList<Students>[] studenti = new LinkedList[4]; 
	public LinkedList<Students> studentivysl = new LinkedList();
	LinkedList<Teachers> lektori = new LinkedList<Teachers>();

	public List<String> zoznsub = new ArrayList<String>();
	
	/**
	 * MetÛda naplnÌ generovan˝mi n·hodn˝mi(Random) ˙dajmi pole 100 ötudentov a pole 5 lektorov,
	 * mÙûe nastaù v˝nimka IndexOutOfBoundsException ak je index poæa mimo rozsah 
	 * @throws Exception
	 */
	public void inicializacia() throws Exception {
		
		for (int i = 0; i < 4; i++) {
			 studenti[i] = new LinkedList<Students>();
		}
		
		/** inicializacia studentov bez maturity z matematiky a informatiky */
		for (int i = 0; i < 40; i++) {
			student[i] = new Students(new Matematika(false), new Informatika(false));
			student[i].naplnBody();
			student[i].setSuma(0);
			student[i].setMeno(i+1);
			studenti[0].add(student[i]);
		}
		
		/**inicializacia studentov s maturitov z matematiky a informatiky */
		for (int i = 40; i < 100; i++) {
			student[i] = new Students(new Matematika(true), new Informatika(true));
			student[i].naplnBody();		
			student[i].setSuma(0);
			student[i].setMeno(i+1);
			studenti[0].add(student[i]);
		}
				
		/**inicializacia ucitelov */
		for (int i = 0; i < 5; i++) {
			lektor[i] = new Teachers(new Matematika(), new Informatika());
			lektor[i].naplnBody();
			lektori.add(lektor[i]);
		}
		
		
	}
	
	
	/** 
	 * MetÛda <b>beh</b> realizuje kurzy ötudentov s n·hodn˝m v˝berom lektora, 
	 * ötudenti dost·vaj˙ za kaûd˝ kurz n·hodne body z matematiky a informatiky,
	 * po 3 kurze sa celkovÈ dosiahnutÈ body zapÌöu do s˙boru cez serializ·ciu objektu
	 * @param kurz - poradie kurzu 1 aû 3
	 */
	public void beh(int kurz) {
		
		int mat = (int) (Math.random() * 5 + 0);		//nahodny vyber lektora na dany kurz
		int inf = (int) (Math.random() * 5 + 0);
		double bodyMat = lektor[mat].mat.getMarks(lektor[mat]);	//zistenie bodov lektora z daneho predmetu
		double bodyInf = lektor[inf].inf.getMarks(lektor[inf]);
		//System.out.println("Mat: ucitel: " + mat + " - " + bodyMat);
		//System.out.println("Inf: ucitel: " + inf + " - " + bodyInf + "\n");
						
		for (int i = 0; i < 100; i++) {
			student[i].addBody(kurz, bodyMat, bodyInf);
			student[i].setSuma(kurz);
			studenti[kurz].add(student[i]);
		}	
			
		// zotriedi podla celkoveho poctu bodov Suma = Mat+Inf	
		Collections.sort(studenti[kurz], Collections.reverseOrder(new SortbySuma(kurz)));
		
		// zapise do suboru celkove vysledky cez serializaciu objektu
		if (kurz == 3) {
			try {
				zapisObjekt(kurz);				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			// prezrie aktualny adres·r na s˙bory s v˝sledkami
			try {
			    najdiSuboryVysled();
			} 
			catch (Exception e) {
			  	e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * MetÛda vyhæad· s˙bory staröÌch v˝sledkov a n·zvy s˙borov uloûÌ do listu <i>zoznsub</i>,
	 * prehæad·va aktu·lny adres·r ".", n·zvy s˙borov v liste zotriedi podæa aktu·lnosti	
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public void najdiSuboryVysled() throws FileNotFoundException, IOException {
		try {
			
			File folder = new File(".");     // moûe byù aj "." , "c:\Users\dede\eclipse-worskpace\"
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
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//* MetÛda zapise vysledky kurzu studentov do suboru cez serializaciu objektu
	
	/**
	 * MetÛda zapisuje celkovÈ v˝sledky kurzu (po 3. kurze) ötudentov do s˙boru s vyzuûitÌm serializ·cie objektu,
	 * ˙daje s˙ zapisovanÈ do s˙boru s generovan˝m n·zvom podæa aktu·lneho d·tumu a Ëasu v tvare "Vysledok_yyyyMMddHHmm.txt",	 
	 * @param kurz - ËÌslo kurzu
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
			System.out.println("V˝sledok 3.kurzu uloûen˝ do s˙boru - serializ·cia.");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * MetÛda naËÌta zo s˙boru s n·zvom v parametri celkovÈ v˝sledky kurzu ötudentov do objektu typu List(Students) cez deserializ·ciu objektu,
	 * pri ËÌtanÌ objektu deserializ·ciou mÙûe nastaù v˝nimka ak objekt v s˙bore je staröieho typu,
	 * to znamen·, ûe medzi z·pisom objektu do s˙boru a aktu·lnym stavom objektu doölo k zmene vo vlastnostiach objektu
	 *  a t˝m nesedÌ serial ËÌslo objektu novÈ a starÈ uloûenÈ v s˙bore. 
	 * @param s
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
			System.out.println("PoËet ötudentov v naËÌtanom objekte : " + studentivysl.size());
			
		}
		catch (InvalidClassException eobj) {
			throw new InvalidClassException("Chyba objektu v s˙bore.");
			
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
			iter.next().setPassword();	// tieto hesla zatial nepouzite v GUI 
		}
	}
	
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
