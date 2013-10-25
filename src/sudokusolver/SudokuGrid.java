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

	private SudokuCell[][] gridCells;
	private SudokuSet[] rows, columns, regions;
	//private SudokuGUI sGUI;

	public SudokuGrid() {
		gridCells = new SudokuCell[9][9];
		rows = new SudokuSet[9];
		columns = new SudokuSet[9];
		regions = new SudokuSet[9];

		//Setup sets before cells
		for (int i = 0; i < 9; i++) {
			rows[i] = new SudokuSet();
			columns[i] = new SudokuSet();
			regions[i] = new SudokuSet();
		}

		//Setup cells and add to respective sets
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int b = (r / 3) * 3 + (c / 3);
				gridCells[r][c] = new SudokuCell(rows[r], columns[c], regions[b]);
				rows[r].add(gridCells[r][c]);
				columns[c].add(gridCells[r][c]);
				regions[b].add(gridCells[r][c]);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				sb.append(gridCells[r][c].getValue());
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public void setCellValues(int[][] cellNums) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				gridCells[r][c].setValue(cellNums[r][c]);
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
		return gridCells;
	}

	SudokuSet[] getRowSetArray() {
		return rows;
	}

	SudokuSet[] getColumnSetArray() {
		return columns;
	}

	SudokuSet[] getRegionSetArray() {
		return regions;
	}

	int emptyCellCount() {
		int count = 0;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (gridCells[r][c].getValue() == 0) {
					count++;
				}
			}
		}
		return count;
	}
}
