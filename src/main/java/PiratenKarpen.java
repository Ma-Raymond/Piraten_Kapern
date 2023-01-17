import pk.Dice;
import pk.*;

import java.util.Arrays;
import java.util.Scanner;

public class PiratenKarpen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("------------------------------");
        int howManyFaces = Faces.values().length;
        System.out.println("  (DEBUG) there are " + howManyFaces + " faces");
        System.out.println("  (DEBUG) " + Arrays.toString(Faces.values()));
        System.out.println("------------------------------");
        System.out.println("What would you like to do?");
        System.out.println("Q: Quit");
        System.out.println("P: Play Game");
        String val = scanner.nextLine();
        if (val.equals("Q")){
            System.exit(0);
        }
        else if (val.equals("P")){
            PLAY();

        }

    }
    public static void printOptions(){
        System.out.println("What would you like to do?");
        System.out.println("Q: Quit\t R: Reroll \t N: Cash Out/Next Turn");
    }
    public static void PLAY(){
        Scanner scanner = new Scanner(System.in);
        Boolean playing = true;
        Player player1 = new Player();
        player1.initalRoll();
        while (playing){
            printOptions();
            String choice = scanner.nextLine();
            if (choice.equals("Q")){
                playing = false;
                System.exit(0);
            }
            else if (choice.equals("N")){
                player1.cash();
                player1.initalRoll();
            }
            else if (choice.equals("R")){
                System.out.println("Please Input which Dice you want to reroll! Format: \"2 3 4 5\"");
                String re = scanner.nextLine();
                player1.reroll(re);
            }
        }

    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    
}
