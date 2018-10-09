package com.example.npc.baocaomonmahoavamatma.model;

import java.util.ArrayList;

public class PlayfairEncryption {
    private char arrKey[][];

    public char[][] getKey(){
        return arrKey;
    }

    public PlayfairEncryption() {
        arrKey = new char[5][5];
        setKey(new String());
    }

    public PlayfairEncryption(String skey) {
        arrKey = new char[5][5];
        setKey(skey);
    }

    public void setKey(String sKey) {
        ArrayList<Character> key = new ArrayList<>();
        // create key
        sKey = sKey.replace(" ","");
        sKey = sKey.toUpperCase();
        for (int i = 0; i < sKey.length(); i++) {
            if (!key.contains(sKey.charAt(i)))
                key.add(sKey.charAt(i));
        }
        for (byte i = 'A'; i <= 'Z'; i++) {
            if (!key.contains((char) i))
                key.add((char) i);
        }

        key.remove(new Character('J'));

        int i = 0;
        for (char c : key) {
            arrKey[i / 5][i % 5] = c;
            i++;
        }
    }

    public String Encrypt(String plainText) {
        String cipherText = "";

        plainText = plainText.toUpperCase();
        plainText = plainText.replace(" ","");
        ArrayList<char[]> splitedPlainText = splitPlainText(plainText);
        int x0 = -1, y0 = -1, x1 = -1, y1 = -1;
        for (char[] temp : splitedPlainText) {
            for (int i = 0; i < arrKey.length; i++) {
                for (int j = 0; j < arrKey[i].length; j++) {
                    if (temp[0] == arrKey[i][j]) {
                        x0 = i;
                        y0 = j;
                    } else if (temp[1] == arrKey[i][j]) {
                        x1 = i;
                        y1 = j;
                    }
                }
            }
            // on the same row
            if (x0 == x1) {
                char temp1[] = new char[2];
                temp1[0] = arrKey[x0][(y0 + 1) % arrKey.length];
                temp1[1] = arrKey[x0][(y1 + 1) % arrKey.length];
                cipherText += new String(temp1);
                continue;
            }
            // on the same column
            if (y0 == y1) {
                char temp1[] = new char[2];
                temp1[0] = arrKey[(x0 + 1) % arrKey.length][y0];
                temp1[1] = arrKey[(x1 + 1) % arrKey.length][y1];
                cipherText += new String(temp1);
                continue;
            }
            // other case
            char temp1[] = new char[2];
            temp1[0] = arrKey[x0][y1];
            temp1[1] = arrKey[x1][y0];
            cipherText += new String(temp1);
        }

        return cipherText;
    }

    public String Decrypt(String cipherText) {
        String plainText = "";

        ArrayList<char[]> splitedCipherText = splitPlainText(cipherText);
        int x0 = -1, y0 = -1, x1 = -1, y1 = -1;
        for (char[] temp : splitedCipherText) {
            for (int i = 0; i < arrKey.length; i++) {
                for (int j = 0; j < arrKey[i].length; j++) {
                    if (temp[0] == arrKey[i][j]) {
                        x0 = i;
                        y0 = j;
                    } else if (temp[1] == arrKey[i][j]) {
                        x1 = i;
                        y1 = j;
                    }
                }
            }
            // on the same row
            if (x0 == x1) {
                char temp1[] = new char[2];
                temp1[0]=arrKey[x0][(y0-1)%arrKey.length];
                temp1[1]=arrKey[x0][(y1-1)%arrKey.length];
                plainText+=new String(temp1);
                continue;
            }
            // on the same column
            if(y0==y1) {
                char temp1[] = new char[2];
                temp1[0]=arrKey[(x0-1)%arrKey.length][y0];
                temp1[1]=arrKey[(x1-1)%arrKey.length][y1];
                plainText+=new String(temp1);
                continue;
            }
            // other case
            char temp1[] = new char[2];
            temp1[0]=arrKey[x0][y1];
            temp1[1]=arrKey[x1][y0];
            plainText+=new String(temp1);
        }

        return plainText;
    }
    private ArrayList<char[]> splitPlainText(String plainText) {
        ArrayList<char[]> splitedPlainText = new ArrayList<>();

        int i = 0;
        for (; i < plainText.length() - 1; i++) {
            char temp[] = new char[2];
            temp[0] = plainText.charAt(i);
            temp[1] = plainText.charAt(i + 1);
            if (temp[0] == temp[1]) {
                temp[1] = 'X';
            } else {
                i++;
            }
            splitedPlainText.add(temp);
        }

        if (i < plainText.length()) {
            char temp[] = new char[2];
            temp[0] = plainText.charAt(i);
            temp[1] = 'Q';
            splitedPlainText.add(temp);
        }
        return splitedPlainText;
    }

    public void show() {
        for (int i = 0; i < arrKey.length; i++) {
            for (int j = 0; j < arrKey[i].length; j++) {
                System.out.print(arrKey[i][j] + " ");
            }
            System.out.println();
        }
    }

}

