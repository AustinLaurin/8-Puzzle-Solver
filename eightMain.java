public class eightMain {
    public static void main(String[] args) {
        int[][] puzzle = {{4, 2, 5},
                          {1, 0, 6},
                          {3, 8, 7}};
                          
        eightGraph eg = new eightGraph(puzzle);
        eg.solve();
        System.out.println("Moves made: ");
        eg.listMoves();
    }
}
