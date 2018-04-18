import java.util.Scanner;
import java.io.IOException;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HangMan {

    private static String[] capitals = {"kabul", "tirana", "algiers", "andorra", "luanda", "yerevan", "canberra", "vienna", 
    "baku", "nassau", "manama", "dhaka", "bridgetown", "minsk", "brussels", "belmopan", "thimphu", "sarajevo", "gaborone", 
    "brasilia", "sofia", "praia", "yaounde", "ottawa", "bangui", "santiago", "beijing", "bogota", "moroni", "kinshasa", 
    "brazzaville", "zagreb", "havana", "nicosia", "prague", "copenhagen", "roseau", "quito", "cairo", "asmara", "tallinn", 
    "suva", "helsinki", "paris", "leibreville", "banjul", "berlin", "accra", "athens", "guatemala", "conakry", "bissau", 
    "georgetown", "tegucigalpa", "budapest", "reykjavik", "jakarta", "tehran", "baghdad", "dublin", "jerusalem", "rome", 
    "kingston", "tokyo", "amman", "astana", "nairobi", "pristina", "kuwait", "bishkek", "vientiane", "riga", "beirut", 
    "maseru", "monrovia", "tripoli", "vaduz", "vilnius", "luxembourg", "skopje", "antananarivo", "linogwe", "male", 
    "bamako", "valletta", "majuro", "mexico", "palikir", "chisinau", "monaco", "ulaanbaatar", "podgorica", "rabat", 
    "maputo", "windhoek", "kathmandu", "amsterdam", "wellington", "managua", "niamey", "abuja", "pyongyang", "oslo", 
    "muscat", "islamabad", "panama", "asuncion", "lima", "manila", "warsaw", "lisbon", "doha", "bucharest", "moscow", 
    "kigali", "basseterre", "castries", "kingstown", "apia", "riyadh", "dakar", "belgrade", "victoria", "freetown", 
    "singapore", "bratislava", "ljubljana", "honiara", "mogadishu", "seoul", "juba", "madrid", "khartoum", "paramaribo", 
    "stocholm", "bern", "damascus", "taipei", "dodoma", "bangkok", "dili", "lome", "tunis", "ankara", "kampala", "kyiv", 
    "london", "washington", "montevideo", "tashkent", "vatican", "caracas", "hanoi", "lusaka", "harare" };

    private static String[] animals = {"dog", "cat", "dolphin", "tiger", "wolf", "fox", "penguin", "horse", "cow", "elephant",
     "lion", "ant", "monkey", "panda", "snake", "eagle", "bear", "rabbit", "turtle", "zebra", "giraffe", "shark", "jaguar",
      "leopard", "seal", "koala", "parrot", "pig", "deer", "hyena", "bat", "rhino", "puffin", "duck", "octopus", "crocodile",
       "frog", "kangaroo", "owl", "butterfly", "goat", "camel", "chicken", "chimpanzee", "llamas", "orangutan", "hawk", "rat",
        "bull", "dingo", "bee", "snail", "swan", "starfish", "fish", "alligator", "chameleon", "dove", "hummingbird", "gorilla",
         "gecko", "fly", "donkey", "pony", "pigeon", "sheep", "flamingo", "buffalo", "caterpillar", "vulture", "spider", "mouse"};

	private static String word = capitals[(int) (Math.random() * capitals.length)]; // use this for reading and picking random word from this file
	
	// use this for reading and picking word from external txt file
	private static String pickWord(String fileName) { 
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

    private static String hashtag = new String(new char[word.length()]).replace("\0", "#");
    private static int count = 0;

    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (count < 7 && hashtag.contains("#")) {
			System.out.println("Guess any letter");
			System.out.println(hashtag);
			String guess = sc.next();
			hangUp(guess);
		}
		sc.close();
	}
    
    public static void hangUp(String guess) {
        String newHashtag = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                newHashtag += guess.charAt(0);
            }else if(hashtag.charAt(i) != '#') {
                newHashtag += word.charAt(i);
            } else {
                newHashtag += "#";
            }
        }

        if (hashtag.equals(newHashtag)) {
            count++;
            hangUpImage();
        } else {
            hashtag = newHashtag;
        }
        if (hashtag.equals(word)) {
            System.out.println("Correct! You won! The word was " + word);
        }     
    }

    public static void hangUpImage() {
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


