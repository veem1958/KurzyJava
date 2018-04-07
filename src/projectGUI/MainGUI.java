package projectGUI;


import javafx.event.*;

import java.util.Iterator;
import java.lang.Exception;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import projectMain.Kurzy;
import projectMain.Students;
//import projectMain.Matematika;
//import projectMain.Informatika;
//import projectMain.Teachers;
//import projectMain.Passwords;
//import projectMain.Subjects;


public class MainGUI extends Application {

	private Label prehlad = new Label("PREHºAD:");
	private Label nazov = new Label("PRÕPRAVN› KURZ Z MATEMATIKY A INFORMATIKY");
	private Button student = new Button("ätudenti");
	private Button lektor = new Button("Lektori");
	private Button tema = new Button("TÈmy");
	private Button signOut = new Button("Odhl·siù sa");
	private TextArea vypis = new TextArea();
	//private ScrollPane scrol = new ScrollPane(vypis);
	
	//
	private Label login = new Label("Prihlasovacie meno: ");
	private Label password = new Label("Heslo: ");
	private TextField loginText = new TextField();
	private PasswordField passwordText = new PasswordField();
	private Button signIn = new Button("Prihl·siù sa");
	//
	
	//studenti
	Label kurzy = new Label("Prehæad bodov");
	Button kurz0 = new Button("Pred kurzom");
	Button kurz1 = new Button("Po 1.kurze");
	Button kurz2 = new Button("Po 2.kurze");
	Button kurz3 = new Button("Po 3.kurze");
	//
	
	//lektori
	Label beh = new Label("Beh kurzov");
	Button beh1 = new Button("1.kurz");
	Button beh2 = new Button("2.kurz");
	Button beh3 = new Button("3.kurz");
	//
	
	//temy
	Label predmety = new Label("Predmety");
	Button mat = new Button("Matematika");
	Button inf = new Button("Informatika");
	//
	
	Kurzy course = new Kurzy();
	Thread vb1 = new Thread(new VlaknoBeh(1));   // vl·kno 1 pre beh 1. kurzu
	Thread vb2 = new Thread(new VlaknoBeh(2));   // vl·kno 2 pre beh 2. kurzu
	Thread vb3 = new Thread(new VlaknoBeh(3));   // vl·kno 3 pre beh 3. kurzu
	
	String hlavicka = "STUDENT\t\t\t\t\t BODY\n";
	
	//main-----------------------------------------------------------------------
	public void start(Stage hlavneOkno) {
				
		hlavneOkno.setTitle("Window");
		
		GridPane loginMenu = new GridPane();		
		BorderPane border = new BorderPane();

		border.setTop(addVBoxTop());
		border.setCenter(addVBoxBottom()); 
		border.setBottom(addHBoxLogOut());
		
		hlavneOkno.setScene(new Scene(loginMenu(), 400, 200));
		hlavneOkno.show();
		hlavneOkno.centerOnScreen();
		
		//-------------------------------------------------------------
		//start hlavneho okna a menu
		class Prihlasenie implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) { 
				
				//hesla
				String uzivatel = "";
				String heslo = "";
				uzivatel = loginText.getText();
				heslo = passwordText.getText();
				if (uzivatel.equalsIgnoreCase("student") && heslo.equalsIgnoreCase("abc123")) {
					hlavneOkno.setScene(new Scene(border, 900, 600));
					hlavneOkno.show();
					hlavneOkno.centerOnScreen();
					
					try {
						  vypis.appendText("\nBeûÌ inicializ·cia ! \n");
					      course.inicializacia();
					      vypis.appendText("Inicializ·cia ukonËen· ! \n");
					}
					catch (Exception e) {
						e.printStackTrace();
						vypis.appendText("Chyba je : " + e.getMessage() + "\n");
					}
							
					kurz1.setDisable(true);
					kurz2.setDisable(true);
					kurz3.setDisable(true);
					beh2.setDisable(true);
					beh3.setDisable(true);
				}
				else {
					System.out.println("Chyba prihl·senia");
				}
				/*
				hlavneOkno.setScene(new Scene(border, 900, 600));
				hlavneOkno.show();
				hlavneOkno.centerOnScreen();
				
				try {
					  vypis.appendText("\nBeûÌ inicializ·cia ! \n");
				      course.inicializacia();
				      vypis.appendText("Inicializ·cia ukonËen· ! \n");
				}
				catch (Exception e) {
					e.printStackTrace();
					vypis.appendText("Chyba je : " + e.getMessage() + "\n");
				}
						
				kurz1.setDisable(true);
				kurz2.setDisable(true);
				kurz3.setDisable(true);
				beh2.setDisable(true);
				beh3.setDisable(true);*/
			}
		}
		
		try {
			signIn.setOnAction(new Prihlasenie());
			
			//hlavne menu
			student.setOnAction((ActionEvent e) -> {		//pouzitie lambda vyrazu
					border.setLeft(addGridPane1());
			});
			
			lektor.setOnAction((ActionEvent e) -> {			
					border.setLeft(addGridPane2());		
			});
			
			tema.setOnAction((ActionEvent e) -> {	
					border.setLeft(addGridPane3());
			});
			//
			
			//odhlasenie a ukoncenie programu
			signOut.setOnAction((ActionEvent e) -> {
				System.out.println("vlakno 1 stav : " + vb1.getState());	
				System.out.println("vlakno 2 stav : " + vb2.getState());	
				System.out.println("vlakno 3 stav : " + vb3.getState());	
				/*
				passwordText.clear();
				hlavneOkno.setScene(new Scene(loginMenu(), 400, 200));  //---
				hlavneOkno.show();
				hlavneOkno.centerOnScreen();
				
				String uzivatel = "";
				String heslo = "";
				uzivatel = loginText.getText();
				heslo = passwordText.getText();
				
				if (uzivatel.equalsIgnoreCase("student") && heslo.equalsIgnoreCase("abc123")) {
					hlavneOkno.setScene(new Scene(border, 900, 600));
					hlavneOkno.show();
					hlavneOkno.centerOnScreen();
				}
				*/
				hlavneOkno.close();	
			});
			
			
			
			//------------------------------------------------------------------------
			
			//vypis bodov studentov pred kurzom s pouzitim lambda vyrazu
			kurz0.setOnAction ((ActionEvent e) -> {
					vypis.clear();
					vypis.appendText("Vypis bodov pred kurzom:\n");
					vypis.appendText(hlavicka);
					Iterator<Students> iter = course.studenti[0].iterator();
					int n = 1; 
					while(iter.hasNext()) {
						vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(0));
						vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
						n++;
						
					}			
			});
		
			//vypis bodov studentov po 1.kurze s pouzitim lambda vyrazu
			kurz1.setOnAction((ActionEvent e) -> {
				vypis.clear();
				vypis.appendText("Vypis bodov po 1. kurze:\n");
				vypis.appendText(hlavicka);
				Iterator<Students> iter = course.studenti[1].iterator();
				int n = 1;
				while(iter.hasNext()) {
					vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(1));
					vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
					n++;
				}	
			});
			
			//vypis bodov studentov po 2.kurze s pouzitim lambda vyrazu
			kurz2.setOnAction((ActionEvent e) -> {
				vypis.clear();
				vypis.appendText("Vypis bodov po 2. kurze:\n");
				vypis.appendText(hlavicka);
				Iterator<Students> iter = course.studenti[2].iterator();
				int n = 1;
				while(iter.hasNext()) {
					vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(2));
					vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
					n++;
				}	
			});
			
			//vypis bodov studentov po 3.kurze s pouzitim lambda vyrazu
			kurz3.setOnAction((ActionEvent e) -> {
				vypis.clear();
				vypis.appendText("Vypis bodov po 3. kurze:\n");
				vypis.appendText(hlavicka);
				Iterator<Students> iter = course.studenti[3].iterator();
				int n = 1;
				while(iter.hasNext()) {
					vypis.appendText( + n + ".\t\t" + iter.next().vypisGUI(3));
					vypis.setFont(Font.font(null, FontWeight.LIGHT, 14));
					n++;
				}	
			});
					
			//beh 1.kurzu - spusta lektor
			beh1.setOnAction((ActionEvent e) -> {
				//course.beh(1);
				vb1.start();               //  spusti beh 1 v novom vlakne
				kurz1.setDisable(false);
				beh2.setDisable(false);
				beh1.setDisable(true);
			});
			
			//beh 2.kurzu - spusta lektor
			beh2.setOnAction((ActionEvent e) -> {
				//course.beh(2);
				vb2.start();               //  spusti beh 2 v novom vlakne
				kurz2.setDisable(false);
				beh3.setDisable(false);
				beh2.setDisable(true);
			});
			
			//beh 2.kurzu - spusta lektor
			beh3.setOnAction((ActionEvent e) -> {
				//course.beh(3);
				vb3.start();
				kurz3.setDisable(false);
				beh3.setDisable(true);
			});
			
			
			
			
			
		}
		catch(Exception e) {
			hlavneOkno.close();
			System.out.println("Chyba............");
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
		
		signIn.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, null, Insets.EMPTY)));
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
		beh1.setPrefSize(100, 20);
		beh2.setPrefSize(100, 20);
		beh3.setPrefSize(100, 20);
		
		beh.setFont(Font.font(16));
		beh1.setFont(Font.font(14));
		beh2.setFont(Font.font(14));
		beh3.setFont(Font.font(14));
		
		grid2.setVgap(10);
	    grid2.setPadding(new Insets(10, 0, 0, 10));
	    
	    grid2.add(beh, 0, 0);
	    grid2.add(beh1, 0, 2);
	    grid2.add(beh2, 0, 3);
	    grid2.add(beh3, 0, 4);
	    
		return grid2;
	}
	
	//temy -> zoznam predmetov
	private GridPane addGridPane3() {
		GridPane grid3 = new GridPane();
		
		predmety.setPrefSize(100, 20);
		mat.setPrefSize(100, 20);
		inf.setPrefSize(100, 20);
		
		grid3.add(predmety, 0, 0);
		grid3.add(mat, 0, 1);
		grid3.add(inf, 0, 2);
		
		predmety.setFont(Font.font(16));
		mat.setFont(Font.font(14));
		inf.setFont(Font.font(14));

		
		grid3.setVgap(10);
	    grid3.setPadding(new Insets(10, 0, 0, 10));
		return grid3;
	}
	

	
	//vypis textarea
	private VBox addVBoxBottom() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 12, 15, 12));
		vypis.setPrefSize(800, 500);
		vbox.getChildren().addAll(vypis);
		vypis.setText("PrÌpravn˝ kurz z matematiky a informatiky FIIT STU\n"
				+ "KliknutÌm na tlaËÌtka v prehæade si mÙûete pozrieù stav bodov jednotliv˝ch ötudentov");
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
		//hboxBottom.setStyle("-fx-background-color: #199998;");
	    
	    prehlad.setPrefSize(100, 20);
	    student.setPrefSize(100, 20);
	    lektor.setPrefSize(100, 20);
	    tema.setPrefSize(100, 20);
	    prehlad.setFont(Font.font(null, FontWeight.BOLD, 16));    
	    student.setFont(Font.font(16));
	    lektor.setFont(Font.font(16));
	    tema.setFont(Font.font(16));
	    	    
	    hboxBottom.setBackground(new Background(new BackgroundFill(Color.MEDIUMTURQUOISE, null, Insets.EMPTY)));
	    hboxBottom.getChildren().addAll(prehlad, student, lektor, tema);
	    
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
	    hboxTop.setBackground(new Background(new BackgroundFill(Color.MEDIUMVIOLETRED, null, Insets.EMPTY)));
	    hboxTop.getChildren().addAll(nazov);

	    return hboxTop;
	}
	
	//odhlasenie
	private HBox addHBoxLogOut() {
		HBox hboxLogOut = new HBox();
		hboxLogOut.setPadding(new Insets(10, 10, 10, 10));
		hboxLogOut.setAlignment(Pos.BOTTOM_RIGHT);
		
		hboxLogOut.getChildren().addAll(signOut);
		
		return hboxLogOut;
	}
	
	// pouzitie Nite - Vlakna - Threads s parametrom behu - t˝m p·dom staËÌ jedna trieda ale bude treba 3 inötancie pre kaûd˝ beh zvl·öù 
	class VlaknoBeh implements Runnable {
		private int k ;
		
		public VlaknoBeh(int s) { 
			this.k = s;
		}
		
   		public void run() {
   			System.out.println("BeûÌ kurz " + this.k +" vo vl·kne !\n");
   			course.beh(this.k);
   			System.out.println("Kurz " + this.k + " ukonËen˝ !\n");
   		}
	}	

	

	public static void main(String[] args) {
		launch(args);		
	}
}
