/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eric
 */
public class SudokuStrategies {

	private SudokuGrid grid;

	SudokuStrategies(SudokuGrid sGrid) {
		this.grid = sGrid;
	}

	void solve() {
		SudokuCell[][] cells = grid.getGridCells();
		boolean cellFilled;
		System.out.println("Attempting to solve...");

		//Loop through solving strategies as long as one new cell is filled
		do {
			cellFilled = false;
			cellFilled = cellFilled || solveSingles(cells);
		} while (cellFilled);

	}

	private boolean solveSingles(SudokuCell[][] cells) {
		boolean singleFound = false;
		List<Integer> possibilities;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				//if cell is empty(==0), check possibilities and fill cell if only one exists
				if ((cells[r][c].getValue()) == 0) {
					possibilities = getPossibilities(cells[r][c]);
					if (possibilities.size() == 1){
						cells[r][c].setValue(possibilities.get(0));
						singleFound = true;
					}
				}
			}
		}
		return singleFound;
	}

	private List<Integer> getPossibilities(SudokuCell cell) {
		//Create ArrayList with 1-9 values, leaving out 0 since it indicates empy cell
		List<Integer> poss = new ArrayList();
		for (int i = 1; i < 10; i++) {
			poss.add(new Integer(i));
		}
		poss.removeAll((cell.getRowSet()).getCellValues());
		poss.removeAll((cell.getColumnSet()).getCellValues());
		poss.removeAll((cell.getRegionSet()).getCellValues());
		return poss;
	}
}
