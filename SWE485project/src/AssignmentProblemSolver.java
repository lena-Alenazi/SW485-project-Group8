import java.util.Scanner;
import java.util.Random;

public class AssignmentProblemSolver {

    // Method to perform the assignment
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

    // Method to print the matrix
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    // Method to print assignment description
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of the matrix (n): ");
        int n = scanner.nextInt();
        System.out.print("Enter the number of instances to test: ");
        int numInstances = scanner.nextInt();
        
        for (int instance = 0; instance < numInstances; instance++) {
            System.out.println("\nInstance " + (instance + 1) + ":");

            int[][] costMatrix;
            System.out.print("Do you want to enter your own cost matrix? (y/n): ");
            if (scanner.next().toLowerCase().equals("y")) {
                costMatrix = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print("Enter cost for task " + (i + 1) + " and worker " + (j + 1) + ": ");
                        costMatrix[i][j] = scanner.nextInt();
                    }
                }
                System.out.println("User-Entered Cost Matrix:");
            } else {
                costMatrix = generateRandomCostMatrix(n);
                System.out.println("Randomly Generated Cost Matrix:");
            }
            printMatrix(costMatrix);

            long startTime = System.currentTimeMillis();

            int[] resultAssignment = doAssignment(costMatrix);

            System.out.println("\nTask assignment:");
            printAssignmentDescription(resultAssignment, costMatrix);
            System.out.println("Computational time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
        }
        
        scanner.close();
    }
}