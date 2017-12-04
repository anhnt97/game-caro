/**
 *  Toa do moi quan co
 */
package model;

public class Point {
	public int x;
	public int y;
	public int player;

	public Point() {
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return x + ";" + y + ";" + player;
	}

}
