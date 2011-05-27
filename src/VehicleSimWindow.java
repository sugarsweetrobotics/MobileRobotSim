import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


/**
 * ��ԃV�~�����[�^�\���t���[��
 */
public class VehicleSimWindow extends JFrame {

	/**
	 * ��ԕ`��p�l��
	 */
	private VehicleSimPanel panel;


	/**
	 * �R���X�g���N�^
	 * @throws HeadlessException
	 */
	public VehicleSimWindow() throws HeadlessException {
		super("VehicleSim 1.0");
		
		getContentPane().setLayout(new BorderLayout());
		panel = new VehicleSimPanel();
		getContentPane().add(BorderLayout.CENTER, 
				new JScrollPane(panel));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}

	/**
	 * �����`��X���b�h�J�n
	 */
	public void start() {
		panel.startAutoRepaint();
	}
	
	/**
	 * �����`��X���b�h�I��
	 */
	public void stop() {
		panel.stopAutoRepaint();
	}
	
	/**
	 * ��Ԏ擾	
	 * @return
	 */
	public Vehicle getVehicle() {
		return panel.getVehicle();
	}

	/**
	 * ���C���֐�
	 * @param arg
	 */
	public static void main (String[] arg) {
		new VehicleSimWindow();
	}
}
