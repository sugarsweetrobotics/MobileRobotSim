public class VehicleSimTime {

	private double time = 0;
	
	VehicleSimTime() {
		
	}
	
	final public void tic(double tic) {
		time += tic;
	}
	
	final public double getCurrentSimTime() {
		return time;
	}
	
	final public double getCurrentSystemTime() {
		return System.currentTimeMillis();
	}
	
	static private VehicleSimTime theOnlyInstance = new VehicleSimTime(); 
	
	public static VehicleSimTime getSimTime() {
		return theOnlyInstance;
	}

}
