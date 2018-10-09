package com.example.npc.baocaomonmahoavamatma.model;

public class VigenereEncryption {
    private static final int Z =26;

    public static String Encypt(String plainText,String key){
        plainText = plainText.replace(" ","").toUpperCase();
        key = key.replace(" ","").toUpperCase();
        int j = 0;
        int keyi;
        int pi ;
        int ci;
        int keyLengh = key.length();
        String cipherText="";
        for(int i=0;i<plainText.length();i++){
            keyi = key.charAt(j)-'A';
            pi = plainText.charAt(i)-'A';
            ci = (pi+keyi) % VigenereEncryption.Z;
            cipherText+=(char)(ci+'A')+"";
            j++;
            if(j==keyLengh) j = 0;
        }
        return cipherText;
    }
    public static String Decrypt(String cipherText,String key){
        cipherText = cipherText.replace(" ","").toUpperCase();
        key = key.replace(" ","").toUpperCase();
        int j = 0;
        int keyi;
        int pi ;
        int ci;
        int keyLengh = key.length();
        String plainText="";
        for(int i=0;i<cipherText.length();i++){
            keyi = key.charAt(j)-'A';
            ci = cipherText.charAt(i)-'A';
            if(ci >= keyi) pi = (ci-keyi);
            else{
                pi = ci-keyi + VigenereEncryption.Z;
            }
            plainText+=(char)(pi+'A')+"";
            j++;
            if(j==keyLengh) j = 0;
        }
        plainText = plainText.toLowerCase();
        return plainText;
    }

}
