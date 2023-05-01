import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

public class Modern_Symmetric_Cipher_and_Decipher {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message to use DES encryption/decryption:");
        String message = scanner.nextLine();
        scanner.close();

        String key = "6D59713373367639";
        
        List<String[]> keys = generateKeys(key);

        List<String> rkb = Arrays.asList(keys.get(0));
        List<String> rk = Arrays.asList(keys.get(1));

        String[] rkbArray = rkb.toArray(new String[0]);
        String[] rkArray = rk.toArray(new String[0]);
        
        System.out.println("Encryption");
        String cipher_text = processText(message, rkbArray, rkArray, true);
        System.out.println("Cipher Text : " + cipher_text);

        System.out.println("Decryption");
        String text = processText(cipher_text, rkbArray, rkArray, false);
        System.out.println("Plain Text : " + text);

    }

public static String processText(String inputText, String[] rkbArray, String[] rkArray, boolean isEncrypt) {
        int blockSize = isEncrypt ? 8 : 16;
        StringBuilder outputText = new StringBuilder();
        int inputLength = inputText.length();
        int blockCount = (int) Math.ceil((double) inputLength / blockSize);
    
        if (!isEncrypt) {
            Collections.reverse(Arrays.asList(rkbArray));
            Collections.reverse(Arrays.asList(rkArray));
        }
    
        for (int i = 0; i < blockCount; i++) {
            int startIndex = i * blockSize;
            int endIndex = Math.min((i + 1) * blockSize, inputLength);
    
            String blockText = inputText.substring(startIndex, endIndex);
    
            // Pad the blockText with spaces if it's less than 8 characters
            if (isEncrypt && blockText.length() < 8) {
                blockText = String.format("%-8s", blockText);
            }
    
            if (isEncrypt) {
                String hexText = plaintextToHex(blockText);
                String encryptedBlock = bin2hex(encrypt(hexText, rkbArray, rkArray));
                outputText.append(encryptedBlock);
            } else {
                String decryptedBlock = hexToPlaintext(bin2hex(encrypt(blockText, rkbArray, rkArray)));
                outputText.append(decryptedBlock);
            }
        }
    
        return outputText.toString();
    }

public static List<String[]> generateKeys(String key) {
        // Key generation
        // --hex to binary
        key = hex2bin(key);

        // --parity bit drop table
        int[] keyp = {57, 49, 41, 33, 25, 17, 9,
                1, 58, 50, 42, 34, 26, 18,
                10, 2, 59, 51, 43, 35, 27,
                19, 11, 3, 60, 52, 44, 36,
                63, 55, 47, 39, 31, 23, 15,
                7, 62, 54, 46, 38, 30, 22,
                14, 6, 61, 53, 45, 37, 29,
                21, 13, 5, 28, 20, 12, 4};

        // getting 56 bit key from 64 bit using the parity bits
        key = permute(key, keyp, 56);

        // Number of bit shifts
        int[] shift_table = {1, 1, 2, 2,
                2, 2, 2, 2,
                1, 2, 2, 2,
                2, 2, 2, 1};

        // Key- Compression Table : Compression of key from 56 bits to 48 bits
        int[] key_comp = {14, 17, 11, 24, 1, 5,
                3, 28, 15, 6, 21, 10,
                23, 19, 12, 4, 26, 8,
                16, 7, 27, 20, 13, 2,
                41, 52, 31, 37, 47, 55,
                30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53,
                46, 42, 50, 36, 29, 32};

        // Splitting
        String left = key.substring(0, 28);    // rkb for RoundKeys in binary
        String right = key.substring(28, 56);  // rk for RoundKeys in hexadecimal

        List<String> rkb = new ArrayList<>();
        List<String> rk = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            // Shifting the bits by nth shifts by checking from shift table
            left = shiftLeft(left, shift_table[i]);
            right = shiftLeft(right, shift_table[i]);

            // Combination of left and right string
            String combine_str = left + right;

            // Compression of key from 56 to 48 bits
            String round_key = permute(combine_str, key_comp, 48);

            rkb.add(round_key);
            rk.add(bin2hex(round_key));
        }

        List<String[]> keys = new ArrayList<>();
        keys.add(rkb.toArray(new String[0]));
        keys.add(rk.toArray(new String[0]));
        return keys;
    }

public static String plaintextToHex(String plaintext) {    //convert Plaintext to Hexadecimal
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            int intValue = (int) c;
            String hexString = Integer.toHexString(intValue);
            hex.append(hexString);
        }
        return hex.toString().toUpperCase();
    }

public static String hexToPlaintext(String hex) {   //convert Hexadecimal back to plaintext
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String hexPair = hex.substring(i, i + 2);
            int intValue = Integer.parseInt(hexPair, 16);
            char c = (char) intValue;
            plaintext.append(c);
        }
        return plaintext.toString();
    }

public static String hex2bin(String hex) {  //hexadecimal to binary function
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < hex.length(); i++) {
        int intValue = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);
        String binaryString = Integer.toBinaryString(intValue);
        binary.append(String.format("%4s", binaryString).replace(' ', '0'));
    }
    return binary.toString();
}

public static String bin2hex(String bin) { //Binary to hexadecimal function
    StringBuilder hex = new StringBuilder();
    for (int i = 0; i < bin.length(); i += 4) {
        String chunk = bin.substring(i, i + 4);
        int intValue = Integer.parseInt(chunk, 2);
        hex.append(Integer.toHexString(intValue).toUpperCase());
    }
    return hex.toString();
}

    public static int bin2dec(int binary) { //Binary to Decimal function
        int decimal = 0;
        int i = 0;
        while (binary != 0) {
            int dec = binary % 10;
            decimal += dec * Math.pow(2, i);
            binary = binary / 10;
            i++;
        }
        return decimal;
    }

    public static String dec2bin(int num) {  //Decimal to Binary function
        String res = Integer.toBinaryString(num);
        if (res.length() % 4 != 0) {
            int div = res.length() / 4;
            int counter = (4 * (div + 1)) - res.length();
            for (int i = 0; i < counter; i++) {
                res = "0" + res;
            }
        }
        return res;
    }

    /**
        This function performs a permutation on a given input string 'input' based on a specified permutation table 'table' of length 'n'.
        It constructs a new string by rearranging the characters of 'input' according to the positions defined in the permutation table.
        The resulting permuted string is returned.
    */

    public static String permute(String input, int[] table, int n) {
        StringBuilder permutation = new StringBuilder();
        for (int i = 0; i < n; i++) {
        permutation.append(input.charAt(table[i] - 1));
        }
        return permutation.toString();
    }

    /**
        This function performs a left shift operation on a given binary string 'k' a specified number of times 'nthShifts'.
        It moves each bit in the string one position to the left, and the leftmost bit is wrapped around to the rightmost position.
        The resulting shifted string is returned.
    */

    public static String shiftLeft(String k, int nthShifts) {
        String s = "";
        for (int i = 0; i < nthShifts; i++) {
            for (int j = 1; j < k.length(); j++) {
                s = s + k.charAt(j);
            }
            s = s + k.charAt(0);
            k = s;
            s = "";
        }
        return k;
    }

    /**
        The provided Java function, "xor", performs an XOR (exclusive OR) operation on two binary strings, 'a' and 'b'. 
        It iterates through each bit in the input strings, comparing the corresponding bits from both strings. 
        If the bits are the same, it appends "0" to a StringBuilder object named 'ans'. Otherwise, if the bits are 
        different, it appends "1" to 'ans'. Finally, it returns the resulting binary string by converting the 
        StringBuilder object to a string using the 'toString()' method.
    */

    public static String xor(String a, String b) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                ans.append("0");
            } else {
                ans.append("1");
            }
        }
        return ans.toString();
    }


    /**
        This function performs encryption using the DES (Data Encryption Standard) algorithm on a given plaintext 'pt' using a series of operations.
        It takes the plaintext, a set of round keys 'rkb' and 'rk', and returns the resulting ciphertext.
        @param pt The plaintext to be encrypted.
        @param rkb The array of round keys (backward keys) used in the encryption process.
        @param rk The array of round keys used in the encryption process.
        @return The ciphertext generated from the plaintext.
    */

    public static String encrypt(String pt, String[] rkb, String[] rk) {

        int[] INITIAL_PERM = {      //represents the initial permutation used in the DES (Data Encryption Standard) algorithm.
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
        };
        
        int[] FINAL_PERM = {        //represents the final permutation used in the DES (Data Encryption Standard) algorithm.
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
        };
        
        int[] EXP_D = {     //represents the expansion permutation used in the DES (Data Encryption Standard) algorithm.
            32, 1, 2, 3, 4, 5, 4, 5,
            6, 7, 8, 9, 8, 9, 10, 11,
            12, 13, 12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21, 20, 21,
            22, 23, 24, 25, 24, 25, 26, 27,
            28, 29, 28, 29, 30, 31, 32, 1
        };
        
        int[][][] SBOX = {      //array represents the substitution boxes (S-boxes) used in the DES (Data Encryption Standard) algorithm.
            {
                {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {
                {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            {
                {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            {
                {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            {
                {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            {
                {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
        };
        
        int[] PER = {       //represents the permutation used in the DES (Data Encryption Standard) algorithm.
               16,  7, 20, 21,
               29, 12, 28, 17,
               1, 15, 23, 26,
               5, 18, 31, 10,
               2,  8, 24, 14,
               32, 27,  3,  9,
               19, 13, 30,  6,
               22, 11,  4, 25
        };

        pt = hex2bin(pt);

        // Initial Permutation
        pt = permute(pt, INITIAL_PERM, 64);
        System.out.println("After initial permutation " + bin2hex(pt));

        // Splitting
        String left = pt.substring(0, 32);
        String right = pt.substring(32, 64);

        for (int i = 0; i < 16; i++) {
            // Expansion of EXP_D: Expands the 32 bits data into 48 bits
            String rightExpanded = permute(right, EXP_D, 48);

            // XOR RoundKey[i] and right_expanded
            String xorX = xor(rightExpanded, rkb[i]);

            // Substitutes the value from s-box table by calculating row and column
            StringBuilder sboxStrBuilder = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                int row = bin2dec(Integer.parseInt(xorX.charAt(j * 6) + "" + xorX.charAt(j * 6 + 5), 2));
                int col = bin2dec(Integer.parseInt(xorX.substring(j * 6 + 1, j * 6 + 5), 2));
                int val = SBOX[j][row][col];
                sboxStrBuilder.append(dec2bin(val));
            }
            String sboxStr = sboxStrBuilder.toString();

            // Straight D-box: After substituting rearranging the bits
            sboxStr = permute(sboxStr, PER, 32);

            // XOR left and sbox_str
            String result = xor(left, sboxStr);
            left = result;

            // Swap
            if (i != 15) {
                String temp = left;
                left = right;
                right = temp;
            }
            System.out.println("Round " + (i + 1) + ":" + bin2hex(left) + " " + bin2hex(right) + " " + rk[i]);
        }

        // Combine
        String combine = left + right;

        // Final permutation
        String cipherText = permute(combine, FINAL_PERM, 64);
        return cipherText;
    }
}

