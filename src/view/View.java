/**
 *  Giao dien chinh cua tro choi 
 */
package view;

import java.io.InputStream;
import java.util.Optional;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BoardState;
import model.ComputerPlayer;
import model.Player;
import model.HumanPlayer;

public class View implements EventHandler<ActionEvent> {
	public static final int WIDTH_BOARD = 20;
	public static final int HEIGHT_BOARD = 20;
	public static final int WIDTH_PANE = 1200;
	public static final int HEIGHT_PANE = 700;
	private Button btnHuman;
	private Button btnComputer;
	private Button btnExit;
	private Button btnUndo;
	private Button btnLoad;
	private Button btnSave;
	private Button btnAbout;
	private Labeled timePlayer1, timePlayer2;
	private BoardState boardState ;
	private ComputerPlayer computer ;
	// lop dieu khien
	Controller controller;
	// mang quan co khi danh
	public Button[][] arrayButtonChess;
	// khung view
	public static Stage primaryStage;

	public View() {
	}

	public void start(Stage primaryStage) {
		try {
			View.primaryStage = primaryStage;
			arrayButtonChess = new Button[WIDTH_BOARD][HEIGHT_BOARD];
			boardState = new BoardState(WIDTH_BOARD, HEIGHT_BOARD);
			computer = new ComputerPlayer(boardState);
			controller = new Controller();
			controller.setView(this);
			controller.setPlayer(computer);
				
			BorderPane borderPane = new BorderPane();
			BorderPane borderPaneRight = new BorderPane();
			menu(borderPaneRight);
			
			GridPane root = new GridPane();
			Scene scene = new Scene(borderPane, WIDTH_PANE, HEIGHT_PANE);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			borderPane.setPadding(new Insets(20));
			borderPane.setCenter(root);
			borderPane.setRight(borderPaneRight);
			// mac dinh player 1 di truoc
			controller.setPlayerFlag(1);
			controller.setTimePlayer(timePlayer1, timePlayer2);
			for (int i = 0; i < WIDTH_BOARD; i++) {
				for (int j = 0; j < HEIGHT_BOARD; j++) {
					Button button = new Button();
					button.setPrefSize(40, 40);
					button.setAccessibleText(i + ";" + j);
					arrayButtonChess[i][j] = button;
					root.add(button, j, i);
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if (!controller.isEnd()) {
								controller.play(button, arrayButtonChess);
							}
						}
					});
				}
			}
			primaryStage.setScene(scene);
			primaryStage.setTitle("Game caro");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void menu(BorderPane pane) {
		VBox box = new VBox();
		box.setSpacing(10);
		Class<?> clazz = this.getClass();
		AnchorPane anchorPaneLogo = new AnchorPane();
		AnchorPane anchorPaneMenu = new AnchorPane();
		// set logo
		InputStream input = clazz.getResourceAsStream("/image/Logo.jpg");
		Image image = new Image(input);
		ImageView imgView = new ImageView(image);
		imgView.setFitHeight(230);
		imgView.setFitWidth(260);
		AnchorPane.setTopAnchor(imgView, 10.0);
		AnchorPane.setLeftAnchor(imgView, 30.0);
		AnchorPane.setRightAnchor(imgView, 30.0);
		anchorPaneLogo.getChildren().add(imgView);
		// Computer
		btnComputer = new Button("Chơi với máy");
		btnComputer.setId("btnMenu");
		btnComputer.setOnAction(this);
		AnchorPane.setTopAnchor(btnComputer, 10.0);
		AnchorPane.setLeftAnchor(btnComputer, 30.0);
		AnchorPane.setRightAnchor(btnComputer, 30.0);
		anchorPaneMenu.getChildren().add(btnComputer);
		// Human
		btnHuman= new Button("Hai người chơi");
		btnHuman.setId("btnMenu");
		btnHuman.setOnAction(this);
		AnchorPane.setTopAnchor(btnHuman, 50.0);
		AnchorPane.setLeftAnchor(btnHuman, 30.0);
		AnchorPane.setRightAnchor(btnHuman, 30.0);
		anchorPaneMenu.getChildren().add(btnHuman);
		
		// Undo
		btnUndo = new Button("Quay lại");
		btnUndo.setId("btnMenu");
		btnUndo.setOnAction(this);
		AnchorPane.setTopAnchor(btnUndo, 90.0);
		AnchorPane.setLeftAnchor(btnUndo, 30.0);
		AnchorPane.setRightAnchor(btnUndo, 30.0);
		anchorPaneMenu.getChildren().add(btnUndo);
		// Save
		btnSave = new Button("Lưu lại");
		btnSave.setId("btnMenu");
		btnSave.setOnAction(this);
		AnchorPane.setTopAnchor(btnSave, 130.0);
		AnchorPane.setLeftAnchor(btnSave, 30.0);
		AnchorPane.setRightAnchor(btnSave, 30.0);
		anchorPaneMenu.getChildren().add(btnSave);
		// Load
		btnLoad = new Button("Load lại");
		btnLoad.setId("btnMenu");
		btnLoad.setOnAction(this);
		AnchorPane.setTopAnchor(btnLoad, 170.0);
		AnchorPane.setLeftAnchor(btnLoad, 30.0);
		AnchorPane.setRightAnchor(btnLoad, 30.0);
		anchorPaneMenu.getChildren().add(btnLoad);
		// About
		btnAbout = new Button("Thông tin");
		btnAbout.setId("btnMenu");
		btnAbout.setOnAction(this);
		AnchorPane.setTopAnchor(btnAbout, 210.0);
		AnchorPane.setLeftAnchor(btnAbout, 30.0);
		AnchorPane.setRightAnchor(btnAbout, 30.0);
		anchorPaneMenu.getChildren().add(btnAbout);
		// exit
		btnExit = new Button("Thoát");
		btnExit.setId("btnMenu");
		btnExit.setOnAction(this);
		AnchorPane.setTopAnchor(btnExit, 250.0);
		AnchorPane.setLeftAnchor(btnExit, 30.0);
		AnchorPane.setRightAnchor(btnExit, 30.0);
		anchorPaneMenu.getChildren().add(btnExit);
		//
		box.getChildren().add(anchorPaneLogo);
		box.getChildren().add(anchorPaneMenu);

		// Bottom
		GridPane gridPaneBottom = new GridPane();
		Labeled namePlayer1 = new Label("Player 1");
		namePlayer1.setId("nameplayer");
		Labeled namePlayer2 = new Label("Player 2");
		namePlayer2.setId("nameplayer");
		gridPaneBottom.add(namePlayer1, 0, 0);
		gridPaneBottom.add(namePlayer2, 1, 0);
		box.getChildren().add(gridPaneBottom);
		//
		GridPane gridPaneBottom1 = new GridPane();
		timePlayer1 = new Label("30");
		timePlayer1.setId("timeplayer");
		timePlayer2 = new Label("30");
		timePlayer2.setId("timeplayer");
		gridPaneBottom1.add(timePlayer1, 0, 0);
		gridPaneBottom1.add(timePlayer2, 1, 0);
		box.getChildren().add(gridPaneBottom1);
		//
		pane.setCenter(box);

	}

	
	
	@Override
	public void handle(ActionEvent e) {
		if (e.getSource() == btnExit) {
			primaryStage.close();
		}
		if (e.getSource() == btnHuman) {
			replayHuman();
		}
		if (e.getSource() == btnComputer) {
			replayComputer();
		}
		if (e.getSource() == btnUndo) {
			controller.undo(arrayButtonChess);
		}
		if (e.getSource() == btnLoad) {
			controller.open(arrayButtonChess);
		}
		if (e.getSource() == btnSave) {
			controller.save();
		}
		if (e.getSource() == btnAbout) {
			aboutUs();
		}
	}
	// che do dau voi may
	public void replayComputer() {
		
		controller.setEnd(false);
		controller.setTimePlayer(timePlayer1, timePlayer2);
		controller.setPlayer(new ComputerPlayer(new BoardState(WIDTH_BOARD, HEIGHT_BOARD)));
		controller.reset(arrayButtonChess);
		gameMode();
		
	}
	// che do 2 nguoi choi
	public void replayHuman() {
		controller.setEnd(false);
		controller.setTimePlayer(timePlayer1, timePlayer2);
		controller.setPlayer(new HumanPlayer(new BoardState(WIDTH_BOARD, HEIGHT_BOARD)));
		controller.setPlayerFlag(1);
		controller.reset(arrayButtonChess);

	}
	// thong tin ve nhom phat trien
	public void aboutUs() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About us");
		alert.setHeaderText("Game được phát triển bởi HAT-team");
		alert.setContentText("1. Nguyễn Tuấn Anh \n2. Trần Sách Hải \n Chúc các bạn chơi game vui vẻ !");
		alert.showAndWait();
	}
	// xet xem ai di truoc
	public void gameMode() {
		Alert gameMode = new Alert(AlertType.CONFIRMATION);
		gameMode.setTitle("Chọn người chơi trước");
		gameMode.setHeaderText("Bạn có muốn chơi trước không ?");
		Optional<ButtonType> result = gameMode.showAndWait();
		if(result.get() == ButtonType.CANCEL) {
			controller.danhCo(WIDTH_BOARD/2 - 1, HEIGHT_BOARD/2,2, arrayButtonChess);
			int[] AScore = {0,3,28,256,2308}; // 0,9,54,162,1458
			int[] DScore = {0,1,9,85,769};   // 0,3,27,99,729
			computer.setAScore(AScore);
			computer.setDScore(DScore);
			controller.setPlayerFlag(1);
		}
		else {
			controller.setPlayerFlag(1);
		}
	}
}
