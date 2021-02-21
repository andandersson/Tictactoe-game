package Game;

import javax.swing.*;
import java.awt.*;

public class LS extends javax.swing.JFrame {

	private static final int EMPTY = 0;
	private static final int HUMAN = 1;
	private static final int COMPUTER = 2;
	private static final int PLAYERTWO = 3;
	private static int OPPONENT;
	private static final int CONTINUE = 6;
	private static final int SIZE = 3;
	private int[][] board = new int[SIZE][SIZE]; // The marks on the board
	private javax.swing.JButton[][] jB; // The buttons of the board
	private static int turn = HUMAN; // HUMAN starts the game
	private static int[][] pieceCounter = new int[SIZE][SIZE];
	private static int[] UsedSquares = new int[9]; // keeping the placements in an array

	public LS() {

		// Close the window when the user exits
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		initBoard(); // Set up the board
	}

	private boolean fullBoard() {

		int count = 0;
		for (int i = 0; i < UsedSquares.length; i++) {
			if (UsedSquares[i] == HUMAN || UsedSquares[i] == COMPUTER) {
				count++;
			}
		}
		if (count == 9) {
			return true;
		} else {
			return false;
		}

	}

	// Initalize the board
	private void initBoard() {
		// Create a 3*3 gridlayput to hold the buttons
		java.awt.GridLayout layout = new GridLayout(3, 3);
		getContentPane().setLayout(layout);
		// The board is a 3*3 grid of buttons
		jB = new Button[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				// Create a new button and add an actionListerner to it
				jB[i][j] = new Button(i, j);
				// Add an action listener to the button to handle mouse clicks
				jB[i][j].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent act) {
						jBAction(act);
					}
				});
				add(jB[i][j]); // Add the buttons to the GridLayout
				board[i][j] = EMPTY; // Initialize all marks on the board to empty
			}
		}
		// Pack the GridLayout and make it visible
		pack();
	}

	private int checkSquare(int row, int col) {
		return pieceCounter[row][col];
	}

	private void jBAction(java.awt.event.ActionEvent act) {
		Button thisButton;
		if (turn == HUMAN) {
			thisButton = (Button) act.getSource(); // Get the button clicked on
			int i = thisButton.get_i();
			int j = thisButton.get_j();
			int squareCheck = checkSquare(i, j);
			int turnCount = 0;
			if (squareCheck == 0) {

				// Squarecheck is checking if the square is empty. this will
				// make placing in ockupied squares impossible
				thisButton.setIcon(new ImageIcon("Pictures/X.png"));
				pieceCounter[i][j] = HUMAN;
				int CounterOne = 0;
				for (int k = 0; k <= pieceCounter.length - 1; k++) {
					for (int l = 0; l <= pieceCounter[k].length - 1; l++) {
						if (pieceCounter[k][l] == HUMAN) {
							UsedSquares[CounterOne] = HUMAN;
						}
						CounterOne++;

					}
				}

				int humanWin = checkIfWinner(HUMAN);
				if (humanWin == 1) {

					gameOver(HUMAN);

				} else if (fullBoard() == true) {
					gameOver(4);
				} else if (OPPONENT == 1) {
					turn = (turn == HUMAN) ? COMPUTER : HUMAN;
				} else if (OPPONENT == 0) {
					turn = (turn == HUMAN) ? PLAYERTWO : HUMAN;

				}
			}
		}

		if (turn == COMPUTER) {
			MoveInfo ComputerTurn = new MoveInfo();
			ComputerTurn.board = this.UsedSquares;
			ComputerTurn.COMP = this.COMPUTER;
			ComputerTurn.HUMAN = this.HUMAN;
			MoveInfo ComputerMove = ComputerTurn.findCompMove();
			int ComputerMovePos = ComputerMove.move;
			UsedSquares[ComputerMovePos] = COMPUTER;
			int[][] tempArray = new int[SIZE][SIZE]; // array for storing the computers moves on the board
			int Counter = 0;
			for (int k = 0; k <= tempArray.length - 1; k++) {
				for (int l = 0; l <= tempArray[k].length - 1; l++) {
					if (Counter == ComputerMovePos) {
						jB[k][l].setIcon(new ImageIcon("Pictures/O.png"));
						pieceCounter[k][l] = COMPUTER;
					}
					Counter++;

				}
			}

			int compWin = checkIfWinner(COMPUTER);

			if (compWin == 1) {
				gameOver(COMPUTER);

			} else if (fullBoard() == true) {
				gameOver(3);
			}

			else {
				turn = (turn == HUMAN) ? COMPUTER : HUMAN;
			}

			ComputerTurn = null;

		}

		if (turn == PLAYERTWO) {
			thisButton = (Button) act.getSource(); // Get the button clicked on
			int i = thisButton.get_i();
			int j = thisButton.get_j();
			int squareCheck = checkSquare(i, j);
			int turnCount = 0;
			if (squareCheck == 0) {
	
				// Squarecheck is checking if the square is empty. this will
				// make placing in ockupied squares impossible

				thisButton.setIcon(new ImageIcon("Pictures/O.png"));
				pieceCounter[i][j] = PLAYERTWO;

				int CounterOne = 0;
				for (int k = 0; k <= pieceCounter.length - 1; k++) {
					for (int l = 0; l <= pieceCounter[k].length - 1; l++) {
						if (pieceCounter[k][l] == PLAYERTWO) {
							UsedSquares[CounterOne] = PLAYERTWO;
						}
						CounterOne++;
					}
				}
				int playerTwoWin = checkIfWinner(PLAYERTWO);
				if (playerTwoWin == 1) {

					gameOver(PLAYERTWO);

				} else if (fullBoard() == true) {
					gameOver(3);
				}

				else {
					turn = (turn == HUMAN) ? PLAYERTWO : HUMAN;
				}
			}
		}
	}

	private void gameOver(int player) {
		// checking if we have awinner yet
		int[][] gameOverArray = new int[SIZE][SIZE];
		for (int k = 0; k <= gameOverArray.length - 1; k++) {
			for (int l = 0; l <= gameOverArray[k].length - 1; l++) {
				jB[k][l].setEnabled(false);
			}
		}
		JFrame NoMorePlaying = new JFrame();
		if (player == HUMAN) {
			JOptionPane.showMessageDialog(NoMorePlaying, "Game over, you win!");
		} else if (player == COMPUTER) {
			JOptionPane.showMessageDialog(NoMorePlaying, "Game over, the computer wins!");
		} else if (player == PLAYERTWO) {
			JOptionPane.showMessageDialog(NoMorePlaying, "Game over, player two wins!");
		} else {
			JOptionPane.showMessageDialog(NoMorePlaying, "Draw!");
		}

	}

	private int checkIfWinner(int player) {
		// checking rows, columns and diagonals

		int p = player;

		int b[] = this.UsedSquares;

		for (int i = 0; i <= 2; i++) {
			if (b[0 + (i * 3)] == p && b[1 + (i * 3)] == p && b[2 + (i * 3)] == p) {
				return 1;

			} else if (b[i] == p && b[i + 3] == p && b[i + 6] == p) {
				return 1;
			}
		}

		if (b[0] == p && b[4] == p && b[8] == p) {
			return 1;
		}
		if (b[2] == p && b[4] == p && b[6] == p) {
			return 1;
		}

		return 0;

	}

	private int chooseOpponent() {

		int choice;
		String[] options1 = { "Against human", "Against computer"};

		while (true) {
			choice = JOptionPane.showOptionDialog(null, "Please choose how you want to play",
					"Welcome to play tic tac toe", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1,
					null);

			if (choice == JOptionPane.YES_OPTION || choice == JOptionPane.NO_OPTION) {

				break;
			}
		}
		return choice;
	}

	public static void main(String[] args) {
		LS lsGUI = new LS(); // constructor, calling initBoard
		for (int j = 0; j <= pieceCounter.length - 1; j++) {
			for (int i = 0; i <= pieceCounter[j].length - 1; i++) {
				pieceCounter[i][j] = 0;
			}
		}
		for (int k = 0; k <= UsedSquares.length - 1; k++) {
			UsedSquares[k] = 0;
		}
		String threadName = Thread.currentThread().getName();
		lsGUI.setVisible(true);
		OPPONENT = lsGUI.chooseOpponent();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				while ((Thread.currentThread().getName() == threadName)) {
					try {
						Thread.sleep(100); // Sleep for 100 millisecond, wait for button press
					} catch (InterruptedException e) {
					}
					;
				}
			}
		});
	}
}