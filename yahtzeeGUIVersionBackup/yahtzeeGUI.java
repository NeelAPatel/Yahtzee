package yahtzeeGUIVersionBackup;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Font;


public class yahtzeeGUI implements ActionListener {

	private JFrame frmYahtzee;
	private final ButtonGroup scoreGroup = new ButtonGroup();
	private static HashMap<String, Component> mainComponentMap; 
	private JTextField txt_p1_1, txt_p1_2, txt_p1_3, txt_p1_4, txt_p1_5, txt_p1_6;
	private JTextField txt_p2_1, txt_p2_2, txt_p2_3, txt_p2_4, txt_p2_5, txt_p2_6;
	private JTextField txt_p1_3Kind, txt_p1_4Kind, txt_p1_SStraight, txt_p1_LStraight,
	txt_p1_Yahtzee, txt_p1_FullHouse, txt_p1_Chance;
	private JTextField txt_p2_3Kind, txt_p2_4Kind, txt_p2_SStraight, txt_p2_LStraight,
	txt_p2_Yahtzee, txt_p2_FullHouse, txt_p2_Chance;
	
	private JTextField txt_p1_USubTotal, txt_p2_USubTotal;
    private JTextField txt_p1_Bonus, txt_p2_Bonus; 
	private JTextField txt_p1_UTotal, txt_p2_UTotal;
	private JTextField txt_p1_LTotal,  txt_p2_LTotal;
	private JPanel upperSection, lowerSection, diceBox;
	private JButton btnRoll, btnPlay;
	private JCheckBox checkBox_1, checkBox_2, checkBox_3, checkBox_4, checkBox_5;
	private JLabel lblDice1, lblDice2, lblDice3, lblDice4, lblDice5;
	private String[] rdbtnNames = {"rdbtn1","rdbtn2", "rdbtn3", "rdbtn4", "rdbtn5", "rdbtn6",
			"rdbtn3Kind", "rdbtn4Kind", "rdbtnSStraight", "rdbtnLStraight", "rdbtnYahtzee", "rdbtnChance", "rdbtnFullHouse", "rdbtnHidden"};
	private boolean isDisplayingScore = false;
	private GameLogic gameLogic;
	private JTextField textField, textField_1; // grand totals
	

    //======================================================= CONSTRUCTOR ==================================================
	/**
	 * Creates the yahtzee game
	 */
	public yahtzeeGUI() {
		gameLogic = new GameLogic();
		getPlayerNames();
		initialize();
		createComponentMap();
		indicatePlayer();
		disableAllRadioButtons();
		disableCheckBoxes();
		((JButton) getComponentByName("btnPlay")).setVisible(false);
		
		
	}

	// ============================================= Creating graphics ================================================================
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmYahtzee = new JFrame();
		frmYahtzee.setResizable(false);
		frmYahtzee.setVisible(true);
		frmYahtzee.setTitle("Yahtzee! with N.A.G.A");
		frmYahtzee.setBounds(100, 100, 496, 610);
		frmYahtzee.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createMenuBar();
		createUpperSection();
		createUpperScoreboard();
		createLowerSection();
		createLowerScoreboard();
		createDice();
		createMainFrameElements();
	}
	
	/**
	 * Creates the mainframe elements
	 */
	private void createMainFrameElements()
	{
		JLabel lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setText(gameLogic.getPlayerOne().getName());
		lblPlayer1.setName("lblPlayer1");
		lblPlayer1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPlayer1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlayer1.setBounds(20, 19, 210, 34);
		frmYahtzee.getContentPane().add(lblPlayer1);
		
		JLabel lblNewLabel = new JLabel("Copyright \u00A9 2015 NAGA Games Co. All Rights Reserved.");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(20, 537, 457, 16);
		frmYahtzee.getContentPane().add(lblNewLabel);
		
		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setName("lblPlayer2");
		lblPlayer2.setText(gameLogic.getPlayerTwo().getName());
		lblPlayer2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPlayer2.setBounds(264, 19, 210, 34);
		frmYahtzee.getContentPane().add(lblPlayer2);
		
		JLabel lblGrandTotal = new JLabel("Grand Total");
		lblGrandTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGrandTotal.setBounds(250, 353, 107, 14);
		frmYahtzee.getContentPane().add(lblGrandTotal);
		
		textField = new JTextField();
		textField.setBounds(368, 351, 45, 20);
		frmYahtzee.getContentPane().add(textField);
		textField.setText("0");
		textField.setName("txt_p1_GTotal");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		
		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setName("txt_p2_GTotal");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBackground(Color.WHITE);
		textField_1.setBounds(420, 351, 45, 20);
		frmYahtzee.getContentPane().add(textField_1);
		
		JRadioButton rdbtnHidden = new JRadioButton("");
		rdbtnHidden.setVisible(false);
		rdbtnHidden.setSelected(true);
		rdbtnHidden.setName("rdbtnHidden");
		scoreGroup.add(rdbtnHidden);
		rdbtnHidden.setBounds(322, 379, 28, 23);
		frmYahtzee.getContentPane().add(rdbtnHidden);
	}
	
	/**
	 * Creates the menu bar
	 */
	private void createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		frmYahtzee.setJMenuBar(menuBar);
		
		JMenu mnuBarMain = new JMenu("Main Menu");
		mnuBarMain.setName("mnuBarMain");
		menuBar.add(mnuBarMain);
		
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.setName("mntmNewGame");
		mntmNewGame.setActionCommand("NewGame");
		mntmNewGame.addActionListener(this);
		mntmNewGame.setMnemonic(KeyEvent.VK_N);
		mnuBarMain.add(mntmNewGame);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setName("mntmExit");
		mnuBarMain.add(mntmExit);
		mntmExit.setActionCommand("ExitGame");
		mntmExit.addActionListener(this);
		mntmExit.setMnemonic(KeyEvent.VK_X);
		frmYahtzee.getContentPane().setLayout(null);
	}
	
	/**
	 * Creates the dice set
	 */
	private void createDice(){
		
		diceBox = new JPanel();
		diceBox.setName("diceBox");
		diceBox.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Dice Box", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		diceBox.setBounds(46, 415, 408, 111);
		frmYahtzee.getContentPane().add(diceBox);
		diceBox.setLayout(null);
		
		lblDice1 = new JLabel("Dice 1");
		lblDice1.setName("lblDice1");
		lblDice1.setVerticalAlignment(SwingConstants.TOP);
		lblDice1.setIcon(new ImageIcon(yahtzeeGUI.class.getResource("/yahtzeeDicePics/DBlank_Select.png")));
		lblDice1.setBounds(26, 21, 50, 50);
		diceBox.add(lblDice1);
		
		lblDice2 = new JLabel("Dice 2");
		lblDice2.setName("lblDice2");
		lblDice2.setIcon(new ImageIcon(yahtzeeGUI.class.getResource("/yahtzeeDicePics/DBlank_Select.png")));
		lblDice2.setBounds(82, 21, 50, 50);
		diceBox.add(lblDice2);
		
		lblDice3 = new JLabel("Dice 3");
		lblDice3.setName("lblDice3");
		lblDice3.setIcon(new ImageIcon(yahtzeeGUI.class.getResource("/yahtzeeDicePics/DBlank_Select.png")));
		lblDice3.setBounds(138, 21, 50, 50);
		diceBox.add(lblDice3);
		
		lblDice4 = new JLabel("Dice 4");
		lblDice4.setName("lblDice4");
		lblDice4.setIcon(new ImageIcon(yahtzeeGUI.class.getResource("/yahtzeeDicePics/DBlank_Select.png")));
		lblDice4.setBounds(194, 21, 50, 50);
		diceBox.add(lblDice4);
		
		lblDice5 = new JLabel("Dice 5");
		lblDice5.setName("lblDice5");
		lblDice5.setIcon(new ImageIcon(yahtzeeGUI.class.getResource("/yahtzeeDicePics/DBlank_Select.png")));
		lblDice5.setBounds(250, 21, 50, 50);
		diceBox.add(lblDice5);
		
		checkBox_1 = new JCheckBox("");
		checkBox_1.setName("checkBox_1");
		checkBox_1.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox_1.setBounds(36, 73, 28, 23);
		checkBox_1.setActionCommand("chkbox");
		checkBox_1.addActionListener(this);
		diceBox.add(checkBox_1);
		
		checkBox_2 = new JCheckBox("");
		checkBox_2.setName("checkBox_2");
		checkBox_2.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox_2.setBounds(92, 73, 28, 23);
		checkBox_2.setActionCommand("chkbox");
		checkBox_2.addActionListener(this);
		diceBox.add(checkBox_2);
		
		checkBox_3 = new JCheckBox("");
		checkBox_3.setName("checkBox_3");
		checkBox_3.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox_3.setBounds(148, 73, 28, 23);
		checkBox_3.setActionCommand("chkbox");
		checkBox_3.addActionListener(this);
		diceBox.add(checkBox_3);
		
		checkBox_4 = new JCheckBox("");
		checkBox_4.setName("checkBox_4");
		checkBox_4.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox_4.setBounds(204, 73, 28, 23);
		checkBox_4.setActionCommand("chkbox");
		checkBox_4.addActionListener(this);
		diceBox.add(checkBox_4);
		
		checkBox_5 = new JCheckBox("");
		checkBox_5.setName("checkBox_5");
		checkBox_5.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox_5.setBounds(263, 73, 28, 23);
		checkBox_5.setActionCommand("chkbox");
		checkBox_5.addActionListener(this);
		diceBox.add(checkBox_5);
		
		btnRoll = new JButton("Roll! [" + getRollsLeft() + "]");
		btnRoll.setName("btnRoll");
		btnRoll.setBounds(307, 29, 90, 29);
		btnRoll.setActionCommand("Roll"); 
		btnRoll.setMnemonic(KeyEvent.VK_R);
		btnRoll.addActionListener(this); 
		diceBox.add(btnRoll);
		
		btnPlay = new JButton("Play");
		btnPlay.setName("btnPlay");
		btnPlay.setBounds(307, 67, 90, 29);
		btnPlay.setMnemonic(KeyEvent.VK_P);
		btnPlay.setActionCommand("Play");
		btnPlay.addActionListener(this);
		btnPlay.setMnemonic(KeyEvent.VK_P);
		diceBox.add(btnPlay);
	}
	
	/**
	 * Creates the upper section
	 */
	private void createUpperSection(){
		upperSection = new JPanel();
		upperSection.setName("upperSection");
		upperSection.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Upper Section", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		upperSection.setBounds(20, 65, 210, 306);
		frmYahtzee.getContentPane().add(upperSection);
		upperSection.setLayout(null);
		
		JRadioButton rdbtn1 = new JRadioButton("Ones");
		rdbtn1.setToolTipText("Sum of all dice with value of 1");
		rdbtn1.setName("rdbtn1");
		rdbtn1.setBounds(6, 18, 78, 23);
		rdbtn1.addActionListener(this);
		rdbtn1.setActionCommand("rdbtn");
		upperSection.add(rdbtn1);
		scoreGroup.add(rdbtn1);
		
		JRadioButton rdbtn2 = new JRadioButton("Twos");
		rdbtn2.setToolTipText("Sum of all dice with value of 2");
		rdbtn2.setName("rdbtn2");
		rdbtn2.setBounds(6, 48, 78, 23);
		rdbtn2.addActionListener(this);
		rdbtn2.setActionCommand("rdbtn");
		upperSection.add(rdbtn2);
		scoreGroup.add(rdbtn2);
		
		JRadioButton rdbtn3 = new JRadioButton("Threes");
		rdbtn3.setToolTipText("Sum of all dice with value of 3");
		rdbtn3.setName("rdbtn3");
		rdbtn3.setBounds(6, 78, 78, 23);
		rdbtn3.addActionListener(this);
		rdbtn3.setActionCommand("rdbtn");
		upperSection.add(rdbtn3);
		scoreGroup.add(rdbtn3);
		
		JRadioButton rdbtn4 = new JRadioButton("Fours");
		rdbtn4.setToolTipText("Sum of all dice with value of 4");
		rdbtn4.setName("rdbtn4");
		rdbtn4.setBounds(6, 108, 78, 23);
		rdbtn4.addActionListener(this);
		rdbtn4.setActionCommand("rdbtn");
		upperSection.add(rdbtn4);
		scoreGroup.add(rdbtn4);
		
		JRadioButton rdbtn5 = new JRadioButton("Fives");
		rdbtn5.setToolTipText("Sum of all dice with value of 5");
		rdbtn5.setName("rdbtn5");
		rdbtn5.setBounds(6, 138, 78, 23);
		rdbtn5.addActionListener(this);
		rdbtn5.setActionCommand("rdbtn");
		upperSection.add(rdbtn5);
		scoreGroup.add(rdbtn5);
		
		JRadioButton rdbtn6 = new JRadioButton("Sixes");
		rdbtn6.setToolTipText("Sum of all dice with value of 6");
		rdbtn6.setName("rdbtn6");
		rdbtn6.setBounds(6, 168, 78, 23);
		rdbtn6.addActionListener(this);
		rdbtn6.setActionCommand("rdbtn");
		upperSection.add(rdbtn6);
		scoreGroup.add(rdbtn6);
		
		txt_p1_1 = new JTextField();
		txt_p1_1.setToolTipText("Sum of all dice with value of 1");
		txt_p1_1.setName("txt_p1_1");
		
		txt_p1_1.setEditable(false);
		txt_p1_1.setBackground(new Color(255, 182, 193));
		txt_p1_1.setBounds(96, 18, 45, 28);
		upperSection.add(txt_p1_1);
		txt_p1_1.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_1.setColumns(10);
		
		txt_p1_2 = new JTextField();
		txt_p1_2.setToolTipText("Sum of all dice with value of 2");
		txt_p1_2.setName("txt_p1_2");
		txt_p1_2.setEditable(false);
		txt_p1_2.setBackground(new Color(255, 182, 193));
		txt_p1_2.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_2.setColumns(10);
		txt_p1_2.setBounds(96, 48, 45, 28);
		upperSection.add(txt_p1_2);
		
		txt_p1_3 = new JTextField();
		txt_p1_3.setToolTipText("Sum of all dice with value of 3");
		txt_p1_3.setName("txt_p1_3");
		txt_p1_3.setEditable(false);
		txt_p1_3.setBackground(new Color(255, 182, 193));
		txt_p1_3.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_3.setColumns(10);
		txt_p1_3.setBounds(96, 78, 45, 28);
		upperSection.add(txt_p1_3);
		
		txt_p1_4 = new JTextField();
		txt_p1_4.setToolTipText("Sum of all dice with value of 4");
		txt_p1_4.setName("txt_p1_4");
		txt_p1_4.setEditable(false);
		txt_p1_4.setBackground(new Color(255, 182, 193));
		txt_p1_4.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_4.setColumns(10);
		txt_p1_4.setBounds(96, 108, 45, 28);
		upperSection.add(txt_p1_4);
		
		txt_p1_5 = new JTextField();
		txt_p1_5.setToolTipText("Sum of all dice with value of 5");
		txt_p1_5.setName("txt_p1_5");
		txt_p1_5.setEditable(false);
		txt_p1_5.setBackground(new Color(255, 182, 193));
		txt_p1_5.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_5.setColumns(10);
		txt_p1_5.setBounds(96, 138, 45, 28);
		upperSection.add(txt_p1_5);
		
		txt_p1_6 = new JTextField();
		txt_p1_6.setToolTipText("Sum of all dice with value of 6");
		txt_p1_6.setName("txt_p1_6");
		txt_p1_6.setEditable(false);
		txt_p1_6.setBackground(new Color(255, 182, 193));
		txt_p1_6.setBounds(96, 168, 45, 28);
		upperSection.add(txt_p1_6);
		txt_p1_6.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_6.setColumns(10);
		
		txt_p2_1 = new JTextField();
		txt_p2_1.setToolTipText("Sum of all dice with value of 1");
		txt_p2_1.setName("txt_p2_1");
		txt_p2_1.setEditable(false);
		txt_p2_1.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_1.setColumns(10);
		txt_p2_1.setBackground(new Color(255, 250, 205));
		txt_p2_1.setBounds(151, 18, 45, 28);
		upperSection.add(txt_p2_1);
		
		txt_p2_2 = new JTextField();
		txt_p2_2.setToolTipText("Sum of all dice with value of 2");
		txt_p2_2.setName("txt_p2_2");
		txt_p2_2.setEditable(false);
		txt_p2_2.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_2.setColumns(10);
		txt_p2_2.setBackground(new Color(255, 250, 205));
		txt_p2_2.setBounds(151, 48, 45, 28);
		upperSection.add(txt_p2_2);
		
		txt_p2_3 = new JTextField();
		txt_p2_3.setToolTipText("Sum of all dice with value of 3");
		txt_p2_3.setName("txt_p2_3");
		txt_p2_3.setEditable(false);
		txt_p2_3.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_3.setColumns(10);
		txt_p2_3.setBackground(new Color(255, 250, 205));
		txt_p2_3.setBounds(151, 78, 45, 28);
		upperSection.add(txt_p2_3);
		
		txt_p2_4 = new JTextField();
		txt_p2_4.setToolTipText("Sum of all dice with value of 4");
		txt_p2_4.setName("txt_p2_4");
		txt_p2_4.setEditable(false);
		txt_p2_4.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_4.setColumns(10);
		txt_p2_4.setBackground(new Color(255, 250, 205));
		txt_p2_4.setBounds(151, 108, 45, 28); 
		upperSection.add(txt_p2_4);
		
		txt_p2_5 = new JTextField();
		txt_p2_5.setToolTipText("Sum of all dice with value of 5");
		txt_p2_5.setName("txt_p2_5");
		txt_p2_5.setEditable(false);
		txt_p2_5.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_5.setColumns(10);
		txt_p2_5.setBackground(new Color(255, 250, 205));
		txt_p2_5.setBounds(151, 138, 45, 28);
		upperSection.add(txt_p2_5);
		
		txt_p2_6 = new JTextField();
		txt_p2_6.setToolTipText("Sum of all dice with value of 6");
		txt_p2_6.setName("txt_p2_6");
		
		txt_p2_6.setEditable(false);
		txt_p2_6.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_6.setColumns(10);
		txt_p2_6.setBackground(new Color(255, 250, 205));
		txt_p2_6.setBounds(151, 168, 45, 28);
		upperSection.add(txt_p2_6);
	}
	
	/**
	 * Creates the upper section's scoreboard
	 */
	private void createUpperScoreboard()
	{
		JLabel lblUpperSubtotal = new JLabel("Upper SubTotal");
		lblUpperSubtotal.setName("lblUpperSubtotal");
		lblUpperSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUpperSubtotal.setBounds(12, 221, 74, 14);
		upperSection.add(lblUpperSubtotal);
		
		JLabel lblBonus = new JLabel("Bonus");
		lblBonus.setName("lblBonus");
		lblBonus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBonus.setBounds(12, 248, 74, 14);
		upperSection.add(lblBonus);
		
		txt_p1_USubTotal = new JTextField();
		txt_p1_USubTotal.setName("txt_p1_USubTotal");
		txt_p1_USubTotal.setEditable(false);
		txt_p1_USubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_USubTotal.setText("0");
		txt_p1_USubTotal.setBackground(Color.WHITE);
		txt_p1_USubTotal.setBounds(96, 218, 45, 20);
		upperSection.add(txt_p1_USubTotal);
		txt_p1_USubTotal.setColumns(10);
		
		txt_p2_USubTotal = new JTextField();
		txt_p2_USubTotal.setName("txt_p2_USubTotal");
		txt_p2_USubTotal.setEditable(false);
		txt_p2_USubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_USubTotal.setText("0");
		txt_p2_USubTotal.setColumns(10);
		txt_p2_USubTotal.setBackground(Color.WHITE);
		txt_p2_USubTotal.setBounds(151, 218, 45, 20);
		upperSection.add(txt_p2_USubTotal);
		
		txt_p1_Bonus = new JTextField();
		txt_p1_Bonus.setName("txt_p1_Bonus");
		txt_p1_Bonus.setEditable(false);
		txt_p1_Bonus.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_Bonus.setText("0");
		txt_p1_Bonus.setColumns(10);
		txt_p1_Bonus.setBackground(Color.WHITE);
		txt_p1_Bonus.setBounds(96, 245, 45, 20);
		upperSection.add(txt_p1_Bonus);
		
		txt_p2_Bonus = new JTextField();
		txt_p2_Bonus.setName("txt_p2_Bonus");
		txt_p2_Bonus.setEditable(false);
		txt_p2_Bonus.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_Bonus.setText("0");
		txt_p2_Bonus.setColumns(10);
		txt_p2_Bonus.setBackground(Color.WHITE);
		txt_p2_Bonus.setBounds(151, 245, 45, 20);
		upperSection.add(txt_p2_Bonus);
		
		JSeparator separator_U1 = new JSeparator();
		separator_U1.setName("separator_U1");
		separator_U1.setBounds(10, 205, 190, 12);
		upperSection.add(separator_U1);
		
		JLabel lblUpperTotal = new JLabel("Upper Total");
		lblUpperTotal.setName("lblUpperTotal");
		lblUpperTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUpperTotal.setBounds(12, 279, 74, 14);
		upperSection.add(lblUpperTotal);
		
		txt_p1_UTotal = new JTextField();
		txt_p1_UTotal.setName("txt_p1_UTotal");
		txt_p1_UTotal.setEditable(false);
		txt_p1_UTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_UTotal.setText("0");
		txt_p1_UTotal.setColumns(10);
		txt_p1_UTotal.setBackground(Color.WHITE);
		txt_p1_UTotal.setBounds(96, 276, 45, 20);
		upperSection.add(txt_p1_UTotal);
		
		txt_p2_UTotal = new JTextField();
		txt_p2_UTotal.setName("txt_p2_UTotal");
		txt_p2_UTotal.setEditable(false);
		txt_p2_UTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_UTotal.setText("0");
		txt_p2_UTotal.setColumns(10);
		txt_p2_UTotal.setBackground(Color.WHITE);
		txt_p2_UTotal.setBounds(151, 276, 45, 20);
		upperSection.add(txt_p2_UTotal);
		
		JSeparator separator_U3 = new JSeparator();
		separator_U3.setName("separator_U3");
		separator_U3.setBounds(10, 270, 186, 3);
		upperSection.add(separator_U3);
	}
	
	/**
	 * Creates the lower section
	 */
	private void createLowerSection(){
		
		lowerSection = new JPanel();
		lowerSection.setName("lowerSection");
		lowerSection.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Lower Section", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lowerSection.setBounds(240, 65, 237, 276);
		frmYahtzee.getContentPane().add(lowerSection);
		lowerSection.setLayout(null);
		

		JRadioButton rdbtn3Kind = new JRadioButton("3 of a Kind");
		rdbtn3Kind.setToolTipText("Sum of all dice when you have at least 3 of a kind");
		rdbtn3Kind.setName("rdbtn3Kind");
		rdbtn3Kind.setBounds(6, 18, 121, 23);
		rdbtn3Kind.addActionListener(this);
		rdbtn3Kind.setActionCommand("rdbtn");
		lowerSection.add(rdbtn3Kind);
		scoreGroup.add(rdbtn3Kind);
		
		JRadioButton rdbtn4Kind = new JRadioButton("4 of a Kind");
		rdbtn4Kind.setToolTipText("Sum of all dice when you have at least 4 of a kind");
		rdbtn4Kind.setName("rdbtn4Kind");
		rdbtn4Kind.setBounds(6, 48, 121, 23);
		rdbtn4Kind.addActionListener(this);
		rdbtn4Kind.setActionCommand("rdbtn");
		lowerSection.add(rdbtn4Kind);
		scoreGroup.add(rdbtn4Kind);
		
		JRadioButton rdbtnFullHouse = new JRadioButton("Full House");
		rdbtnFullHouse.setToolTipText("25 pts when you have 3 of one kind and 2 of another");
		rdbtnFullHouse.setName("rdbtnFullHouse");
		rdbtnFullHouse.setBounds(6, 168, 121, 23);
		rdbtnFullHouse.addActionListener(this);
		rdbtnFullHouse.setActionCommand("rdbtn");
		lowerSection.add(rdbtnFullHouse);
		scoreGroup.add(rdbtnFullHouse);
		
		JRadioButton rdbtnSStraight = new JRadioButton("Small Straight");
		rdbtnSStraight.setToolTipText("30 pts when you have 4 dice in consequtive order");
		rdbtnSStraight.setName("rdbtnSStraight");
		rdbtnSStraight.setBounds(6, 78, 121, 23);
		rdbtnSStraight.addActionListener(this);
		rdbtnSStraight.setActionCommand("rdbtn");
		lowerSection.add(rdbtnSStraight);
		scoreGroup.add(rdbtnSStraight);
		
		JRadioButton rdbtnLStraight = new JRadioButton("Large Straight");
		rdbtnLStraight.setToolTipText("40 pts when you have 5 dice in consequtive order");
		rdbtnLStraight.setName("rdbtnLStraight");
		rdbtnLStraight.setBounds(6, 108, 121, 23);
		rdbtnLStraight.addActionListener(this);
		rdbtnLStraight.setActionCommand("rdbtn");
		lowerSection.add(rdbtnLStraight);
		scoreGroup.add(rdbtnLStraight);
		
		JRadioButton rdbtnYahtzee = new JRadioButton("Yahtzee!");
		rdbtnYahtzee.setToolTipText("50 pts when you have 5 of a kind");
		rdbtnYahtzee.setName("rdbtnYahtzee");
		rdbtnYahtzee.setBounds(6, 138, 121, 23);
		rdbtnYahtzee.addActionListener(this);
		rdbtnYahtzee.setActionCommand("rdbtn");
		lowerSection.add(rdbtnYahtzee);
		scoreGroup.add(rdbtnYahtzee);
		
		JRadioButton rdbtnChance = new JRadioButton("Chance");
		rdbtnChance.setToolTipText("Sum of all 5 dice");
		rdbtnChance.setName("rdbtnChance");
		rdbtnChance.setBounds(6, 198, 121, 23);
		rdbtnChance.addActionListener(this);
		rdbtnChance.setActionCommand("rdbtn");
		lowerSection.add(rdbtnChance);
		scoreGroup.add(rdbtnChance);
		
		txt_p1_3Kind = new JTextField();
		txt_p1_3Kind.setToolTipText("Sum of all dice when you have at least 3 of a kind");
		txt_p1_3Kind.setName("txt_p1_3Kind");
		txt_p1_3Kind.setBackground(new Color(255, 182, 193));
		txt_p1_3Kind.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_3Kind.setEditable(false);
		txt_p1_3Kind.setColumns(10);
		txt_p1_3Kind.setBounds(127, 16, 45, 28);
		lowerSection.add(txt_p1_3Kind);
		
		txt_p1_4Kind = new JTextField();
		txt_p1_4Kind.setToolTipText("Sum of all dice when you have at least 4 of a kind");
		txt_p1_4Kind.setName("txt_p1_4Kind");
		txt_p1_4Kind.setBackground(new Color(255, 182, 193));
		txt_p1_4Kind.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_4Kind.setEditable(false);
		txt_p1_4Kind.setColumns(10);
		txt_p1_4Kind.setBounds(127, 46, 45, 28);
		lowerSection.add(txt_p1_4Kind);
		
		txt_p1_SStraight = new JTextField();
		txt_p1_SStraight.setToolTipText("30 pts when you have 4 dice in consequtive order");
		txt_p1_SStraight.setName("txt_p1_SStraight");
		txt_p1_SStraight.setBackground(new Color(255, 182, 193));
		txt_p1_SStraight.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_SStraight.setEditable(false);
		txt_p1_SStraight.setColumns(10);
		txt_p1_SStraight.setBounds(127, 76, 45, 28);
		lowerSection.add(txt_p1_SStraight);
		
		txt_p1_LStraight = new JTextField();
		txt_p1_LStraight.setToolTipText("40 pts when you have 5 dice in consequtive order");
		txt_p1_LStraight.setName("txt_p1_LStraight");
		txt_p1_LStraight.setBackground(new Color(255, 182, 193));
		txt_p1_LStraight.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_LStraight.setEditable(false);
		txt_p1_LStraight.setColumns(10);
		txt_p1_LStraight.setBounds(127, 106, 45, 28);
		lowerSection.add(txt_p1_LStraight);
		
		txt_p1_Yahtzee = new JTextField();
		txt_p1_Yahtzee.setToolTipText("50 pts when you have 5 of a kind");
		txt_p1_Yahtzee.setName("txt_p1_Yahtzee");
		txt_p1_Yahtzee.setBackground(new Color(255, 182, 193));
		txt_p1_Yahtzee.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_Yahtzee.setEditable(false);
		txt_p1_Yahtzee.setColumns(10);
		txt_p1_Yahtzee.setBounds(127, 136, 45, 28);
		lowerSection.add(txt_p1_Yahtzee);
		
		txt_p1_FullHouse = new JTextField();
		txt_p1_FullHouse.setToolTipText("25 pts when you have 3 of one kind and 2 of another");
		txt_p1_FullHouse.setName("txt_p1_FullHouse");
		txt_p1_FullHouse.setBackground(new Color(255, 182, 193));
		txt_p1_FullHouse.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_FullHouse.setEditable(false);
		txt_p1_FullHouse.setColumns(10);
		txt_p1_FullHouse.setBounds(127, 166, 45, 28);
		lowerSection.add(txt_p1_FullHouse);
		
		txt_p1_Chance = new JTextField();
		txt_p1_Chance.setToolTipText("Sum of all 5 dice");
		txt_p1_Chance.setName("txt_p1_Chance");
		txt_p1_Chance.setBackground(new Color(255, 182, 193));
		txt_p1_Chance.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_Chance.setEditable(false);
		txt_p1_Chance.setColumns(10);
		txt_p1_Chance.setBounds(127, 196, 45, 28);
		lowerSection.add(txt_p1_Chance);
		
		txt_p2_3Kind = new JTextField();
		txt_p2_3Kind.setToolTipText("Sum of all dice when you have at least 3 of a kind");
		txt_p2_3Kind.setName("txt_p2_3Kind");
		txt_p2_3Kind.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_3Kind.setEditable(false);
		txt_p2_3Kind.setColumns(10);
		txt_p2_3Kind.setBackground(new Color(255, 250, 205));
		txt_p2_3Kind.setBounds(181, 16, 45, 28);
		lowerSection.add(txt_p2_3Kind);
		
		txt_p2_4Kind = new JTextField();
		txt_p2_4Kind.setToolTipText("Sum of all dice when you have at least 4 of a kind");
		txt_p2_4Kind.setName("txt_p2_4Kind");
		txt_p2_4Kind.setBounds(181, 46, 45, 28);
		lowerSection.add(txt_p2_4Kind);
		txt_p2_4Kind.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_4Kind.setEditable(false);
		txt_p2_4Kind.setColumns(10);
		txt_p2_4Kind.setBackground(new Color(255, 250, 205));
		
		txt_p2_SStraight = new JTextField();
		txt_p2_SStraight.setToolTipText("30 pts when you have 4 dice in consequtive order");
		txt_p2_SStraight.setName("txt_p2_SStraight");
		txt_p2_SStraight.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_SStraight.setEditable(false);
		txt_p2_SStraight.setColumns(10);
		txt_p2_SStraight.setBackground(new Color(255, 250, 205));
		txt_p2_SStraight.setBounds(181, 76, 45, 28);
		lowerSection.add(txt_p2_SStraight);
		
		txt_p2_LStraight = new JTextField();
		txt_p2_LStraight.setToolTipText("40 pts when you have 5 dice in consequtive order");
		txt_p2_LStraight.setName("txt_p2_LStraight");
		txt_p2_LStraight.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_LStraight.setEditable(false);
		txt_p2_LStraight.setColumns(10);
		txt_p2_LStraight.setBackground(new Color(255, 250, 205));
		txt_p2_LStraight.setBounds(181, 106, 45, 28);
		lowerSection.add(txt_p2_LStraight);
		
		txt_p2_Yahtzee = new JTextField();
		txt_p2_Yahtzee.setToolTipText("50 pts when you have 5 of a kind");
		txt_p2_Yahtzee.setName("txt_p2_Yahtzee");
		txt_p2_Yahtzee.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_Yahtzee.setEditable(false);
		txt_p2_Yahtzee.setColumns(10);
		txt_p2_Yahtzee.setBackground(new Color(255, 250, 205));
		txt_p2_Yahtzee.setBounds(181, 136, 45, 28);
		lowerSection.add(txt_p2_Yahtzee);
		
		txt_p2_FullHouse = new JTextField();
		txt_p2_FullHouse.setToolTipText("25 pts when you have 3 of one kind and 2 of another");
		txt_p2_FullHouse.setName("txt_p2_FullHouse");
		txt_p2_FullHouse.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_FullHouse.setEditable(false);
		txt_p2_FullHouse.setColumns(10);
		txt_p2_FullHouse.setBackground(new Color(255, 250, 205));
		txt_p2_FullHouse.setBounds(181, 166, 45, 28);
		lowerSection.add(txt_p2_FullHouse);
		
		txt_p2_Chance = new JTextField();
		txt_p2_Chance.setToolTipText("Sum of all 5 dice");
		txt_p2_Chance.setName("txt_p2_Chance");
		txt_p2_Chance.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_Chance.setEditable(false);
		txt_p2_Chance.setColumns(10);
		txt_p2_Chance.setBackground(new Color(255, 250, 205));
		txt_p2_Chance.setBounds(181, 196, 45, 28);
		lowerSection.add(txt_p2_Chance);
	}
	
	/**
	 * Creates the lower section's scoreboard
	 */
	private void createLowerScoreboard()
	{
		JSeparator separator_L1 = new JSeparator();
		separator_L1.setName("separator_L1");
		separator_L1.setBounds(6, 232, 220, 6);
		lowerSection.add(separator_L1);
		
		txt_p1_LTotal = new JTextField();
		txt_p1_LTotal.setName("txt_p1_LTotal");
		txt_p1_LTotal.setText("0");
		txt_p1_LTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p1_LTotal.setEditable(false);
		txt_p1_LTotal.setColumns(10);
		txt_p1_LTotal.setBackground(Color.WHITE);
		txt_p1_LTotal.setBounds(127, 243, 45, 20);
		lowerSection.add(txt_p1_LTotal);
		
		txt_p2_LTotal = new JTextField();
		txt_p2_LTotal.setName("txt_p2_LTotal");
		txt_p2_LTotal.setText("0");
		txt_p2_LTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txt_p2_LTotal.setEditable(false);
		txt_p2_LTotal.setColumns(10);
		txt_p2_LTotal.setBackground(Color.WHITE);
		txt_p2_LTotal.setBounds(181, 243, 45, 20);
		lowerSection.add(txt_p2_LTotal);
		
		JLabel lblLowerTotal = new JLabel("Lower Total");
		lblLowerTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLowerTotal.setBounds(6, 246, 107, 14);
		lowerSection.add(lblLowerTotal);
	}

	/**
	 * Runs when any component is clicked and runs the appropriate set of actions
	 */ 
	public void actionPerformed(ActionEvent e) {
		
		if("ExitGame".equals(e.getActionCommand()))
			System.exit(0);
		if("NewGame".equals(e.getActionCommand()))
			resetEntireGame();
	    if ("Roll".equals(e.getActionCommand()))
	    {	
	    	enableAllRadioButtons();
	    	disableCertainButtons();
	    	enableCheckBoxes();
	    	gameLogic.rollDice();
	    	displayRolledDice();
	    	decreaseTimesRolled();
	    	if (getRollsLeft() == 0)
	    		((JButton) getComponentByName("btnRoll")).setVisible(false);
	    	if (getRollsLeft() < 3 && (((JRadioButton) getComponentByName("rdbtnHidden")).isSelected() == false))
	    		((JButton) getComponentByName("btnPlay")).setVisible(true);
	    	if (isDisplayingScore)
	    		displayTheorizedScore();
	    	
	    }
	    if("chkbox".equals(e.getActionCommand()))
	    {
	    	checkBoxTheDie();
	    	displayRolledDice(); 
	    }
	    if("rdbtn".equals(e.getActionCommand()))
	    {
	    	displayTheorizedScore();
	    	isDisplayingScore = true;
	    	if (((JRadioButton) getComponentByName("rdbtnHidden")).isSelected() == false)
	    		((JButton) getComponentByName("btnPlay")).setVisible(true);
	    }
	    
	    if("Play".equals(e.getActionCommand()))
	    {
	    	boolean can = false;
	    	isDisplayingScore = false;
	    	for (int radioIndex = 0; radioIndex < rdbtnNames.length; radioIndex++)
			{
				if(((JRadioButton) getComponentByName(rdbtnNames[radioIndex])).isSelected() == true)
					can = true;
			}
	    	
	    	if(can)
	    	{
		    	confirmSelection();
		    	switchCurrentPlayer();
		    	resetToBlankDice();
		    	uncheckBoxes();
		    	disableCheckBoxes();
		    	disableAllRadioButtons();
		    	resetRollButton();
		    	((JButton) getComponentByName("btnPlay")).setVisible(false);
		    	((JRadioButton) getComponentByName("rdbtnHidden")).setSelected(true);
		    	saveScores();
				displayTotals();
				
				if(checkGameOver())
					showGameResults();
					
	    	}
	    }
	}

	/**
	 * Checks the selected checkbox
	 */
	private void checkBoxTheDie()
	{
		for (int i = 0; i < 5; i++)
		{
			Die[] diceSet = gameLogic.getDice();
			String checkboxName = "checkBox_" +(i+1);
			
			JCheckBox selectedBox  = ((JCheckBox) getComponentByName(checkboxName));
			if(selectedBox.isSelected())
				diceSet[i].setSelect(true);
			else
				diceSet[i].setSelect(false);
		}
	}
	
	/**
	 * Confirms the selection by disabling the selected radiobutton
	 */
	private void confirmSelection()
	{
		
		for (int radioIndex = 0; radioIndex < rdbtnNames.length-1; radioIndex++)
		{
			JRadioButton button = ((JRadioButton) getComponentByName(rdbtnNames[radioIndex]));
			Player currentPlayer = gameLogic.getCurrentPlayer();
			
			if(button.isSelected())
				currentPlayer.setAction(radioIndex,false); 

			button.setEnabled(false);

			String p1Str = "txt_p1_" + rdbtnNames[radioIndex].substring(5);
			String p2Str = "txt_p2_" + rdbtnNames[radioIndex].substring(5);
			JTextField p1Field = ((JTextField)(getComponentByName(p1Str)));
			JTextField p2Field = ((JTextField)(getComponentByName(p2Str)));
			if (!p1Field.getText().equalsIgnoreCase("") && !p2Field.getText().equalsIgnoreCase(""))
				button.setEnabled(false);
			
		}
		

	}
	
	/**
	 * Displays a theoretical score in the appropriate textfield
	 */
	private void displayTheorizedScore()
	{
		int currentPlayer = gameLogic.getCurrentPlayerTurn();
		
		for (int radioIndex = 0; radioIndex < rdbtnNames.length-1; radioIndex++)
		{
			JRadioButton button = ((JRadioButton) getComponentByName(rdbtnNames[radioIndex]));
			Player player = gameLogic.getCurrentPlayer();
			boolean[] actions = player.getAllowedActions();
			if(button.isSelected() && actions[radioIndex])
				setTextField(getSpecifiedScore(rdbtnNames[radioIndex].substring(5)), currentPlayer,rdbtnNames[radioIndex].substring(5));
			else
			{
				if(button.isEnabled() && actions[radioIndex])
					setTextField("",currentPlayer,rdbtnNames[radioIndex].substring(5));
			}
		}
	}

	/**
	 * Displays the results of the game; Winner
	 */
	public void showGameResults()
	{
		int win = gameLogic.getWinner();
		String msg ="";
		if (win == 1)
			msg = gameLogic.getPlayerOne().getName() + " won with " + gameLogic.getPlayerOne().getGrandTotal() + " points!!!\n Would you like to start a new game?";
		else if (win == 2)
			msg = gameLogic.getPlayerTwo().getName() + " won with " + gameLogic.getPlayerTwo().getGrandTotal() + " points!!!\n Would you like to start a new game?";
		else if (win == 0)
			msg = "You tied.\nWould you like to start a new game?";

		int reply = JOptionPane.showConfirmDialog(null, msg, "Yahtzee - Game Over", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          resetEntireGame();
        }
        else
           System.exit(0);
		
	}

	/**
	 * Resets the entire game to its initial state
	 */
	private void resetEntireGame()
	{
		gameLogic = new GameLogic();
		isDisplayingScore = false;
		this.resetRollButton();
		this.resetToBlankDice();
		uncheckBoxes();
		disableCheckBoxes();
		disableAllRadioButtons();
		resetTextFields();
		indicatePlayer();
    	((JButton) getComponentByName("btnPlay")).setVisible(false);
    	((JRadioButton) getComponentByName("rdbtnHidden")).setSelected(true);
	}

	/**
	 * Creates a hashMap which stores all the components in the project.
	 */
	private void createComponentMap() {
	    mainComponentMap = new HashMap<String,Component>();
	    List<Component> components = getAllComponents(frmYahtzee.getContentPane());
	    for (Component comp : components)
	        mainComponentMap.put(comp.getName(), comp);
	}
	
	/**
	 * Returns a List of type Component with all the components in a specified Container
	 * @param c A Container with various components (Ex: JPanel with multiple JTextFields)
	 * @return A List of Components of type Component
	 */
	private List<Component> getAllComponents(Container c) {
	    Component[] comps = c.getComponents();
	    List<Component> compList = new ArrayList<Component>();
	    for (Component comp : comps) {
	        compList.add(comp);
	        if (comp instanceof Container)
	            compList.addAll(getAllComponents((Container) comp));
	    }
	    return compList;
	}
	
	/**
	 * Returns the Component specified by name
	 * @param name The actual name of the Component you are looking for
	 * @return  if found, the Component you are looking for; null otherwise. 
	 */
	public static Component getComponentByName(String name) {
	    if (mainComponentMap.containsKey(name))
	        return (Component) mainComponentMap.get(name);
	    else return null;
	}
	
	/**
	 * Sets a specific JTextField to a specific value.
	 * @param valueToEnter The value showing up in the box
	 * @param player Current Player, 1 or 2
	 * @param typeOfField Indicates which field to look for
	 */
	public void setTextField(String valueToEnter, int player, String typeOfField)
	{
		String nameOfField = "txt_p" + player + "_" + typeOfField;
		JTextField x = ((JTextField)(getComponentByName(nameOfField)));
		String y = "" + valueToEnter;
		x.setText(y);
	}

	/**
	 * Displays the set of 5 dice on screen
	 */
	private void displayRolledDice() {
		Die[] diceSet = gameLogic.getDice();
		for (int diceNum = 0; diceNum < 5; diceNum++)
			setDie(diceNum,diceSet[diceNum].getValue(), diceSet[diceNum].isSelected());
		
	}
	
	/**
	 * Sets the die JLabel to the correct picture
	 * @param diceNum a number from the 5 dice 
	 * @param diceVal the face value of the die
	 * @param isSelected the selected status of the die
	 */
	private void setDie(int diceNum, int diceVal, boolean isSelected)
	{
		String diceName = "lblDice" + (diceNum+1);
		Die[] diceSet = gameLogic.getDice();
		diceSet[diceNum].setSelect(isSelected);
		JLabel die = ((JLabel)(getComponentByName(diceName)));
		die.setIcon(getDiePicture(diceVal, isSelected));
	}
	
	/**
	 * Gets the appropriate dice picture
	 * @param num The face value of the die
	 * @param isSelected The selected status of the die
	 * @return an image of the correct die
	 */
	private ImageIcon getDiePicture(int num, boolean isSelected) 
	{
		String pictureSource = "/yahtzeeDicePics/D";
		if (num == 0)
			pictureSource+="Blank";
		else
			pictureSource+=num;
		
		if (isSelected)
			pictureSource+="_Select";
		
		pictureSource+=".png";
		return (new ImageIcon(yahtzeeGUI.class.getResource(pictureSource)));
	}

	/**
	 * Returns how many rolls the player has left
	 * @return the number of rolls left
	 */
	private int getRollsLeft()
	{
		 return gameLogic.getRollsLeft();
	}
	
	/**
	 * Decrements the roll count by 1
	 */
	private void decreaseTimesRolled()
	{
		gameLogic.decreaseTimesRolled();
		btnRoll.setText("Roll! [" + getRollsLeft() + "]");
	}
	
	/**
	 * Resets the roll count of the player
	 */
	private void resetRollCount()
	{
		gameLogic.resetRolls();
	}

	/**
	 * Switches between first and second player
	 */
	public void switchCurrentPlayer()
	{
		gameLogic.switchCurrentPlayer();
		indicatePlayer();
	}
	
	/**
	 * Indicates the current player by bolding and increasing the font size
	 */
	public void indicatePlayer()
	{
		if(gameLogic.getCurrentPlayerTurn() == 1)
		{
			((JLabel) getComponentByName("lblPlayer1")).setFont(new Font("Lucida Grande", Font.BOLD, 20));
			((JLabel) getComponentByName("lblPlayer2")).setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		}
		else
		{
			((JLabel) getComponentByName("lblPlayer2")).setFont(new Font("Lucida Grande", Font.BOLD, 20));
			((JLabel) getComponentByName("lblPlayer1")).setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		}
			
	}

	/**
	 * Displays blank dice
	 */
	private void resetToBlankDice()
	{
		for( int i = 0; i < 5; i++)
			setDie(i, 0, true);
	}
	
	/**
	 * Enables all checkboxes
	 */
	private void enableCheckBoxes()
	{
		for( int i = 0; i < 5; i++)
		{
			String name = "checkBox_" + (i+1);
			((JCheckBox) getComponentByName(name)).setEnabled(true);
		}
	}
	
	/**
	 * Disables the checkboxes
	 */
	private void disableCheckBoxes()
	{
		for( int i = 0; i < 5; i++)
		{
			String name = "checkBox_" + (i+1);
			((JCheckBox) getComponentByName(name)).setEnabled(false);
		}
	}
	
	/**
	 * Unchecks the checkboxes
	 */
	private void uncheckBoxes()
	{
		for( int i = 0; i < 5; i++)
		{
			String name = "checkBox_" + (i+1);
			((JCheckBox) getComponentByName(name)).setSelected(false);
		}
		
		for(Die die: gameLogic.getDice())
			die.setSelect(false);
	}
	
	/**
	 * Disables all radiobuttons
	 */
	private void disableAllRadioButtons()
	{
		for (int radioIndex = 0; radioIndex < rdbtnNames.length; radioIndex++)
		{
			((JRadioButton) getComponentByName(rdbtnNames[radioIndex])).setEnabled(false);
			((JRadioButton) getComponentByName(rdbtnNames[radioIndex])).setSelected(false);
		}
	}
	
	/**
	 * Enables all radiobuttons
	 */
	private void enableAllRadioButtons()
	{
		for (int radioIndex = 0; radioIndex < rdbtnNames.length; radioIndex++)
			((JRadioButton) getComponentByName(rdbtnNames[radioIndex])).setEnabled(true);
	}
	
	/**
	 * Disables specific buttons based on current player and the occupied textfields
	 */
	private void disableCertainButtons()
	{
		for (int radioIndex = 0; radioIndex < rdbtnNames.length-1; radioIndex++)
		{
			JRadioButton button = ((JRadioButton) getComponentByName(rdbtnNames[radioIndex]));
			Player player = gameLogic.getCurrentPlayer();
			boolean[] actions = player.getAllowedActions();
			if (!actions[radioIndex])
				button.setEnabled(false);
			else
				button.setEnabled(true);
			
			String p1Str = "txt_p1_" + rdbtnNames[radioIndex].substring(5);
			String p2Str = "txt_p2_" + rdbtnNames[radioIndex].substring(5);
			JTextField p1Field = ((JTextField)(getComponentByName(p1Str)));
			JTextField p2Field = ((JTextField)(getComponentByName(p2Str)));
			if (!p1Field.getText().equalsIgnoreCase("") && !p2Field.getText().equalsIgnoreCase(""))
				button.setEnabled(false);
		}
	}
	
	/**
	 * Resets the rollbutton
	 */
	private void resetRollButton()
	{
		resetRollCount();
		((JButton) getComponentByName("btnRoll")).setText("Roll! [" + getRollsLeft() + "]");
		((JButton) getComponentByName("btnRoll")).setVisible(true);
	}
	
	/**
	 * Resets the textfields to its original state
	 */
	private void resetTextFields()
	{
		
		for(int i = 0; i < rdbtnNames.length -1; i++)
		{
			setTextField("", 1, rdbtnNames[i].substring(5));
			setTextField("", 2, rdbtnNames[i].substring(5));	
		}
		
		setTextField("0", 1, "USubTotal");
		setTextField("0", 1, "UTotal");
		setTextField("0", 1, "LTotal");
		setTextField("0", 1, "Bonus");
		setTextField("0", 2, "USubTotal");
		setTextField("0", 2, "UTotal");
		setTextField("0", 2, "LTotal");
		setTextField("0", 2, "Bonus");
		
		((JTextField)getComponentByName("txt_p1_GTotal")).setText("0");
		((JTextField)getComponentByName("txt_p2_GTotal")).setText("0");

	}
	
	/**
	 * Takes all scores displayed on screen and puts them in the player's profile
	 */
	private void saveScores()
	{
		for (int radioIndex = 0; radioIndex < rdbtnNames.length-1; radioIndex++)
		{
			String p1Str = "txt_p1_" + rdbtnNames[radioIndex].substring(5);
			String p2Str = "txt_p2_" + rdbtnNames[radioIndex].substring(5);
			JTextField p1Field = ((JTextField)(getComponentByName(p1Str)));
			JTextField p2Field = ((JTextField)(getComponentByName(p2Str)));
			
			int p1Score = 0;
			int p2Score = 0;
			if (p1Field.getText().equalsIgnoreCase(""))
				p1Score = 0;
			else
				p1Score = Integer.parseInt(p1Field.getText());
			
			if (p2Field.getText().equalsIgnoreCase(""))
				p2Score = 0;
			else
				p2Score = Integer.parseInt(p2Field.getText());
			
			gameLogic.addScore(1, rdbtnNames[radioIndex].substring(5), p1Score);
			gameLogic.addScore(2, rdbtnNames[radioIndex].substring(5), p2Score);
			
		}
	}
	
	/**
	 * Returns a specific score of String type
	 * @param typeOfScore the desired type of score
	 * @return The desired score
	 */
	private String getSpecifiedScore(String typeOfScore)
	{
		return gameLogic.getScore(typeOfScore);
	}

	/**
	 * Checks if the game has ended.
	 * @return true if game has ended
	 */
	public boolean checkGameOver() 
	{
		return gameLogic.checkGameOver();
	}

	/**
	 * Displays the total scores at the bottom
	 */
	private void displayTotals()
	{
		gameLogic.calculateTotals();
		Player player1 = gameLogic.getPlayerOne();
		Player player2 = gameLogic.getPlayerTwo();
		((JTextField)getComponentByName("txt_p1_USubTotal")).setText(""+player1.getUpperSubTotal());
		((JTextField)getComponentByName("txt_p2_USubTotal")).setText(""+player2.getUpperSubTotal());
		((JTextField)getComponentByName("txt_p1_Bonus")).setText(""+player1.getUpperBonus());
		((JTextField)getComponentByName("txt_p2_Bonus")).setText(""+player2.getUpperBonus());
		((JTextField)getComponentByName("txt_p1_UTotal")).setText(""+player1.getUpperSubTotal());
		((JTextField)getComponentByName("txt_p2_UTotal")).setText(""+player2.getUpperSubTotal());
		((JTextField)getComponentByName("txt_p1_LTotal")).setText(""+player1.getLowerTotal());
		((JTextField)getComponentByName("txt_p2_LTotal")).setText(""+player2.getLowerTotal());
		((JTextField)getComponentByName("txt_p1_GTotal")).setText(""+player1.getGrandTotal());
		((JTextField)getComponentByName("txt_p2_GTotal")).setText(""+player2.getGrandTotal());
		//update boxes
	}

	/**
	 * Asks the players for their names 
	 */
	private void getPlayerNames()
	{
		String player1Name = JOptionPane.showInputDialog(frmYahtzee,"Enter name for Player 1", "Player 1", JOptionPane.QUESTION_MESSAGE);
		String player2Name = JOptionPane.showInputDialog(frmYahtzee,"Enter name for Player 2", "Player 2", JOptionPane.QUESTION_MESSAGE);
		
		(gameLogic.getPlayerOne()).setName(player1Name);
		(gameLogic.getPlayerTwo()).setName(player2Name);
	}
}