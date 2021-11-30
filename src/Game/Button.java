package Game;

/**
 * Class game.Button extends JButton with (x,y) coordinates
 *
 */
class Button extends javax.swing.JButton {
	public int i; 
	public int j;

	/**
	 * Constructor
	 */
	public Button(int x, int y) {
		super(); // Create a JButton with a blank icon
		super.setIcon(new javax.swing.ImageIcon(("Pictures/None.png")));
		this.i = x;
		this.j = y;
	}

	/**
	 * returning the row-coordinate
	 */
	public int get_i() {
		return i;
	}

	/**
	 * Returning the column-coordinata
	 */
	public int get_j() {
		return j;
	}
}
