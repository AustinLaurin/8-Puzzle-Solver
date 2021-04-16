import java.util.ArrayList;
import java.util.Arrays;
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
        private int createdBy;

        Node(int[][] element, int depth, Node parent, int createdBy) {
            this.element = element;
            this.depth = depth;
            distancesFromFinalPositionSum = calculateDistancesFromFinalPosition();
            this.parent = parent;
            this.createdBy = createdBy;
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
                    if(element[i][j] != 0) {
                        int iExpected = (element[i][j] - 1) / element[i].length;
                        int jExpected = (element[i][j] - 1) % element[i].length;
                        
                        sum += Math.abs(i - iExpected) + Math.abs(j - jExpected);
                    }
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
                    int correctValue = (i * element.length) + j + 1;
                    
                    if(i - 1 >= 0) {
                        int correctValueAbove = ((i - 1) * element.length) + j + 1;
                        
                        if((element[i][j] == correctValueAbove) && (element[i - 1][j] == correctValue)) {
                            count++;
                            break;
                        }
                    }
                    if(i + 1 < element.length) {
                        int correctValueBelow = ((i + 1) * element.length) + j + 1;
                        
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
                        int correctValueLeft = (i * element.length) + (j - 1) + 1;
                        
                        if((element[i][j] == correctValueLeft) && (element[i][j - 1] == correctValue)) {
                            count++;
                            break;
                        }
                    }
                    if(j + 1 < element.length) {
                        int correctValueRight = (i * element.length) + (j + 1) + 1;
                        
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
    
        public Node getParent() {
            return parent;
        }

        public ArrayList<Node> getChildren() {
            return children;
        }

        public void addChild(Node n) {
            children.add(n);
        }

        public int getCreatedBy() {
            return createdBy;
        }
    }

    private Node root;
    private Node lastMove;
    private Queue<Integer> moves = new LinkedList<>();

    fifteenGraph(int[][] puzzle) {
        root = new Node(puzzle, 0, null, 0);
    }

    public void solve() {
        lastMove = root;
        boolean solved = false;
        while(!solved && h(root) != 0) {
            System.out.println("Possible moves: ");
            for(Integer i: lastMove.moves()) {
                System.out.println(i);
            }
            for(Integer i: lastMove.moves()) {
                Node possibleMove = new Node(makeMove(lastMove, i), lastMove.getDepth() + 1, lastMove, i);
                lastMove.addChild(possibleMove);
            }
            
            int minF = findMinimumFInChildren(lastMove);
            for(Node c: lastMove.getChildren()) {
                if(h(c) == 0) {
                    lastMove = c;
                    moves.add(c.getCreatedBy());
                    solved = true;
                    break;
                }
                else if(f(c) == minF) {
                    lastMove = c;
                    moves.add(c.getCreatedBy());
                    break;
                }
            }
            System.out.println("Chosen move: " + lastMove.getCreatedBy());
            System.out.println("Resulting matrix: ");
            for(int i = 0; i < lastMove.getElement().length; i++)
                System.out.println(Arrays.toString(lastMove.getElement()[i]));
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
        int[][] puzzle = copyMatrix(n.getElement());
        boolean moved = false;

        for(int i = 0; i < puzzle.length; i++) {
            for(int j = 0; j < puzzle[i].length; j++) {
                if(puzzle[i][j] == move) {
                    if(i - 1 >= 0 && puzzle[i - 1][j] == 0) {
                        puzzle[i - 1][j] = puzzle[i][j];
                        puzzle[i][j] = 0;
                        moved = true;
                    }
                    else if(i + 1 < puzzle.length && puzzle[i + 1][j] == 0) {
                        puzzle[i + 1][j] = puzzle[i][j];
                        puzzle[i][j] = 0;
                        moved = true;
                    }
                    else if(j - 1 >= 0 && puzzle[i][j - 1] == 0) {
                        puzzle[i][j - 1] = puzzle[i][j];
                        puzzle[i][j] = 0;
                        moved = true;
                    }
                    else if(j + 1 < puzzle.length && puzzle[i][j + 1] == 0) {
                        puzzle[i][j + 1] = puzzle[i][j];
                        puzzle[i][j] = 0;
                        moved = true;
                    }                           
                }
                if(moved)
                    break;
            }
            if(moved)
                break;
        }

        return puzzle;
    }

    public int[][] copyMatrix(int[][] data) {
        int[][] copy = new int[data.length][data[0].length];
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                copy[i][j] = data[i][j];
            }
        }

        return copy;
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