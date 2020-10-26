package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int level = getGameLevel();
        String secret = mindSecret(level);
        playGame(secret);
    }

    private static void playGame(String secret) {
        if (secret.isEmpty()) {
            return;
        }
        int turn = 1;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.printf("Turn %d:%n", turn++);
            String guess = s.next();
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

            if (bulls + cows == 0) {
                System.out.println("Grade: None. ");
            }
            if (bulls == 0 && cows != 0) {
                System.out.printf("Grade: %d cow(s). %n", cows);
            }
            if (bulls != 0 && cows == 0) {
                System.out.printf("Grade: %d bull(s). %n", bulls);
            }
            if (bulls != 0 && cows != 0) {
                System.out.printf("Grade: %d bull(s) and %d cow(s). %n", bulls, cows);
            }
            if (bulls == secret.length()) {
                System.out.println("Congratulations! You guessed the secret code.");
                return;
            }
        }
    }

    private static String mindSecret(int level) {
        Random r = new Random(System.nanoTime());
        int rc = r.nextInt(10);
        while (rc == 0) {
            rc = r.nextInt();
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < level) {
            if (!sb.toString().contains(String.valueOf(rc))) {
                sb.append(rc);
                if (sb.length() == level) {
                    break;
                }
            }
            rc = r.nextInt(10);
        }
        return sb.toString();
    }

    private static int getGameLevel() {
        System.out.println("Please, enter the secret code's length:");
        Scanner s = new Scanner(System.in);
        int level = s.nextInt();
        if (level > 10) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", level);
            System.out.println();
            return 0;
        } else {
            System.out.println("Okay, let's start a game!");
        }
        return level;
    }
}
