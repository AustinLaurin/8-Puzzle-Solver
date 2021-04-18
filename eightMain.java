import java.io.FileReader;
import java.util.Scanner;

public class eightMain {
    public static void main(String[] args) {
        // int[][] puzzle = {{4, 2, 5},
        //                   {1, 0, 6},
        //                   {3, 8, 7}};
        try {
            FileReader fr = new FileReader("puzzle");
            int[][] puzzle = new int[3][3];
            Scanner s = new Scanner(fr);

            for(int i = 0; i < puzzle.length; i++) {
                for(int j = 0; j < puzzle[i].length; j++) {
                    if(s.hasNextInt())
                        puzzle[i][j] = s.nextInt();
                }
            }

            eightGraph eg = new eightGraph(puzzle);
            eg.solve();
            System.out.println("Moves made: ");
            eg.listMoves();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
