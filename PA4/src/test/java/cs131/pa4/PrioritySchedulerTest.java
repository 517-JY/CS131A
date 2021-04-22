package cs131.pa4;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cs131.pa4.Abstract.Direction;
import cs131.pa4.Abstract.Scheduler;
import cs131.pa4.Abstract.Tunnel;
import cs131.pa4.Abstract.Vehicle;
import cs131.pa4.Abstract.Log.Event;
import cs131.pa4.Abstract.Log.EventType;
import cs131.pa4.Abstract.Log.Log;

public class PrioritySchedulerTest {

    private final String prioritySchedulerName = "SCHEDULER";
    
    private static final int NUM_RUNS = 20;

    @BeforeEach
    public void setUp() {
        Tunnel.DEFAULT_LOG.clearLog();
    }

    @BeforeAll
    public static void broadcast() {
        System.out.printf("Running Priority Scheduler Tests using %s \n", TestUtilities.factory.getClass().getCanonicalName());
    }

    private Scheduler setupSimplePriorityScheduler(String name) {
        Collection<Tunnel> tunnels = new ArrayList<Tunnel>();
        tunnels.add(TestUtilities.factory.createNewBasicTunnel(name));
        return TestUtilities.factory.createNewPriorityScheduler(prioritySchedulerName, tunnels);
    }
    

    @RepeatedTest(NUM_RUNS)
    @Timeout(30)
    public void Car_Enter() {
        Vehicle car = TestUtilities.factory.createNewCar(TestUtilities.gbNames[0], Direction.random());
        Scheduler scheduler = setupSimplePriorityScheduler(TestUtilities.mrNames[0]);
        TestUtilities.VehicleEnters(car, scheduler);
    }

    @RepeatedTest(NUM_RUNS)
    @Timeout(30)
    public void Sled_Enter() {
    	Vehicle sled = TestUtilities.factory.createNewSled(TestUtilities.gbNames[0], Direction.random());
    	Scheduler scheduler = setupSimplePriorityScheduler(TestUtilities.mrNames[0]);
        TestUtilities.VehicleEnters(sled, scheduler);
    }
    
    @RepeatedTest(NUM_RUNS)
    @Timeout(30)
    public void Priority() {
    	List<Thread> vehicleThreads = new ArrayList<Thread>();
        Scheduler priorityScheduler = setupSimplePriorityScheduler(TestUtilities.mrNames[0]);
		for (int i=0; i<7; i++) {
			Vehicle car = TestUtilities.factory.createNewCar(Integer.toString(i), Direction.NORTH);
            car.setScheduler(priorityScheduler);
            if (i<3) {
				car.setPriority(4);
			}
			else {
				car.setPriority(i-3);
			}
            Thread sharedThread = new Thread(car);
            sharedThread.start();
            vehicleThreads.add(sharedThread);
		}
		for (Thread t: vehicleThreads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Tunnel.DEFAULT_LOG.addToLog(EventType.END_TEST);
		Log log = Tunnel.DEFAULT_LOG;
		Event currentEvent;
		int i=0;
		Vehicle lastEnteredVehicle = null;
		do {
			currentEvent = log.get();
			if(currentEvent.getEvent() == EventType.ENTER_SUCCESS) {
				if(i++ > 2) {
					if (lastEnteredVehicle == null) {
						lastEnteredVehicle = currentEvent.getVehicle();
					}
					else if (currentEvent.getVehicle().getPriority() > lastEnteredVehicle.getPriority()){
						assertTrue(false, "Vehicle "+currentEvent.getVehicle() + " has higher priority than "+lastEnteredVehicle + " and should run before!");
					}
				}
			}
		} while (!currentEvent.getEvent().equals(EventType.END_TEST));
        if(i!=7)
			assertTrue(false, "Priority Scheduler failed to admit all 7 vehicles");
    }
}
