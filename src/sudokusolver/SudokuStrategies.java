/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
		boolean cellAction;
		System.out.println("Attempting to solve...");

		//Loop through solving strategies as long as one new cell is filled
		do {
			trimCandidates();
//			printCandidates();
			cellAction = solveNakedSingles();
			cellAction = solveNakedPairs() || cellAction;
			cellAction = solveNakedTriples() || cellAction;
		} while (cellAction);

//		printCandidates();

		if (validateGrid()) {
			System.out.println("Grid valid");
		} else {
			System.out.println("Grid invalid");
		}

	}

	private boolean solveNakedTriples() {
		System.out.println("Entering solveNakedTriples");
		SudokuSet[] rows = grid.getRowSetArray();
		SudokuSet[] columns = grid.getColumnSetArray();
		SudokuSet[] regions = grid.getRegionSetArray();

		for (int i = 0; i < 9; i++) {
			Set<Integer> trim;

			trim = checkForNakedTuple(rows[i], new SudokuSet(), 3);
			if (trim != null) {
//				System.out.println("Naked triple found in row " + i);
//				printTrimSet(trim);
				trimSetCandidates(rows[i], trim);
			}

			trim = checkForNakedTuple(columns[i], new SudokuSet(), 3);
			if (trim != null) {
//				System.out.println("Naked triple found in column " + i);
//				printTrimSet(trim);
				trimSetCandidates(columns[i], trim);
			}

			trim = checkForNakedTuple(regions[i], new SudokuSet(), 3);
			if (trim != null) {
//				System.out.println("Naked triple found in regions " + i);
//				printTrimSet(trim);
				trimSetCandidates(regions[i], trim);
			}
		}

		System.out.println("Leaving solveNakedTriples");
		return solveNakedSingles();
	}

	private boolean solveNakedPairs() {
		System.out.println("Entering solveNakedPairs");
		SudokuSet[] rows = grid.getRowSetArray();
		SudokuSet[] columns = grid.getColumnSetArray();
		SudokuSet[] regions = grid.getRegionSetArray();

		for (int i = 0; i < 9; i++) {
			Set<Integer> trim;

			trim = checkForNakedTuple(rows[i], new SudokuSet(), 2);
			if (trim != null) {
//				System.out.println("Naked pair found in row " + i);
//				printTrimSet(trim);
				trimSetCandidates(rows[i], trim);
			}

			trim = checkForNakedTuple(columns[i], new SudokuSet(), 2);
			if (trim != null) {
//				System.out.println("Naked pair found in column " + i);
//				printTrimSet(trim);
				trimSetCandidates(columns[i], trim);
			}

			trim = checkForNakedTuple(regions[i], new SudokuSet(), 2);
			if (trim != null) {
//				System.out.println("Naked pair found in regions " + i);
//				printTrimSet(trim);
				trimSetCandidates(regions[i], trim);
			}
		}

		System.out.println("Leaving solveNakedPairs");
		return solveNakedSingles();
	}

	private Set<Integer> checkForNakedTuple(SudokuSet source, SudokuSet match, int tupleSize) {
		SudokuSet subsetSource = (SudokuSet) source.clone();
		for (SudokuCell cell : source) {
			//remove current cell from source clone to create subset for recursive call
			subsetSource.remove(cell);
			int numCandidates = cell.getCandidates().size();
			if ((numCandidates > 1) && (numCandidates <= tupleSize)) {
				//clone match and add cell to create superset for validation and recursive call
				SudokuSet superMatch = (SudokuSet) match.clone();
				superMatch.add(cell);

				//Build set of unique candidates from cells in superMatch
				Set<Integer> tupleSet = new LinkedHashSet<Integer>();
				for (SudokuCell cell2 : superMatch) {
					tupleSet.addAll(cell2.getCandidates());
				}

				if (tupleSet.size() > tupleSize) {
					continue;
				} else if (superMatch.size() < tupleSize) {
					return checkForNakedTuple(subsetSource, superMatch, tupleSize);
				} else {
					return tupleSet;
				}
			}
		}
		return null;
	}

	private boolean solveNakedSingles() {
		System.out.println("Entering solveNakedSingles");
		SudokuCell[][] cells = grid.getGridCells();
		boolean singleFound = false;
		Set<Integer> candidates;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				//if cell is empty(==0), check possibilities and fill cell if only one exists
				if ((cells[r][c].getValue()) == 0) {
					trimCellCandidates(cells[r][c]);
					candidates = cells[r][c].getCandidates();
					if (candidates.size() == 1) {
						Integer val = candidates.iterator().next();
						cells[r][c].setValue(val.intValue());
						singleFound = true;
					}
				}
			}
		}
		System.out.println("Leaving solveNakedSingles");
		return singleFound;
	}

	private void trimSetCandidates(SudokuSet set, Set<Integer> trim) {
		//Only trim candidates from cell if they are subset of cell candidates
		for (SudokuCell cell : set) {
			if (!trim.containsAll(cell.getCandidates())) {
				trimCellCandidates(cell, trim);
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

	private void trimCellCandidates(SudokuCell cell, Set<Integer> trim) {
		Set<Integer> candidates = cell.getCandidates();
		candidates.removeAll(trim);
	}

	private void printCandidates() {
		SudokuCell[][] cells = grid.getGridCells();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				Set<Integer> candidates = cells[r][c].getCandidates();
				System.out.print(r + "x" + c + " candidates:");
				for (Integer val : candidates) {
					System.out.print(val.toString() + ",");
				}
				System.out.println("");
			}
		}
	}

	private void printTrimSet(Set set) {
		System.out.print("Set values:");
		for (Object val : set) {
			System.out.print(val + ",");
		}
		System.out.println("");
	}

	private boolean validateGrid() {
		SudokuSet[] rows = grid.getRowSetArray();
		SudokuSet[] columns = grid.getColumnSetArray();
		SudokuSet[] regions = grid.getRegionSetArray();
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
		for (SudokuCell cell : set) {
			Integer x = new Integer(cell.getValue());
			if (x != 0) {
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
