import java.util.*;
import static java.lang.System.exit;
public class Astar {
   
    private static final int PUZZLE_SIZE = 3; 
    private static final int ERROR = -1; 
    private static final int FINISHED = 0;  
    private static final int CUSTOM_PUZZLE = 2;
    private static final int EUCLIDEAN_DISTANCE = 2;
    private static final int A_STAR_MANHATTAN = 3;
    private static PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparing(State::getF_n));  // Sort queue by f_n value
    private static ArrayList<State> explored = new ArrayList<>(); 
    private int[][] goalState = {{0, 1, 2},
                                 {3, 4, 5},
                                 {6, 7, 8}};
	private int searchDepth;
	private int maxSearchDepth;
	private Integer costOfPath;
    private static int nodesExpanded = 0;
    private static int maxQueueSize = 1;
    public Astar() {
       
        System.out.println("Type '2' to put your initial state");
        int returnVal = -1;
        int choice;
        int algorithm;
        long startTime = -1;
        long endTime = -1;
        Scanner scanner = new Scanner(System.in);
        choice = Integer.parseInt(scanner.nextLine());
        	if(choice == CUSTOM_PUZZLE) {
            System.out.println("Please enter your custom puzzle with white spaces separating each number \n" +
                    "and hit enter to go to the next row. Use 0 to represent the blank slot.");
            int[][] custom = new int[PUZZLE_SIZE][PUZZLE_SIZE];
            for(int i=0; i<PUZZLE_SIZE; i++) {
                String[] row;
                String line = scanner.nextLine();
                if(line == null) {
                    System.err.println("Please enter a valid layout!");
                    exit(1);
                }
                row = line.split(" ");
                for(int j=0; j<PUZZLE_SIZE; j++)
                    custom[i][j] = Integer.parseInt(row[j]);
            }	
            System.out.println("Please select a search algorithm:\n1)\n2) A* Euclidean Ditance\n3) A* Manhattan");
            algorithm = Integer.parseInt(scanner.nextLine());
            if(algorithm<=0 || algorithm>3) {
                System.err.println("Please enter a correct algorithm: 1-3!");
                exit(1);
            } else {
                System.out.println("Input state:");
                printState(custom);
                startTime = System.currentTimeMillis();
                returnVal = generalSearch(custom,algorithm);
                endTime = System.currentTimeMillis();
            }
        } else {
            System.err.println("Please enter a valid choice: '1' or '2'");
            exit(1);
        }
        if(returnVal == FINISHED) {
            System.out.println("Solved!\n");
            System.out.println("Number of nodes expanded: " + nodesExpanded);
            System.out.println("Max queue size: " + maxQueueSize);
            System.out.println("Time taken: "+(endTime-startTime)+"ms");
        } else if (returnVal == ERROR) {
            System.out.println("Error: Given input has no solution!");
        }
    }

    private int generalSearch(int[][] currGrid, int searchFunction) {
        queue.add(new State(currGrid,0,0));
        explored.add(new State(currGrid,0,0));
        while(true) {
            if(queue.isEmpty()) return ERROR;
            ArrayList<State> children;
            State tempState = queue.peek();
            int[][] tempNode = tempState.getGrid();
            int [][] topNode = new int[currGrid.length][];
            for(int i = 0; i < currGrid.length; i++)
                topNode[i] = tempNode[i].clone();
            State topState = new State(topNode,tempState.getG_n(),tempState.getH_n());
            System.out.println("Fetching top node in queue with g(n)="+topState.getG_n()+" and h(n)="+topState.getH_n()+" and f(n)="+topState.getF_n()+"...");
            printState(topState.getGrid());
            queue.remove();
            if(stateEqual(topState.getGrid(), goalState)) {
                return FINISHED;
            } else {  // keep expanding
                children = getChildren(topState, searchFunction);
                System.out.println("children size -> "+children.size()+"\n");
                if(children.size()==0) {
                    System.out.println("Top node in queue has no descendants, going to next node.\n");
                    continue;  
                }
                nodesExpanded++;
                System.out.println("Expanding top node in queue...");
                for(State child : children) {
                    if(!containsChild(child)) { 
                        queue.add(child);
                        explored.add(child);
                        if(queue.size() > maxQueueSize) maxQueueSize = queue.size();
                        System.out.println("Adding following child to queue:");
                        printState(child.getGrid());
                    } else {

                    }
                }
            }
        }

    }
    private ArrayList<State> getChildren(State currState, int searchFunction) {
        ArrayList<State> retArray = new ArrayList<>();
        int[][] currGrid = currState.getGrid();
        int g_n = currState.getG_n();
        int zero_loc_x = -1;
        int zero_loc_y = -1;
        for(int i=0; i<PUZZLE_SIZE; i++) {
            for(int j=0; j<PUZZLE_SIZE; j++) {
                if(currGrid[i][j]==0) {
                    zero_loc_x = i;
                    zero_loc_y = j;
                }
            }
        }

        int h_n = 0;
        switch(searchFunction) {
        case EUCLIDEAN_DISTANCE:
        	int counter = 0;
        	for (int i = 0;i < 3;i++)
        	{
        		for (int j = 0;j < 3;j++)
        		{
        			if (currGrid[i][j] == 0)
        			{
        				continue;
        			}
        			if (currGrid[i][j] != goalState[i][j])
        			{
        				double n = 0;
        				double m = 0;

        				for (int y = 0;y < 3;y++)
        				{
        					for (int z = 0;z < 3;z++)
        					{
        						if (goalState[y][z] == currGrid[i][j])
        						{
        							n = y;
        							m = z;
        						}
        					}
        				}
        				n = Math.pow((n - m),2);
        				m = Math.pow((i - j),2);
        				while (n != 0 || m != 0)
        				{
        					if (n < 0)
        					{
        						counter++;
        						n++;
        					}
        					if (n > 0)
        					{
        						counter++;
        						n--;
        					}
        					if (m < 0)
        					{
        						counter++;
        						m++;
        					}
        					if (m > 0)
        					{
        						counter++;
        						m--;
	}}
        				
        		}}}
            case A_STAR_MANHATTAN:
                for(int i=0; i<PUZZLE_SIZE; i++)
                    for(int j=0; j<PUZZLE_SIZE; j++) {
                        int target = currGrid[i][j];
                        for (int k = 0; k < PUZZLE_SIZE; k++)
                            for (int l = 0; l < PUZZLE_SIZE; l++) { // Note: Deeply-nested for loop is not a bottle neck here due to PUZZLE_SIZE limit
                                if(goalState[k][l]==target) {
                                    h_n += Math.abs(k-i) + Math.abs(l-j);
                                }
                            }
                    }
                break;
            default:
                System.err.println("Error: Entered wrong algorithm!");
                break;
        }
        if(zero_loc_x-1 >=0) { 
            int [][] workingGrid = new int[currGrid.length][];
            for(int i = 0; i < currGrid.length; i++)
                workingGrid[i] = currGrid[i].clone();
            int temp = workingGrid[zero_loc_x][zero_loc_y];
            workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x-1][zero_loc_y];
            workingGrid[zero_loc_x-1][zero_loc_y] = temp;
            retArray.add(new State(workingGrid,g_n+1,h_n));
        }
        if(zero_loc_x+1 < PUZZLE_SIZE) {  
            int [][] workingGrid = new int[currGrid.length][];
            for(int i = 0; i < currGrid.length; i++)
                workingGrid[i] = currGrid[i].clone();
            int temp = workingGrid[zero_loc_x][zero_loc_y];
            workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x+1][zero_loc_y];
            workingGrid[zero_loc_x+1][zero_loc_y] = temp;
            retArray.add(new State(workingGrid,g_n+1,h_n));
        }
        if(zero_loc_y-1 >=0) { 
            int [][] workingGrid = new int[currGrid.length][];
            for(int i = 0; i < currGrid.length; i++)
                workingGrid[i] = currGrid[i].clone();
            int temp = workingGrid[zero_loc_x][zero_loc_y];
            workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x][zero_loc_y-1];
            workingGrid[zero_loc_x][zero_loc_y-1] = temp;
            retArray.add(new State(workingGrid,g_n+1,h_n));
        }
        if(zero_loc_y+1 < PUZZLE_SIZE) { 
            int [][] workingGrid = new int[currGrid.length][];
            for(int i = 0; i < currGrid.length; i++)
                workingGrid[i] = currGrid[i].clone();
            int temp = workingGrid[zero_loc_x][zero_loc_y];
            workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x][zero_loc_y+1];
            workingGrid[zero_loc_x][zero_loc_y+1] = temp;
            retArray.add(new State(workingGrid,g_n+1,h_n));
        }
        return retArray;
    }
    private void printState(int[][] currGrid) {
        for(int i=0; i<PUZZLE_SIZE; i++) {
            for(int j=0; j<PUZZLE_SIZE; j++) {
                System.out.print(currGrid[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    private boolean stateEqual(int[][] grid1, int[][] grid2) {
        for(int i=0; i<PUZZLE_SIZE; i++)
            for(int j=0; j<PUZZLE_SIZE; j++)
                if(grid1[i][j] != grid2[i][j])
                    return false;
        return true;
    }
    private boolean containsChild(State state) {
        int[][] child = state.getGrid();
        for(State state2 : explored) {
            int[][] temp = state2.getGrid();
            boolean identical = true;
            for(int i=0; i<PUZZLE_SIZE; i++)
                for(int j=0; j<PUZZLE_SIZE; j++)
                    if(temp[i][j] != child[i][j])
                        identical = false;
            if(identical)
                return true;
        }
        return false;
    }
    public class State {
        private int[][] grid;
        private int g_n;  
        private int h_n;  
        private State(int[][] grid, int g_n, int h_n) {
            this.grid = grid;
            this.g_n = g_n;
            this.h_n = h_n;
        }
        private int[][] getGrid() {
            return grid;
        }
        private int getG_n() { //cost to reach n
            return g_n;
        }
        private int getH_n() { //cost to get from n to the goal
            return h_n;
        }
        private int getF_n() {
            return (g_n + h_n); //g(n)+h(n)
        }}


	}


