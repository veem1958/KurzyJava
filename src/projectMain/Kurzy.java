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
*Trieda Kurzy zabezpe�uje hlavn� met�dy pre pr�cu so �tudentami, lektormi, na��tanie a z�pis v�sledkov. 
*@author Denisa Mensatorisov�  
*/
public class Kurzy {
	
	Students[] student = new Students[100];
	Teachers[] lektor = new Teachers[5];
	
	public LinkedList<Students>[] studenti = new LinkedList[4];
	public LinkedList<Students> studentivysl = new LinkedList();
	LinkedList<Teachers> lektori = new LinkedList<Teachers>();

	public List<String> zoznsub = new ArrayList<String>();
	
	/**
	 * Met�da napln� generovan�mi n�hodn�mi(Random) �dajmi pole 100 �tudentov a pole 5 lektorov,
	 * m��e nasta� v�nimka IndexOutOfBoundsException ak je index po�a mimo rozsah 
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
	 * Met�da <b>beh</b> realizuje kurzy �tudentov s n�hodn�m v�berom lektora, 
	 * �tudenti dost�vaj� za ka�d� kurz n�hodne body z matematiky a informatiky,
	 * po 3 kurze sa celkov� dosiahnut� body zap�u do s�boru cez serializ�ciu objektu
	 * @param kurz - poradie kurzu 1 a� 3
	 */
	public void beh(int kurz) {
		
		int mat = (int) (Math.random() * 5 + 0);		//nahodny vyber lektora na dany kurz
		int inf = (int) (Math.random() * 5 + 0);
		double bodyMat = lektor[mat].mat.getMarks(lektor[mat]);	//zistenie bodov lektora z daneho predmetu
		double bodyInf = lektor[inf].inf.getMarks(lektor[inf]);
		System.out.println("Mat: ucitel: " + mat + " - " + bodyMat);
		System.out.println("Inf: ucitel: " + inf + " - " + bodyInf + "\n");
						
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
			
			// prezrie aktualny adres�r na s�bory s v�sledkami
			try {
			    najdiSuboryVysled();
			} 
			catch (Exception e) {
			  	e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Met�da vyh�ad� s�bory star��ch v�sledkov a n�zvy s�borov ulo�� do listu <i>zoznsub</i>,
	 * preh�ad�va aktu�lny adres�r ".", n�zvy s�borov v liste zotriedi pod�a aktu�lnosti	
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	public void najdiSuboryVysled() throws FileNotFoundException, IOException {
		try {
			
			File folder = new File(".");     // mo�e by� aj "." , "d:\\down\\" , "..\\KurzyJava\\"
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
	
	
//	 * Met�da zapise vysledky kurzu studentov do suboru cez serializaciu objektu
	
	
	/**
	 * Met�da zapisuje celkov� v�sledky kurzu (po 3. kurze) �tudentov do s�boru s vyzu�it�m serializ�cie objektu,
	 * �daje s� zapisovan� do s�boru s generovan�m n�zvom pod�a aktu�lneho d�tumu a �asu v tvare "Vysledok_yyyyMMddHHmm.txt",	 
	 * @param kurz - ��slo kurzu
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 * @throws java.io.InvalidObjectException  
	 */
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
			System.out.println("V�sledok 3.kurzu ulo�en� do s�boru - serializ�cia.");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Met�da na��ta zo s�boru s n�zvom v parametri celkov� v�sledky kurzu �tudentov do objektu typu List(Students) cez deserializ�ciu objektu,
	 * pri ��tan� objektu deserializ�ciou m��e nasta� v�nimka ak objekt v s�bore je star�ieho typu,
	 * to znamen�, �e medzi z�pisom objektu do s�boru a aktu�lnym stavom objektu do�lo k zmene vo vlastnostiach objektu
	 *  a t�m nesed� serial ��slo objektu nov� a star� ulo�en� v s�bore. 
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
			System.out.println("Po�et �tudentov v na��tanom objekte : " + studentivysl.size());
			
		}
		catch (InvalidClassException eobj) {
			throw new InvalidClassException("Chyba objektu v s�bore.");
			
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
