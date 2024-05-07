package phase3topics;

import java.util.Random;

public class HillClimbingAssignmentSolver {

    public static int[] HillClimbingAssignment(int n) {
        int[][] costMatrix = generateCostMatrix(n);
        int[] assignment = initializeAssignment(n);
        shuffleAssignment(assignment, new Random());
        int currentCost = computeTotalCost(assignment, costMatrix);
        while (true) {
            int[] neighbor = assignment.clone();
            Random random = new Random();
            int task1 = random.nextInt(n);
            int task2 = random.nextInt(n);
            swapAssignments(neighbor, task1, task2);
            int neighborCost = computeTotalCost(neighbor, costMatrix);
            if (neighborCost < currentCost) {
                assignment = neighbor;
                currentCost = neighborCost;
            } else {
                break;
            }
        }
        return assignment;
    }

    private static int[] initializeAssignment(int numTasks) {
        int[] assignment = new int[numTasks];
        for (int task = 0; task < numTasks; task++) {
            assignment[task] = task;
        }
        return assignment;
    }

    private static void shuffleAssignment(int[] assignment, Random rand) {
        int numTasks = assignment.length;
        for (int i = numTasks - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = assignment[i];
            assignment[i] = assignment[j];
            assignment[j] = temp;
        }
    }

    private static void swapAssignments(int[] assignment, int task1, int task2) {
        int temp = assignment[task1];
        assignment[task1] = assignment[task2];
        assignment[task2] = temp;
    }

    public static int computeTotalCost(int[] assignment, int[][] costMatrix) {
        int totalCost = 0;
        for (int task = 0; task < assignment.length; task++) {
            int worker = assignment[task];
            totalCost += costMatrix[task][worker];
        }
        return totalCost;
    }

    public static void main(String[] args) {
        int n = 4;
        int[] resultAssignment = HillClimbingAssignment(n);
        int[][] costMatrix = generateCostMatrix(n);
        System.out.println("Original Cost Matrix:");
        printMatrix(costMatrix);
        long startTime = System.currentTimeMillis();
        System.out.println("\nTask assignment:");
        printAssignmentDescription(resultAssignment, costMatrix);
        System.out.println("\nComputational time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }

    public static int[][] generateCostMatrix(int n) {
        int[][] costMatrix = {
            {9, 8, 7, 6},
            {3, 6, 5, 9},
            {2, 8, 6, 1},
            {4, 3, 7, 9}
        };
        if (costMatrix.length != n || costMatrix[0].length != n) {
            throw new IllegalArgumentException("The provided cost matrix does not match the size " + n);
        }
        return costMatrix;
    }

    public static void printMatrix(int[][] matrix) {
        int numTasks = matrix.length;
        System.out.print("Agent");
        for (int j = 0; j < numTasks; j++) {
            System.out.printf("%6d", j + 1);
        }
        System.out.println();
        for (int i = 0; i < numTasks; i++) {
            System.out.printf("Task %2d:", i + 1);
            for (int j = 0; j < numTasks; j++) {
                System.out.printf("%6d", matrix[i][j]);
            }
            System.out.println();
        }
    }

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
