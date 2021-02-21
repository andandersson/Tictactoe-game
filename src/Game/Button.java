package Game;

//Class game.Button extends JButton with (x,y) coordinates
class Button extends javax.swing.JButton {
	public int i; // The x- and y- coordinate of the button in a GridLayout
	public int j;

	public Button(int x, int y) {
		super(); // Create a JButton with a blank icon
		super.setIcon(new javax.swing.ImageIcon(("Pictures/None.png")));
		this.i = x;
		this.j = y;
	}

	// Return row coordinate
	public int get_i() {
		return i;
	}

	// Return column coordinate
	public int get_j() {
		return j;
	}

}
