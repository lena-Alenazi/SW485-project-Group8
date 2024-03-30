import java.util.PriorityQueue;
//import AssignmentProblemSolver.State;

public class AssignmentProblemSolver {
    // Variables
    int[][] assignments; // Matrix to represent assignments: Xij

    // Constructor
    public AssignmentProblemSolver(int numTasks, int numAgents) {
        assignments = new int[numTasks][numAgents];
        // Initialize assignments matrix, initially all set to 0 (no task assigned)
        for (int i = 0; i < numTasks; i++) {
            for (int j = 0; j < numAgents; j++) {
                assignments[i][j] = 0;
            }
        }
    }

    // Method to assign task i to agent j
    public void assignTaskToAgent(int taskIndex, int agentIndex) {
        // Check if the assignment violates constraints
        if (!isTaskAssignedToAnyAgent(taskIndex) && !isAgentAssignedToAnyTask(agentIndex)) {
            assignments[taskIndex][agentIndex] = 1; // Assign task i to agent j
        } else {
            // Handle constraint violation (e.g., throw an exception, print an error message)
            System.err.println("Constraint violation: Task or agent is already assigned!");
        }
    }

    // Method to check if task i is assigned to any agent
    public boolean isTaskAssignedToAnyAgent(int taskIndex) {
        for (int j = 0; j < assignments[taskIndex].length; j++) {
            if (assignments[taskIndex][j] == 1) {
                return true;
            }
        }
        return false;
    }

    // Method to check if agent j is assigned to any task
    public boolean isAgentAssignedToAnyTask(int agentIndex) {
        for (int i = 0; i < assignments.length; i++) {
            if (assignments[i][agentIndex] == 1) {
                return true;
            }
        }
        return false;
    }

    // Method to solve the assignment problem using A* algorithm
    public void solveAssignmentProblem() {
        int numTasks = assignments.length;
        int numAgents = assignments[0].length;

        // Initialize priority queue for A* search
        PriorityQueue<State> openList = new PriorityQueue<>();

        // Initial state: no task is assigned yet
        State initialState = new State(new int[numTasks][numAgents], 0);
        openList.add(initialState);

        while (!openList.isEmpty()) {
            // Get the state with minimum f(n) from the open list
            State currentState = openList.poll();

            // Check if the current state is a goal state
            if (currentState.isGoalState(numTasks, numAgents)) {
                System.out.println("Goal state reached:");
                currentState.printAssignments();
                return;
            }

            // Generate successors
            for (int i = 0; i < numTasks; i++) {
                // Skip if task i is already assigned
                if (currentState.isTaskAssigned(i)) continue;

                for (int j = 0; j < numAgents; j++) {
                    // Skip if agent j is already assigned to a task
                    if (currentState.isAgentAssigned(j)) continue;

                    // Create a new state by assigning task i to agent j
                    int[][] newAssignments = currentState.cloneAssignments();
                    newAssignments[i][j] = 1;

                    // Calculate cost (objective function)
                    int cost = currentState.cost + getCostForAssignment(i, j);

                    // Create the successor state
                    State successor = new State(newAssignments, cost);

                    // Add the successor to the open list
                    openList.add(successor);
                }
            }
        }

        // If open list is empty and no goal state found, print failure message
        System.out.println("Failed to find a valid assignment.");
    }

    // Method to calculate cost for assigning task i to agent j (example: cost matrix is assumed)
    private int getCostForAssignment(int taskIndex, int agentIndex) {
        // Example cost matrix (replace with your actual cost matrix)
        int[][] costMatrix = {
                {1, 3, 2},
                {2, 4, 3},
                {2,1 , 5}
        };
        return costMatrix[taskIndex][agentIndex];
    }

    // Internal class representing the state of the assignment problem
    private static class State implements Comparable<State> {
        int[][] assignments; // Assignments: Xij
        int cost; // Cost (objective function)

        // Constructor
        public State(int[][] assignments, int cost) {
            this.assignments = assignments;
            this.cost = cost;
        }

        // Method to check if the state is a goal state
     // Method to check if the state is a goal state
        public boolean isGoalState(int numTasks, int numAgents) {
            // Check if each task is assigned exactly once
            for (int i = 0; i < numTasks; i++) {
                int sum = 0;
                for (int j = 0; j < numAgents; j++) {
                    sum += assignments[i][j];
                }
                if (sum != 1) {
                    return false; // Task i is not assigned exactly once
                }
            }
            
            // Check if each agent is assigned exactly once
            for (int j = 0; j < numAgents; j++) {
                int sum = 0;
                for (int i = 0; i < numTasks; i++) {
                    sum += assignments[i][j];
                }
                if (sum != 1) {
                    return false; // Agent j is not assigned exactly once
                }
            }
            
            return true; // All tasks and agents are assigned exactly once
        }


        // Method to check if task i is assigned
        public boolean isTaskAssigned(int taskIndex) {
            for (int j = 0; j < assignments[taskIndex].length; j++) {
                if (assignments[taskIndex][j] == 1) {
                    return true;
                }
            }
            return false;
        }

        // Method to check if agent j is assigned
        public boolean isAgentAssigned(int agentIndex) {
            for (int i = 0; i < assignments.length; i++) {
                if (assignments[i][agentIndex] == 1) {
                    return true;
                }
            }
            return false;
        }

        // Method to clone assignments array
        public int[][] cloneAssignments() {
            int[][] clonedAssignments = new int[assignments.length][assignments[0].length];
            for (int i = 0; i < assignments.length; i++) {
                System.arraycopy(assignments[i], 0, clonedAssignments[i], 0, assignments[i].length);
            }
            return clonedAssignments;
        }

        // Method to compare states based on cost
        @Override
        public int compareTo(State other) {
            return Integer.compare(cost, other.cost);
        }

        // Method to print assignments matrix (for debugging purposes)
        public void printAssignments() {
            for (int i = 0; i < assignments.length; i++) {
                for (int j = 0; j < assignments[i].length; j++) {
                    System.out.print(assignments[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

 // Main method for testing
    public static void main(String[] args) {
        // Example usage
        AssignmentProblemSolver solver = new AssignmentProblemSolver(3, 3); // Example: 3 tasks, 3 agents
        solver.solveAssignmentProblem();
    }

}