package cs131.pa4;

import cs131.pa4.Abstract.Factory;
import cs131.pa4.Abstract.Scheduler;
import cs131.pa4.Abstract.Tunnel;
import cs131.pa4.Abstract.Vehicle;
import cs131.pa4.CarsTunnels.ConcreteFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestUtilities {
    
    public static final String carName = "CAR";
    public static final String sledName = "SLED";
    //Names used in testing
    public static final String[] gbNames = {"VENKMAN", "STANTZ", "SPENGLER", "ZEDDEMORE", "BARRETT", "TULLY", "MELNITZ", "PECK", "LENNY", "GOZER", "SLIMER", "STAY PUFT", "GATEKEEPER", "KEYMASTER"};
    public static final String[] mrNames = {"CATSKILL", "ROCKY", "APPALACHIAN", "OLYMPIC", "HIMALAYA", "GREAT DIVIDING", "TRANSANTRIC", "URAL", "ATLAS", "ALTAI", "CARPATHIAN", "KJOLEN", "BARISAN", "COAST", "QIN", "WESTERN GHATS"};
    
    public static final Factory factory = new ConcreteFactory();
    
    public static void VehicleEnters(Vehicle vehicle, Tunnel tunnel) {
        boolean canEnter = tunnel.tryToEnter(vehicle);
        assertTrue(canEnter, String.format("%s cannot use", vehicle));
        vehicle.doWhileInTunnel();
        tunnel.exitTunnel(vehicle);
    }
    
    public static void VehicleEnters(Vehicle vehicle, Scheduler scheduler) {
        Tunnel tunnel = scheduler.admit(vehicle);
        assertTrue((tunnel != null), String.format("%s cannot use", vehicle));
        vehicle.doWhileInTunnel();
        scheduler.exit(vehicle);
    }
   
}
