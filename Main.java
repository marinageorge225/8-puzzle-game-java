import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {
	static final long MEGABYTE_FACTOR = 1024L * 1024L;
	 
	public static void main (String []args) {
		System.out.println("Choose 1 for bfs, 2 for dfs, 3 for A*");
		Scanner s = new Scanner(System.in);
		int x=s.nextInt();
		
		switch (x) {
		case 1:
			bfsmain();
			break;
		case 2:
			dfsmain();
			break;
		case 3:
			astarmain();
			break;
			default:
		}
	}

	private static void bfsmain() {
		

		System.out.println("Starting bfs to solve 8-puzzle");
		LocalDateTime startTime = LocalDateTime.now();
		BFS bfs = new BFS("1","2","5","3","4","0","6","7","8");
		boolean success = bfs.solve();
		LocalDateTime endTime = LocalDateTime.now();
		
		if(success)
		{
			System.out.println("Information:");
			System.out.println("Path to goal: " + bfs.getCurrentNode().getPathToGoal());
			System.out.println("Cost to goal: " + bfs.getCurrentNode().getCostOfPath());
			System.out.println("Nodes expanded: "+bfs.getExploredNodes().size());
			System.out.println("Search depth: " + bfs.getCurrentNode().getSearchDepth());
			System.out.println("Max Search depth: " + bfs.getCurrentNode().getMaxSearchDepth());
			long seconds = startTime.until( endTime, ChronoUnit.SECONDS);
			if(seconds == 0)
			{
				long milliSeconds = startTime.until( endTime, ChronoUnit.MILLIS);
				System.out.println("running time:  "+ milliSeconds+" MILLISECONDS");
			}
			else
			{
				System.out.println("running time:  "+ seconds+" SECONDS");
			}
			final long MEGABYTE_FACTOR = 1024L * 1024L;
		}
		

	}

	private static void dfsmain() {
		System.out.println("Starting DFS to solve 8-puzzle");
		LocalDateTime startTime = LocalDateTime.now();
		DFS bfs = new DFS("1","2","0","3","4","5","6","7","8");
		boolean success = bfs.solve();
		LocalDateTime endTime = LocalDateTime.now();
		
		if(success)
		{
			System.out.println("\t\t\tStatistics:");
			System.out.println("Path to goal: " + bfs.getCurrentNode().getPathToGoal());
			System.out.println("Cost to goal: " + bfs.getCurrentNode().getCostOfPath());
			System.out.println("Nodes expanded: "+bfs.getExploredNodes().size());
			System.out.println("Search depth: " + bfs.getCurrentNode().getSearchDepth());
			System.out.println("Max Search depth: " + bfs.getCurrentNode().getMaxSearchDepth());
			{
				long milliSeconds = startTime.until( endTime, ChronoUnit.MILLIS);
			}}}
	
	private static void astarmain() {
		new Astar();

	}

	}



