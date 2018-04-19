import java.util.Scanner;
import java.io.IOException;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.lang.*;

public class HangMan {
    private static Scanner sc = new Scanner(System.in);

    private static String[] usedAndMissedLetters = {"",""};
    /* in the first String are all the used letters, they cannot typed again (needs exception handling), 
    in the second array there are only the missed letters, they will be printed on the screen */

    private static int count = 0;

    private static Boolean[] inputState = { true, true };

    public static void main(String[] args) {

        Boolean incorrectInput = true;
        System.out.println("Please select a gamemode:\nS - Single Player\nM - Multi Player\nExit - Quit game");
        while (incorrectInput) {
            String gamemode = sc.nextLine().trim().toLowerCase();
            if (gamemode.equals("s")) {
                System.out.println("single-player");
                System.out.println("Please select a category:\nC - Capitals\nA - Animals");
                String category = sc.nextLine();
                if (category.equals("c")) {
                    String word = getWord("capitals.txt");
                    System.out.println("Which capital is this?");
                    singlePlayer(word);
                } else if (category.equals("a")) {
                    String word = getWord("animals.txt");
                    System.out.println("Which animal is this?");
                    singlePlayer(word);
                }
                incorrectInput = false;
            } else if (gamemode.equals("m")) {
                System.out.println("multi-player");
                String word = sc.nextLine();
                multiPlayer(word);
                incorrectInput = false;
            } else if (gamemode.equals("exit")) {
                incorrectInput = false;
            } else {
                System.out.println("Wrong input. Please type in 'S' or 'M' or 'Exit'.");
            }
        }
        sc.close();
    }

    private static String getWord(String fileName) {
        try {
            Stream<String> words = Files.lines(Paths.get(fileName));
            String[] result = words.toArray(String[]::new);
            int idx = new Random().nextInt(result.length);
            String pickedWord = (result[idx]);
            words.close();
            return pickedWord;
        } catch (IOException e) {
            return "";
        }
    }

    public static void singlePlayer(String word) {
        inputState[0] = true;

        String hashtag = new String(new char[word.length()]).replace("\0", "#");
        System.out.println("Guess any letter");
        while (inputState[0]) {
            //input check
            inputState[1] = true;
            System.out.println(hashtag);
            while (inputState[1]) {
                String input = sc.nextLine().trim().toLowerCase();
                inputCheck(input);
                System.out.println("Missed letters: " + usedAndMissedLetters[1]);
                if (inputState[0].equals(false)) {
                    System.out.println("Goodbye.");
                } else if (inputState[1] == false) {
                    hashtag = hangUp(input, word, hashtag);
                }
            }
            //game

            if (count == 7) {
                inputState[0] = false;
                inputState[1] = false;
                // loose
            } else if (!hashtag.contains("#")) {
                inputState[0] = false;
                inputState[1] = false;
                // win
            }

        }
    }

    public static void multiPlayer(String word) {
        inputState[0] = true;

        String hashtag = new String(new char[word.length()]).replace("\0", "#");
        System.out.println("Guess any letter");
        while (inputState[0]) {
            //input check
            inputState[1] = true;
            System.out.println(hashtag);
            while (inputState[1]) {
                String input = sc.nextLine().trim().toLowerCase();
                inputCheck(input);
                if (inputState[0].equals(false)) {
                    System.out.println("Goodbye.");
                } else if (inputState[1] == false) {
                    hashtag = hangUp(input, word, hashtag);
                }
            }
            //game

            if (count == 7) {
                inputState[0] = false;
                inputState[1] = false;
                // loose
            } else if (!hashtag.contains("#")) {
                inputState[0] = false;
                inputState[1] = false;
                // win
            }
        }
    }

    public static void inputCheck(String input) {
        String englishAlphabet = "abcdefghijklmnopqrstuvwxyz";
        if (input.equals("exit")) {
            inputState[1] = false;
            inputState[0] = false;
        } else if (input.length() != 1) {
            System.out.println("Please only type in a single letter.");
        } else if (englishAlphabet.contains(input)) {
            inputState[1] = false;
        } else if (usedAndMissedLetters[0].contains(input)) {
            System.out.println("This letter is already used. Try another.");
        } else {
            System.out.println("Please only type in letters from the english alphabet.");
        }
    }

    public static String hangUp(String guess, String word, String hashtag) {
        String newHashtag = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                newHashtag += guess.charAt(0);
                usedAndMissedLetters[0] += word.charAt(i);
            } else if (hashtag.charAt(i) != '#') {
                newHashtag += word.charAt(i);
                usedAndMissedLetters[1] += word.charAt(i);
            } else {
                newHashtag += "#";
            }
        }
        System.out.println("Missssssssed letters are: " + usedAndMissedLetters[1]);

        if (hashtag.equals(newHashtag)) {
            count++;
            hangUpImage(word);
        } else {
            hashtag = newHashtag;
        }
        if (hashtag.equals(word)) {
            System.out.println("Correct! You won! The word was " + word);
        }
        return hashtag;
    }

    public static void hangUpImage(String word) {
        if (count == 1) {
            System.out.println("Wrong guess, try again");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("___|___");
            System.out.println();
        }
        if (count == 2) {
            System.out.println("Wrong guess, try again");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("___|___");
        }
        if (count == 3) {
            System.out.println("Wrong guess, try again");
            System.out.println("   ____________");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   | ");
            System.out.println("___|___");
        }
        if (count == 4) {
            System.out.println("Wrong guess, try again");
            System.out.println("   ____________");
            System.out.println("   |          _|_");
            System.out.println("   |         /   \\");
            System.out.println("   |        |     |");
            System.out.println("   |         \\_ _/");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("   |");
            System.out.println("___|___");
        }
        if (count == 5) {
            System.out.println("Wrong guess, try again");
            System.out.println("   ____________");
            System.out.println("   |          _|_");
            System.out.println("   |         /   \\");
            System.out.println("   |        |     |");
            System.out.println("   |         \\_ _/");
            System.out.println("   |           |");
            System.out.println("   |           |");
            System.out.println("   |");
            System.out.println("___|___");
        }
        if (count == 6) {
            System.out.println("Wrong guess, try again");
            System.out.println("   ____________");
            System.out.println("   |          _|_");
            System.out.println("   |         /   \\");
            System.out.println("   |        |     |");
            System.out.println("   |         \\_ _/");
            System.out.println("   |           |");
            System.out.println("   |           |");
            System.out.println("   |          / \\ ");
            System.out.println("___|___      /   \\");
        }
        if (count == 7) {
            System.out.println("GAME OVER!");
            System.out.println("   ____________");
            System.out.println("   |          _|_");
            System.out.println("   |         /   \\");
            System.out.println("   |        |     |");
            System.out.println("   |         \\_ _/");
            System.out.println("   |          _|_");
            System.out.println("   |         / | \\");
            System.out.println("   |          / \\ ");
            System.out.println("___|___      /   \\");
            System.out.println("GAME OVER! The word was " + word);
        }
    }
}