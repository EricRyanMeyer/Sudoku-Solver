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
final class SudokuSet {

	//private SudokuCell[] sCells;
	private List<SudokuCell> cells;

	SudokuSet() {
		this(new ArrayList<SudokuCell>());
	}

	SudokuSet(ArrayList<SudokuCell> sc) {
		cells = sc;
	}

//	 SudokuSet(SudokuCell[] sc) {
//		sCells.addAll(Arrays.asList(sc));
//	}

	SudokuCell getCell(int index) {
		return cells.get(index);
	}

	void setCell(SudokuCell sc, int index) {
		cells.set(index, sc);
	}

	void addCell(SudokuCell sc) {
		cells.add(sc);
	}

	List<Integer> getCellValues() {
		List<Integer> list = new ArrayList();
		for (SudokuCell cell : cells) {
			int val = cell.getValue();
			if (val != 0) {
				list.add(new Integer(val));
			}
		}
		return list;
	}
}
