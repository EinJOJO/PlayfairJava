package me.einjojo.playfair.util;

import java.util.ArrayList;

/**
 * @author - Ein_Jojo
 *
 * Created at 10.09.21 22:10
 */

public class Playfair {

    private Matrix matrix;
    private String key;
    private final String regex = "^[a-zA-Z]+$";

    public Playfair(String key) {
        this.key = key.toLowerCase();
        createMatrix();
    }

    private void createMatrix() {
        this.matrix = new Matrix(5,5);
        String key = this.key.toLowerCase();
        if (!key.matches(regex)) {
            throw new IllegalArgumentException("Only letters from A to Z");
        }
        key = key.replace("j", "i");
        String text = key + "abcdefghiklmnopqrstuvwxyz";

        ArrayList<Character> arrayList = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!arrayList.contains(c)) {
                arrayList.add(c);
            }
        }

        //Fill the matrix
        String string = arrayList.toString()
                .replace(" ", "")
                .replace(",", "")
                .replace("[","")
                .replace("]","");
        matrix.fill(string);

    }

    public void printMatrix() {
        matrix.print();
    }

    public void setKey(String key) {
        this.key = key;
        createMatrix();
    }

    public String encrypt(String text) {
        String encryption = "";

        String string = text.toLowerCase().replace(" ", "").replace("j","i");
        if (!string.matches(regex)) {
            throw new IllegalArgumentException("Only letters from A to Z");
        }

        //Create substring
        String[] stringArray = splitString(string);

        //Encrypt it
        for (String block: stringArray) {
            char c1 = block.charAt(0); char c2 = block.charAt(1);
            int[] loc1 = matrix.getIndex(c1); int[] loc2 = matrix.getIndex(c2);
            int rule = getReplaceRule(loc1, loc2);

            char e1; char e2;
            if(rule == 1) {
                e1 = matrix.getNextHorizontalChar(loc1[0], loc1[1]);
                e2 = matrix.getNextHorizontalChar(loc2[0], loc2[1]);

            } else if (rule == 2) {
                e1 = matrix.getNextVerticalChar(loc1[0], loc1[1]);
                e2 = matrix.getNextVerticalChar(loc2[0], loc2[1]);

            }
            else {
                int r_1 = loc1[0]; int r_2 = loc2[0];
                int c_1 = loc1[1]; int c_2 = loc2[1];
                e1 = matrix.charAt(r_1, c_2);
                e2 = matrix.charAt(r_2, c_1);
            }

            encryption += String.valueOf(e1) + String.valueOf(e2);
        }

        return encryption.toUpperCase();
    }


    /**
     * @param loc1 location of the first char
     * @param loc2 location of the second char
     * @return integer
     * |1 - Same ROW
     * |2 - Same column
     * |3 - the other thing
     *
     */
    private int getReplaceRule(int[] loc1, int[] loc2) {
        int row1 = loc1[0]; int col1 = loc1[1];
        int row2 = loc2[0]; int col2 = loc2[1];


        if(row1 == row2) {
            return 1;
        } else if (col1 == col2) {
            return 2;
        } else {
            return 3;
        }
    }

    private String[] splitString(String text) {
        ArrayList<String> arrayList = new ArrayList<>();
        String string = "";

        for (int i = 0; i < text.length(); i++) {
            String ch = String.valueOf(text.charAt(i));

            if(string.length() == 0) {
                string += ch;
            } else {
                if (string.equals(ch)) {
                    string += "x";
                    i--;
                } else {
                    string += ch;
                }
                arrayList.add(string);
                string = "";
            }
        }
        if(string.length() == 1) {
            string += "x";
            arrayList.add(string);
        }
        System.out.println("[INFO] Divided string::" + arrayList.toString());
        String[] array = arrayList.stream().toArray(String[]::new);

        return array;
        //return (String[]) ;
    }

    private String[] splitCipher(String cipher) {
        ArrayList<String> arrayList = new ArrayList<>();

        String temp = "";
        for (char c: cipher.toCharArray()) {

            temp += String.valueOf(c);

            if (temp.length() == 2) {
                arrayList.add(temp);
                temp = "";
            }
        }

        return arrayList.stream().toArray(String[]::new);
    }
    public String decrypt(String text) {
        String decryption = "";

        String string = text.toLowerCase().replace(" ", "");
        if (!string.matches(regex)) {
            throw new IllegalArgumentException("Only letters from A to Z");
        }

        //Create substring
        String[] stringArray = splitCipher(string);

        //Decrypt it
        for (String block: stringArray) {
            char c1 = block.charAt(0); char c2 = block.charAt(1);
            int[] loc1 = matrix.getIndex(c1); int[] loc2 = matrix.getIndex(c2);
            int rule = getReplaceRule(loc1, loc2);

            char d1; char d2;
            if(rule == 1) {
                d1 = matrix.getPreviousHorizontalChar(loc1[0], loc1[1]);
                d2 = matrix.getPreviousHorizontalChar(loc2[0], loc2[1]);

            } else if (rule == 2) {
                d1 = matrix.getPreviousVerticalChar(loc1[0], loc1[1]);
                d2 = matrix.getPreviousVerticalChar(loc2[0], loc2[1]);
            }
            else {
                int r_1 = loc1[0]; int r_2 = loc2[0];
                int c_1 = loc1[1]; int c_2 = loc2[1];
                d1 = matrix.charAt(r_1, c_2);
                d2 = matrix.charAt(r_2, c_1);
            }

            decryption += String.valueOf(d1) + String.valueOf(d2);
        }

        return decryption.toUpperCase();
    }

    public Matrix getMatrix() {
        return matrix;
    }
}
