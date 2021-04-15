import java.util.ArrayList;

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

            for(int i = 0; i < element.length; i+=2) {
                for(int j = 0; j < element[i].length; j++) {
                    int correctValue = (i * element.length) + j;
                    
                    if(i - 1 >= 0) {
                        int correctValueAbove = ((i - 1) * element.length) + j;
                        
                        if((element[i][j] == correctValueAbove) && (element[i - 1][j] == correctValue)) {
                            count++;
                            break;
                        }
                    }
                    if(i + 1 < element.length) {
                        int correctValueBelow = ((i + 1) * element.length) + j;
                        
                        if((element[i][j] == correctValueBelow) && (element[i + 1][j] == correctValue)) {
                            count++;
                            break;
                        }
                    }
                }
            }

            return count;
        }

        private int caclculateHorizontalLinearConflicts() {
            int count = 0;

            for(int i = 0; i < element.length; i++) {
                for(int j = 0; j < element[i].length; j+=2) {
                    int correctValue = (i * element.length) + j;
                    
                    if(j - 1 >= 0) {
                        int correctValueLeft = (i * element.length) + (j - 1);
                        
                        if((element[i][j] == correctValueLeft) && (element[i][j - 1] == correctValue)) {
                            count++;
                            break;
                        }
                    }
                    if(j + 1 < element.length) {
                        int correctValueRight = (i * element.length) + (j + 1);
                        
                        if((element[i][j] == correctValueRight) && (element[i][j + 1] == correctValue)) {
                            count++;
                            break;
                        }
                    }
                }
            }

            return count;
        }
    
        public ArrayList<Integer> moves() {
            ArrayList<Integer> moves = new ArrayList<>();
            boolean found = false;

            for(int i = 0; i < element.length; i++) {
                for(int j = 0; j < element[i].length; j++) {
                    if(element[i][j] == 0) {
                        if(i - 1 >= 0)
                            moves.add(element[i - 1][j]);
                        if(i + 1 < element.length)
                            moves.add(element[i + 1][j]);
                        if(j - 1 >= 0)
                            moves.add(element[i][j - 1]);
                        if(j + 1 < element.length)
                            moves.add(element[i][j + 1]);
                        
                        found = true;
                        break; 
                    }
                }

                if(found)
                    break;
            }

            return moves;
        }
    }

    private Node root;
    private Node lastMove;

    fifteenGraph(int[][] puzzle) {
        root = new Node(puzzle, 0);
    }

    public int f(Node n) {
        return g(n) + h(n);
    }

    public int g(Node n) {
        return n.getDepth();
    }

    public int h(Node n) {
        return n.getDistancesFromFinalPosition() + n.calculateLinearConflicts();
    }
}