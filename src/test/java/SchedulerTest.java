import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class SchedulerTest {

    @Test
    public void testFCFSSchedulingOrder() {
        Process p1 = new Process("P1", 0, 5); // ID, Arrival Time, CPU Burst
        Process p2 = new Process("P2", 2, 3);
        
        List<Process> processes = new ArrayList<>();
        processes.add(p2); // Listeye bilerek karışık ekliyoruz
        processes.add(p1); 
      
        FCFSStrategy scheduler = new FCFSStrategy();

        // 2. Act: Algoritmayı çalıştır
        List<Process> result = scheduler.schedule(processes);

        // 3. Assert: İlk gelenin (P1, arrival:0) önce çalışıp çalışmadığını doğrula
        assertNotNull(result, "Sonuç listesi boş olmamalı!");
        assertEquals("P1", result.get(0).getId(), "FCFS mantığına göre ilk süreç P1 olmalıydı!");
        assertEquals("P2", result.get(1).getId(), "İkinci süreç P2 olmalıydı!");
    }
}
