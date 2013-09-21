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
//		SudokuCell[][] cells = grid.getGridCells();
		boolean cellAction;
		System.out.println("Attempting to solve...");

		//Loop through solving strategies as long as one new cell is filled
		do {
			trimCandidates();
			cellAction = solveNakedSingles();
			cellAction = cellAction || solveNakedPairs();
		} while (cellAction);

//		printCandidates();

		if (validateGrid()) {
			System.out.println("Grid valid");
		} else {
			System.out.println("Grid invalid");
		}

	}

	private boolean solveNakedPairs() {
		System.out.println("Entering solveNakedPairs");
//		SudokuCell[][] cells = grid.getGridCells();
		SudokuSet[] rows = grid.getsRows();
		SudokuSet[] columns = grid.getsColumns();
		SudokuSet[] regions = grid.getsRegions();
		boolean action = false;

		for (int i = 0; i < 9; i++) {
			if (checkForNakedPairs(rows[i])) {
				System.out.println("Naked pair found in row " + i);
//				action = true;
			}
			if (checkForNakedPairs(columns[i])) {
				System.out.println("Naked pair found in column " + i);
//				action = true;
			}
			if (checkForNakedPairs(regions[i])) {
				System.out.println("Naked pair found in region " + i);
//				action = true;
			}
		}
		System.out.println("Leaving solveNakedPairs");
//		return action;
		return solveNakedSingles();
	}

	private boolean checkForNakedPairs(SudokuSet set) {
		List<SudokuCell> possibles, matches;
		possibles = new ArrayList<SudokuCell>();
		SudokuCell cellA, cellB;

		//Add cells with requisite candidate list size
		for (int i = 0; i < 9; i++) {
			cellA = set.getCell(i);
			if (cellA.getCandidates().size() == 2) {
				possibles.add(cellA);
			}
		}

		if (possibles.size() >= 2) {
			for (int i = 0; i < possibles.size(); i++) {
				cellA = possibles.get(i);
				for (int j = i + 1; j < possibles.size(); j++) {
					cellB = possibles.get(j);
					if (cellA.getCandidates().containsAll(cellB.getCandidates())) {
						trimNakedTupleCandidates(set, cellA.getCandidates());
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean solveNakedSingles() {
		System.out.println("Entering solveNakedSingles");
		SudokuCell[][] cells = grid.getGridCells();
		boolean singleFound = false;
		List<Integer> candidates;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				//if cell is empty(==0), check possibilities and fill cell if only one exists
				if ((cells[r][c].getValue()) == 0) {
					trimCellCandidates(cells[r][c]);
					candidates = cells[r][c].getCandidates();
					if (candidates.size() == 1) {
						cells[r][c].setValue(candidates.get(0));
						singleFound = true;
					}
				}
			}
		}
		System.out.println("Leaving solveNakedSingles");
		return singleFound;
	}

	private void trimNakedTupleCandidates(SudokuSet set, List<Integer> trim) {
		for (int i = 0; i < 9; i++) {
			//don't remove candidates from naked tuples
			if (!trim.containsAll(set.getCell(i).getCandidates())) {
				trimCellCandidates(set.getCell(i), trim);
			}
		}
	}

	private void trimCandidates() {
		SudokuCell[][] cells = grid.getGridCells();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				trimCellCandidates(cells[r][c]);
			}
		}
	}

	private void trimCellCandidates(SudokuCell cell) {
		trimCellCandidates(cell, cell.getRowSet().getCellValues());
		trimCellCandidates(cell, cell.getColumnSet().getCellValues());
		trimCellCandidates(cell, cell.getRegionSet().getCellValues());
	}

	private void trimCellCandidates(SudokuCell cell, List<Integer> trim) {
		List<Integer> candidates = cell.getCandidates();
		candidates.removeAll(trim);
	}

	private void printCandidates() {
		SudokuCell[][] cells = grid.getGridCells();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				List<Integer> candidates = cells[r][c].getCandidates();
				System.out.print(r + "x" + c + " candidates:");
				for (int i = 0; i < candidates.size(); i++) {
					System.out.print(candidates.get(i).toString() + ",");
				}
				System.out.println("");
			}
		}
	}

	private boolean validateGrid() {
		SudokuSet[] rows = grid.getsRows();
		SudokuSet[] columns = grid.getsColumns();
		SudokuSet[] regions = grid.getsRegions();
		for (int i = 0; i < 9; i++) {
			if (!validateSet(rows[i])) {
				return false;
			}
			if (!validateSet(columns[i])) {
				return false;
			}
			if (!validateSet(regions[i])) {
				return false;
			}
		}
		return true;
	}

	private boolean validateSet(SudokuSet set) {
		List<Integer> values = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			if (set.getCell(i).getValue() != 0) {
				Integer x = new Integer(set.getCell(i).getValue());
				if (values.contains(x)) {
					return false;
				} else {
					values.add(x);
				}
			}
		}
		return true;
	}
}
