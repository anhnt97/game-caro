/**
 * Lop dieu khien chinh 
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.BoardState;
import model.ComputerPlayer;
import model.Player;
import model.HumanPlayer;
import model.Point;
import model.TaskTimer;
import view.View;

public class Controller implements IController {
	public View view; 
	private Player player;
	private Stack<Point> stack; // ngan xep luu cac nuoc da di
	private Class<?> classImg ; //  lay anh quan co
	private InputStream o;
	private InputStream x;
	private Image imageO;
	private Image imageX;
	private boolean end;
	private int tongNuocDi;
	private String playerWin;

	public Controller() {
		getComponents();	
	}

	private void getComponents() {
		end = false;
		tongNuocDi = 0;
		playerWin = "";
		stack = new Stack<>();
		classImg = this.getClass();
		o = classImg.getResourceAsStream("/image/o.png");
		x = classImg.getResourceAsStream("/image/x.png");
		imageO = new Image(o);
		imageX = new Image(x);
	}

	@Override
	public Point AI(int player) {
		return this.player.movePoint(player);
	}

	@Override
	public int getPlayerFlag() {
		return player.getPlayerFlag();
	}

	@Override
	public void setPlayerFlag(int playerFlag) {
		player.setPlayerFlag(playerFlag);
	}

	@Override
	public BoardState getBoardState() {
		return player.getBoardState();
	}

	@Override
	public int checkEnd(int x, int y) {
		return player.getBoardState().checkEnd(x, y);
	}


	@Override
	public boolean isEnd() {
		return end;
	}

	@Override
	public void play(Button c, Button[][] a) {
		StringTokenizer tokenizer = new StringTokenizer(c.getAccessibleText(), ";");
		int x = Integer.parseInt(tokenizer.nextToken());
		int y = Integer.parseInt(tokenizer.nextToken());
		//
		if (player instanceof HumanPlayer) {
			getBoardState();
			if (getPlayerFlag() == 1 && BoardState.boardArr[x][y] == 0) {
				danhCo(x, y, 1, a);
				setPlayerFlag(2);
			} else {
				getBoardState();
				if (getPlayerFlag() == 2 && BoardState.boardArr[x][y] == 0) {
					danhCo(x, y, 2, a);
					setPlayerFlag(1);
				}
			}

		} else {
			if (getPlayerFlag() == 1) {
				if (getBoardState().getPosition(x, y) == 0) {
					danhCo(x, y, 1, a);
					setPlayerFlag(2);
				}
			}
			if (getPlayerFlag()== 2) {
					Point p = AI(2);
					danhCo(p.x, p.y, 2, a);
					setPlayerFlag(1);
			}
		}
		if (end) {
			if (player instanceof ComputerPlayer && playerWin.equals("2")) {
				playerWin = "Computer";
			}
			timer1.cancel();
			timer2.cancel();
			
			dialog("Player " + playerWin + " win!");
			return;
		}
		runTimer(getPlayerFlag());
	}


	public void danhCo(int x, int y, int player, Button[][] arrayButtonChess) {
		getBoardState().setPosition(x, y, player);
		if (player == 1) {
			arrayButtonChess[x][y].setGraphic(new ImageView(imageX));
			Point point = new Point(x, y);
			point.setPlayer(1);
			stack.push(point);
			tongNuocDi++;
		} else {
			arrayButtonChess[x][y].setGraphic(new ImageView(imageO));
			Point point = new Point(x, y);
			point.setPlayer(2);
			stack.push(point);
			tongNuocDi++;
		}
		if (getBoardState().checkEnd(x, y) == player) {
			playerWin = player + "";
			end = true;
		}
		if (tongNuocDi == (getBoardState().height * getBoardState().width)) {
			playerWin = 2 + "";
			end = true;
		}

	}

	void print(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
	}
	// luu man choi 
	@Override
	public void save() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Luu man choi");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DATA", "*.dat"),
				new FileChooser.ExtensionFilter("All Images", "*.*"));
		File file = fileChooser.showSaveDialog(View.primaryStage);
		if (file != null) {
			ghiFile(file);
		}
	}
	// ghi file
	public void ghiFile(File file) {
		try {
			PrintStream printStream = new PrintStream(file);
			while (!stack.isEmpty()) {
				printStream.println(stack.pop().toString());
			}
			printStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// mo lai man choi
	@Override
	public void open(Button[][] arrayButtonChess) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Mo man choi");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DATA", "*.dat"),
				new FileChooser.ExtensionFilter("All Images", "*.*"));
		File file = fileChooser.showOpenDialog(View.primaryStage);
		if (file != null) {
			load(file);
			reset(arrayButtonChess);

			stack = new Stack<>();
			while (!queue.isEmpty()) {
				Point point = queue.poll();
				stack.push(point);
				danhCo(point.x, point.y, point.player, arrayButtonChess);
			}

		}

	}
	Queue<Point> queue;
	public boolean load(File file) {
		if (file != null) {
			queue = new LinkedList<>();
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = bufferedReader.readLine()) != null) { // doc theo tung dong
					StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
					int x = Integer.parseInt(stringTokenizer.nextToken());
					int y = Integer.parseInt(stringTokenizer.nextToken());
					int player = Integer.parseInt(stringTokenizer.nextToken());
					Point point = new Point(x, y);
					point.setPlayer(player);
					queue.add(point);
				}
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	// quay lại 1 nuoc co
	@Override
	public void undo(Button[][] arrayButtonChess) {
		if (!stack.isEmpty()) {
			tongNuocDi--;
			Point point = stack.pop();
			getBoardState();
			BoardState.boardArr[point.x][point.y] = 0;
			arrayButtonChess[point.x][point.y].setGraphic(null);
		}
	}
	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}

	public EventHandler<ActionEvent> action(String action) {
		return null;
	}

	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

		}
	};

	public void dialog(String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Trò chơi kết thúc");
		alert.setHeaderText(title);
		alert.setContentText("Bạn có muốn chơi lại");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (getPlayer() instanceof HumanPlayer) {
				view.replayHuman();
			} else {
				view.replayComputer();
			}
		} else {
			// su dung khi chon khong hoac dong hoi thoai
		}
	}

	@Override
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void setEnd(boolean end) {
		this.end = end;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void reset(Button[][] arrayButtonChess) {
		tongNuocDi = 0;
		timer1.cancel();
		timer2.cancel();
		timePlayer1.setText("30");
		timePlayer2.setText("30");
		getBoardState().resetBoard();
		for (int i = 0; i < arrayButtonChess.length; i++) {
			for (int j = 0; j < arrayButtonChess[i].length; j++) {
				arrayButtonChess[i][j].setGraphic(null);
			}
		}
	}

	Labeled timePlayer1, timePlayer2;

	@Override
	public void setTimePlayer(Labeled timePlayer1, Labeled timePlayer2) {
		this.timePlayer1 = timePlayer1;
		this.timePlayer2 = timePlayer2;
	}
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	@Override
	public void runTimer(int player) {
		if(end){
			timer1.cancel();
			timer2.cancel();
		}else{
			timer1.cancel();
			timer2.cancel();
			TaskTimer task1 = new TaskTimer(timePlayer1);
			TaskTimer task2 = new TaskTimer(timePlayer2);
			task1.setController(this);
			task2.setController(this);
			if (player == 1) {
				timer2.cancel();
				timer1 = new Timer();
				timer1.schedule(task1, 0, 1000);
			} else {
				timer1.cancel();
				timer2 = new Timer();
				timer2.schedule(task2, 0, 1000);
			}
		}
	}
}
