import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * ��ԃV�~�����[�^�\���p�l��
 * @author ysuga
 *
 */
public class VehicleSimPanel extends JPanel implements SimParam {
	/**
	 * �R���X�g���N�^
	 */
	public VehicleSimPanel() {
		super();
		vehicle = new Vehicle(this, SimParam.INIT_X, SimParam.INIT_Y,
				SimParam.INIT_THETA);
		vehicle.setVelocity(0, 0, 0);
		vehicle.setOffset(SimParam.INIT_X, SimParam.INIT_Y, SimParam.INIT_THETA);
		setPreferredSize(new Dimension(SimParam.BOUND + 20, SimParam.BOUND + 20));
	}
	

	/**
	 * ���
	 */
	private Vehicle vehicle;

	/**
	 * ��Ԃ̎Q�Ƃ��擾
	 * @return
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void checkBump() {
		double x = vehicle.getX();
		double y = vehicle.getY();
		double th = vehicle.getTh();
		boolean right = false, left = false;
		
		vehicle.setHit(false, false);
		
		if(x > SimParam.BOUND/2 -5 - SimParam.RADIUS) {
			if( (th < Math.PI/2 && th > -Math.PI*1/6)){
				left = true;
			}
			if( (th < Math.PI*1/6 && th > -Math.PI/2 ) ){
				right = true;
			}
		}
		
		if(y < -SimParam.BOUND/2 + 5 + SimParam.RADIUS) {
			if( (th > -Math.PI && th < -Math.PI*2/6)) {
				right = true;
			}
			if( (th > -Math.PI*4/6 && th < 0)) {
				left = true;
			}
		}
		
		if(x < -SimParam.BOUND/2 +5 + SimParam.RADIUS) {
			if( (th > Math.PI/2 && th <= Math.PI) || (th > -Math.PI && th < -Math.PI*5/6)){
				right = true;
			}
			if( (th < -Math.PI/2 && th > -Math.PI ) || (th <= Math.PI && th > Math.PI*5/6) ) {
				left = true;
			}
		}

		if(y > SimParam.BOUND/2 - 5 - SimParam.RADIUS) {
			if( th > 0 && th < Math.PI*4/6) {
				right = true;
			}
			if( th > Math.PI*2/6 && th <= Math.PI) {
				left = true;
			}
		}
		//SimParam.BOUND

		vehicle.setHit(right, left);
	}

	/**
	 * �`��֐�
	 */
	@Override
	public void paint(Graphics g) {
		
		checkBump();
		
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = getSize();
		Rectangle2D rect = new Rectangle2D.Double(0, 0, SimParam.BOUND,
				SimParam.BOUND);

		Color color = g2d.getColor();
		g2d.setColor(Color.white);
		g2d.fill(rect);
		g2d.setColor(color);

		vehicle.draw(g2d);

		//g.drawString("Battery:" + Float.toString(vehicle.getBatteryByPercent()), 0, 20);
	}

	/**
	 * �ĕ`��X���b�h�̃I�u�W�F�N�g
	 */
	RepaintThread repaintThread;

	/**
	 * �ĕ`��J�n
	 */
	public void startAutoRepaint() {
		repaintThread = new RepaintThread();
		repaintThread.start();
	}

	/**
	 * �ĕ`��I��
	 */
	public void stopAutoRepaint() {
		repaintThread.stopRepaint();
	}

	/**
	 * �ĕ`��p�X���b�h
	 * @author ysuga
	 *
	 */
	public class RepaintThread extends Thread {
		private boolean endflag;

		public RepaintThread() {

		}

		public void run() {
			endflag = false;
			while (!endflag) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO �����������ꂽ catch �u���b�N
					e.printStackTrace();
				}
				repaint();
			}
		}

		public void stopRepaint() {
			endflag = true;
		}
	}

}
