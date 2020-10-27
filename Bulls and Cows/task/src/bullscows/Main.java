package bullscows;

import java.util.Scanner;

public class Main {
    private static final int EXTENDED_ALPHABET_SIZE = 36;

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner s = new Scanner(System.in);
        String secretLengthRaw = s.next();
        int secretLength;
        try {
            secretLength = Integer.parseInt(secretLengthRaw);
        } catch (NumberFormatException e) {
            System.out.printf("%s isn't a valid number.%n", secretLengthRaw);
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int alphabetSize = s.nextInt();
        if(secretLength > alphabetSize) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", secretLength, alphabetSize);
            return;
        }

        if (alphabetSize > EXTENDED_ALPHABET_SIZE) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).\n");
            return;
        }

        Game game = new Game(secretLength, alphabetSize);
        game.play();
    }
}
