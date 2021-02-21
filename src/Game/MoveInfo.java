package Game;

public class MoveInfo {

	int COMP;
	int HUMAN;

	public int move; // representerar rutor!
	public int value;
	public int usedSquares = 0;
	public int COMP_LOSS = -1;
	public int COMP_WIN = 1;
	int DRAW = 0;
	int[] board; // array for storing the pieces on the board

	public boolean fullBoard() {

		for (int i : board) {
			if (i == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty(int pos) {
		// Checking if the square is empty
		int position = pos;
		int[] bor = this.board;

		if (bor[position] != COMP && bor[position] != HUMAN) {
			return true;

		}
		return false;
	}

	void place(int position, int playerInteger) { // placing on the board

		board[position] = playerInteger;
	}

	void unplace(int p) {
		// unplacing
		board[p] = 0;
	}

	int checkIfWinner(int player, int win, int notWin) {
		// checking rows, columns and diagonals

		int p = player; // 1
		int w = win; // -1
		int nw = notWin; // 1 !!!!
		int b[] = this.board;

		for (int i = 0; i <= 2; i++) {
			if (b[0 + (i * 3)] == p && b[1 + (i * 3)] == p && b[2 + (i * 3)] == p) {
				// checking rows
				return w;
			} else if (b[i] == p && b[i + 3] == p && b[i + 6] == p) {
				// then diagonals
				return w;
			}
		}

//	checking diagnoals

		if (b[0] == p && b[4] == p && b[8] == p) {
			return w;
		} else if (b[2] == p && b[4] == p && b[6] == p) {
			return w;
		}

		return nw;

	}

	public MoveInfo() {

		// constructor for creating instance of this class without parameters in game.LS
	}

	public MoveInfo(int m, int v) {

		move = m;
		value = v;
	}

	public MoveInfo immediateCompWin(int computer, int computerWin, int computerLoss, int move) {

		// Checking if there are 3 in a row for computer

		computer = this.COMP;
		computerWin = this.COMP_WIN;
		computerLoss = this.COMP_LOSS;
		int m = move;

		if (checkIfWinner(computer, computerWin, computerLoss) == computerWin) {
			return new MoveInfo(m, COMP_WIN);
		}
		return null;

	}

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

	int counter = 0;

	public MoveInfo findCompMove() {
		int i, responseValue;
		int value = 1;
		int bestMove = 1;
		MoveInfo quickWinInfo = immediateCompWin(COMP, COMP_WIN, COMP_LOSS, bestMove);

		if (fullBoard() == true) {

			value = DRAW;

		} else if (quickWinInfo != null) {
			return quickWinInfo;
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

	public MoveInfo findHumanMove() {
		int i, responseValue;
		int value = 1;
		int bestMove = 1;
		MoveInfo quickWinInfo = immediateHumanWin(HUMAN, COMP_LOSS, COMP_WIN, bestMove);
		if (fullBoard() == true) {
			value = DRAW;
		} else if (quickWinInfo != null) {
			return quickWinInfo;
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
