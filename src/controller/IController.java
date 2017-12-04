package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import model.BoardState;
import model.Player;
import model.Point;
import view.View;

public interface IController {
	// set den luot nguoi choi
	public void setPlayerFlag(int playerFlag);
	// tra ve luot nguoi choi
	public int getPlayerFlag();
	// toa do cua nguoi choi
	public Point AI(int player);
	// danh dau ket thuc
	int checkEnd(int x, int y);
	// Kiem tra chien thang
	BoardState getBoardState();
	// set nguoi choi
	public void setPlayer(Player player);
	// bat dau choi
	void play(Button c, Button[][] a);
	// thoat
	boolean isEnd();
	// set giao dien 
	void setView(View view);
	// set ket thuc
	void setEnd(boolean end);
	// quay lai nuoc
	void undo(Button[][] a);
	// luu tro choi
	void save();
	// reset tro choi
	void reset(Button[][] a);
	// mo tro choi da luu
	void open(Button[][] a);
	// set thoi gian choi cho nguoi choi
	public void setTimePlayer(Labeled timePlayer1, Labeled timePlayer2);
	// chay thoi gian choi
	void runTimer(int player);

}
