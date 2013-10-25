/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Eric
 */
final class SudokuCell {

	private SudokuSet rowSet, columnSet, regionSet;
	private int value;
	private Set<Integer> candidates;

	SudokuCell() {
		this(0);
	}

	SudokuCell(int digit) {
		init();
		value = digit;
	}

	SudokuCell(SudokuSet row, SudokuSet column, SudokuSet region) {
		init();
		this.rowSet = row;
		this.columnSet = column;
		this.regionSet = region;
		value = 0;
	}

	private void init() {
		loadCandidates();
	}

	SudokuSet getRegionSet() {
		return regionSet;
	}

	void setRegionSet(SudokuSet region) {
		this.regionSet = region;
	}

	SudokuSet getColumnSet() {
		return columnSet;
	}

	void setColumnSet(SudokuSet column) {
		this.columnSet = column;
	}

	SudokuSet getRowSet() {
		return rowSet;
	}

	void setRowSet(SudokuSet row) {
		this.rowSet = row;
	}

	int getValue() {
		return value;
	}

	void setValue(int v) {
		value = v;
		if (value == 0) {
			loadCandidates();
		} else {
			candidates.clear();
		}
	}

	Set<Integer> getCandidates() {
		return candidates;
	}

	void setCandidates(Set<Integer> candidates) {
		this.candidates = candidates;
	}

	private void loadCandidates() {
		candidates = new LinkedHashSet<Integer>();
		for (int i = 1; i < 10; i++) {
			candidates.add(new Integer(i));
		}
	}
}