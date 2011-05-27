
import java.awt.Color;


public interface SimParam {

	public int BOUND =  400;
	public int INIT_X = BOUND/2;
	public int INIT_Y = BOUND/2;
	public int RADIUS = (int)(BOUND*0.05);
	public int ARROW = (int)(RADIUS*1.5);
	
	public int ORERADIUS = RADIUS/2;
	public int ORENUM = 0;

	public int FUNGIRADIUS = RADIUS/2;
	public int FUNGINUM = 0;
	
	public int INIT_THETA = 0;
		
	public float BATTERY_CONSUMPTION = 0.0001f;
	public float BATTERY_MAX = 1.0f;
	public Color ORECOLOR = Color.red;
	public Color FUNGICOLOR = Color.blue;


	public float FUNGIPOWER = 0.1f;
	public float VEHICLE_SPEED = 1.0f;
}
