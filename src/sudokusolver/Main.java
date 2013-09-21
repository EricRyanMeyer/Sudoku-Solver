/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

/**
 *
 * @author Eric
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SudokuGrid grid = new SudokuGrid();
		SudokuStrategies strategies = new SudokuStrategies(grid);
		grid.loadFromFile("test/np2.txt");
		System.out.println(grid.toString());
		System.out.println("Total empty cells:" + grid.emptyCellCount());
		strategies.solve();
		System.out.println(grid.toString());
		System.out.println("Total empty cells:" + grid.emptyCellCount());
	}
}
