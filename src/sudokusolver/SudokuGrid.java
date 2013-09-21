/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eric
 */
public class SudokuGrid {

	private SudokuCell[][] sGridCells;
	private SudokuSet[] sRows, sColumns, sRegions;
	//private SudokuGUI sGUI;

	public SudokuGrid() {
		sGridCells = new SudokuCell[9][9];
		sRows = new SudokuSet[9];
		sColumns = new SudokuSet[9];
		sRegions = new SudokuSet[9];

		//Setup sets before cells
		for (int i = 0; i < 9; i++) {
			sRows[i] = new SudokuSet();
			sColumns[i] = new SudokuSet();
			sRegions[i] = new SudokuSet();
		}

		//Setup cells and add to respective sets
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int b = (r / 3) * 3 + (c / 3);
				sGridCells[r][c] = new SudokuCell(sRows[r], sColumns[c], sRegions[b]);
				sRows[r].addCell(sGridCells[r][c]);
				sColumns[c].addCell(sGridCells[r][c]);
				sRegions[b].addCell(sGridCells[r][c]);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				sb.append(sGridCells[r][c].getValue());
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public void setCellValues(int[][] cellNums) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				sGridCells[r][c].setValue(cellNums[r][c]);
			}
		}
	}

	public void loadFromFile(String filename) {
		int[][] numArray = new int[9][9];

		Path path = FileSystems.getDefault().getPath(filename);
		try {
			BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset());
			String line;

			for (int r = 0; r < 9; r++) {
				line = br.readLine();
				for (int c = 0; c < 9; c++) {
					numArray[r][c] = Character.getNumericValue(line.charAt(c));
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(SudokuGrid.class.getName()).log(Level.SEVERE, null, ex);
		}

		setCellValues(numArray);
	}

	SudokuCell[][] getGridCells() {
		return sGridCells;
	}

	SudokuSet[] getsRows() {
		return sRows;
	}

	SudokuSet[] getsColumns() {
		return sColumns;
	}

	SudokuSet[] getsRegions() {
		return sRegions;
	}

	int emptyCellCount() {
		int count = 0;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (sGridCells[r][c].getValue() == 0) {
					count++;
				}
			}
		}
		return count;
	}
}
