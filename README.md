# DES Encryption and Decrytion

This Java program performs encryption and decryption using the Data Encryption Standard (DES) algorithm. DES is a symmetric-key algorithm for the encryption of digital data. It encrypts and decrypts messages in blocks of 64 bits using 56-bit keys. The algorithm consists of 16 rounds of encryption and decryption, providing a good level of security.

## Features

* XOR operation on two binary strings
* DES encryption algorithm
* 16 rounds of encryption and decryption
* Generate keys function

## Usage

The main components of the program are three Methods:

* public static String xor(String a, String b)
* public static String encrypt(String pt, String[] rkb, String[] rk)
* public static String processText(String inputText, String[] rkbArray, String[] rkArray, boolean isEncrypt)

## XOR

The provided Java function, "xor", performs an XOR (exclusive OR) operation on two binary strings, 'a' and 'b'. 
It iterates through each bit in the input strings, comparing the corresponding bits from both strings. 
If the bits are the same, it appends "0" to a StringBuilder object named 'ans'. Otherwise, if the bits are 
different, it appends "1" to 'ans'. Finally, it returns the resulting binary string by converting the 
StringBuilder object to a string using the 'toString()' method.

## DES Encryption

This function is an implementation of the DES (Data Encryption Standard) algorithm. DES is a symmetric-key block cipher that takes a plaintext string and a key as inputs and produces a ciphertext string as output.

* Takes three input arguments: plaintext (pt), round key bits (rkb), and round keys (rk).
* Defines the initial permutation (INITIAL_PERM), final permutation (FINAL_PERM), expansion permutation (EXP_D), substitution boxes (SBOX), and * permutation (PER) tables used in the DES algorithm.
* Converts the plaintext from hexadecimal to binary format.
* Performs the initial permutation on the plaintext.
* Splits the permuted plaintext into two halves (left and right).
* Iterates through 16 rounds of encryption:
 * Expands the right half using the expansion permutation (EXP_D).
 * XORs the expanded right half with the current round key bits (rkb[i]).
 * Performs substitution using the S-boxes (SBOX) based on the row and column calculated from the XORed result.
 * Performs the straight permutation (PER) on the substituted result.
 * XORs the left half with the permuted result and updates the left half.
 * Swaps the left and right halves, except for the last round.
* Combines the left and right halves after the 16 rounds.
* Performs the final permutation (FINAL_PERM) on the combined result.
* Returns the ciphertext in hexadecimal format.

## Process Text

* Set the blockSize to 8 if isEncrypt is true (encryption mode), otherwise set it to 16 (decryption mode).
* Initialize an empty StringBuilder named outputText to store the result.
* Calculate the number of blocks needed for the input text based on the blockSize.
* If the function is in decryption mode (isEncrypt is false), reverse the order of elements in rkbArray and rkArray.
* Loop through each block in the input text:
* Get the start and end indices of the block within the input text.
* Extract the block of text from the input text.
* If the function is in encryption mode and the block text length is less than 8, pad the block text with spaces to make it 8 characters long.
* If the function is in encryption mode, convert the plaintext block to a hex string, encrypt the hex string using the encrypt function and the key arrays, and append the encrypted block to the output text.
* If the function is in decryption mode, encrypt the block text using the encrypt function and the key arrays, convert the result to a plaintext string, and append the decrypted block to the output text.
* Return the final output text as a string.





