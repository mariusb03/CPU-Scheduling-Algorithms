package MariusSivert;

import static MariusSivert.CPUScheduler.fcfs;
import static MariusSivert.CPUScheduler.preemptivePriority;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> fcfsProcesses = new ArrayList<>();
        List<Process> priorityProcesses = new ArrayList<>();

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        System.out.println("\nEnter FCFS Process details:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process ID: ");
            String id = scanner.next();
            System.out.print("Arrival Time: ");
            int at = scanner.nextInt();
            System.out.print("Burst Time: ");
            int bt = scanner.nextInt();
            fcfsProcesses.add(new Process(id, at, bt, 0));  // Priority unused in FCFS
        }

        fcfs(fcfsProcesses);

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

        preemptivePriority(priorityProcesses);
        scanner.close();
    }
}