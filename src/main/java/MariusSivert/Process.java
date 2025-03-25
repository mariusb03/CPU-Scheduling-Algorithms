package MariusSivert;

/**
 * class that represents a processes in the CPU scheduler, that is has the following attributes:
 * id: the id of the process
 * arrivalTime: the time the process arrives
 * burstTime: the time the process takes to complete
 * priority: the priority of the process
 */
public class Process {
    String id;
    int arrivalTime, burstTime, remainingTime, priority;
    int waitingTime = 0, turnaroundTime = 0, startTime = -1, completionTime = 0;

    /**
     * Constructor for the Process class
     * @param id
     * @param arrivalTime
     * @param burstTime
     * @param priority
     */
    public Process(String id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}