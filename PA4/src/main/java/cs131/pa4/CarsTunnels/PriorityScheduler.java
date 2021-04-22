package cs131.pa4.CarsTunnels;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import cs131.pa4.Abstract.Scheduler;
import cs131.pa4.Abstract.Tunnel;
import cs131.pa4.Abstract.Vehicle;

/**
 * The priority scheduler assigns vehicles to tunnels based on their priority
 * It extends the Scheduler class.
 * @author cs131a
 *
 */
public class PriorityScheduler extends Scheduler{
	
	// Keep references to BasicTunnels as private field
	private Collection<Tunnel> basicTunnels;
	
	// A ReentrantLock type variable for locking shared sources
	private ReentrantLock lock;
	
	// A Condition Variable for lock
	private Condition canEnter; 
	
	// A PrioirtyQueue for keeping waiting vehicles based on their priorities
	// Vehicle with higher priority goes first
	private PriorityQueue<Vehicle> waitingVehicles;
	
	// A Map variable keeps track of which Vehicle in which Tunnel
	private Map<Vehicle, Tunnel> vehicleTunnel; 
	

	/**
	 * Creates an instance of a priority scheduler with the given name and tunnels
	 * @param name the name of the priority scheduler
	 * @param tunnels the tunnels where the vehicles will be scheduled to
	 */
	public PriorityScheduler(String name, Collection<Tunnel> tunnels) {
		super(name, tunnels);		
		basicTunnels = tunnels;
		vehicleTunnel = new HashMap<Vehicle, Tunnel>();
		
		// Initialize the waiting PriorityQueue with customized priority comparator 
		waitingVehicles = new PriorityQueue<Vehicle>((v1, v2) -> v2.getPriority() - v1.getPriority());
		
		lock = new ReentrantLock();
		// Initialize the condition variable based on lock
		canEnter = lock.newCondition();
	}

	
	@Override
	public Tunnel admit(Vehicle vehicle) {	
		// Put a lock
		lock.lock();
		
		try {
			// Adds the given vehicle into the waiting Priority Queue
			waitingVehicles.offer(vehicle);
			
			// Sets a running while loop until the given vehicle enters into one of those basic Tunnels successfully 
			while (true) {
				// Checks whether there are vehicles with higher priorities already in the waiting queue
				// If it is true, then skips the else-part, and waits the condition variable to be notified
				if (existHigherPriority(vehicle)) {
					
				} else {
					// Traverse all the basic tunnels to check whether there is any vacancy available
					for (Tunnel t : basicTunnels) {
						if (t.tryToEnter(vehicle)) {
							// If there is vacancy available 
							//		1. move the vehicle out of the waiting queue into basic tunnel
							waitingVehicles.poll();
							// 		2. track the vehicle with according tunnel
							vehicleTunnel.put(vehicle, t);
							return t;
						}
					}	
				}
				// put the condition variable await until it is notified somewhere
				canEnter.await();
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// Release the lock
			lock.unlock();
		}		
		return null;
	}
	
	
	@Override
	public void exit(Vehicle vehicle) {
		// Put a lock
		lock.lock();	
		try {
			// Get the tunnel according to the vehicle key
			Tunnel currentTunnel = vehicleTunnel.get(vehicle);	
			// Call the exitTunnel method 
			currentTunnel.exitTunnel(vehicle);
			// Remove the vehicle from the current working basic tunnels 
			vehicleTunnel.remove(vehicle);
			// Signal the condition variable
			canEnter.signalAll();
			
		} finally {
			// Release the lock
			lock.unlock();
		}
	}
	
	
	/**
	 * A private method checks whether there are already vehicles with higher priority in the waiting queue
	 * @param the given Vehicle v
	 * @return true if vehicles with higher priority exists, false otherwise
	 */
	private boolean existHigherPriority(Vehicle v) {
		
		return !waitingVehicles.peek().equals(v);
	}
	
}
