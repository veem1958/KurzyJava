package projectGUI;


import javafx.event.*;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class MainGUI extends Application {

	private Label prehlad = new Label("PREHºAD:");
	private Label nazov = new Label("KURZY");
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
	private TextField passwordText = new TextField();
	private Button signIn = new Button("Prihl·siù sa");
	//
	
	public void start(Stage hlavneOkno) {
		
		hlavneOkno.setTitle("Window");
		
		GridPane loginMenu = new GridPane();
		
		BorderPane border = new BorderPane();

		border.setTop(addVBoxTop());
		border.setCenter(addVBoxBottom()); 
		border.setBottom(addHBoxLogOut());
		
		
		class Prihlasenie implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				//BorderPane border = new BorderPane();
				//border.setTop(addVBoxTop());
				//border.setCenter(addVBoxBottom()); 
				hlavneOkno.setScene(new Scene(border, 900, 600));
				hlavneOkno.show();
			}
		}
		
		signIn.setOnAction(new Prihlasenie());
		
		//---------------------------------------
		//BorderPane border = new BorderPane();

		//border.setTop(addVBoxTop());
		//border.setCenter(addVBoxBottom()); 
		
		
		
		student.setOnAction(new EventHandler <ActionEvent>() { 
			@Override 
	    	public void handle(ActionEvent event) {
				border.setLeft(addGridPane1());;
								
			}
		});
		
		tema.setOnAction(new EventHandler <ActionEvent>() {
			@Override 
	    	public void handle(ActionEvent event) {
				border.setLeft(addGridPane2());
			}

		});
		
		lektor.setOnAction(new EventHandler <ActionEvent>() {
			@Override 
	    	public void handle(ActionEvent event) {
				border.setLeft(null);
			}
		});
		
		signOut.setOnAction(new EventHandler <ActionEvent>() {
			@Override 
	    	public void handle(ActionEvent event) {
				
				
			
			}
		});
		
		
		
		hlavneOkno.setScene(new Scene(loginMenu(), 440, 200));
		hlavneOkno.show();
		
	}
	
	//hlavne prihlasovacie okno
	private GridPane loginMenu() {
		GridPane loginMenu = new GridPane();
		
		loginMenu.getChildren().addAll(login, password, loginText, passwordText, signIn);
		
		GridPane.setConstraints(login, 0, 0);
		GridPane.setConstraints(loginText, 1, 0);
		GridPane.setConstraints(password, 0, 1);
		GridPane.setConstraints(passwordText, 1, 1);
		GridPane.setConstraints(signIn, 1, 2);
		
		loginMenu.setVgap(10);
		loginMenu.setHgap(10);
	    loginMenu.setPadding(new Insets(40, 40, 40, 40));
	    
		return loginMenu;
	}
	
	//zoznam predmetov
	private GridPane addGridPane2() {
		GridPane grid2 = new GridPane();
		
		Label predmety = new Label("Predmety");
		Button mat = new Button("Matematika");
		Button inf = new Button("Informatika");
		
		predmety.setPrefSize(100, 20);
		mat.setPrefSize(100, 20);
		inf.setPrefSize(100, 20);
		
		grid2.add(predmety, 0, 0);
		grid2.add(mat, 0, 1);
		grid2.add(inf, 0, 2);
		
		predmety.setFont(Font.font(16));
		mat.setFont(Font.font(14));
		inf.setFont(Font.font(14));

		
		grid2.setVgap(10);
	    grid2.setPadding(new Insets(10, 0, 0, 10));
		return grid2;
	}
	

	//zoznam bodov
	private GridPane addGridPane1() {
		GridPane grid1 = new GridPane();
		
		Label kurzy = new Label("Body");
		Button kurz0 = new Button("Pred kurzom");
		Button kurz1 = new Button("Po 1.kurze");
		Button kurz2 = new Button("Po 2.kurze");
		Button kurz3 = new Button("Po 3.kurze");
		
		kurzy.setPrefSize(100, 20);
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
	
	private HBox addHBoxLogOut() {
		HBox hboxLogOut = new HBox();
		hboxLogOut.setPadding(new Insets(10, 10, 10, 10));
		hboxLogOut.setAlignment(Pos.BOTTOM_RIGHT);
		
		hboxLogOut.getChildren().addAll(signOut);
		
		return hboxLogOut;
	}

	

	public static void main(String[] args) {
		launch(args);		
	}
}
