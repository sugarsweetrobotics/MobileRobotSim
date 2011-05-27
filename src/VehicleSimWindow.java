import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


/**
 * 台車シミュレータ表示フレーム
 */
public class VehicleSimWindow extends JFrame {

	/**
	 * 台車描画パネル
	 */
	private VehicleSimPanel panel;


	/**
	 * コンストラクタ
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
	 * 自動描画スレッド開始
	 */
	public void start() {
		panel.startAutoRepaint();
	}
	
	/**
	 * 自動描画スレッド終了
	 */
	public void stop() {
		panel.stopAutoRepaint();
	}
	
	/**
	 * 台車取得	
	 * @return
	 */
	public Vehicle getVehicle() {
		return panel.getVehicle();
	}

	/**
	 * メイン関数
	 * @param arg
	 */
	public static void main (String[] arg) {
		new VehicleSimWindow();
	}
}
