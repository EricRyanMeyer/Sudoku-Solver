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
final class SudokuSet extends LinkedHashSet<SudokuCell> {
	
	SudokuSet(){
		super();
	}

	Set<Integer> getCellValues() {
		Set<Integer> list = new LinkedHashSet();
		for (SudokuCell cell : this) {
				list.add(new Integer(cell.getValue()));
		}
		return list;
	}
}
