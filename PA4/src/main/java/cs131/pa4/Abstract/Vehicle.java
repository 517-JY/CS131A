package cs131.pa4.Abstract;
import java.util.*;

import cs131.pa4.Abstract.Log.EventType;
import cs131.pa4.Abstract.Log.Log;

/**
 * A Vehicle is a Runnable which enters tunnels. You must subclass
 * Vehicle to customize its behavior (e.g., Car, Sled, Ambulance).
 *
 * When you start a thread which runs a Vehicle, the Vehicle will
 * use the given scheduler to try to be admitted in one of the tunnels.
 * The scheduler.admit() will successfully return when a tunnel is found,
 * or it will wait and retry until it eventually admits that vehicle in a tunnel.
 * 
 * Unlike PA3, the vehicle does not know about the available tunnels and 
 * relies on the scheduler to be entered in a tunnel.
 * Thus, the vehicle does not retry to enter a tunnel with busy-waiting like PA3.
 *
 * You should not edit this class.
 * 
 * @author cs131a
 */
public abstract class Vehicle implements Runnable {
	/**
	 * The name of this vehicle
	 */
    private String            	name;
    /**
     * The direction of this vehicle
     */
    private Direction          	direction;
    
    /**
     * The scheduler that this vehicle will use
     */
    private Scheduler scheduler;
    
    /**
     * The priority of this vehicle
     */
    private int                	priority;
    /**
     * The speed of this vehicle
     */
    private int                	speed;
    /**
     * The log used to log operations
     */
    private Log 				log;

    /**
     * Initialize a Vehicle; called from Vehicle constructors.
     * 
     * @param name the name of the vehicle
     * @param direction the direction of the vehicle
     * @param scheduler the scheduler to be used for this vehicle
     * @param log the log to be use for logging
     */
    private void init(String name, Direction direction, Scheduler scheduler, Log log) {
        this.name      = name;
        this.direction = direction;
        this.speed     = getDefaultSpeed();
        this.scheduler = scheduler;
        this.log       = log;

        if(this.speed < 0 || this.speed > 9) {
            throw new RuntimeException("Vehicle has invalid speed");
        }
    }

    /**
     * Override in a subclass to determine the speed of the
     * vehicle.
     *
     * Must return a number between 0 and 9 (inclusive). Higher
     * numbers indicate greater speed. The faster a vehicle, the less
     * time it will spend in a tunnel.
     * 
     * @return the speed of this vehicle
     */
    protected abstract int getDefaultSpeed();
    
    /**
     * Create a Vehicle with default priority that can cross one of
     * a collection of tunnels with the given scheduler.
     * 
     * @param name      The name of this vehicle to be displayed in the
     *                  output.
     * @param direction The side of the tunnel being entered.
     * @param scheduler The scheduler to be used for this vehicle
     * @param log the log to be used for logging
     */
    public Vehicle(String name, Direction direction, Scheduler scheduler, Log log) {
        init(name, direction, scheduler, log);
    }

    /**
     * Create a Vehicle with default priority that can cross one of
     * a collection of tunnels.
     * 
     * @param name      The name of this vehicle to be displayed in the
     *                  output.
     * @param direction The side of the tunnel being entered.
     * @param log the log to be used for logging
     */
    public Vehicle(String name, Direction direction, Log log) {
    	this(name, direction, null, log);
    }
    
    /**
     * Create a Vehicle with default priority that can cross one of
     * a collection of tunnels and use the default log.
     * 
     * @param name      The name of this vehicle to be displayed in the
     *                  output.
     * @param direction The side of the tunnel being entered.
     */
    public Vehicle(String name, Direction direction) {
        this(name, direction, Tunnel.DEFAULT_LOG);
    }
    
    /**
     * Sets this vehicle's speed - used for preemptive priority scheduler test
     * @param speed the new speed to be set (0 to 9)
     */
    public void setSpeed(int speed) {
    	if(this.speed < 0 || this.speed > 9) {
            throw new RuntimeException("Invalid speed: "+ speed);
        }
    	this.speed = speed;
    }
    
    /** 
     * Sets this vehicle's priority - used for priority scheduling
     *
     * @param priority The new priority (between 0 and 4 inclusive)
     */
    public final void setPriority(int priority) {
        if(priority < 0 || priority > 4) {
            throw new RuntimeException("Invalid priority: " + priority);
        }
        this.priority = priority;
    }
    
    /**
     * Returns the priority of this vehicle
     *
     * @return This vehicle's priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns the name of this vehicle
     *
     * @return The name of this vehicle
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns a string representation of this vehicle
     * 
     * @return the string representation of this vehicle
     */
    public String toString() {
    		return String.format("%s VEHICLE %s", this.direction, this.name);
    }
    
    /**
     * Sets the given scheduler as the scheduler of this vehicle
     * @param newScheduler the new scheduler to be used
     */
    public final void setScheduler(Scheduler newScheduler) {
        this.scheduler = newScheduler;
    }
    
    /**
     * Find and cross through one of the tunnels via the scheduler.
     * 
     * When a thread is run, it asks the scheduler to admit it in one
     * of the tunnels. The scheduler is responsible for taking this vehicle
     * and going through its collection of available tunnels until it 
     * succeeds in entering one of them. Then, the vehicle thread will call 
     * doWhileInTunnel (to simulate doing some work inside the tunnel,
     * i.e., that it takes time to cross the tunnel) and finally exit that tunnel
     * through the scheduler.
     */
    public final void run() {
    	if(scheduler == null) { // Cannot operate if there is no scheduler set
        	throw new UnsupportedOperationException("There is no scheduler set!");
        }
    	//use the given scheduler to schedule this vehicle
		if(scheduler.admit(this) == null){
            throw new UnsupportedOperationException("Scheduler failed to admit given vehicle!");
        }
		doWhileInTunnel();
		scheduler.exit(this);
		this.log.addToLog(this, EventType.COMPLETE);
    }
    
    /**
     * Returns the direction of this vehicle
     *
     * @return the direction of this vehicle
     */
    public final Direction getDirection() {
        return direction;
    }

    /**
     * This is what your vehicle does while inside the tunnel to
     * simulate taking time to "cross" the tunnel. The faster your
     * vehicle is, the less time this will take.
     */
    public final void doWhileInTunnel() {
         try {
             Thread.sleep((10 - speed) * 100);
         } catch(InterruptedException e) {
             System.err.println("Interrupted vehicle " + getName());
         }
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.direction);
        hash = 23 * hash + this.speed;
        hash = 23 * hash + this.priority;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vehicle other = (Vehicle) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        if (this.speed != other.speed) {
            return false;
        }
        if (this.priority != other.priority) {
            return false;
        }
        return true;
    }
}
