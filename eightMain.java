import java.io.FileReader;
import java.util.Scanner;

public class eightMain {
    public static void main(String[] args) {
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
            s.close();
            fr.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
