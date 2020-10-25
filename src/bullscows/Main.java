package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int bullCount;
    public static int cowCount;
    public static int turn = 1;
    public static int digits;
    public static int possibleSymbols;
    public static char[] code;
    public static boolean winner = false;
    public static boolean validInput = false;
    public static String alphabet = "0123456789abcdefghijklmnopqrstuvwxyz";


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //checks that the conditions for a valid game are met, otherwise repeats the user input

        while (!validInput) {
            System.out.println("Please, enter the secret code's length:");
            try {
                digits = Integer.parseInt(scan.nextLine());
                if (digits <= 1) {
                    System.out.println("Error: Digits cannot be smaller than 2");
                    continue;
                }
                System.out.println("Input the number of possible symbols in the code");
                possibleSymbols = Integer.parseInt(scan.nextLine());
                if (digits > possibleSymbols) {
                    System.out.println("Error: The code can't be longer than the possible symbols");
                } else if (possibleSymbols < 10) {
                    System.out.println("Error: Code must include at least 10 symbols");
                } else if (possibleSymbols > 36) {
                    System.out.println("Error: Possible symbols must not be longer than 36");
                } else {
                    System.out.println("Okay, let's start a game!");
                    validInput = true;
                    code = generateRandomNumber(digits, possibleSymbols);
                    System.out.println(printSecretCode(possibleSymbols));
                    while (!winner) {
                        System.out.println("Turn " + turn + ".  Answer:");
                        String input = scan.nextLine();
                        if (input.length() != digits) {
                            System.out.println("Error: Invalid guess, enter " + digits + " characters");
                            input = scan.nextLine();
                        }
                        takeTurn(input);
                        printResult();
                        bullCount = 0;
                        cowCount = 0;
                        turn++;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: invalid input format");
            }
        }
    }

    public static void takeTurn(String guess) {
        //converts the input into a char array
        char[] guessArr = guess.toCharArray();
        for (int i = 0; i < guessArr.length; i++) {
            if (guessArr[i] == code[i]) {
                //if the position (i) and value of a character match
                //the bull count gets increased
                bullCount++;
                //otherwise, it checks if that character is found in the rest of the
                //original secret code in any position, and increases the cow count
            } else if (contains(guessArr[i], code)) {
                cowCount++;
            }
        }
    }


    public static void printResult() {
        //uses conditional statements to figure out the result printed to the user
        StringBuilder sb = new StringBuilder("Grade: ");

        if (bullCount == 0 && cowCount == 0) {
            sb.append("None");
        }
        if (bullCount != 0) {
            if (bullCount == 1) {
                sb.append("1 bull");
            } else {
                sb.append(bullCount).append(" bulls");
            }
            if (bullCount == code.length) {
                sb.append("\nCongrats! You guessed the secret code");
                winner = true;
            }
        }
        if (cowCount != 0) {
            if (bullCount > 0) {
                sb.append(" and ");
            }
            if (cowCount == 1) {
                sb.append("1 cow");
            } else {
                sb.append(cowCount).append(" cows");
            }
        }
        System.out.println(sb.toString());
    }


    public static char[] generateRandomNumber(int digits, int possibleSymbols) {
        //uses a Random to generate the required digits
        Random rand = new Random();
        char[] charArr = new char[digits];
        for (int i = 0; i < digits; i++) {
            char temp = alphabet.charAt(rand.nextInt(possibleSymbols));
            while (contains(temp, charArr)) {
                temp = alphabet.charAt(rand.nextInt(possibleSymbols));
            }
            charArr[i] = temp;
        }
        return charArr;
    }

    //builds a string depending on the possible characters input by the user
    public static String printSecretCode(int possibleCharacters) {
        char range = alphabet.charAt(possibleCharacters - 1);
        if (possibleCharacters > 10) {
            return "The code is prepared: " + "*".repeat(code.length) +
                    " (0-9, a-" + range + ")";
        } else {
            return "The code is prepared: " + "*".repeat(code.length) +
                    " (0-9)";
        }
    }

    //simple method to check if a specific int number is contained in an array (useful to generate unique codes)
    public static boolean contains(char c, char[] arr) {
        for (char ch : arr) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }
}