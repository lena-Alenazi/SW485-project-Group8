
import java.util.Scanner;

public class AssignmentProblemSolver {

    public static int[] doAssignment(int[][] costMatrix) {
        int numTasks = costMatrix.length;
        int[] assignedAgentsForForwardChecking = new int[numTasks];
        int[] assignment = new int[numTasks];

        for (int task = 0; task < numTasks; task++) {
            int minCost = Integer.MAX_VALUE;
            int assignedWorker = -1;

            for (int worker = 0; worker < numTasks; worker++) {
                if (assignedAgentsForForwardChecking[worker] == 0 && costMatrix[task][worker] < minCost) {
                    minCost = costMatrix[task][worker];
                    assignedWorker = worker;
                }
            }

            assignment[task] = assignedWorker;
            assignedAgentsForForwardChecking[assignedWorker] = 1;
        }
        return assignment;
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
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
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
            System.out.println("Task " + (task + 1) + " is assigned to Worker " + (worker + 1) + " (Cost: " + cost + ")");
        }
        System.out.println("Total cost: " + totalCost);
    }

    public static void main(String[] args) {
        int n = 4;

        System.out.println("\nInstance:");
        int[][] costMatrix = generateCostMatrix(n);
        System.out.println("Constant Cost Matrix:");
        printMatrix(costMatrix);

        long startTime = System.currentTimeMillis();

        int[] resultAssignment = doAssignment(costMatrix);
        System.out.println("\nTask assignment:");
        printAssignmentDescription(resultAssignment, costMatrix);
        System.out.println("Computational time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }
}
