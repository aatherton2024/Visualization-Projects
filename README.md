# Visualization-Projects

This repository contains backtracking visualization to the algorithmic solutions to:
1. Solving a sudoku puzzle
2. Solving the LeetCode NQueens question

All programs are written in java and utilize the swing tool to create a graphical user interface

SolveSudoku.java:
Running program from command line will open GUI and solve out. Additionally the puzzle before and after
will be printed out to the terminal.

NQueens.java:
Running program from command line will print out a set of basic instructions and prompt the user to
input an integer. This integer, n, will set the nxn dimensions of the "chess" board and will dictate
the number of queens that must fit on the board. The program will then launch into a separate visual
window similar to SolveSudoku and will print out final solution. The user should input a positive integer
in range [4, 20] else program will override and run with n being 8.
