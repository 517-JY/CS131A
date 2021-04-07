package cs131.pa3.Abstract;
import java.util.*;

import cs131.pa3.Abstract.Log.EventType;
import cs131.pa3.Abstract.Log.Log;

/**
 * A Vehicle is a Runnable which enters tunnels. You must extend
 * Vehicle to customize its behavior (e.g., Car, Sled).
 *
 * When you start a thread which runs a Vehicle, the Vehicle will
 * immediately begin trying to enter the tunnel or tunnels passed into
 * its constructor by calling tryToEnter on each Tunnel instance. As
 * long as tryToEnter returns false (indicating that the Vehicle did
 * not enter that tunnel), the Vehicle will keep trying. This is
 * called busy-waiting.
 *
 * In addition to recreating the constructors, the only method that
 * you must override in Vehicle subclasses is getDefaultSpeed. This
 * instance method is called from the private init method, and the
 * integer that it returns is used as the speed for the vehicle.
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
     * The collection of tunnels available to this vehicle (The vehicle should attempt to enter any of these tunnels)
     */
    private Collection<Tunnel> 	tunnels;
    
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
     * @param log the log to be use for logging
     */
    private void init(String name, Direction direction, Log log) {
        this.name      = name;
        this.direction = direction;
        this.speed     = getDefaultSpeed();
        this.log       = log;
        this.tunnels   = new ArrayList<Tunnel>();

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
     * Create a Vehicle that can cross one of a collection of tunnels.
     * 
     * @param name      The name of this vehicle to be displayed in the
     *                  output.
     * @param direction The side of the tunnel being entered.
     * @param log the log to be used for logging
     */
    public Vehicle(String name, Direction direction, Log log) {
    	init(name, direction, log);
    }
    
    /**
     * Create a Vehicle that can cross one of a collection of tunnels 
     * and use the default log.
     * 
     * @param name      The name of this vehicle to be displayed in the
     *                  output.
     * @param direction The side of the tunnel being entered.
     */
    public Vehicle(String name, Direction direction) {
        this(name, direction, Tunnel.DEFAULT_LOG);
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
     * Adds a new tunnel to the collection of tunnels for this vehicle
     * @param newTunnel the new tunnel to be added
     */
    public final void addTunnel(Tunnel newTunnel) {
        this.tunnels.add(newTunnel);
    }

    /**
     * Adds a new collection of tunnels to the current collection
     * @param newTunnels the new collection of tunnels to be added
     */
    public final void addTunnels(Collection<Tunnel> newTunnels) {
        this.tunnels.addAll(newTunnels);
    }
    
    /**
     * Find and cross through one of the tunnels.
     * 
     * When a thread is run, it keeps looping through its collection
     * of available tunnels until it succeeds in entering one of
     * them. Then, it will call doWhileInTunnel (to simulate doing
     * some work inside the tunnel, i.e., that it takes time to cross
     * the tunnel), then exit that tunnel.
     */
    public final void run() {
        // Loop over all tunnels repeated until we can enter one, then
        // think inside the tunnel, exit the tunnel, and leave this
        // entire method.
        while(true) {
    	    for(Tunnel tunnel : tunnels) {
                if(tunnel.tryToEnter(this)) {
                    doWhileInTunnel();
                    tunnel.exitTunnel(this);
                    this.log.addToLog(this, EventType.COMPLETE);
                    return; // done, so leave the whole function
                }
            }
        }
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
        return true;
    }
}
