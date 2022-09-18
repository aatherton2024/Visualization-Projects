import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Scanner;

public class NQueens extends JFrame{

  private int boardSize;
  private JLabel[][] table;
  private int[][] board;

  public NQueens(int n) {
    super("N-Queens visualization");
    boardSize = n;
    board = new int[boardSize][boardSize];
    startGame();
    createGrid();
    solveNQueens();
  }

  public void startGame() {
    this.setVisible(true);
    this.setLayout(new GridLayout(boardSize, boardSize));
    this.setSize(400, 400); //values can be changed
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void createGrid() {
    //create colors to alternate for our chess table look
    Color blackColor = new Color(235,235, 208);
    Color whiteColor = new Color(119, 148, 85);
    boolean isWhite = false;

    //place tiles in each slot of our table
    table = new JLabel[boardSize][boardSize];

    for (int row = 0; row < table.length; row++) {
      for (int col = 0; col < table[0].length; col++) {
        table[row][col] = new JLabel();
        table[row][col].setHorizontalAlignment(SwingConstants.CENTER);

        table[row][col].setSize(50, 50);

        table[row][col].setOpaque(true);

        if (isWhite) {
          table[row][col].setBackground(whiteColor);
        } else {
          table[row][col].setBackground(blackColor);
        }

        this.add(table[row][col]);
        isWhite = !isWhite;
      }
      isWhite = !isWhite;
    }

  }

  public void solveNQueens() {
    backtrack(0, new HashSet<Integer>(), new HashSet<Integer>(), new HashSet<Integer>());
  }

  public boolean backtrack(int row,
  HashSet<Integer> blockedCols,
  HashSet<Integer> blockedUpDiags,
  HashSet<Integer> blockedDownDiags)
  {
    if (row == boardSize) {
      printBoard();
      return true;
    }
    for (int col = 0; col < boardSize; col++) {
      try {
        Thread.sleep(0);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (isValid(row, col, blockedCols, blockedUpDiags, blockedDownDiags)) {

        addQueen(row, col, blockedCols,
        blockedUpDiags, blockedDownDiags);

        board[row][col] = 1;
        table[row][col].setBackground(Color.ORANGE);
        table[row][col].setText("Queen");

        if (backtrack(row + 1, blockedCols, blockedUpDiags, blockedDownDiags)) {
          return true;
        }
        board[row][col] = 0;
        table[row][col].setBackground(Color.RED);
        removeQueen(row, col, blockedCols, blockedUpDiags, blockedDownDiags);
        table[row][col].setText("");
      }
    }
    return false;
  }

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


  public void printBoard(){

    for(int i = 0; i < boardSize; ++i){
      for(int j = 0; j < boardSize; ++j){
        System.out.print(board[i][j] + " | ");
      }
      System.out.println();
    }

  }

  public static void printInfo() {
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println("N-Queens visualizer designed by Alex Atherton 9/18/22");
    System.out.println();
    System.out.println("This program is used to visualize the backtracking process for the leet code n-queens question");
    System.out.println();
    System.out.println("When inputting an integer for the program consider that any integer <= 4 will result in the program running so quickly that the backtracking process cannot be visualized");
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

  public static void main(String[] args) {
    printInfo();
    int option = 8;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter positive integer for board dimensions:");
    int size = sc.nextInt();
    if (size < 1 || size > 20) size = option;
    NQueens sim = new NQueens(size);

  }
}
