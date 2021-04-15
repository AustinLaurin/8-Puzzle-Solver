public class fifteenGraph {
    private static class Node {
        private int[][] element;
        private int depth;

        Node(int[][] element, int depth) {
            this.element = element;
            this.depth = depth;
        }

        public int[][] getElement() {
            return element;
        }

        public int getDepth() {
            return depth;
        }
    }
}