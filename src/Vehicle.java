
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * 台車
 * @author ysuga
 *
 */
public class Vehicle implements SimParam {

	/**
	 * シミュレータ内時間
	 */
	static VehicleSimTime time = VehicleSimTime.getSimTime();

	private double vx = 0;
	private double vy = 0;
	private double va = 0;
	
	
	private Color color;
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	/**
	 * 速度の設定
	 * @param vx
	 * @param vy
	 * @param va
	 */
	public void setVelocity(double vx, double vy, double va) {
		this.vx = vx;
		this.vy = vy;
		this.va = va;
	}

	/**
	 * 充電
	 */
	public void chargeFull() {
		batteryPercent = 1.0f;
	}
	
	/**
	 * 
	 */
	double timeBuf = -1;;
	
	/**
	 * 
	 */
	float batteryConsumption = SimParam.BATTERY_CONSUMPTION;
	
	/**
	 * バッテリの消費割合を変更
	 * @param cons
	 */
	public void setBatterConsumption(float cons) {
		batteryConsumption = cons;
	}
	
	/**
	 * ロボットの速度に応じて現在位置を変更する
	 */
	public void refreshPosition() {
		if (timeBuf < 0) {
			timeBuf = System.currentTimeMillis();
		}
		double currentTime = System.currentTimeMillis();
		double deltaTime = (currentTime - timeBuf) / 1000;
		timeBuf = currentTime;
		
		// まずはロボット座標系で考える．
		double dFront = vx * deltaTime;
		double dSide =  vy * deltaTime;
		double dTheta = va * deltaTime;

		// ざっと書いたから厳密に言うともうちょい・・
		double dx = dFront * Math.cos(theta) - dSide * Math.sin(theta);
		double dy = -dFront * Math.sin(theta) + dSide * Math.cos(theta);

		x += dx;
		if (x + offsetX < SimParam.RADIUS)
			x = SimParam.RADIUS  - offsetX;
		else if (x + offsetX > SimParam.BOUND-SimParam.RADIUS)
			x = SimParam.BOUND-SimParam.RADIUS  - offsetX;
		y -= dy;
		if (y + offsetY < SimParam.RADIUS)
			y = SimParam.RADIUS - offsetY;
		else if (y + offsetY > SimParam.BOUND-SimParam.RADIUS)
			y = SimParam.BOUND-SimParam.RADIUS - offsetY;
		theta += dTheta;
		double thetabuf = theta % (Math.PI*2);
		if(thetabuf > Math.PI) {
			theta = thetabuf - (Math.PI*2);
		}else if (thetabuf < -Math.PI) {
			theta = thetabuf + (Math.PI*2);
		} else {
			theta = thetabuf;
		}

		ellipse = new Ellipse2D.Double(x + offsetX - radius, y + offsetY - radius, radius * 2,
				radius * 2);
		line = new Line2D.Double(x + offsetX, y + offsetY, 
				x + offsetX + arrow * Math.cos(theta + offsetTh),
				y + offsetY + arrow * Math.sin(theta + offsetTh));
		
		rightBump = new Arc2D.Double(	x + offsetX - radius - 5, // + (radius - 10) * Math.cos(theta + offsetTh),
										y + offsetY - radius - 5, // + (radius - 10) * Math.sin(theta + offsetTh),
										radius * 2 + 10, // * Math.cos(theta + offsetTh + Math.PI / 2),
										radius * 2 + 10, // * Math.sin(theta + offsetTh + Math.PI / 2),
										(-theta + offsetTh) / Math.PI * 180.0 + 0.0,
										-90, //(-theta + offsetTh) / Math.PI * 180.0 + 180.0,
										//0, 90,
										Arc2D.PIE);

		leftBump = new Arc2D.Double(	x + offsetX - radius - 5, // + (radius - 10) * Math.cos(theta + offsetTh),
				y + offsetY - radius - 5, // + (radius - 10) * Math.sin(theta + offsetTh),
				radius * 2 + 10, // * Math.cos(theta + offsetTh + Math.PI / 2),
				radius * 2 + 10, // * Math.sin(theta + offsetTh + Math.PI / 2),
				-(theta + offsetTh) / Math.PI * 180.0 - 0.0,
				+90, //-(theta + offsetTh) / Math.PI * 180  + 0.0,
				Arc2D.PIE);
										
		
		batteryPercent -= batteryConsumption;
		if(batteryPercent < 0) batteryPercent = 0;
		
	}

	/**
	 * 現在位置x
	 */
	private double x;
	private double offsetX;

	/*
	 * 現在位置y
	 */
	private double y;
	private double offsetY;

	/*
	 * 現在方向theta
	 */
	private double theta;
	private double offsetTh;
	
	public void setOffset(double x, double y, double th) {
		offsetX = x; offsetY = y;offsetTh = th;
	}
	
	public void setPose(double x, double y, double th) {
		this.x = x; this.y = y; this.theta = th;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getTh() { return theta; }

	private float batteryPercent;

	final public float getBatteryByPercent() {
		return batteryPercent;
	}
	
	VehicleSimPanel owner;

	private int radius = SimParam.RADIUS;

	private int arrow = SimParam.ARROW;

	/**
	 * 台車クラス コンストラクタ
	 * @param owner
	 * @param x
	 * @param y
	 * @param theta
	 */
	public Vehicle(VehicleSimPanel owner, double x, double y, double theta) {
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.theta = theta;
		
		this.vx = this.vy = 0;
		this.color = Color.red;

		batteryPercent = 1.0f;
	}


	Arc2D rightBump;
	
	Arc2D leftBump;
	
	
	private boolean rightHit;
	private boolean leftHit;
	
	public void setHit(boolean right, boolean left) {
		rightHit = right; leftHit = left;
	}
	
	public boolean getRightBump() {return rightHit;}
	public boolean getLeftBump() {return leftHit;}
	Ellipse2D ellipse;

	Line2D line;

	public void draw(Graphics2D g2d) {
		Color oc = g2d.getColor();
		g2d.setColor(color);
		if (ellipse != null) {
			g2d.draw(ellipse);
		}
		if (line != null) {
			g2d.draw(line);
		}
		
		if(rightHit) {
			g2d.fill(rightBump);
		} else {
			g2d.draw(rightBump);
		}
		
		if(leftHit) {
			g2d.fill(leftBump);
		} else {
			g2d.draw(leftBump);
		}

		
		g2d.setColor(oc);
	}

	public float getBatteryVoltage() {
		return batteryPercent * SimParam.BATTERY_MAX;
	}

}
