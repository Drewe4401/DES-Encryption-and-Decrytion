# DES Encryption and Decrytion

This repository contains a Java implementation of the XOR operation and the Data Encryption Standard (DES) algorithm. The program performs encryption on a given plaintext using a series of operations, including initial and final permutations, expansion, substitution, and straight permutation.

## Features

* XOR operation on two binary strings
* DES encryption algorithm
* Generate keys function

## Usage

The main components of the program are two Methods:

* public static String xor(String a, String b)
* public static String encrypt(String pt, String[] rkb, String[] rk)

## XOR

The provided Java function, "xor", performs an XOR (exclusive OR) operation on two binary strings, 'a' and 'b'. 
It iterates through each bit in the input strings, comparing the corresponding bits from both strings. 
If the bits are the same, it appends "0" to a StringBuilder object named 'ans'. Otherwise, if the bits are 
different, it appends "1" to 'ans'. Finally, it returns the resulting binary string by converting the 
StringBuilder object to a string using the 'toString()' method.

## DES Encryption

The encrypt function takes a plaintext pt, a set of round keys rkb (backward keys), and rk (forward keys) and returns the resulting ciphertext. The key generation process is not included in the code, so you'll need to generate the round keys beforehand.





