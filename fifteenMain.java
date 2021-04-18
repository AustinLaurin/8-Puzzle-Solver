public class fifteenMain {
    public static void main(String[] args) {
        int[][] puzzle = {{ 5,  6,  0,  1},
                          { 2,  7, 14,  4},
                          {13,  3, 11, 10},
                          {15,  9, 12,  8}};
        fifteenGraph fg = new fifteenGraph(puzzle);
        fg.solve();
        System.out.println("Moves made: ");
        fg.listMoves();
    }
}
