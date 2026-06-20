import java.util.List;
import java.util.ArrayList;
public class SchedulerTest {
    public static void main(String[] args) {
        try {
            testFCFSSchedulingOrder();
            System.out.println("[SUCCESS] FCFS Strategy Test Passed.");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void testFCFSSchedulingOrder() throws Exception {
        Process p1 = new Process("P1", 0, 5, 2, 3); 
        Process p2 = new Process("P2", 2, 3, 1, 2);
        List<Process> readyQueue = new ArrayList<>();
        readyQueue.add(p1);
        readyQueue.add(p2);
        FCFSStrategy scheduler = new FCFSStrategy();
        Process nextProcess = scheduler.getNextProcess(null, readyQueue, 0, 0);
        if (nextProcess == null || !nextProcess.processId.equals("P1")) {
            throw new Exception("Test Failed!");
        }
    }
}
