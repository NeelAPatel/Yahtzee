package yahtzeeGUIVersion;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class yahtzeeGUIRunner {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					@SuppressWarnings("unused")
					yahtzeeGUI yahtzeeWindow = new yahtzeeGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
