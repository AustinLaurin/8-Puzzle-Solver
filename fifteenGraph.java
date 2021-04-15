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

    Node root;
    Node lastMove;

    public int f(Node n) {
        return g(n) + h(n);
    }

    public int g(Node n) {
        return n.getDepth();
    }

    public int h(Node n) {
        
        return 0;
    }
}