package cs131.pa4.CarsTunnels;

import cs131.pa4.Abstract.Tunnel;
import cs131.pa4.Abstract.Vehicle;
import cs131.pa4.Abstract.Direction;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * The class for the Basic Tunnel, extending Tunnel.
 * Using the same code for PA3
 * @author cs131a
 *
 */
public class BasicTunnel extends Tunnel{
	/**
	 * Restrictions for the BasicTunnel, at any given time :
	 * 		1. All vehicles must be traveling with the same direction
	 * 		2. Only 3 cars may be allowed inside a tunnel
	 * 		3. Only 1 sled may be allowed inside a tunnel
	 * 		4. Cars and sleds cannot share a tunnel
	 */
	
	// Maximum number of cars allowed in a tunnel at any given time
	public static final int maxCarNum = 3;
	
	// Maximum number of sled allowed in a tunnel at any given time
	public static final int maxSledNum = 1; 

	// An integer keeps tracking of the number of Car in this tunnel
	private int carNum;
	
	// An integer keeps tracking of the number of Sled in this tunnel
	private int sledNum; 
	
	// A List collection stores all the vehicles current in the tunnel
	private List<Vehicle> vehicleList;
	
	// A Direction type variable indicates the currentDirection of the tunnel
	private Direction currDirection;
	
	
	/**
	 * Creates a new instance of a basic tunnel with the given name
	 * @param name the name of the basic tunnel
	 */
	public BasicTunnel(String name) {
		super(name);
		// Initialize the below shared data among threads 
		this.carNum = 0;
		this.sledNum = 0;
		this.vehicleList = new ArrayList<Vehicle>();
		this.currDirection = null;
	}
	

	/**
	 * Keyword Synchronized is used to achieve mutual exclusion for this critical-section
	 * When one vehicle thread is trying to enter a tunnel, other threads should be prevented to make any modifications
	 */
	@Override
	protected synchronized boolean tryToEnterInner(Vehicle vehicle) {
		if (vehicleList.isEmpty()) 
		{
			if ((vehicle instanceof Car) || (vehicle instanceof Sled)) {
				// Set direction of the tunnel with the direction of the given vehicle when the current tunnel is empty
				currDirection = vehicle.getDirection();
				
				if (vehicle instanceof Car) {
					carNum++;
				} else {
					sledNum++;
				}	
				
				vehicleList.add(vehicle);
				
				return true;
			}
		}
		
		// if the vehicle's direction is not consistent with the tunnel or tunnel meets its Car or Sled capacity
		// the feasibility-loop check should fall into "busy-waiting"
		if (!vehicle.getDirection().equals(currDirection) || 
				carNum >= maxCarNum || sledNum >= maxSledNum) {
			return false;
		}
		
		
		if (vehicle instanceof Car) {
			if (sledNum < 1) {
				carNum++;
				vehicleList.add(vehicle);
				return true;
			}
		} else if (vehicle instanceof Sled) {
			if (carNum < 1) {
				sledNum++;
				vehicleList.add(vehicle);
				return true;
			}
		}
		
		return false;
	}

	
	
	/**
	 * Similarly, keyword Synchronized is again used to achieve mutual exclusion for this critical-section
	 */
	@Override
	protected synchronized void exitTunnelInner(Vehicle vehicle) {
		if (vehicleList.remove(vehicle)) {
			if (vehicle instanceof Car) {
				carNum--; 
			} else if (vehicle instanceof Sled) {
				sledNum--;
			}
			
			// If there is no vehicles left in the tunnel, the direction should be left to be further reset
			if (vehicleList.isEmpty()) {
				currDirection = null;
			}
		}
	}
	
}
