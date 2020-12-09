package com.company;

import java.util.HashMap;

public class DiffieHellman {

    private static boolean isPrime(int n) {
        int i;
        for(i=2;i<=Math.sqrt(n);i++){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }


    private static HashMap<String, Integer> initializeGeneratorAndPrime(){
        HashMap<String, Integer> returnMap = new HashMap<>();

        int p;
        while (true) {
            p = (int) (Math.random() * (20) + 5);
            if(isPrime(p)){
                System.out.println(".../...Generated prime p : "+p + " .../...");
                break;
            }
        }

        returnMap.put("p",p);

        int G = (int) (Math.random() * (20) + 5);
        returnMap.put("G",G);

        System.out.println(".../... Generated Generator G : " + G + " .../...");
        return returnMap;
    }

    private static int generatePrivateKey(){
        return (int) (Math.random() * (20) + 5);
    }

    public static int generatePublicKey(int p, int G, int pKey){
        return ((int)Math.pow(G,pKey) % p);
    }

    public static int generateSharedKey(int p, int pubKey, int pKey){
        return ((int)Math.pow(pubKey,pKey) % p);
    }

    public static void main(String[] args) {
        HashMap<String, Integer> generatorAndPrime = initializeGeneratorAndPrime();
        int G = generatorAndPrime.get("G");
        int p = generatorAndPrime.get("p");

        System.out.println("Generating Private Key For Alice");
        int a = generatePrivateKey();
        System.out.println(".../...Alice private Key a : " + a + " .../...");
        System.out.println("Generating Private Key For Bob");
        int b = generatePrivateKey();
        System.out.println(".../...Alice private Key b : " + b + " .../...");

        System.out.println("Alice::: Generating public key for bob");
        int alicePubKey = generatePublicKey(p,G, a);
        System.out.println(".../...Bob::: received public key: "+ alicePubKey + " .../...");

        System.out.println("Bob::: Generating public key for Alice");
        int bobPubKey = generatePublicKey(p,G, b);
        System.out.println(".../...Alice::: received public key: "+ bobPubKey + " .../...");

        System.out.println("Alice::: Generating shared secret key");
        int sharedKeyAlice = generateSharedKey(p,bobPubKey,a);
        System.out.println(".../...Alice::: shared secret key : "+ sharedKeyAlice + " .../...");

        System.out.println("Bob::: Generating shared secret key");
        int sharedKeyBob = generateSharedKey(p,alicePubKey,b);
        System.out.println(".../...Bob::: shared secret key : "+ sharedKeyBob + " .../...");

        System.out.println(".../...Both Keys are same? " + (sharedKeyAlice == sharedKeyBob) + " .../...");
    }
}
