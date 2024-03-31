
import java.util.Scanner;

public class testingAssignment {

  
  
    public static void printAssignmentDescription(int[] assignment) {
        for (int task = 0; task < assignment.length; task++) {
            System.out.println("Task " + (task + 1) + " is assigned to Worker " + (assignment[task] + 1));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of the matrix (n): ");
        int n = scanner.nextInt();

        // Generate a dummy cost matrix
        int[][] costMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costMatrix[i][j] = i + j;
            }
        }

        // Print the original matrix
        System.out.println("Original Cost Matrix:");
        AssignmentProblemSolver.printMatrix(costMatrix);

        // Measure the computational time
        long startTime = System.currentTimeMillis();

        // Call the function
        int[] resultAssignment = AssignmentProblemSolver.doAssignment(costMatrix);

        // Print the result and computational time
        System.out.println("\nTask assignment:");
        printAssignmentDescription(resultAssignment);
        System.out.println("\nComputational time: " + (System.currentTimeMillis() - startTime) + " milliseconds");

        scanner.close();
    }
}