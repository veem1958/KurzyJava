
package projectGUI;

import javafx.event.*;
import java.util.Iterator;
import java.util.Optional;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InvalidClassException;
import java.lang.Exception;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.LineToBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import projectMain.Kurzy;
import projectMain.Students;

/**
 * Aplikácia simuluje kurz pre študentov strednıch škôl, ktorí sa zaujímajú o štúdium na vysokej škole so zameraním na Informatiku.
 * Vstup do aplikácie je cez prihlasovaciu obrazovku. Prihlásenie je urèené pre 2 uívate¾ov - lektor a študent, ktorí majú vlastné heslo. 
 * Lektor štartuje novı kurz - iniciálne sa naplnia údaje o 100 študentoch a 5 lektoroch. Pri inicializácii sa pre kadého študenta vytvorí meno 
 * v tvare MenoStud_xx, kde xx je poradové èíslo od 1 do 100. Náhodne sa generujú body za 2 predmety a to Matematika a Informatika. 
 * Prvé body predstavujú hodnotenie študenta na základe dosiahnutıch vısledkov na strednej škole. Ïalšie body sú študentovi pridávané za to, 
 * èi v danom predmete aj maturoval.</br>
 * Lektorom sa náhodne tie pridelia body pre kadı predmet, ktoré vyjadrujú schopnos lektora vysvetli danú tému za Matematiku alebo Informatiku. 
 * Ïalej lektor riadi beh 3 kurzov. Študenti získavajú v kadom z 3 behov kurzu náhodne body za kadı predmet. Pre kadı beh kurzu sa náhodne vyberá aj lektor.
 * Body za kadı kurz sa generujú náhodne za kadı predmet, prièom sa ešte zoh¾adòuje momentálna dispozícia študenta v danı deò. Dispozícia sa generuje náhodne 
 * pre kadého študenta a môe by z intervalu 0,7 a 1. Tie sa do celkovıch bodov študenta zoh¾adní schopnos náhodne vybraného lektora vysvetli danú tému.
 * Získané body v kadom behu sa prièítavajú k predošlım získanım bodom. Po kadom kurze je moné prezera vısledky zotriedené pod¾a celkovo dosiahnutıch bodov 
 * z obidvoch predmetov. Po poslednom behu sa celkové dosiahnuté vısledky všetkıch študentov uloia ako objekt do súboru v aktuálnom adresári s názvom 
 * Vysledok_yyyyMMddHHmm.txt. Lektor má monos prezera tieto uloené vısledky aj za predchádzajúce kurzy vıberom príslušného súboru.  
 * Študenti majú monos vidie priebené ako aj celkové vısledky kurzu. 
 *@author Denisa Mensatorisová 
 */
public class MainGUI extends Application {

	private Label prehlad = new Label("PREH¼AD:");
	private Label nazov = new Label("PRÍPRAVNİ KURZ Z MATEMATIKY A INFORMATIKY");
	private Button student = new Button("Študenti");
	private Button lektor = new Button("Lektori");
	private Button signOut = new Button("Odhlási sa");
	private TextArea vypis = new TextArea();
	
	//
	private Label login = new Label("Prihlasovacie meno: ");
	private Label password = new Label("Heslo: ");
	private TextField loginText = new TextField();
	private PasswordField passwordText = new PasswordField();
	private Button signIn = new Button("Prihlási sa");
	//
	
	//studenti
	Label kurzy = new Label("Preh¾ad bodov");
	Button kurz0 = new Button("Pred kurzom");
	Button kurz1 = new Button("Po 1.kurze");
	Button kurz2 = new Button("Po 2.kurze");
	Button kurz3 = new Button("Po 3.kurze");
	
	
	//lektori
	Label beh = new Label("Beh kurzov");
	Button novy = new Button("nová skupina");
	Button beh1 = new Button("1.kurz");
	Button beh2 = new Button("2.kurz");
	Button beh3 = new Button("3.kurz");
	Button stare = new Button("staré vısledky");
	//
	
	
	Kurzy course = new Kurzy();
	
	/** 
	 * 3 vlákna pre beh kurzov, kadı kurz má svoje vlákno
	 * @see java.lang.Thread
	 * @see java.lang.Runnable
	 */	
	Thread vb1 = new Thread(new VlaknoBeh(1));   
	Thread vb2 = new Thread(new VlaknoBeh(2));   // vlákno 2 pre beh 2. kurzu
	Thread vb3 = new Thread(new VlaknoBeh(3));   // vlákno 3 pre beh 3. kurzu
	
	String hlavicka = "STUDENT\t\t\t\t\t BODY\n";
	
	//main-----------------------------------------------------------------------
	public void start(Stage hlavneOkno) throws Exception {
				
		hlavneOkno.setTitle("Prípravnı kury FIIT STU");
		
		BorderPane border = new BorderPane();

		border.setTop(addVBoxTop());
		border.setCenter(addVBoxBottom()); 
		border.setBottom(addHBoxLogOut());
		
		kurz0.setDisable(true);
		kurz1.setDisable(true);
		kurz2.setDisable(true);
		kurz3.setDisable(true);

		novy.setDisable(false);
		beh1.setDisable(true);
		beh2.setDisable(true);
		beh3.setDisable(true);
				
		Scene hlavnascena = new Scene(border, 900, 600);
		Scene loginscena = new Scene(loginMenu(), 400, 200);
		hlavneOkno.setScene(hlavnascena);
		Thread.sleep(500);		  			//spomalenie 0,5 sek. pri prepnuti okien
		hlavneOkno.setScene(loginscena);
		hlavneOkno.show();
		hlavneOkno.centerOnScreen();
		
		vypis.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, null, Insets.EMPTY)));
		
		//-------------------------------------------------------------
		
		/**
		 * štart hlavného okna, menu a okna pre login
		 */
		class Prihlasenie implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) { 
				
				//hesla
				String uzivatel = "";
				String heslo = "";
				uzivatel = loginText.getText();
				heslo = passwordText.getText();

				if (uzivatel.equalsIgnoreCase("student") && heslo.equalsIgnoreCase("abc123")) {
					hlavneOkno.setScene(hlavnascena);
					hlavneOkno.show();
					hlavneOkno.centerOnScreen();
					student.setDisable(false);
					lektor.setDisable(true);
					border.setLeft(addGridPane1());
				}
				else if (uzivatel.equalsIgnoreCase("lektor") && heslo.equalsIgnoreCase("123abc")) {
					hlavneOkno.setScene(hlavnascena);
					hlavneOkno.show();
					hlavneOkno.centerOnScreen();
					student.setDisable(false);
					lektor.setDisable(false);
					border.setLeft(addGridPane2());
					
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Chyba prihlásenia !");
					alert.showAndWait();
				}
			}
		}
		
		try {
			signIn.setOnAction(new Prihlasenie());
						
			//prihlasenie cez enter
			passwordText.setOnKeyPressed(e -> {
			    if (e.getCode() == KeyCode.ENTER) {
			    	passwordText.setOnAction(new Prihlasenie());
			        //System.out.println("A key was pressed");
			        
			    }
			});

			
			//hlavne menu
			student.setOnAction((ActionEvent e) -> {		//pouzitie lambda vyrazu
					border.setLeft(addGridPane1());
			});
			
			lektor.setOnAction((ActionEvent e) -> {			
					border.setLeft(addGridPane2());		
			});
			
			//
			
			/**
			 * odhlásenie a prípadné ukonèenie programu
			 */
			signOut.setOnAction((ActionEvent e) -> {
				System.out.println("vlakno 1 stav : " + vb1.getState());	
				passwordText.clear();
				loginText.clear();
				vypis.clear();
				hlavneOkno.hide();	
				hlavneOkno.setScene(loginscena);
				hlavneOkno.show();
				hlavneOkno.centerOnScreen();	
			});
			
			
			//------------------------------------------------------------------------
			
			/**vıpis bodov študentov po inicializácii pred kurzom s pouitím lambda vırazu*/
			kurz0.setOnAction ((ActionEvent e) -> {
				vypis.clear();
				vypis.appendText("Preh¾ad bodov pred kurzom:\n");
				vypis.appendText(hlavicka);
				Iterator<Students> iter = course.studenti[0].iterator();
				int n = 1; 
				while(iter.hasNext()) {
					vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(0));
					vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
					n++;
				}			
			});
		
			/**vıpis bodov študentov po 1.kurze s pouitím lambda vırazu*/
			kurz1.setOnAction((ActionEvent e) -> {
				vypis.clear();
				vypis.appendText("Preh¾ad bodov po 1. kurze:\n");
				vypis.appendText(hlavicka);
				Iterator<Students> iter = course.studenti[1].iterator();
				int n = 1;
				while(iter.hasNext()) {
					vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(1));
					vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
					n++;
				}	
			});
			
			/**vypis bodov studentov po 2.kurze s pouzitim lambda vyrazu*/
			kurz2.setOnAction((ActionEvent e) -> {
				vypis.clear();
				vypis.appendText("Preh¾ad bodov po 2. kurze:\n");
				vypis.appendText(hlavicka);
				Iterator<Students> iter = course.studenti[2].iterator();
				int n = 1;
				while(iter.hasNext()) {
					vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(2));
					vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
					n++;
				}	
			});
			
			/**vıpis bodov študentov po 3.kurze s pouitím lambda vırazu*/
			kurz3.setOnAction((ActionEvent e) -> {
				vypis.clear();
				vypis.appendText("Preh¾ad bodov po 3. kurze:\n");
				vypis.appendText(hlavicka);
				Iterator<Students> iter = course.studenti[3].iterator();
				int n = 1;
				while(iter.hasNext()) {
					vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(3));
					vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
					n++;
				}	
			});
					
			//--------------------------------------

			/**beh - prvotná inicializácia a vıpis údajov po naplnení - spúša lektor*/
			novy.setOnAction((ActionEvent e) -> {
				 
				try {
					  vypis.clear();
					  vypis.appendText("\nPrebieha inicializácia údajov ! \n");
				      course.inicializacia();
				      vypis.appendText("Inicializácia ukonèená ! \n");
				}
				catch (Exception ex) {
					ex.printStackTrace();
					vypis.appendText("Chyba: " + ex.getMessage() + "\n");
				}
				
				novy.setDisable(true);
				kurz0.setDisable(false);
				beh1.setDisable(false);
								
			});

			
			/**beh 1.kurzu a vıpis vısledkov po 1.kurze - spúša lektor*/
			beh1.setOnAction((ActionEvent e) -> {
				vypis.clear();
 			    vypis.appendText("\nPrebieha 1. kurz ! \n");
				vb1.start();				//  spusti beh 1 v novom vlakne
 			    vypis.appendText("\n1. kurz ukonèenı ! \n");
				kurz1.setDisable(false);
				beh2.setDisable(false);
				beh1.setDisable(true);
			});
			
			/**beh 2.kurzu a vıpis vısledkov po 2.kurze - spúša lektor*/
			beh2.setOnAction((ActionEvent e) -> {
				vypis.clear();
 			    vypis.appendText("\nPrebieha 2. kurz ! \n");
				vb2.start();				//  spusti beh 2 v novom vlakne
 			    vypis.appendText("\n2. kurz ukonèenı ! \n");
				kurz2.setDisable(false);
				beh3.setDisable(false);
				beh2.setDisable(true);
			});
			
			//beh 3.kurzu - spusta lektor
			beh3.setOnAction((ActionEvent e) -> {
				vypis.clear();
 			    vypis.appendText("\nPrebieha 3. kurz ! \n");
				vb3.start();				//  spusti beh 3 v novom vlakne
 			    vypis.appendText("\n3. kurz ukonèenı ! \n");
				kurz3.setDisable(false);
				beh3.setDisable(true);				
			});


			/**
			 * Vyh¾adanie, naèítanie a vıpis starıch celkovıch vısledkov kurzov uloenıch v súboroch - spúša lektor
			 @throws InvalidClassException v prípade ak uloenı objekt s vısledkami je staršia verzia a nesedí sériové èíslo objektu 
			 */
			stare.setOnAction((ActionEvent e) -> {
				try {
			        String selected = "canceled";
			        
			        /*
					// zistenie cesty k suboru MainGUI.class
					String path2 = MainGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
				    path2 = path2.substring(0, path2.lastIndexOf("/")) ;
				    path2 = path2.substring(1, path2.lastIndexOf("/")) + "/" ;
				    
					FileChooser vs = new FileChooser();
					vs.setTitle("Vıber starších vısledkov");
					vs.setInitialDirectory(new File(path2) );
					vs.getExtensionFilters().addAll(
							new ExtensionFilter("Vısledky Files", "Vysledok*.txt"),
							new ExtensionFilter("Text Files", "*.txt"),
							new ExtensionFilter("All Files", "*.*")
							);
					File selectedFile = vs.showOpenDialog(hlavneOkno);
					*/
			        
					course.zoznsub.clear();      // vycisti pole pre názvy súborov s vısledkami
					course.najdiSuboryVysled();  // vyh¾adá súbory s vısledkami a naplní do zoznsub

					// obalí objekt zoznsub do Observable Listu, èím bude tento automaticky aktualizovanı pod¾a obsahu v zoznsub
					ObservableList<String> observableList = FXCollections.observableList (course.zoznsub);
			        observableList.addListener(new ListChangeListener<Object>() {
			            @Override
			            public void onChanged(ListChangeListener.Change change) {
			                System.out.println("Zistená zmena v zozname ! ");
			            }
			        });					
			        
			        // vyuitie objektu ChoiceDialog pre zobrazenie a vıber 1 súboru s vısledkami
			        ChoiceDialog dialog = new ChoiceDialog(observableList.get(0), observableList);
			        dialog.setTitle("Vıber");
			        dialog.setHeaderText("Vyberte súbor s vısledkami");
			        Thread.sleep(300);
			        Optional<String> result = dialog.showAndWait();	
			        selected = "canceled";
			        
			        if (result.isPresent()) {
			            selected = result.get();
			        }

			        /*			        
					if (selectedFile != null) {
						selected = selectedFile.getName();
					*/
			        
			        // vıpis TOP 20 vısledkov naèítanıch zo zvoleného súboru
			        if (selected != "canceled") {
						vypis.clear();
						vypis.appendText("Zvolenı súbor : " + selected + "\n\n");										
						course.nacitajObjekt(selected);    
						vypis.appendText("Vıpis starıch vısledkov TOP20 :\n");
						vypis.appendText(hlavicka);
						Iterator<Students> iter = course.studentivysl.iterator();
						int n = 1;
						while(iter.hasNext()) {
							vypis.appendText( + n + ".\t\t" + iter.next().vypisvyslGUI());
							vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
							n++;
							if (n >= 21) break;  // top 20
						}
					}
			        			      					
				}
				catch (InvalidClassException e1) {
					Alert al1 = new Alert(AlertType.INFORMATION);
					al1.setHeaderText("Informácia");
					al1.setContentText("Chyba ! Uloenı objekt vısledkov je starı. Skúste novší súbor !");
					al1.showAndWait();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			});
									
			
		}
		catch(Exception e) {
			hlavneOkno.close();
			System.out.println("Chyba !");
		}
				
	}
	
	
	//VYTVARANIE TABULIEK A PANELOV
	//*****************************************************************************************
	
	//hlavne prihlasovacie okno
	private GridPane loginMenu() {
		GridPane loginMenu = new GridPane();
		
		loginMenu.getChildren().addAll(login, password, loginText, passwordText, signIn);
		
		GridPane.setConstraints(login, 0, 0);
		GridPane.setConstraints(loginText, 1, 0);
		GridPane.setConstraints(password, 0, 1);
		GridPane.setConstraints(passwordText, 1, 1);
		GridPane.setConstraints(signIn, 1, 2);
		
		loginMenu.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, null, Insets.EMPTY)));
		signIn.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, null, Insets.EMPTY)));
		signIn.setBorder(new Border(new BorderStroke(Color.DARKSEAGREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		loginMenu.setVgap(10);
		loginMenu.setHgap(10);
	    loginMenu.setPadding(new Insets(40, 40, 40, 40));
	    
		return loginMenu;
	}
	

	//studenti -> prehlad bodov
	private GridPane addGridPane1() {
		GridPane grid1 = new GridPane();
		
		kurzy.setPrefSize(110, 20);
		kurz0.setPrefSize(100, 20);
		kurz1.setPrefSize(100, 20);
		kurz2.setPrefSize(100, 20);
		kurz3.setPrefSize(100, 20);
		
		kurzy.setFont(Font.font(16));
		kurz0.setFont(Font.font(14));
		kurz1.setFont(Font.font(14));
		kurz2.setFont(Font.font(14));
		kurz3.setFont(Font.font(14));
		
		grid1.setVgap(10);
	    grid1.setPadding(new Insets(10, 0, 0, 10));
	    
	    grid1.add(kurzy, 0, 0);
	    grid1.add(kurz0, 0, 1);
	    grid1.add(kurz1, 0, 2);
	    grid1.add(kurz2, 0, 3);
	    grid1.add(kurz3, 0, 4);
		
		return grid1;
	}
	
	//lektori -> spustenie kurzov
	private GridPane addGridPane2() {
		GridPane grid2 = new GridPane();
				
		beh.setPrefSize(110, 20);
		novy.setPrefSize(110, 20);
		beh1.setPrefSize(110, 20);
		beh2.setPrefSize(110, 20);
		beh3.setPrefSize(110, 20);
		stare.setPrefSize(110, 20);
		
		beh.setFont(Font.font(16));
		novy.setFont(Font.font(14));
		beh1.setFont(Font.font(14));
		beh2.setFont(Font.font(14));
		beh3.setFont(Font.font(14));
		stare.setFont(Font.font(14));

		grid2.setVgap(10);
	    grid2.setPadding(new Insets(10, 0, 0, 10));
	    
	    grid2.add(beh, 0, 0);
	    grid2.add(novy, 0, 2);
	    grid2.add(beh1, 0, 3);
	    grid2.add(beh2, 0, 4);
	    grid2.add(beh3, 0, 5);
	    grid2.add(stare, 0, 7);
	    
		return grid2;
	}
	

	
	//vypis textarea
	private VBox addVBoxBottom() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vypis.setPrefSize(800, 500);
		vbox.getChildren().addAll(vypis);
		vypis.setText("Prípravnı kurz z matematiky a informatiky FIIT STU\n"
				+ "Kliknutím na tlaèítka v preh¾ade si môete pozrie stav bodov jednotlivıch študentov");
		return vbox;
	}


	private VBox addVBoxTop() {
		VBox vboxTop = new VBox();
		vboxTop.getChildren().addAll(addHBoxTop());
		vboxTop.getChildren().addAll(addHBoxBottom());		
		return vboxTop;
	}
	
	//menu
	private HBox addHBoxBottom() {
		HBox hboxBottom = new HBox();
		hboxBottom.setPadding(new Insets(15, 12, 15, 12));
		hboxBottom.setSpacing(12);
	    
	    prehlad.setPrefSize(100, 20);
	    student.setPrefSize(100, 20);
	    lektor.setPrefSize(100, 20);
	    prehlad.setFont(Font.font(null, FontWeight.BOLD, 16));    
	    student.setFont(Font.font(16));
	    lektor.setFont(Font.font(16));
	    	    
	    hboxBottom.setBackground(new Background(new BackgroundFill(Color.MEDIUMTURQUOISE, null, Insets.EMPTY)));
	    hboxBottom.getChildren().addAll(prehlad, student, lektor);
	    
	    return hboxBottom;
	}
	
	//nazov kurzu
	private HBox addHBoxTop() {
		HBox hboxTop = new HBox();
		hboxTop.setPadding(new Insets(10, 0, 10, 0));
		hboxTop.setAlignment(Pos.CENTER);
		//hboxTop.setStyle("-fx-background-color: #821950;");
	
	    nazov.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
	    nazov.setTextFill(Color.WHITE);
	    hboxTop.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, null, Insets.EMPTY)));	//mediumvioletred
	    hboxTop.getChildren().addAll(nazov);

	    return hboxTop;
	}
	
	//odhlasenie
	private HBox addHBoxLogOut() {
		HBox hboxLogOut = new HBox();
		hboxLogOut.setPadding(new Insets(10, 10, 10, 10));
		hboxLogOut.setAlignment(Pos.BOTTOM_RIGHT);
		
		hboxLogOut.getChildren().addAll(signOut);
		signOut.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, null, Insets.EMPTY)));
		signOut.setBorder(new Border(new BorderStroke(Color.DARKSEAGREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		return hboxLogOut;
	}
	
	/**
	 * Trieda pre pouitie Vlakna (Threads) s parametrom èísla behu(kurzu),
	 * staèí jedna trieda ale bude potreba vytvori 3 inštancie pre kadı beh zvláš 
	 */
	class VlaknoBeh implements Runnable {
		private int kurz ;
		
		public VlaknoBeh(int s) { 
			this.kurz = s;
		}
		
   		public void run() {
   			System.out.println("Beí kurz " + this.kurz +" vo vlákne !\n");
   			course.beh(this.kurz);
   			System.out.println("Kurz " + this.kurz + " ukonèenı !\n");
   		}
	}	

	

	public static void main(String[] args) {
		launch(args);		
	}
}
