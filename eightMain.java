public class eightMain {
    public static void main(String[] args) {
        int[][] puzzle = {{4, 2, 5},
                          {1, 0, 6},
                          {3, 8, 7}};
                          
        eightGraph fg = new eightGraph(puzzle);
        fg.solve();
        System.out.println("Moves made: ");
        fg.listMoves();
    }
}
