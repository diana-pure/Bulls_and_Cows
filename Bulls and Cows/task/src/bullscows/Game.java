package bullscows;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final int CIPHER_ALPHABET_SIZE = 10;
    private static final char[] alphabetExtended = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final String ANNOUNCE_MESSAGE = "The secret is prepared: %s %s.\n";
    private static final String START_MESSAGE = "Okay, let's start a game!\n";
    private final int secretLength;
    private final int alphabetSize;
    private String secret;

    public Game(int secretLength, int alphabetSize) {
        this.secretLength = secretLength;
        this.alphabetSize = alphabetSize;
        generateSecret();
    }

    private void generateSecret() {
        Random r = new Random(System.nanoTime());
        int rc = r.nextInt(alphabetSize);
        StringBuilder sb = new StringBuilder();
        while (sb.length() < secretLength) {
            if (!sb.toString().contains(String.valueOf(alphabetExtended[rc]))) {
                sb.append(alphabetExtended[rc]);
                if (sb.length() == secretLength) {
                    break;
                }
            }
            rc = r.nextInt(alphabetSize);
        }
        secret = sb.toString();
    }

    public void play() {
        System.out.printf(ANNOUNCE_MESSAGE, "*".repeat(Math.max(0, secretLength)), countSymbolRange());
        System.out.print(START_MESSAGE);

        if (secret.isEmpty()) {
            return;
        }
        int turn = 1;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.printf("Turn %d:%n", turn++);
            String guess = s.next();
            if (guess.length() != secretLength) {
                System.out.printf("Error: enter %d length guess%n", secretLength);
                return;
            }

            if(!areSymbolsInAlphabetRange(guess)) {
                System.out.println("Error: symbols out of alphabet range were used");
                return;
            }

            BullsAndCows bc = countBullsAndCows(guess);
            System.out.println(gradeMessage(bc));
            if (bc.bulls == secret.length()) {
                System.out.println("Congratulations! You guessed the secret code.");
                return;
            }
        }
    }

    private boolean areSymbolsInAlphabetRange(String guess) {
        for (int i = 0; i < guess.length(); i++) {
            for (int j = 0; j < alphabetExtended.length; j++) {
                if (guess.charAt(i) == alphabetExtended[j] && j + 1 > alphabetSize) {
                    return false;
                }
            }
        }
        return true;
    }

    private String countSymbolRange() {
        if (alphabetSize <= CIPHER_ALPHABET_SIZE) {
            return "(0-9)";
        }
        if (alphabetSize == CIPHER_ALPHABET_SIZE + 1) {
            return "(0-9, a)";
        }
        return String.format("(0-9, %s)", String.format("a-%c", alphabetExtended[alphabetSize - 1]));
    }

    private BullsAndCows countBullsAndCows(String guess) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (secret.indexOf(guess.charAt(i)) >= 0) {
                if (secret.charAt(i) == guess.charAt(i)) {
                    bulls++;
                } else {
                    cows++;
                }
            }
        }
        return new BullsAndCows(bulls, cows);
    }

    private String gradeMessage(BullsAndCows bullsAndCows) {
        if (bullsAndCows.bulls + bullsAndCows.cows == 0) {
            return Grade.NONE.messageTemplate;
        }
        if (bullsAndCows.bulls == 0 && bullsAndCows.cows != 0) {
            return String.format(Grade.COWS.messageTemplate, bullsAndCows.cows);
        }
        if (bullsAndCows.bulls != 0 && bullsAndCows.cows == 0) {
            return String.format(Grade.BULLS.messageTemplate, bullsAndCows.bulls);
        }
        return String.format(Grade.BULLS_AND_COWS.messageTemplate, bullsAndCows.bulls, bullsAndCows.cows);
    }

    private static class BullsAndCows {
        private final int bulls;
        private final int cows;

        public BullsAndCows(int bulls, int cows) {
            this.bulls = bulls;
            this.cows = cows;
        }
    }

    private enum Grade {
        NONE("Grade: None."),
        COWS("Grade: %d cow(s)."),
        BULLS("Grade: %d bull(s)."),
        BULLS_AND_COWS("Grade: %d bull(s) and %d cow(s).");

        private final String messageTemplate;

        Grade(String messageTemplate) {
            this.messageTemplate = messageTemplate;
        }
    }
}
