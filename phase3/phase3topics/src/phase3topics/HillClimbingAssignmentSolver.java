package phase3topics;

import java.util.Random;
import java.util.Scanner;

public class HillClimbingAssignmentSolver {

    // Method to perform the assignment using hill climbing
    public static int[] doHillClimbingAssignment(int[][] costMatrix) {
        int numTasks = costMatrix.length;
        Random rand = new Random();
        
        // Initialize a complete assignment
        int[] assignment = initializeAssignment(numTasks);
        
        // Shuffle the initial assignment
        shuffleAssignment(assignment, rand);
        
        // Compute initial total cost
        int currentCost = computeTotalCost(assignment, costMatrix);
        
        // Perform hill climbing search
        while (true) {
            // Generate a neighboring solution by swapping two tasks
            int[] neighbor = assignment.clone();
            int task1 = rand.nextInt(numTasks);
            int task2 = rand.nextInt(numTasks);
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of the matrix (n): ");
        int n = scanner.nextInt();

        // Generate a random cost matrix
        int[][] costMatrix = generateRandomCostMatrix(n);

        // Print the original cost matrix
        System.out.println("Original Cost Matrix:");
        printMatrix(costMatrix);

        // Measure the computational time
        long startTime = System.currentTimeMillis();

        // Call the hill climbing function
        int[] resultAssignment = doHillClimbingAssignment(costMatrix);

        // Print the result assignment and the computational time
        System.out.println("\nTask assignment:");
        printAssignmentDescription(resultAssignment, costMatrix);
        System.out.println("\nComputational time: " + (System.currentTimeMillis() - startTime) + " milliseconds");

        scanner.close();
    }

    // Method to generate a random cost matrix
    public static int[][] generateRandomCostMatrix(int n) {
        Random rand = new Random();
        int[][] costMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costMatrix[i][j] = rand.nextInt(10) + 1;
            }
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
