/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueenga;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Safat 12101066
 */
public class NQueenGA {

    int popSize = 50;
    int boardSize = 9 + 1;
    boolean board[][] = new boolean[boardSize][boardSize];
    String[] pop = new String[popSize];
    int[] conflicts = new int[popSize];
    int stringSize = 9;
    int maxPos1 = -1, maxPos2 = -1;

    private String getRowString() {

        //build a linear String retuen it
        String row = "";
        for (int i = 0; i < stringSize; i++) {
            int td = 1 + (int) (Math.random() * ((9 - 1) + 1));
            row += ("" + td);
        }
        print(row, "\nrows:\n");
        return row;
    }

    private void buildPop() {
        //make string put in popu
        for (int i = 0; i < pop.length; i++) {
            pop[i] = getRowString();
        }

    }

    private void printPop() {
        System.out.println("\nPopu:  ");
        for (int i = 0; i < pop.length; i++) {
            System.out.print(pop[i]+"--");

        }
        System.out.println("end of Popu  \n");
    }

    private void getAllFitness() {
        //fill the conflicts array with fitness values return least conflict value
        int temp_fitness = 999, t;
        for (int i = 0; i < pop.length; i++) {
            t = getFitness(pop[i]);
            conflicts[i] = t;
            if (t < temp_fitness) {
                temp_fitness = t;
//                System.out.println("temp_fit: " + t);
            }
        }

//        return temp_fitness;//returns least conflict value 
    }

    private int getFitness(String str) {
        //get each string conflicts
        int conflicts = 0;
//        System.out.println("c is: " + c);
//        String str = pop[c];
        for (boolean[] u : board) {
            Arrays.fill(u, false);
        }

        buildBoard(str);
//        printBoard("");
        for (int j = 0; j < str.length(); j++) {

            int cRow = Integer.parseInt("" + str.charAt(j));
            int cCol = j + 1;

            //vert COL==fixed
            for (int i = 1; i < boardSize; i++) {
                if (board[i][cCol]) {
                    if (i == cRow) {
                        conflicts--;
                    }
                    conflicts++;
                }
            }

            //hori row=FIXED
            for (int i = 1; i < boardSize; i++) {
                if (board[cRow][i]) {
                    if (i == cCol) {
                        conflicts--;
                    }
                    conflicts++;
                }
            }

//            rdiag
//            start at i,j use twoloops one to go right and other to left diagonally see copy @#467#
            int ic = cCol;
            int ir = cRow;
            while (ir < boardSize && ic < boardSize) { //see if +1 required remove if error
                if (board[ir][ic]) {
                    if (ic == cCol && ir == cRow) {
                        conflicts--;
                    }
                    conflicts++;
                }
                ir++;
                ic++;
            }
            ir = cRow;
            ic = cCol;
            while (ir > 0 && ic < 0) {
                if (board[ir][ic]) {
                    if (ic == cCol && ir == cRow) {
                        conflicts--;
                    }
                    conflicts++;
                }
                ir--;
                ic--;
            }
//            ldiag
//            similar to  rdiag
            ic = cCol;
            ir = cRow;
            while (ir < boardSize && ic > 0) {
                if (board[ir][ic]) {
                    if (ic == cCol && ir == cRow) {
                        conflicts--;
                    }
                    conflicts++;
                }
                ir++;
                ic--;
            }
            ir = cRow;
            ic = cCol;
            while (ir > 0 && ic < boardSize) {
                if (board[ir][ic]) {
                    if (ic == cCol && ir == cRow) {
                        conflicts--;
                    }
                    conflicts++;
                }
                ir--;
                ic++;
            }
        }
        return conflicts;
    }

    private void buildBoard(String row_values) {
        for (int i = 1; i < row_values.length(); i++) {
//            System.out.println("boardSize: " + board.length);
            board[Integer.parseInt("" + row_values.charAt(i))][i] = true;
        }
    }

    private void printBoard(String mssg) {
        System.out.print("\n" + mssg + "\n");
        for (boolean[] u : board) {
            for (boolean elem : u) {
                // Your individual element
                if (elem) {
                    System.out.print("|Q|");
                } else {
                    System.out.print("|-|");
                }
            }
            System.out.print("\n");
        }
    }

    private void print(String tp, String mssg) {
        System.out.print(mssg + ": " + tp);
    }

    private void getLeastTwoConflicts() {
        int max = -1;
        int maxPos = -1;
        for (int i = 0; i < conflicts.length; i++) {
            if (conflicts[i] > max) {
                max = conflicts[i];
                maxPos = i;
            }
        }
        
        int max2 = -1;
        int maxPos2 = -1;
        for (int i = 0; i < conflicts.length; i++) {
            if (conflicts[i] > max2 && i != maxPos) {
                max2 = conflicts[i];
                maxPos2 = i;
            }
        }
        this.maxPos1 = maxPos;
        this.maxPos2 = maxPos2;
    }

    private String crossOver(int p1, int p2) {
        String par1 = pop[p1];
        String par2 = pop[p2];
        String offspring = "";

        for (int i = 0; i < stringSize; i++) {
            if (i < (stringSize / 2)) {
                offspring = offspring + Integer.parseInt("" + par1.charAt(i));
            } else {
                offspring = offspring + Integer.parseInt("" + par2.charAt(i));
            }
        }
        return offspring;
    }

    private void printConflicts() {
        for (int z : this.conflicts) {
            System.out.print("Conf: "+""+z+"--");
        }
    }

    private static String mutate(String str) {
        return str;
    }

    private int getHighestConflict() {
        
        int max = -1;
        int maxPositon=-1;
        printConflicts();
        for (int i = 0; i < conflicts.length; i++) {
//            System.out.println(" # "+conflicts[i]+ " &&& "+max);
            if (conflicts[i] > max) {
                max = conflicts[i];
                maxPositon=i;
            }
        }
        return maxPositon;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        boolean run = true;
        NQueenGA obj = new NQueenGA();
        obj.buildPop();
        obj.printPop();
        int runner=0;
        while (runner<obj.popSize+10) {
//             run=false;

            obj.getAllFitness();
            obj.getLeastTwoConflicts();
            System.out.println("least1 ="+obj.pop[obj.maxPos1]+" &&& least2="+obj.pop[obj.maxPos2]);
            String currentBestStr
                    = obj.crossOver(obj.maxPos1, obj.maxPos2);
            currentBestStr = mutate(currentBestStr);
            System.out.println("CB: "+currentBestStr);
            obj.buildBoard(currentBestStr);
            int result = obj.getFitness(currentBestStr);
            System.out.println("The result is: " + result);

            System.out.println("Result:  " + result);
            if (result == 0) {
                System.out.println("Solution is");
            } else {
                
                int high = obj.getHighestConflict();
                obj.print(""+high,"\nthe highest conflict");
                obj.printPop();
                //0 + (int) (Math.random() * ((obj.popSize - 0) + 0))
                obj.pop[high] = currentBestStr;
                obj.printPop();
                System.out.println("\nBest match is");
            }
            obj.printBoard("Printing Final Borad");
            if (result == 0) {
                run = false;
            }

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
       runner++;
        }
    }
}
