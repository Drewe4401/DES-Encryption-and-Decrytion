# DES Encryption and Decrytion

This Java program performs encryption and decryption using the Data Encryption Standard (DES) algorithm. DES is a symmetric-key algorithm for the encryption of digital data. It encrypts and decrypts messages in blocks of 64 bits using 56-bit keys. The algorithm consists of 16 rounds of encryption and decryption, providing a good level of security.

## Features

* XOR operation on two binary strings
* DES encryption algorithm
* 16 rounds of encryption and decryption
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





