
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * ���
 * @author ysuga
 *
 */
public class Vehicle implements SimParam {

	/**
	 * �V�~�����[�^������
	 */
	static VehicleSimTime time = VehicleSimTime.getSimTime();

	private double vx = 0;
	private double vy = 0;
	private double va = 0;
	
	/**
	 * ���x�̐ݒ�
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
	 * �[�d
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
	 * �o�b�e���̏������ύX
	 * @param cons
	 */
	public void setBatterConsumption(float cons) {
		batteryConsumption = cons;
	}
	
	/**
	 * ���{�b�g�̑��x�ɉ����Č��݈ʒu��ύX����
	 */
	public void refreshPosition() {
		if (timeBuf < 0) {
			timeBuf = System.currentTimeMillis();
		}
		double currentTime = System.currentTimeMillis();
		double deltaTime = (currentTime - timeBuf) / 1000;
		timeBuf = currentTime;
		
		// �܂��̓��{�b�g���W�n�ōl����D
		double dFront = vx * deltaTime;
		double dSide =  vy * deltaTime;
		double dTheta = va * deltaTime;

		// �����Ə��������猵���Ɍ����Ƃ������傢�E�E
		double dx = dFront * Math.cos(theta) - dSide * Math.sin(theta);
		double dy = -dFront * Math.sin(theta) + dSide * Math.cos(theta);

		x += dx;
		if (x < SimParam.RADIUS)
			x = SimParam.RADIUS;
		else if (x > SimParam.BOUND-SimParam.RADIUS)
			x = SimParam.BOUND-SimParam.RADIUS;
		y -= dy;
		if (y < SimParam.RADIUS)
			y = SimParam.RADIUS;
		else if (y > SimParam.BOUND-SimParam.RADIUS)
			y = SimParam.BOUND-SimParam.RADIUS;
		theta += dTheta;
		double thetabuf = theta % (Math.PI*2);
		if(thetabuf <= Math.PI) {
			theta = thetabuf;
		}else {
			theta = thetabuf - (Math.PI*2);
		}

		ellipse = new Ellipse2D.Double(x - radius, y - radius, radius * 2,
				radius * 2);
		line = new Line2D.Double(x, y, x + arrow * Math.cos(theta), y + arrow
				* Math.sin(theta));
		
		
		batteryPercent -= batteryConsumption;
		if(batteryPercent < 0) batteryPercent = 0;
		
	}

	/**
	 * ���݈ʒux
	 */
	public double x;

	/*
	 * ���݈ʒuy
	 */
	public double y;

	/*
	 * ���ݕ���theta
	 */
	public double theta;

	private float batteryPercent;

	final public float getBatteryByPercent() {
		return batteryPercent;
	}
	
	VehicleSimPanel owner;

	private int radius = SimParam.RADIUS;

	private int arrow = SimParam.ARROW;

	/**
	 * ��ԃN���X �R���X�g���N�^
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

		batteryPercent = 1.0f;
	}



	Ellipse2D ellipse;

	Line2D line;

	public void draw(Graphics2D g2d) {
		if (ellipse != null) {
			g2d.draw(ellipse);
		}
		if (line != null) {
			g2d.draw(line);
		}
	}

	public float getBatteryVoltage() {
		return batteryPercent * SimParam.BATTERY_MAX;
	}

}
