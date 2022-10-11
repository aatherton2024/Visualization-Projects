import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.util.*;
import java.util.Scanner;


/*
 * Implementation of a Sudolu Solver using Swing to produce a GUI to visualize
 * how backtracking is used to solve the problem. The JFrame class is extended
 * to provide and implement our GUI.
 */
public class SolveSudoku extends JFrame{

  /*
   * The visual representation of the sudoku board
   */
  private JLabel[][] table = new JLabel[9][9];

  /*
   * Representation of sudoku board used in algorithm, not seen by user
   */
  private int[][] representation = new int[9][9];

  /*
   * Constructor initiates game and then solves out
   */
  public SolveSudoku() {

    //super call to JFrame class to instantiate GUI
    super("Visualization of backtracking solution to Sudoku");

    //begin game, print out start board, solve, print out final board
    startGame();
    createGrid();
    fillInInitialValues();
    printBoard();
    solver(0, 0);
    printBoard();
  }

  /*
   * Method to create and set the JFrame representation
   */
  public void startGame() {
    this.setVisible(true);
    this.setLayout(new GridLayout(9, 9));
    this.setSize(630, 630);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /*
   * Method to instantiate the the visual representation of the sudoku board
   * Method initializes the table instance variable
   */
  public void createGrid() {

    //initialize instance variable
    table = new JLabel[9][9];

    //put a new JLabel in every matrix slot
    //draw thin lines to resemble sudoku board
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        table[row][col] = new JLabel();
        table[row][col].setHorizontalAlignment(SwingConstants.CENTER);
        table[row][col].setSize(70, 70);
        table[row][col].setOpaque(true);
        Border blackLine = BorderFactory.createLineBorder(Color.BLACK, 1);
        table[row][col].setBorder(blackLine);
        this.add(table[row][col]);
      }
    }

    //draw thick row and col lines on board
    for (int col = 0; col < 9; col++) {
      table[2][col].setBorder(BorderFactory.createMatteBorder(1,1,5,1, Color.BLACK));
      table[5][col].setBorder(BorderFactory.createMatteBorder(1,1,5,1, Color.BLACK));
      table[col][2].setBorder(BorderFactory.createMatteBorder(1,1,1,5, Color.BLACK));
      table[col][5].setBorder(BorderFactory.createMatteBorder(1,1,1,5, Color.BLACK));
    }

    //above loop deleted some thick lines, this fixes the issue
    table[2][2].setBorder(BorderFactory.createMatteBorder(1,1,5,5, Color.BLACK));
    table[5][2].setBorder(BorderFactory.createMatteBorder(1,1,5,5, Color.BLACK));
    table[2][5].setBorder(BorderFactory.createMatteBorder(1,1,5,5, Color.BLACK));
    table[5][5].setBorder(BorderFactory.createMatteBorder(1,1,5,5, Color.BLACK));
  }

  /*
   * Method to initialize the starting values of the board
   * Values can change, but should represent a valid sudoku puzzle
   * If puzzle invalid, backtracking method will quit after exhaustion
   */
  public void fillInInitialValues() {
    representation[0][0] = 1;
    representation[0][5] = 7;
    representation[0][7] = 9;
    representation[1][0] = 5;
    representation[1][1] = 3;
    representation[1][4] = 2;
    representation[1][8] = 8;
    representation[2][2] = 9;
    representation[2][3] = 6;
    representation[2][6] = 5;
    representation[3][0] = 4;
    representation[3][2] = 5;
    representation[3][3] = 3;
    representation[3][6] = 9;
    representation[4][1] = 1;
    representation[4][4] = 8;
    representation[4][8] = 2;
    representation[5][0] = 6;
    representation[5][3] = 7;
    representation[5][5] = 4;
    representation[5][0] = 6;
    representation[5][5] = 4;
    representation[6][0] = 3;
    representation[6][7] = 1;
    representation[7][1] = 4;
    representation[7][7] = 6;
    representation[7][8] = 7;
    representation[8][2] = 7;
    representation[8][6] = 3;
    representation[8][8] = 4;

    table[0][0].setText("1");
    table[0][5].setText("7");
    table[0][7].setText("9");
    table[1][0].setText("5");
    table[1][1].setText("3");
    table[1][4].setText("2");
    table[1][8].setText("8");
    table[2][2].setText("9");
    table[2][3].setText("6");
    table[2][6].setText("5");
    table[3][0].setText("4");
    table[3][2].setText("5");
    table[3][3].setText("3");
    table[3][6].setText("9");
    table[4][1].setText("1");
    table[4][4].setText("8");
    table[4][8].setText("2");
    table[5][0].setText("6");
    table[5][3].setText("7");
    table[5][5].setText("4");
    table[5][5].setText("4");
    table[6][0].setText("3");
    table[6][7].setText("1");
    table[7][1].setText("4");
    table[7][7].setText("6");
    table[7][8].setText("7");
    table[8][2].setText("7");
    table[8][6].setText("3");
    table[8][8].setText("4");
  }

  /*
   * Backtracking method to solve sudoku puzzle
   * Row and col coordinates for which board tile to start with
   * Row and col must be valid (between 0 and 9)
   */
  public boolean solver(int row, int col) {
    int curRow = row;
    int curCol = col;

    //if past last col, go to next row
    if (curCol > 8) {
      curRow++;
      curCol = 0;

      //puzzle must be solved if outside valid rows
      if (curRow > 8) {
        return true;
      }
    }

    //if current tile has not been filled in try all valid solutions
    if (representation[curRow][curCol] == 0) {
      return tryDigits(curRow, curCol);
    }

    //else make recursive call to the right tile
    return solver(curRow, curCol + 1);
  }

  /*
   * Helper method for solver used to try all valid numbers at a sudoku square
   * curRow and curCol used to index current tile on sudoku board
   * curRow and curCol must be in [0,8]
   */
  public boolean tryDigits(int curRow, int curCol) {

    //try every number between 1 and 9 in current tile
    //if invalid then continue to next number
    for (int i = 1; i <= 9; i++) {
      if (validMove(curRow, curCol, i)) {

        //try catch statement used to slow down the backtracking solution
        //so the user can properly visualize it
        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        //set cur value to i in our instance variables
        representation[curRow][curCol] = i;
        table[curRow][curCol].setText("" + i);
        table[curRow][curCol].setBackground(Color.GREEN);

        //if ultimately valid via recursive backtracking process return true
        if (solver(curRow, curCol + 1)) return true;
      }
    }

    //solution was not valid, reset current to being an unmarked tile
    representation[curRow][curCol] = 0;
    table[curRow][curCol].setText("");
    table[curRow][curCol].setBackground(Color.RED);

    //try catch statement used to slow down the backtracking solution
    //so the user can properly visualize it
    try {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //invalid, return false
    return false;
  }

  /*
   * Helper method for solver used to confirm a move is valid
   * row and col used to index sudoku tile
   * number the value to inserted into current tile
   * row and col must be in range [0,8]
   * number must be in range [1,9]
   */
  public boolean validMove(int row, int col, int number) {

    //slow down process to improve visualizaton
    try {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //check if row or col contains number
    for (int i = 0; i < 9; i++) {
      if (representation[row][i] == number) return false;
      if (representation[i][col] == number) return false;
    }

    //check if current grid of sudoku board contains number
    int rowGrid = (row / 3) * 3;
    int colGrid = (col / 3) * 3;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int curRow = rowGrid + i;
        int curCol = colGrid + j;
        if (representation[curRow][curCol] == number) return false;
      }
    }

    //board is valid
    return true;
  }

  /*
   * Method to print board
   */
  public void printBoard(){
    for(int row = 0; row < 9; row++){
      for(int col = 0; col < 9; col++){
        System.out.print(representation[row][col] + " | ");
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }

  /*
   * Main method creates a new instance of SolveSudoku class
   * Calling main method will create GUI and then solve out puzzle
   * Additionally, the puzzle will be printed twice to user's terminal
   * First print as start board
   * Second print as final solution
   */
  public static void main(String[] args) {

    SolveSudoku solved = new SolveSudoku();

  }
}
