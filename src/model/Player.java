/**
 *  lop dung chung cho computer player va human player
 */
package model;

public interface Player {
	public Point movePoint(int player);

	int getPlayerFlag();

	void setPlayerFlag(int playerFlag);

	BoardState getBoardState();
}
