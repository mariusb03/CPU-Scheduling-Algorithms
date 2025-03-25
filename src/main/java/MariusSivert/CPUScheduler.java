package MariusSivert;

import java.util.*;

/**
 * A CPU Scheduler that implements:
 * 1. First Come First Serve (FCFS) Scheduling
 * 2. Preemptive Priority Scheduling
 *
 * Accepts user input from the console and outputs average waiting and turnaround times.
 */
public class CPUScheduler {

    /**
     * First Come First Serve Scheduling
     * Non-preemptive algorithm that executes processes in order of arrival.
     *
     * @param processes List of processes to be scheduled
     */
    public static void fcfs(List<Process> processes) {
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        int totalWaiting = 0, totalTurnaround = 0;

        for (Process p : processes) {
            // Wait if no process has arrived yet
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }

            // Set the process start time
            p.startTime = currentTime;

            // Waiting time = time CPU started - arrival time
            p.waitingTime = currentTime - p.arrivalTime;

            // Advance current time by burst time (non-preemptive)
            currentTime += p.burstTime;

            // Turnaround time = completion time - arrival time
            p.turnaroundTime = currentTime - p.arrivalTime;

            totalWaiting += p.waitingTime;
            totalTurnaround += p.turnaroundTime;
        }

        // Output results
        System.out.println("\n--- FCFS Scheduling ---");
        for (Process p : processes) {
            System.out.println(p.id + ": Waiting = " + p.waitingTime + ", Turnaround = " + p.turnaroundTime);
        }
        System.out.println("Average Waiting Time: " + (float) totalWaiting / processes.size());
        System.out.println("Average Turnaround Time: " + (float) totalTurnaround / processes.size());
    }

    /**
     * Preemptive Priority Scheduling
     * CPU always runs the process with the highest priority (lowest number).
     *
     * @param processes List of processes to be scheduled
     */
    public static void preemptivePriority(List<Process> processes) {
        int currentTime = 0, completed = 0;
        int totalWaiting = 0, totalTurnaround = 0;
        int n = processes.size();

        // Continue until all processes are completed
        while (completed < n) {
            Process current = null;

            // Find available process with highest priority
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (current == null || p.priority < current.priority) {
                        current = p;
                    }
                }
            }

            // If a process is ready to run
            if (current != null) {
                // Mark start time once
                if (current.startTime == -1) {
                    current.startTime = currentTime;
                }

                // Run process for one unit of time (preemptive)
                current.remainingTime--;

                // If process finishes
                if (current.remainingTime == 0) {
                    current.completionTime = currentTime + 1;
                    current.turnaroundTime = current.completionTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;

                    totalWaiting += current.waitingTime;
                    totalTurnaround += current.turnaroundTime;
                    completed++;
                }
            }

            // Increment time
            currentTime++;
        }

        // Output results
        System.out.println("\n--- Preemptive Priority Scheduling ---");
        for (Process p : processes) {
            System.out.println(p.id + ": Waiting = " + p.waitingTime + ", Turnaround = " + p.turnaroundTime);
        }
        System.out.println("Average Waiting Time: " + (float) totalWaiting / n);
        System.out.println("Average Turnaround Time: " + (float) totalTurnaround / n);
    }

    /**
     * The main method that runs both scheduling algorithms.
     * Accepts user input for process details and displays the output.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> fcfsProcesses = new ArrayList<>();
        List<Process> priorityProcesses = new ArrayList<>();

        // User input for number of processes
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        // Get input for FCFS
        System.out.println("\nEnter FCFS Process details:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process ID: ");
            String id = scanner.next();
            System.out.print("Arrival Time: ");
            int at = scanner.nextInt();
            System.out.print("Burst Time: ");
            int bt = scanner.nextInt();

            // Priority is not used for FCFS, set dummy value
            fcfsProcesses.add(new Process(id, at, bt, 0));
        }

        // Run FCFS algorithm
        fcfs(fcfsProcesses);

        // Get input for Preemptive Priority
        System.out.println("\nEnter Preemptive Priority Process details:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process ID: ");
            String id = scanner.next();
            System.out.print("Arrival Time: ");
            int at = scanner.nextInt();
            System.out.print("Burst Time: ");
            int bt = scanner.nextInt();
            System.out.print("Priority (lower = higher): ");
            int pr = scanner.nextInt();

            priorityProcesses.add(new Process(id, at, bt, pr));
        }

        // Run Preemptive Priority algorithm
        preemptivePriority(priorityProcesses);
        scanner.close();
    }
}