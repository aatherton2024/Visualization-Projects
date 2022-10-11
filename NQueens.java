import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Scanner;

/*
 * Implementation of a LeetCode NQueens problem using Swing to produce a GUI to
 * visualize backtracking solves the problem. The JFrame class is extended
 * to provide and implement our GUI.
 */
public class NQueens extends JFrame{

  /*
   * Dimension for the nxn board
   */
  private int boardSize;

  /*
   * Visual representation of the board
   */
  private JLabel[][] table;

  /*
   * Internal representation of the board, only used for printing board
   */
  private int[][] board;

  /*
   * Constructor initiates problem and then solves it out
   * n the dimension board will be set to
   */
  public NQueens(int n) {
    //call JFrame constructor to initialize GUI
    super("N-Queens visualization");

    //initilize instance variables and solve problem
    boardSize = n;
    board = new int[boardSize][boardSize];
    startGame();
    createGrid();
    solveNQueens();
  }

  /*
   * Method to create and set the JFrame representation
   */
  public void startGame() {
    this.setVisible(true);
    this.setLayout(new GridLayout(boardSize, boardSize));
    this.setSize(400, 400); //values can be changed
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /*
   * Method to create visual representation of board
   */
  public void createGrid() {
    //create colors to alternate for our chess table look
    Color c1 = new Color(235,235, 208);
    Color c2 = new Color(119, 148, 85);
    boolean is_c2 = false;

    //initialize visual rep instance variable
    table = new JLabel[boardSize][boardSize];

    //fill in table with JLabels and set their bakcgrounds for visuals
    for (int row = 0; row < table.length; row++) {
      for (int col = 0; col < table[0].length; col++) {
        table[row][col] = new JLabel();
        table[row][col].setHorizontalAlignment(SwingConstants.CENTER);

        table[row][col].setSize(50, 50);

        table[row][col].setOpaque(true);

        if (is_c2) {
          table[row][col].setBackground(c2);
        } else {
          table[row][col].setBackground(c1);
        }

        this.add(table[row][col]);
        is_c2 = !is_c2;
      }
      is_c2 = !is_c2;
    }

  }

  /*
   * Method to solve NQueens problem
   */
  public void solveNQueens() {
    backtrack(0, new HashSet<Integer>(), new HashSet<Integer>(), new HashSet<Integer>());
  }

  /*
   * Backtracking method to solve NQueens
   * row used to designate current row of table, must be in [0, boardSize]
   * blockedCols the columns that already have queens
   * blockedUpDiags the diagonals that have queens (left to right)
   * blockedDownDiags the diagonals that have queens (right to left)
   */
  public boolean backtrack(
    int row,
    HashSet<Integer> blockedCols,
    HashSet<Integer> blockedUpDiags,
    HashSet<Integer> blockedDownDiags) {

    //base case where puzzle is solved, return true
    if (row == boardSize) {
      printBoard();
      return true;
    }

    //try inserting a queen at each tile in current row
    for (int col = 0; col < boardSize; col++) {

      //try catch used to slow down program for proper visualization experience
      try {
        Thread.sleep(0);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      //if valid we add queen to our multiple representations
      if (isValid(row, col, blockedCols, blockedUpDiags, blockedDownDiags)) {

        addQueen(row, col, blockedCols,
        blockedUpDiags, blockedDownDiags);

        board[row][col] = 1;
        table[row][col].setBackground(Color.ORANGE);
        table[row][col].setText("Queen");

        //if ultimately valid return true
        if (backtrack(row + 1, blockedCols, blockedUpDiags, blockedDownDiags)) {
          return true;
        }

        //else remove queen from all representations
        board[row][col] = 0;
        table[row][col].setBackground(Color.RED);
        removeQueen(row, col, blockedCols, blockedUpDiags, blockedDownDiags);
        table[row][col].setText("");
      }
    }

    //no valid representation, return false
    return false;
  }

  /*
   * Method to add queen to our HashSet representations of blocked tiles
   * row and col used to index position on board
   * blockedCols the columns that already have queens
   * blockedUpDiags the diagonals that have queens (left to right)
   * blockedDownDiags the diagonals that have queens (right to left)
   */
  public void addQueen(
    int row,
    int col,
    HashSet<Integer> blockedCols,
    HashSet<Integer> blockedUpDiags,
    HashSet<Integer> blockedDownDiags) {

    blockedCols.add(col);
    blockedUpDiags.add(row + col);
    blockedDownDiags.add(row - col);

  }

  /*
   * Method to remove queen from our HashSet representations of blocked tiles
   * row and col used to index position on board
   * blockedCols the columns that already have queens
   * blockedUpDiags the diagonals that have queens (left to right)
   * blockedDownDiags the diagonals that have queens (right to left)
   */
  public void removeQueen(
    int row,
    int col,
    HashSet<Integer> blockedCols,
    HashSet<Integer> blockedUpDiags,
    HashSet<Integer> blockedDownDiags) {

    blockedCols.remove(col);
    blockedUpDiags.remove(row + col);
    blockedDownDiags.remove(row - col);
  }

  /*
   * Method to check if queen insertion is valid
   * row and col used to index position on board
   * blockedCols the columns that already have queens
   * blockedUpDiags the diagonals that have queens (left to right)
   * blockedDownDiags the diagonals that have queens (right to left)
   */
  public boolean isValid(
    int row,
    int col,
    HashSet<Integer> blockedCols,
    HashSet<Integer> blockedUpDiags,
    HashSet<Integer> blockedDownDiags) {

    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (blockedCols.contains(col)) return false;
    if (blockedUpDiags.contains(row + col)) return false;
    if (blockedDownDiags.contains(row - col)) return false;

    return true;
  }

  /*
   * Method to print board out to terminal
   */
  public void printBoard(){

    for(int i = 0; i < boardSize; ++i){
      for(int j = 0; j < boardSize; ++j){
        System.out.print(board[i][j] + " | ");
      }
      System.out.println();
    }

  }

  /*
   * Method to print problem overveiw out to terminal
   */
  public static void printInfo() {
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("N-Queens visualizer designed by Alex Atherton 9/18/22");
    System.out.println();
    System.out.println("This program is used to visualize the backtracking process for the leet code n-queens question");
    System.out.println();
    System.out.println("When inputting an integer for the program consider that any integer < 4 will result in the program running so quickly that the backtracking process cannot be visualized. Similarly larger than 20 will take too long.");
    System.out.println();
    System.out.println("On that note, any number too large will result in a very long run time due to the nature of backtracking");
    System.out.println();
    System.out.println("It is reccomended to start by inputting 8 and then slowly incrementing / decrementing on later calls");
    System.out.println();
    System.out.println("The chess board is checkered in shades of green: tiles with queens will be orange and have text labeling them queen");
    System.out.println();
    System.out.println("A tile where a queen has been tried but later removed will be colored red");
    System.out.println();
    System.out.println();
    System.out.println();
  }

  /*
   * Main method creates a new instance of NQueens class
   * Calling main method will create GUI and then solve out puzzle
   * User will be asked to enter n for the nxn board dimensions
   * If n too small or too large, n will be assumed to be 8
   * Final board will be printed out
   */
  public static void main(String[] args) {
    printInfo();
    int option = 8;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter positive integer in [4, 20] for board dimensions:");
    int size = sc.nextInt();
    if (size < 4 || size > 20) size = option;
    NQueens sim = new NQueens(size);

  }
}
