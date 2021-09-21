package me.einjojo.playfair.util;


/**
 * @author - Ein_Jojo
 *
 * Created at 10.09.21 22:16
 */


public class Matrix {

    private final char[][] matrix;

    public Matrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix(int rows, int cols) {
        this.matrix = new char[rows][cols];
    }

    public void fill() {
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = '-';
            }
        }
    }

    public void fill(String text) {

        System.out.println("[DEBUG] Filling matrix with '" + text + "' ("+ text.length() +")");


        int k=0;
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = text.charAt(k);
                k++;
            }
        }
    }

    public void print() {
        for (char[] row : this.matrix) {
            for (char c : row) {
                System.out.print(c + "  ");
            }
            System.out.println();
        }
    }

    /**
     *
     * @param search char
     * @return Returns an Array with the row and column of the char
     */
    public int[] getIndex(char search) {
        int[] result = new int[2];
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[0].length ; j++) {
                if (matrix[i][j] == search) {
                    result[0] = i; result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    public char charAt(int row, int col) {
        return matrix[row][col];
    }

    public char getNextHorizontalChar(int row, int col) {
        int c = ++col % matrix[0].length;

        return matrix[row][c];
    }

    public char getNextVerticalChar(int row, int col) {
        int r = ++row % matrix.length;
        return matrix[r][col];
    }

    public char getPreviousHorizontalChar(int row, int col) {
        int c = (col-1 == -1) ? matrix[0].length-1 : col-1;
        return matrix[row][c];
    }

    public char getPreviousVerticalChar(int row, int col) {
        int r = (row-1 == -1) ? matrix.length-1 : row-1;
        return matrix[r][col];
    }

}
