package Game;

/**
 * This class is implementing the logic for playing against the computer.
 *
 */

public class MoveInfo {

	int COMP;
	int HUMAN;
	public int move; 
	public int value;
	public int usedSquares = 0;
	public int COMP_LOSS = -1;
	public int COMP_WIN = 1;
	int DRAW = 0;
	int[] board; 
	int counter = 0;
	
	/**
	 * constructor for creating instance of this class without parameters in game.
	 */
	public MoveInfo() {

	}
	
	/**
	 * Constructor with move and value as parameters.
	 * @param m
	 * @param v
	 */

	public MoveInfo(int m, int v) {
		move = m;
		value = v;
	}
	
	/**
	 * Checking whether the board is full.
	 * @return
	 */
	public boolean fullBoard() {
		for (int i : board) {
			if (i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checking if the square is empty before a move.
	 */
	public boolean isEmpty(int pos) {
		// Checking if the square is empty
		int position = pos;
		int[] bor = this.board;

		if (bor[position] != COMP && bor[position] != HUMAN) {
			return true;
		}
		return false;
	}
	
	/**
	 * Placing a piece on the square.
	 */
	void place(int position, int playerInteger) { // placing on the board
		board[position] = playerInteger;
	}
	
	/**
	 * Unplacing a piece, this is needed in the algorithm that is looping through the squares.
	 */
	void unplace(int p) {
		board[p] = 0;
	}

	/**
	 * Looping through rows, columns and diagonals after every move.
	 * 
	 */
	int checkIfWinner(int player, int win, int notWin) {
		int p = player; // 1
		int w = win; // -1
		int nw = notWin; // 1 !!!!
		int b[] = this.board;
		for (int i = 0; i <= 2; i++) {
			if (b[0 + (i * 3)] == p && b[1 + (i * 3)] == p && b[2 + (i * 3)] == p) {
				return w;
			} else if (b[i] == p && b[i + 3] == p && b[i + 6] == p) {
				return w;
			}
		}
		if (b[0] == p && b[4] == p && b[8] == p) {
			return w;
		} else if (b[2] == p && b[4] == p && b[6] == p) {
			return w;
		}
		return nw;
	}

	
	
	/**
	 *Checking if the computer wins.
	 */
	public MoveInfo immediateCompWin(int computer, int computerWin, int computerLoss, int move) {
		computer = this.COMP;
		computerWin = this.COMP_WIN;
		computerLoss = this.COMP_LOSS;
		int m = move;

		if (checkIfWinner(computer, computerWin, computerLoss) == computerWin) {
			return new MoveInfo(m, COMP_WIN);
		}
		return null;

	}
	/**
	 *Checking if the human wins.
	 */
	public MoveInfo immediateHumanWin(int human, int computerLoss, int computerWin, int move) {
		human = this.HUMAN;
		computerLoss = this.COMP_LOSS;
		computerWin = this.COMP_WIN;
		int m = move;
		if (checkIfWinner(human, computerLoss, computerWin) == computerLoss) {

			return new MoveInfo(m, COMP_LOSS);
		}
		return null;
	}
	
	/**
	 * Checking every possible combination a computer can place the piece on the square,
	 * recursively.
	 * 
	 */

	public MoveInfo findCompMove() {
		int i, responseValue;
		int value = 1;
		int bestMove = 1;
		MoveInfo quickWinInfo = immediateCompWin(COMP, COMP_WIN, COMP_LOSS, bestMove);
		if (fullBoard() == true) {
			value = DRAW;

		} else if (quickWinInfo != null) {
			//return quickWinInfo;
			return  new MoveInfo(bestMove, COMP_WIN);
			//return new MoveInfo(bestMove, value);
		} else {

			value = COMP_LOSS;
			for (i = 0; i <= 8; i++) {
				if (isEmpty(i) == true) {
					place(i, COMP);
					responseValue = findHumanMove().value;
					unplace(i);
					if (responseValue > value) {
						value = responseValue;
						bestMove = i;

					}
				}
			}
		}

		return new MoveInfo(bestMove, value);
	}
	
	/**
	 * Checking every possible combination a human can place the piece on the square,
	 * recursively.
	 * 
	 */
	public MoveInfo findHumanMove() {
		int i, responseValue;
		int value = 1;
		int bestMove = 1;
		MoveInfo quickWinInfo = immediateHumanWin(HUMAN, COMP_LOSS, COMP_WIN, bestMove);
		if (fullBoard() == true) {
			value = DRAW;
		} else if (quickWinInfo != null) {
			return new MoveInfo(bestMove, COMP_LOSS);
		}
		else {
			value = COMP_WIN;
			for (i = 0; i <= 8; i++) {
				if (isEmpty(i)) {
					place(i, HUMAN);
					responseValue = findCompMove().value;
					unplace(i);
					if (responseValue < value) {
						value = responseValue;
						bestMove = i;

					}
				}
			}
		}
		return new MoveInfo(bestMove, value);
	}
}
