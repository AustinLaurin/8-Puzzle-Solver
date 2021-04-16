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

            for(int i = 0; i < element.length; i++) {
                for(int j = 0; j < element[i].length; j++) {
                    //We need to check every other tile in the same column.
                    for(int k = 0; k < element.length; k++) {
                        //The element is not in its appropriate position and belongs in the column.
                        //The element we are checking around must belong in the column.
                        //No linear conflict with self. 
                        boolean wrongPlace = element[k][j] != ((k * element.length) + j + 1);
                        boolean goalColumn = j == ((element[k][j] - 1) % element[k].length);
                        boolean columnMember = j == ((element[i][j] - 1) % element[k].length);
                        if((i != k) && (wrongPlace && goalColumn) && element[i][j] != 0) {
                            /*
                                *Is our tile blocking this tile from being in its position?
                                *If so, we have two cases to account for.
                                *One: the tile needs to be higher than the position of our current tile.
                                *Two: the tile needs to be lower than the position of our current tile.
                            */
                            
                            //First case.  
                            int kExpected = (element[k][j] - 1) / element[k].length;
                            if(element[k][j] != 0 && kExpected < k && kExpected <= i && k > i && columnMember)
                                count++;
                            //Second case.
                            if(element[k][j] != 0 && kExpected > k && kExpected >= i && k < i && columnMember)
                                count++;
                        }
                    }
                    //The current tile we are at is not in its appropriate position.
                    //If not, we need to check if it's being blocked by another tile.
                    boolean wrongPlace = element[i][j] != ((i * element.length) + j + 1);
                    boolean goalColumn = j == ((element[i][j] - 1) % element[i].length);
                    if(wrongPlace && goalColumn) {
                        //We need to check every other tile in the column.
                        int iExpected = (element[i][j] - 1) / element[i].length;
                        for(int k = 0; k < element.length; k++) {
                            //No linear conflict with itself.
                            if(i != k) {
                                /*
                                    *Is our tile being blocked by another tile belonging in the column?
                                    *If so, we have two cases to account for.
                                    *One: the tile needs to be higher.
                                    *Two: the tile needs to be lower.
                                */
                                boolean columnMember = j == ((element[k][j] - 1) % element[k].length);
                                //First case.
                                if(iExpected < i && iExpected <= k &&  k < i && columnMember && element[k][j] != 0)
                                    count++;
                                //Second case.
                                if(iExpected > i && iExpected >= k &&  k > i && columnMember && element[k][j] != 0)
                                    count++;
                            }
                        
                        }
                    }
                }
            }

            //Conflicts are counted twice in this framework, so divide by 2. Even result, so no remainder.
            return count/2;
        }

        private int caclculateHorizontalLinearConflicts() {
            int count = 0;

            for(int i = 0; i < element.length; i++) {
                for(int j = 0; j < element[i].length; j++) {
                    //We need to check every other tile in the same row.
                    for(int k = 0; k < element[i].length; k++) {
                        //The element is not in its appropriate position and belongs in the row.
                        //The element we are checking around must belong in the row.
                        //No linear conflict with self. 
                        boolean wrongPlace = element[i][k] != ((i * element.length) + k + 1);
                        boolean goalRow = i == ((element[i][k] - 1) / element[i].length);
                        boolean rowMember = j == ((element[i][j] - 1) % element[i].length);
                        if((j != k) && (wrongPlace && goalRow) && element[i][j] != 0) {
                            /*
                                *Is our tile blocking this tile from being in its position?
                                *If so, we have two cases to account for.
                                *One: the tile needs to be to the left of the position of our current tile.
                                *Two: the tile needs to be to the right of the position of our current tile.
                            */
                            
                            //First case.  
                            int kExpected = (element[k][j] - 1) / element[k].length;
                            if(element[i][k] != 0 && kExpected < k && kExpected <= i && k > i && rowMember)
                                count++;
                            //Second case.
                            if(element[i][k] != 0 && kExpected > k && kExpected >= i && k < i && rowMember)
                                count++;
                        }
                    }
                    //The current tile we are at is not in its appropriate position.
                    //If not, we need to check if it's being blocked by another tile.
                    boolean wrongPlace = element[i][j] != ((i * element.length) + j + 1);
                    boolean goalRow = i == ((element[i][j] - 1) / element[i].length);
                    if(wrongPlace && goalRow) {
                        //We need to check every other tile in the row.
                        int jExpected = (element[i][j] - 1) % element[i].length;
                        for(int k = 0; k < element.length; k++) {
                            //No linear conflict with itself.
                            if(j != k) {
                                /*
                                    *Is our tile being blocked by another tile belonging in the column?
                                    *If so, we have two cases to account for.
                                    *One: the tile needs to be to the right.
                                    *Two: the tile needs to be to the left.
                                */
                                boolean rowMember = i == ((element[i][k] - 1) / element[i].length);
                                //First case.
                                if(jExpected < j && jExpected <= k &&  k < j && rowMember && element[i][k] != 0)
                                    count++;
                                //Second case.
                                if(jExpected > j && jExpected >= k &&  k > j && rowMember && element[i][k] != 0)
                                    count++;
                            }
                        
                        }
                    }
                }
            }

            //Conflicts are counted twice in this framework, so divide by 2. Even result, so no remainder.
            return count/2;
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
        return n.getDistancesFromFinalPosition() + (2 * n.calculateLinearConflicts());
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