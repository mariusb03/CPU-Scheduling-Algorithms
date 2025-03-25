package MariusSivert;

import java.util.*;

public class CPUScheduler {

    public static void fcfs(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        int totalWaiting = 0, totalTurnaround = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.startTime = currentTime;
            p.waitingTime = currentTime - p.arrivalTime;
            currentTime += p.burstTime;
            p.turnaroundTime = currentTime - p.arrivalTime;

            totalWaiting += p.waitingTime;
            totalTurnaround += p.turnaroundTime;
        }

        System.out.println("\n--- FCFS Scheduling ---");
        for (Process p : processes) {
            System.out.println(p.id + ": Waiting = " + p.waitingTime + ", Turnaround = " + p.turnaroundTime);
        }
        System.out.println("Average Waiting Time: " + (float) totalWaiting / processes.size());
        System.out.println("Average Turnaround Time: " + (float) totalTurnaround / processes.size());
    }

    public static void preemptivePriority(List<Process> processes) {
        int currentTime = 0, completed = 0;
        int totalWaiting = 0, totalTurnaround = 0;
        int n = processes.size();

        while (completed < n) {
            Process current = null;
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (current == null || p.priority < current.priority) {
                        current = p;
                    }
                }
            }

            if (current != null) {
                if (current.startTime == -1) {
                    current.startTime = currentTime;
                }

                current.remainingTime--;
                if (current.remainingTime == 0) {
                    current.completionTime = currentTime + 1;
                    current.turnaroundTime = current.completionTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;
                    totalWaiting += current.waitingTime;
                    totalTurnaround += current.turnaroundTime;
                    completed++;
                }
            }

            currentTime++;
        }

        System.out.println("\n--- Preemptive Priority Scheduling ---");
        for (Process p : processes) {
            System.out.println(p.id + ": Waiting = " + p.waitingTime + ", Turnaround = " + p.turnaroundTime);
        }
        System.out.println("Average Waiting Time: " + (float) totalWaiting / n);
        System.out.println("Average Turnaround Time: " + (float) totalTurnaround / n);
    }
}