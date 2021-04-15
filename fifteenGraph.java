import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class fifteenGraph {
    private static class Node {
        private int[][] element;
        private int depth;
        private int distancesFromFinalPositionSum;
        private Node parent;
        private ArrayList<Node> children = new ArrayList<>();

        Node(int[][] element, int depth, Node parent) {
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
                        if(j + 1 < element[i].length)
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
    
        public Node getParent(Node n) {
            return parent;
        }

        public ArrayList<Node> getChildren() {
            return children;
        }

        public void addChild(Node n) {
            children.add(n);
        }
    }

    private Node root;
    private Node lastMove;
    private Queue<Integer> moves = new LinkedList<>();

    fifteenGraph(int[][] puzzle) {
        root = new Node(puzzle, 0, null);
    }

    public void solve() {
        lastMove = root;
        boolean solved = false;

        while(!solved) {
            Map<Node, Integer> m = new HashMap<>();

            for(Integer i: lastMove.moves()) {
                Node possibleMove = new Node(makeMove(lastMove, i), lastMove.getDepth() + 1, lastMove);
                lastMove.addChild(possibleMove);
                m.put(possibleMove, i);
            }

            int minF = findMinimumFInChildren(lastMove);
            for(Node c: lastMove.getChildren()) {
                if(f(c) == 0) {
                    solved = true;
                }
                else if(f(c) == minF) {
                    lastMove = c;
                    moves.add(m.get(c));
                }
            }
        }
    }

    public int findMinimumFInChildren(Node n) {
        int minimum = Integer.MAX_VALUE;
        
        for(Node c: n.getChildren()) {
            if(minimum > f(c)) {
                minimum = f(c);
            }
        }

        return minimum;
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

    public int[][] makeMove(Node n, int move) {
        int[][] puzzle = n.getElement().clone();

        for(int i = 0; i < puzzle.length; i++) {
            for(int j = 0; j < puzzle[i].length; j++) {
                if(puzzle[i][j] == move) {
                    if(i - 1 >= 0 && puzzle[i - 1][j] == 0) {
                        puzzle[i - 1][j] = puzzle[i][j];
                        puzzle[i][j] = 0;
                    }
                    if(i + 1 < puzzle.length && puzzle[i + 1][j] == 0) {
                        puzzle[i + 1][j] = puzzle[i][j];
                        puzzle[i][j] = 0;
                    }
                    if(j - 1 >= 0 && puzzle[i][j - 1] == 0) {
                        puzzle[i][j - 1] = puzzle[i][j];
                        puzzle[i][j] = 0;
                    }
                    if(j + 1 < puzzle.length && puzzle[i][j + 1] == 0) {
                        puzzle[i][j + 1] = puzzle[i][j];
                        puzzle[i][j] = 0;
                    }                           
                }
            }
        }

        return puzzle;
    }

    public void listMoves() {
        int count = 0;

        while(count < moves.size()) {
            int move = moves.remove();
            System.out.println(move);
            moves.add(move);
            count++;
        }
    }
}