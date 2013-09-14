/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

/**
 *
 * @author Eric
 */
final class SudokuCell {

	private SudokuSet rowSet;
	private SudokuSet columnSet;
	private SudokuSet regionSet;
	private int value;

	SudokuCell() {
		this(0);
	}

	SudokuCell(int digit) {
		value = digit;
	}

	SudokuCell(SudokuSet row, SudokuSet column, SudokuSet region) {
		this.rowSet = row;
		this.columnSet = column;
		this.regionSet = region;
		value = 0;
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
	}
}