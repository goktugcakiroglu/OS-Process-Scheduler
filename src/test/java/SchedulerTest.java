import java.util.List;
import java.util.ArrayList;

public class SchedulerTest {

    public static void main(String[] args) {
        System.out.println("=== OS PROCESS SCHEDULER UNIT TESTS STARTING ===");
        
        try {
            testFCFSSchedulingOrder();
            System.out.println("[SUCCESS] FCFS Strategy Test Passed.");
        } catch (Exception e) {
            System.err.println("[FAILURE] FCFS Strategy Test Failed!");
            e.printStackTrace();
            System.exit(1); // Test başarısız olursa CI/CD'ye hata bildir
        }

        System.out.println("=== ALL TESTS PASSED SUCCESSFULLY ===");
        System.exit(0);
    }

    public static void testFCFSSchedulingOrder() throws Exception {
        // 1. Arrange: Süreç nesnelerini senin orijinal constructor yapına göre oluşturuyoruz
        // Parametreler sırasıyla: processId, arrivalTime, cpuBurst1, ioBurst, cpuBurst2
        Process p1 = new Process("P1", 0, 5, 2, 3); 
        Process p2 = new Process("P2", 2, 3, 1, 2);
        
        List<Process> readyQueue = new ArrayList<>();
        // FCFS mantığı için readyQueue'ya süreçleri ekliyoruz
        readyQueue.add(p1);
        readyQueue.add(p2);
        
        FCFSStrategy scheduler = new FCFSStrategy();

        // 2. Act: Stratejinin bir sonraki süreci seçmesini sağla
        // getNextProcess parametreleri: currentCpuProcess, readyQueue, currentQuantumTick, quantum
        Process nextProcess = scheduler.getNextProcess(null, readyQueue, 0, 0);

        // 3. Assert: İlk gelen sürecin (P1) doğru şekilde seçildiğini doğrula
        if (nextProcess == null) {
            throw new Exception("Test Failed: Selected process is null!");
        }
        
        if (!nextProcess.processId.equals("P1")) {
            throw new Exception("Test Failed: FCFS should select P1, but selected: " + nextProcess.processId);
        }
    }
}
