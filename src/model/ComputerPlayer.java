/**
 *  Lop computer player
 */
package model;
import java.util.ArrayList;
public class ComputerPlayer implements Player {
	EvalBoard eBoard; // diem cua trang thai ban co 
	BoardState boardState; // trang thai cua ban co
	int playerFlag = 2; // danh dau la computer player
	int _x, _y; // toa do nuoc di

	public static int maxDepth = 15; // do sau toi da 
	public static int maxMove = 3; // so nut con duyet qua toi da

	public int[] DScore = { 0, 1, 9, 81, 729 ,6561 ,59049};  // Mang diem phong ngu
	public int[] AScore = {0, 2, 18, 162 , 1458 ,13122,118098};// Mang diem tan cong
	public boolean cWin = false;
	public Point goPoint;
	public ComputerPlayer(BoardState board) {
		this.boardState = board;
		this.eBoard = new EvalBoard(board.width, board.height);
	}
	// ham luong gia
	public void evalChessBoard(int player, EvalBoard eBoard) {
		int row, col;
		int ePC, eHuman;
		eBoard.resetBoard();
		// Duyet theo hang
		for (row = 0; row < eBoard.width; row++)
			for (col = 0; col < eBoard.height - 4; col++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(row, col + i) == 1) // neu quan do la cua human
						eHuman++;
					if (boardState.getPosition(row, col + i) == 2) // neu quan do la cua pc
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						if (boardState.getPosition(row, col + i) == 0) { // neu o chua danh
							if (eHuman == 0) // ePC khac 0
								if (player == 1)
									eBoard.EBoard[row][col + i] += DScore[ePC]; // cho diem phong ngu
								else
									eBoard.EBoard[row][col + i] += AScore[ePC];// cho diem tan cong
							if (ePC == 0) // eHuman khac 0
								if (player == 2)
									eBoard.EBoard[row][col + i] += DScore[eHuman];// cho diem phong ngu								else
									eBoard.EBoard[row][col + i] += AScore[eHuman];// cho diem tan cong
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[row][col + i] *= 2;
						}
					}
			}

		// Duyet theo cot
		for (col = 0; col < eBoard.height; col++)
			for (row = 0; row < eBoard.width - 4; row++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(row + i, col) == 1)
						eHuman++;
					if (boardState.getPosition(row + i, col) == 2)
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						if (boardState.getPosition(row + i, col) == 0) // Neu o chua duoc danh
						{
							if (eHuman == 0)
								if (player == 1)
									eBoard.EBoard[row + i][col] += DScore[ePC];
								else
									eBoard.EBoard[row + i][col] += AScore[ePC];
							if (ePC == 0)
								if (player == 2)
									eBoard.EBoard[row + i][col] += DScore[eHuman];
								else
									eBoard.EBoard[row + i][col] += AScore[eHuman];
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[row + i][col] *= 2;
						}

					}
			}

		// Duyet theo duong cheo xuong
		for (col = 0; col < eBoard.height - 4; col++)
			for (row = 0; row < eBoard.width - 4; row++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(row + i, col + i) == 1)
						eHuman++;
					if (boardState.getPosition(row + i, col + i) == 2)
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						if (boardState.getPosition(row + i, col + i) == 0) // Neu o chua duoc danh
						{
							if (eHuman == 0)
								if (player == 1)
									eBoard.EBoard[row + i][col + i] += DScore[ePC];
								else
									eBoard.EBoard[row + i][col + i] += AScore[ePC];
							if (ePC == 0)
								if (player == 2)
									eBoard.EBoard[row + i][col + i] += DScore[eHuman];
								else
									eBoard.EBoard[row + i][col + i] += AScore[eHuman];
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[row + i][col + i] *= 2;
						}

					}
			}

		// Duyet theo duong cheo len
		for (row = 4; row < eBoard.width; row++)
			for (col = 0; col < eBoard.height - 4; col++) {
				ePC = 0;
				eHuman = 0;
				for (int i = 0; i < 5; i++) {
					if (boardState.getPosition(row - i, col + i) == 1)
						eHuman++;
					if (boardState.getPosition(row - i, col + i) == 2)
						ePC++;
				}
				if (eHuman * ePC == 0 && eHuman != ePC)
					for (int i = 0; i < 5; i++) {
						if (boardState.getPosition(row - i, col + i) == 0) { // neu o chua duoc danh
							if (eHuman == 0)
								if (player == 1)
									eBoard.EBoard[row - i][col + i] += DScore[ePC];
								else
									eBoard.EBoard[row - i][col + i] += AScore[ePC];
							if (ePC == 0)
								if (player == 2)
									eBoard.EBoard[row - i][col + i] += DScore[eHuman];
								else
									eBoard.EBoard[row - i][col + i] += AScore[eHuman];
							if (eHuman == 4 || ePC == 4)
								eBoard.EBoard[row - i][col + i] *= 2;
						}

					}
			}

	}

	int depth = 0;
	// thuat toan alpha-beta
	public void alphaBeta(int alpha, int beta, int depth, int player) {
		if(player==2){
			maxValue(boardState, alpha, beta, depth);
			
		}else{
			minValue(boardState, alpha, beta, depth);
		}
	}

	private int maxValue(BoardState state, int alpha, int beta, int depth) {
		evalChessBoard(2, eBoard);
		eBoard.MaxPos();  
		int value = eBoard.evaluationBoard;
		if (depth >= maxDepth) {
			return value;
		}
		evalChessBoard(2, eBoard);
		ArrayList<Point> list = new ArrayList<>();
		for (int i = 0; i < maxMove; i++) {
			Point node = eBoard.MaxPos();
			if(node == null)
				break;
			list.add(node);
			eBoard.EBoard[node.x][node.y] = 0;
		}
		int v = Integer.MIN_VALUE;
		for (int i = 0; i < list.size(); i++) {
			Point com = list.get(i);
			state.setPosition(com.x, com.y, 2);
			v = Math.max(v, minValue(state, alpha, beta, depth+1));
			state.setPosition(com.x, com.y, 0);
			if(v>= beta || state.checkEnd(com.x, com.y)==2){
				goPoint = com;
				return v;
				
			}
			alpha = Math.max(alpha, v);
		}

		return v;
	}

	private int minValue(BoardState state, int alpha, int beta, int depth) {
		evalChessBoard(1, eBoard);
		eBoard.MaxPos();
		int value = eBoard.evaluationBoard;
		if (depth >= maxDepth) {
			return value;
		}
		evalChessBoard(1, eBoard);
		ArrayList<Point> list = new ArrayList<>();
		for (int i = 0; i < maxMove; i++) {
			Point node = eBoard.MaxPos();
			if(node==null)
				break;
			list.add(node);
			eBoard.EBoard[node.x][node.y] = 0;
		}
		int v = Integer.MAX_VALUE;
		for (int i = 0; i < list.size(); i++) {
			Point com = list.get(i);
			state.setPosition(com.x, com.y, 1);
			v = Math.min(v, maxValue(state, alpha, beta, depth+1));
			state.setPosition(com.x, com.y, 0);
			if(v <= alpha || state.checkEnd(com.x, com.y)==1){
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}

	
//	private ArrayList<Point> listEmty(){
//		ArrayList<Point> list = new ArrayList<>();
//		for (int i = 0; i < boardState.width; i++) {
//			for (int j = 0; j < boardState.height; j++) {
//				if (boardState.getPosition(i, j) == 0) {
//					list.add(new Point(i, j));
//				}
//			}
//		}
//		return list;
//	}
//
//	private Point randomNuocCo() {
//		List<Point> ran = new ArrayList<>();
//		for (int i = 0; i < boardState.width; i++) {
//			for (int j = 0; j < boardState.height; j++) {
//				if (boardState.getPosition(i, j) == 0) {
//					ran.add(new Point(i, j));
//				}
//			}
//		}
//		if (!ran.isEmpty()) {
//			Random rd = new Random();
//			int p = rd.nextInt(ran.size());
//			return ran.get(p);
//		} else {
//			return null;
//		}
//	}


	public Point AI(int player) {
		depth = 0;
		alphaBeta(0, 1,2 ,player);
		Point temp = goPoint;
		if (temp != null) {
			_x = temp.x;
			_y = temp.y;
		}
		return new Point(_x, _y);
	}

	@Override
	public int getPlayerFlag() {
		return playerFlag;
	}

	@Override
	public void setPlayerFlag(int playerFlag) {
		this.playerFlag = playerFlag;
	}

	@Override
	public BoardState getBoardState() {
		return boardState;
	}

	@Override
	public Point movePoint(int player) {
		return AI(player);
	}

}
