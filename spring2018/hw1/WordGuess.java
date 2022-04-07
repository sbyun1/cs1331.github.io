import java.util.Random;
import java.util.Scanner;
public class WordGuess {

    static final String[] CANDIDATES = {"cat", "dad", "dog", "mom", "rat"};

    public static void main(String[] args) {
        String secretWord = args.length > 0
            ? CANDIDATES[Integer.parseInt(args[0])]
            : CANDIDATES[new Random().nextInt(CANDIDATES.length)];

        // Your code goes here:
        System.out.println(secretWord);
        Scanner sc = new Scanner(System.in);
        StringBuilder wrong_letter = new StringBuilder();

        StringBuilder hidden = new StringBuilder("_".repeat(secretWord.length()));
        int count;

//
//        for(int i = 0; i < secretWord.length(); i++){
//            hidden.append("_");
//        }

        for(count = 5; count >= 1; count--){
            //System.out.println("Guess a letter: ");
            System.out.println("Missed letters" +"(" + (count) + " left): " + wrong_letter.toString());
            System.out.println("Current Guess: " + hidden);
            System.out.print("Guess a letter: ");
            char letter = sc.next().charAt(0);
            String guess_letter = String.valueOf(letter);

            System.out.println("");

            if(!(secretWord.contains(guess_letter))){
                wrong_letter.append(guess_letter);
            }
            else{
                count++;
                int index = secretWord.indexOf(guess_letter);

                while(index >= 0){
                    char correct_word = secretWord.charAt(index);
                    hidden.setCharAt(index,correct_word);//
                    index = secretWord.indexOf(guess_letter, index + 1);
                }
                if((hidden.toString()).equals(secretWord)){
//                  count = 0;
                    System.out.println("Missed letters: " + wrong_letter.toString());
                    System.out.println("Final guess: " + hidden);
                    System.out.println("Congratulations! You got it!");
                    break;
                }
            }
            if(count == 1){
                System.out.println("Missed letters: " + wrong_letter.toString());
                System.out.println("Final guess: " + hidden);
                System.out.println("Sorry, too many misses. The secret word was " + secretWord);
            }
        }
    }
}
