package cs131.pa4.Abstract;

import java.util.Collection;

/**
 * This is the scheduler class that is responsible for scheduling vehicles to tunnels based
 * on their priority. It contains a list of tunnels and it will attempt to admit incoming vehicles
 * in these tunnels. If a vehicle cannot enter any of the tunnels, the scheduler will wait 
 * in a condition variable and retry when signaled. This is different than the busy-waiting 
 * approach of PA3.
 * 
 * @author cs131a
 *
 */
public abstract class Scheduler {
	/**
	 * The name of the scheduler
	 */
	private String name;
	/**
	 * The available tunnels where the scheduler can schedule vehicles
	 */
	private Collection<Tunnel> tunnels;
	
	/**
	 * Creates a new scheduler with the given name and tunnels
	 * @param name the name of the scheduler
	 * @param tunnels the tunnels where the vehicles will be scheduled to
	 */
	public Scheduler(String name, Collection<Tunnel> tunnels) {
		this.name = name;
		this.tunnels = tunnels;
	}
	
	/**
	 * Returns the tunnels for this scheduler
	 * @return the collection of tunnels for this scheduler
	 */
	public Collection<Tunnel> getTunnels(){
		return tunnels;
	}
	
	/**
	 * Returns the name of the scheduler
	 * @return the name of the scheduler
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Attempts to admit the given vehicle in one of the scheduler's tunnels.
	 * If there is no tunnel available, the method should wait and try again when appropriate.
	 * This means that the method should only return if the vehicle was
	 * successfully admitted in one of the tunnels and return that tunnel.

	 * @param vehicle the vehicle to admit.
	 * @return the tunnel that the given vehicle managed to enter.
	 */
	public abstract Tunnel admit(Vehicle vehicle);
	
	/**
	 * Exits the given vehicle from the tunnel it was in.
	 * @param vehicle the vehicle to exit from its tunnel
	 */
	public abstract void exit(Vehicle vehicle);

}
