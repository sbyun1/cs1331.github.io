import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;

public class Battleship {
    static Scanner scanner = new Scanner(System.in);

    public static char[][] initBoard(int n) {
        // YOUR CODE BELOW: initializes a board of size n * n
        char[][] board = new char[n][n];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = '~';
            }
        }
        return board;
    }

    public static void printBoard(int playerTurn, char[][] board,
                                  int hitsLeft) {
        // YOUR CODE BELOW: prints the board and player information
        //  (as in the example output)
        String player = "Player " + playerTurn;
        String hits = " (" + hitsLeft + " hits left):";

        System.out.println(player + hits);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int fireMissile(char[][] board, String target,
                                  String[] shipLocations, int hitsLeft) {
        // YOUR CODE BELOW: updates the board given the missile
        //  target and returns if a ship is hit

        int[] index = convertLocation(target);
        int row = index[0];
        int col = index[1];

        if (board[row][col] == '~') {
            if (isShip(target, shipLocations)) {
                board[row][col] = 'X';
                System.out.println("Hit!");
                hitsLeft--;
            } else {
                board[row][col] = '0';
                System.out.println("Miss!");
            }
        } else {
            System.out.println("target(" + target + ") has already been chosen!");
        }

        return hitsLeft;
    }

    public static boolean isShip(String target, String[] shipLocations) {
        // YOUR CODE BELOW: returns if location is ship

        target = target.toLowerCase();
        for (String loc : shipLocations) {
            loc = loc.toLowerCase();
            if (target.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    public static int[] convertLocation(String coordinate) {
        // YOUR CODE BELOW: returns the integer indices corresponding
        //  with the coordinate string

        coordinate = coordinate.toLowerCase();
        int row = coordinate.charAt(0) - 'a';
        int col = (Integer.parseInt(coordinate.substring(1))) - 1;

        int[] index = {row, col};

        return index;
    }

    public static void main(String[] args) {

        int fileInd = (args.length > 0) ? Integer.parseInt(args[0])
                : new Random().nextInt(4);
        //fileInd = 2;
        String filename = "game" + fileInd + ".txt";

        try {
            Scanner fileReader = new Scanner(new File(filename));
            Scanner inputScanner = new Scanner(System.in);
            // YOUR CODE BELOW: carries out the execution of the game
            //  using the methods defined above

            int playerTurn;
            boolean flag = true;

            //read first line for initBoard();
            int n = Integer.parseInt(fileReader.nextLine());
            System.out.println(filename + ": " + n);
            char[][] p1Board = initBoard(n);
            char[][] p2Board = initBoard(n);

            //read lines for shipLoc

            String p1Ship = fileReader.nextLine();
            String p2Ship = fileReader.nextLine();

            String[] shipLoc1 = p1Ship.split(" ");
            String[] shipLoc2 = p2Ship.split(" ");

            int p1HitsLeft = shipLoc1.length;
            int p2HitsLeft = shipLoc2.length;

            //calling printBoard
            while (p1HitsLeft != 0 && p2HitsLeft != 0) {
                if (flag) {
                    playerTurn = 1;
                    printBoard(playerTurn, p2Board, p2HitsLeft);
                    System.out.print("Enter missile location: ");
                    String target = inputScanner.next();
                    p2HitsLeft = fireMissile(p2Board, target, shipLoc2, p2HitsLeft);
                    printBoard(playerTurn, p2Board, p2HitsLeft);
                    System.out.println("----------");

                    if (p2HitsLeft == 0) {
                        System.out.println("The winner is Player 1");
                    }

                } else {
                    playerTurn = 2;
                    printBoard(playerTurn, p1Board, p1HitsLeft);
                    System.out.print("Enter missile location: ");
                    String target = inputScanner.next();
                    p1HitsLeft = fireMissile(p1Board, target, shipLoc1, p1HitsLeft);
                    printBoard(playerTurn, p1Board, p1HitsLeft);
                    System.out.println("----------");

                    if (p1HitsLeft == 0) {
                        System.out.println("The winner is Player 2");
                    }
                }
                flag = !flag;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Make sure that " + filename
                    + " is in the current directory!");
        }
    }
}