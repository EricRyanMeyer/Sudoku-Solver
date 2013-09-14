/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Eric
 */
public class SudokuGUI extends JFrame{
	
	private JTextField[][] jtfCells;
	private JButton jbSolve;
	private JButton jbExit;
	
	public SudokuGUI(){
		initComponents();
	} 
	
	private void initComponents(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku Solver");
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(9,9));
		
		jtfCells = new JTextField[9][9];
		for(int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				jtfCells[i][j] = new JTextField();
				jtfCells[i][j].setColumns(2);
				gridPane.add(jtfCells[i][j]);
			}
		}
		
		JPanel optionPane = new JPanel();
		optionPane.setLayout(new BoxLayout(optionPane, BoxLayout.Y_AXIS));
		
		jbSolve = new JButton("Solve");
		jbExit = new JButton("Exit");
		optionPane.add(jbSolve);
		optionPane.add(jbExit);
		
		contentPane.add(gridPane);
		contentPane.add(optionPane);
		
		this.add(contentPane);
		this.pack();
		this.setVisible(true);
	}
}
