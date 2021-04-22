package cs131.pa4.CarsTunnels;

import java.util.Collection;

import cs131.pa4.Abstract.Direction;
import cs131.pa4.Abstract.Factory;
import cs131.pa4.Abstract.Scheduler;
import cs131.pa4.Abstract.Tunnel;
import cs131.pa4.Abstract.Vehicle;

/**
 * The class implementing the Factory interface for creating instances of classes
 * @author cs131a
 *
 */
public class ConcreteFactory implements Factory {

    @Override
    public Tunnel createNewBasicTunnel(String name){
    	// Call constructor of the BasicTunnel Class and return the newly created instance object tunnel
    	Tunnel tunnel = new BasicTunnel(name);
    	return tunnel;  
    }

    @Override
    public Vehicle createNewCar(String name, Direction direction){
    	// Call constructor of the Car Class and return the newly created instance object car
    	Car car = new Car(name, direction);
    	return car; 
    }

    @Override
    public Vehicle createNewSled(String name, Direction direction){
    	// Call constructor of the Sled Class and return the newly created instance object sled
    	Sled sled = new Sled(name, direction);
    	return sled;   
    }

    @Override
    public Scheduler createNewPriorityScheduler(String name, Collection<Tunnel> tunnels){
    	// Call constructor of the PrioritySchedular Class and return the newly created instance object priorityScheduler
    	PriorityScheduler priorityScheduler = new PriorityScheduler(name, tunnels);
    	return priorityScheduler; 
    }
}
