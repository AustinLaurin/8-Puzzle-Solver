public class fifteenMain {
    public static void main(String[] args) {
        int[][] puzzle = {{4, 2, 5},
                          {1, 0, 6},
                          {3, 8, 7}};
                          
        fifteenGraph fg = new fifteenGraph(puzzle);
        fg.solve();
        System.out.println("Moves made: ");
        fg.listMoves();
    }
}
