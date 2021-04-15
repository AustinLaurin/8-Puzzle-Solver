public class fifteenGraph {
    private static class Node {
        private int[][] element;
        private int depth;
        private int distancesFromFinalPositionSum;

        Node(int[][] element, int depth) {
            this.element = element;
            this.depth = depth;
            distancesFromFinalPositionSum = calculateDistancesFromFinalPosition();
        }

        public int[][] getElement() {
            return element;
        }

        public int getDepth() {
            return depth;
        }

        public int calculateDistancesFromFinalPosition() {
            int sum = 0;
            
            for(int i = 0; i < element.length; i++) {
                for(int j = 0; j < element[i].length; j++) {
                    if(element[i][j] == 0)
                        break;
                    int iExpected = (element[i][j] - 1) / element[i].length;
                    int jExpected = (element[i][j] - 1) % element[i].length;

                    sum = Math.abs(i - iExpected) + Math.abs(j - jExpected);
                }
            }
            
            return sum;
        }

        public int getDistancesFromFinalPosition() {
            return distancesFromFinalPositionSum;
        }

        public int calculateLinearConflicts() {
            return caclculateHorizontalLinearConflicts() + calculateVerticalLinearConflicts();
        }

        private int calculateVerticalLinearConflicts() {
            int count = 0;

            

            return count;
        }

        private int caclculateHorizontalLinearConflicts() {
            int count = 0;

            return count;
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
        return n.getDistancesFromFinalPosition();
    }
}