package phase3topics;

import java.util.Random;

public class HillClimbingAssignmentSolver {

    // Method to perform the assignment using hill climbing
    public static int[] HillClimbingAssignment(int n) {
        // Generate a constant cost matrix
        int[][] costMatrix = generateCostMatrix(n);
        
        // Initialize a complete assignment
        int[] assignment = initializeAssignment(n);
        
        // Shuffle the initial assignment
        shuffleAssignment(assignment, new Random());
        
        // Compute initial total cost
        int currentCost = computeTotalCost(assignment, costMatrix);
        
        // Perform hill climbing search
        while (true) {
            // Generate a neighboring solution by swapping two tasks
            int[] neighbor = assignment.clone();
            Random random = new Random();
            int task1 = random.nextInt(n);
            int task2 = random.nextInt(n);
            swapAssignments(neighbor, task1, task2);
            
            // Compute the cost of the neighboring solution
            int neighborCost = computeTotalCost(neighbor, costMatrix);
            
            // If the neighboring solution is better, accept it
            if (neighborCost < currentCost) {
                assignment = neighbor;
                currentCost = neighborCost;
            } else {
                // No improvement, stop the search
                break;
            }
        }

        // Return the final assignment
        return assignment;
    }

    // Method to initialize the assignment array with default values
    private static int[] initializeAssignment(int numTasks) {
        int[] assignment = new int[numTasks];
        for (int task = 0; task < numTasks; task++) {
            assignment[task] = task;
        }
        return assignment;
    }

    // Method to shuffle the assignment array
    private static void shuffleAssignment(int[] assignment, Random rand) {
        int numTasks = assignment.length;
        for (int i = numTasks - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = assignment[i];
            assignment[i] = assignment[j];
            assignment[j] = temp;
        }
    }

    // Method to swap assignments of two tasks
    private static void swapAssignments(int[] assignment, int task1, int task2) {
        int temp = assignment[task1];
        assignment[task1] = assignment[task2];
        assignment[task2] = temp;
    }

    // Method to compute the total cost of an assignment
    public static int computeTotalCost(int[] assignment, int[][] costMatrix) {
        int totalCost = 0;
        for (int task = 0; task < assignment.length; task++) {
            int worker = assignment[task];
            totalCost += costMatrix[task][worker];
        }
        return totalCost;
    }

    // Main method to run the hill climbing local search algorithm
    public static void main(String[] args) {
        int n = 4; // Size of the matrix

        // Call the hill climbing function
        int[] resultAssignment = HillClimbingAssignment(n);

        // Generate a constant cost matrix
        int[][] costMatrix = generateCostMatrix(n);

        // Print the original cost matrix
        System.out.println("Original Cost Matrix:");
        printMatrix(costMatrix);

        // Measure the computational time
        long startTime = System.currentTimeMillis();

        // Print the result assignment and the computational time
        System.out.println("\nTask assignment:");
        printAssignmentDescription(resultAssignment, costMatrix);
        System.out.println("\nComputational time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }

    // Method to generate a constant cost matrix
    public static int[][] generateCostMatrix(int n) {
        // Define a constant 4x4 cost matrix
        int[][] costMatrix = {
            {9, 8, 7, 6},
            {3, 6, 5, 9},
            {2, 8, 6, 1},
            {4, 3, 7, 9}
        };

        // Verify that the size of the matrix matches the expected size `n`
        if (costMatrix.length != n || costMatrix[0].length != n) {
            throw new IllegalArgumentException("The provided cost matrix does not match the size " + n);
        }

        return costMatrix;
    }

    // Method to print the cost matrix
    public static void printMatrix(int[][] matrix) {
        int numTasks = matrix.length;

        // Print column headers (agents)
        System.out.print("Agent");
        for (int j = 0; j < numTasks; j++) {
            System.out.printf("%6d", j + 1); // Agent numbers (1-based indexing)
        }
        System.out.println();

        // Print each row (task)
        for (int i = 0; i < numTasks; i++) {
            System.out.printf("Task %2d:", i + 1); // Task numbers (1-based indexing)
            for (int j = 0; j < numTasks; j++) {
                System.out.printf("%6d", matrix[i][j]); // Cost values
            }
            System.out.println(); // Move to the next line after each task row
        }
    }

    // Method to print the assignment description
    public static void printAssignmentDescription(int[] assignment, int[][] costMatrix) {
        int totalCost = 0;
        for (int task = 0; task < assignment.length; task++) {
            int worker = assignment[task];
            int cost = costMatrix[task][worker];
            totalCost += cost;
            System.out.println("Task " + (task + 1) + " is assigned to Agent " + (worker + 1) + " (Cost: " + cost + ")");
        }
        System.out.println("Total cost: " + totalCost);
    }
}
