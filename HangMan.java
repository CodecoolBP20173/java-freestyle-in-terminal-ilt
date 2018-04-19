import java.util.Scanner;
import java.io.IOException;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.lang.*;
import Terminal.*;

public class HangMan {
    private static Scanner sc = new Scanner(System.in);

    private static String[] usedAndMissedLetters = { "", "" };
    /* in the first String are all the used letters, they cannot typed again (needs exception handling), 
    in the second array there are only the missed letters, they will be printed on the screen */

    private static int count = 0;

    private static Boolean[] inputState = { true, true };

    public static void main(String[] args) {

        Boolean incorrectInput = true;
        System.out
                .println("\nPlease select a gamemode:\n  S - Single Player\n  M - Multi Player\n  Exit - Quit game\n");
        while (incorrectInput) {
            String gamemode = sc.nextLine().trim().toLowerCase();
            System.out.print("\033[" + "2J");
            if (gamemode.equals("s")) {
                System.out.println("\nSingle player mode\n");
                System.out.println("Please select a category:\n  A - Animals\n  B - Body parts\n  C - Capitals\n");
                String category = sc.nextLine();
                System.out.print("\033[" + "2J");
                if (category.equals("c")) {
                    String word = getWord("capitals.txt");
                    String hashtag = new String(new char[word.length()]).replace("\0", "#");
                    System.out.println("Which capital is this?");
                    singlePlayer(word, hashtag);
                } else if (category.equals("a")) {
                    String word = getWord("animals.txt");
                    String hashtag = new String(new char[word.length()]).replace("\0", "#");
                    System.out.println("Which animal is this?");
                    singlePlayer(word, hashtag);
                } else if (category.equals("b")) {
                    String word = getWord("body_parts.txt");
                    String hashtag = new String(new char[word.length()]).replace("\0", "#");
                    System.out.println("Which part of body is this?");
                    singlePlayer(word, hashtag);
                }
                incorrectInput = false;
            } else if (gamemode.equals("m")) {
                System.out.println("Enter the word you want to be guessed by the other player:");
                String word = checkWord();
                String hashtag = new String(new char[word.length()]).replace("\0", "#");
                System.out.print("\033[" + "2J");
                multiPlayer(word, hashtag);
                incorrectInput = false;
            } else if (gamemode.equals("exit")) {
                incorrectInput = false;
            } else {
                System.out.println("Wrong input. Please type in 'S' or 'M' or 'Exit'.");
            }
        }
        sc.close();
    }

    private static String checkWord() {
        String englishAlphabet = "abcdefghijklmnopqrstuvwxyz";
        Boolean incorrectInput = true;
        String word = "";
        while (incorrectInput) {
            word = sc.nextLine();
            incorrectInput = false;
            for (int i = 0; i < word.length(); i++) {
                if (!englishAlphabet.contains(Character.toString(word.charAt(i)))) {
                    incorrectInput = true;
                    System.out.println("Please only use letters from the english alphabet.");
                }
            }
        }
        return word;

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

    public static void singlePlayer(String word, String hashtag) {
        inputState[0] = true;

        System.out.println("Guess any letter");
        while (inputState[0]) {
            //input check
            inputState[1] = true;
            System.out.println(hashtag);
            while (inputState[1]) {
                String input = sc.nextLine().trim().toLowerCase();
                System.out.print("\033[" + "2J");
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

    public static void multiPlayer(String word, String hashtag) {
        inputState[0] = true;

        while (inputState[0]) {
            //input check
            inputState[1] = true;
            System.out.println(hashtag);
            System.out.println("Guess a letter (or 'exit' to quit the game):");
            System.out.println(hashtag);
            while (inputState[1]) {
                String input = sc.nextLine().trim().toLowerCase();
                System.out.print("\033[" + "2J");
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
                System.out.println("You lost!");
            } else if (!hashtag.contains("#")) {
                inputState[0] = false;
                inputState[1] = false;
                System.out.println("You won!");
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
        if (!word.contains(guess)) { // guess is not in word
            usedAndMissedLetters[1] += guess.charAt(0);
            newHashtag = hashtag;
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (guess.charAt(0) == hashtag.charAt(i)
                        && !usedAndMissedLetters[0].contains(Character.toString(guess.charAt(0)))) { //already found letter
                    usedAndMissedLetters[0] += word.charAt(i);
                    newHashtag += guess;
                } else if (hashtag.charAt(i) != '#') { //add already guessed letter to newHashtag
                    newHashtag += word.charAt(i);
                } else if (word.charAt(i) == guess.charAt(0)) { // found a new letter
                    newHashtag += guess.charAt(0);
                } else {
                    newHashtag += "#";
                }
            }
        }
        System.out.println("Missed letters are: " + usedAndMissedLetters[1]);
        if (hashtag.equals(newHashtag) && !usedAndMissedLetters[0].contains(guess)) {
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
            System.out.println("\nWrong guess, try again\n");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(" ___|___");
            System.out.println("|_______|");
        }
        if (count == 2) {
            System.out.println("\nWrong guess, try again\n");
            System.out.println();
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println(" ___|___");
            System.out.println("|_______|");
        }
        if (count == 3) {
            System.out.println("\nWrong guess, try again\n");
            System.out.println("    ____________");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    | ");
            System.out.println(" ___|___");
            System.out.println("|_______|");
        }
        if (count == 4) {
            System.out.println("\nWrong guess, try again\n");
            System.out.println("    ____________");
            System.out.println("    |          _|_");
            System.out.println("    |         /   \\");
            System.out.println("    |        |     |");
            System.out.println("    |         \\_ _/");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println(" ___|___");
            System.out.println("|_______|");
        }
        if (count == 5) {
            System.out.println("\nWrong guess, try again\n");
            System.out.println("    ____________");
            System.out.println("    |          _|_");
            System.out.println("    |         /   \\");
            System.out.println("    |        |     |");
            System.out.println("    |         \\_ _/");
            System.out.println("    |           |");
            System.out.println("    |           |");
            System.out.println("    |");
            System.out.println("    |");
            System.out.println(" ___|___");
            System.out.println("|_______|");
        }
        if (count == 6) {
            System.out.println("\nWrong guess, try again\n");
            System.out.println("    ____________");
            System.out.println("    |          _|_");
            System.out.println("    |         /   \\");
            System.out.println("    |        |     |");
            System.out.println("    |         \\_ _/");
            System.out.println("    |           |");
            System.out.println("    |           |");
            System.out.println("    |          / \\ ");
            System.out.println("    |         /   \\");
            System.out.println(" ___|___");
            System.out.println("|_______|");
        }
        if (count == 7) {
            System.out.println("\nGAME OVER!\n");
            System.out.println("    ____________");
            System.out.println("    |          _|_");
            System.out.println("    |         /   \\");
            System.out.println("    |        |     |");
            System.out.println("    |         \\_ _/");
            System.out.println("    |          _|_");
            System.out.println("    |         / | \\");
            System.out.println("    |          / \\ ");
            System.out.println("    |         /   \\");
            System.out.println(" ___|___");
            System.out.println("|_______|");
            System.out.println("\nGAME OVER! The word was " + word);
        }
    }
}