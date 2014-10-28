package com.sopra.battlecode.bc2014;

public class LinuxAndMackerels {

	/** The transport capacity of linux */
	private final int transportCapacity;

	/** The initial stock */
	private final int initialStock;

	/**
	 * maxDistances[i] is the maximum distance linux is able to walk with
	 * initial stock "i"
	 */
	private final int[] maxDistances;

	LinuxAndMackerels(int transportCapacity, int initialStock) {
		this.initialStock = initialStock;
		this.transportCapacity = transportCapacity;
		this.maxDistances = new int[this.initialStock + 1];
	}

	/**
	 * <p>
	 * Given an initial "stock" and a predefined "distance" to reach, this
	 * method computes the maximal number of fishes that Linux can put at
	 * destination WITHOUT creating intermediate stocks (i.e Linux always does
	 * trips of the predefined distance to the left or right).
	 * </p>
	 * 
	 * <p>
	 * Here is an example for transportCapacity = 50, stock = 165 and distance =
	 * 10
	 * 
	 * <pre>
	 * Step 1: Take 50 fishes and bring them at destination consuming 10 fishes. 
	 *         => Now we have 40 fishes at destination, 115 at starting point
	 * Step 2: Is it worth going back to initial position an gather more fishes ? Yes because going back and forth will cost us 2*10 fishes, but we can take 50 so we can add 30 at destination.
	 *         => After doing the roundtrip we are back at destination with 70 fishes, 65 at starting point
	 * Step 3: We do another roundtrip
	 *         => We now have 100 fishes at destination, 15 at starting point
	 * (End) : Is it not worth going back at start position, it will cost us 20 fishes but there are only 15 at starting point, so if we do it we will end up with 95. So stop here.
	 *         => The maximum number of fishes that can reach destination is 100
	 * </pre>
	 * 
	 * </p>
	 */
	int countFishesAtDestination(int stock, int distance) {
		if (transportCapacity < distance || stock < distance) {
			throw new IllegalStateException(String.format("Cannot reach distance %d", distance));
		}

		boolean isRoundtripPossible = stock >= 3 * distance && transportCapacity >= 2 * distance;
		boolean isWorthDoingRoundtrip = stock - transportCapacity >= 2 * distance;
		if (!isRoundtripPossible || !isWorthDoingRoundtrip) {
			// Single trip to destination
			return Math.min(transportCapacity, stock) - distance;
		} else {
			// Go to destination and come back to starting point
			int newFishesAtDestination = transportCapacity - 2 * distance;
			int fishesAtStartingPoint = stock - transportCapacity;
			return newFishesAtDestination + countFishesAtDestination(fishesAtStartingPoint, distance);
		}
	}

	/**
	 * <p>
	 * We suppose (correctly) that to reach maximum distance we can use
	 * following strategy. Starting with an initial stock s0,
	 * 
	 * <ul>
	 * <li>Choose a first distance d1 and put as much fishes as possible at d1
	 * (doing multiple roundtrips between 0 and d1). We now have a new stock s1
	 * = countFishesAtDestination(s0,d1) at position d1</li>
	 * <li>Choose a second distance d2 and put as much fishes as possible at d2,
	 * doing multiple roundtrips between d1 and d2. We not have a new stock s2
	 * at d2</li>
	 * <li>Repeat the process with distances d3,d4...dN</li>
	 * </ul>
	 * So the whole point is to find the right d1,d2,...dN positions to reach
	 * maximum distance.
	 * <p>
	 * 
	 * <p>
	 * To find the best (d1,d2...dN) positions to create the intermediate
	 * stocks, we use <a
	 * href="http://en.wikipedia.org/wiki/Dynamic_programming}">dynamic
	 * programming</a>.<br/>
	 * Suppose that you know all the maximal distances for an initial stock of
	 * 1,2,3...N-1. Then to find the maximal distance for initial stock N we can
	 * try to:
	 * <ul>
	 * <li>choose a first intermediate stop d1 at distance 1 km. If we do that,
	 * we can move s1=countFishesAtDestination(s0,1) fishes at position 1km and
	 * then travel the known maximal distance with stock s1</li>
	 * <li>choose a first intermediate stop d1=2km. We can then reach 2 +
	 * maxDistances[countFishesAtDestination(s0,2)]</li>
	 * <li>... choose a first stop at d1=k. We could then reach distance: k +
	 * maxDistances[countFishesAtDestination(s0,2)]</li>
	 * </ul>
	 * and take the maximum of all those trials.
	 * </p>
	 * 
	 * <p>
	 * This method computes maximalDistance with initial stock s0 and supposes
	 * we already know all maximal distances for stocks smaller than s0 (stored
	 * in array maxDistances)
	 */
	int computeMaxDistanceKnowingMaxDistancesForSmallerStocks(int s0) {
		int maxDistance = 0;
		int maxDistanceForFirstIntermediateStock = Math.min(transportCapacity, s0);
		for (int k = 1; k <= maxDistanceForFirstIntermediateStock; k++) {
			int remaining = countFishesAtDestination(s0, k);
			int val = k + maxDistances[remaining];
			if (val > maxDistance) {
				maxDistance = val;
			}
		}
		return maxDistance;
	}

	int solve() {
		maxDistances[0] = 0;
		for (int s = 1; s <= initialStock; s++) {
			maxDistances[s] = computeMaxDistanceKnowingMaxDistancesForSmallerStocks(s);
		}
		return maxDistances[initialStock];
	}

	public static void main(String[] args) {
		LinuxAndMackerels solver = new LinuxAndMackerels(15, 134);
		System.out.format("With stock %d and transport capacity %d linux can go to distance %d", solver.initialStock, solver.transportCapacity,
				solver.solve());
	}
}
