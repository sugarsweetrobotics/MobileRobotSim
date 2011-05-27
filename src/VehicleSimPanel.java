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


	/**
	 * �`��֐�
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = getSize();
		Rectangle2D rect = new Rectangle2D.Double(0, 0, SimParam.BOUND,
				SimParam.BOUND);

		Color color = g2d.getColor();
		g2d.setColor(Color.white);
		g2d.fill(rect);
		g2d.setColor(color);

		vehicle.draw(g2d);

		g.drawString("Battery:" + Float.toString(vehicle.getBatteryByPercent()), 0, 20);
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
