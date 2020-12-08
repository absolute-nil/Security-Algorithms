package com.company;

import java.util.Arrays;

public class RSA_Digital_Signature {
//  Implement RSA digital signature.
//  generate alice private key and public key
//  generate bob private key and public key
//  if alice wants to send the message to bob
//  encrypt using alice private key
//  if at receiving end bob can decrypt using alice public key
//  then the algorithm succeeded
// select p and q ( prime numbers )
// calculate n = p*q;
// calculate phi(n) = phi(p)*phi(q) = (p-1)*(q-1)
// 1 < e < phi(n) and gcd(e,phi) = 1
// public key is e and n
// private key e.d = 1 mod phi(n) d -> 0,n
// private d,p,q public e,n
    // convert number to character
    private static char getCharForNumber(int i) {
        return (char)(i + 65);
    }
    // convert letter to number
    private static int getIntForLetter(char ch) {
        return ch - 'a';
    }

    //convert text to array
    public static int[] textToArray(String text){
        text = text.toLowerCase().replace(" ", "_");
        char[] ch = text.toCharArray();
        int[] textArray = new int[text.length()];
        for (int i = 0; i < ch.length; i++) {
            textArray[i] = getIntForLetter(ch[i]);
        }
        return textArray;
    }


    // calculate gcd
    static int gcd(int a, int b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    private static int calculatePublicKey(int phi){
        int e = 1;
        for (int i = 6; i < phi; i++) {
            if(gcd(i,phi) == 1){
                e = i;
                break;
            }
        }
        return e;
    }

    private  static int calculatePrivateKey(int phi, int n){
        int e = calculatePublicKey(phi);
        for (int d = 2; d < n; d++) {
            if(((d*e)%phi) == 1){
                return d;
            }
        }
        return 1;
    }

    private static int[] generateDigitalSignature(String text, int p, int q){
        int n = p*q;
        int phi = (p-1)*(q-1);
        int d = calculatePrivateKey(phi, n);
        int[] textNumArray = textToArray(text);

        int[] signature = new int[textNumArray.length];
        int i =0;
        for (int value: textNumArray){
            int sig = (int) (Math.pow((value), d) % n);
            signature[i] = sig;
            i++;
        }
        System.out.println("Private Key :::" + "{" + "d :" + d + "," + "p :" + p + "q: " + q + "}");
        return signature;
    }

    private static boolean verifySignature(String idToVerify, int[] signature, int publicKey, int n){
        StringBuilder decryptedBuilder = new StringBuilder();

        for (int value : signature) {
            int dec = (int) (Math.pow(value,publicKey) % n);
            decryptedBuilder.append(getCharForNumber((dec % 26)));
        }
        String idDecrypted = decryptedBuilder.toString();

        System.out.println("Decrypted Id ::: " + idDecrypted);

        return (idDecrypted.toLowerCase().equals(idToVerify.toLowerCase()));
    }


    public static void main(String[] args) {
        String id = "NIKHIL";
        int p = 13;
        int q = 5;
        int phi = (p-1)*(q-1);
        int n = p*q;
        int publicKeySender = calculatePublicKey(phi);
        System.out.println("Id of Sender::: " + id);
        int[] signature = generateDigitalSignature(id,p,q);
        System.out.println("DIGITAL SIGNATURE::: " + Arrays.toString(signature));

        System.out.println("### Digital Signature received by the receiver .../ now verifying the signature");

        System.out.println("Public key : {e : "+ publicKeySender + " , n : " + n + " }" );
        boolean isValidSignature = verifySignature(id,signature,publicKeySender,n);

        System.out.println("Signature is : " + isValidSignature);
    }
}
