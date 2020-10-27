package bullscows;

import java.util.Scanner;

public class Main {
    private static final int EXTENDED_ALPHABET_SIZE = 36;

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner s = new Scanner(System.in);
        int secretLength = s.nextInt();
        System.out.println("Input the number of possible symbols in the code:");
        int alphabetSize = s.nextInt();
        if(secretLength > alphabetSize) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique symbols.\n", secretLength);
            return;
        }
        if (alphabetSize > EXTENDED_ALPHABET_SIZE) {
            System.out.printf("Error: can't generate a secret number with a alphabet of %d size because there aren't enough unique symbols.\n", alphabetSize);
            return;
        }

        Game game = new Game(secretLength, alphabetSize);
        game.play();
    }
}
