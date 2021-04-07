package cs131.pa3.Abstract;

/**
 * The interface that creates instances of specific classes
 * @author cs131a
 *
 */
public interface Factory {

	/**
	 * Creates a new instance of class BasicTunnel
	 * @param name the name of the tunnel to create
	 * @return the newly created instance of the BasicTunnel class
	 */
    public abstract Tunnel createNewBasicTunnel(String name);
    
    /**
     * Creates a new instance of class Car
     * @param name the name of the car to create
     * @param direction the direction of the car
     * @return the newly created instance of the Car class as type Vehicle
     */
    public abstract Vehicle createNewCar(String name, Direction direction);

    /**
     * Creates a new instance of class Sled
     * @param name the name of the sled to create
     * @param direction the direction of the sled
     * @return the newly created instance of the Sled class as type Vehicle
     */
    public abstract Vehicle createNewSled(String name, Direction direction);

}
