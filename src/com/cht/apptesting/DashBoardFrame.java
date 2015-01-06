package com.cht.apptesting;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class DashBoardFrame {
	public JTextPane MessagePane;
	private String apkPath = "";
	private String packageName = "";
	private String sha256Sum = "";
	private String workDir = "";
	private static String scriptPath = "/Tools/Data/workspace/ADAP/scripts/";
	private Object emulators[] ={};
	private JFrame frame;
	private JTextField apkPathText;
	private JButton btnChooseApk;
	private JLabel lblDecompilingStep;
	private JButton btnCreateWorkspace;
	private JButton btnOpenFolder;
	private JButton btnOnekeydecompile;
	private JButton btnSetWorkspace;
	private JCheckBox chckbxUseHttpProxy;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DashBoardFrame window = new DashBoardFrame();
					window.frame.setVisible(true);
					window.frame.setTitle("Dashboard of Android APP Security Testing");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DashBoardFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Load APK File
		JLabel lblApkPath = new JLabel("Apk Path:");
		lblApkPath.setBounds(33, 12, 86, 33);
		lblApkPath.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(lblApkPath);
		
		apkPathText = new JTextField();
		apkPathText.setToolTipText("path...");
		apkPathText.setBounds(108, 16, 435, 26);
		apkPathText.setColumns(10);
		frame.getContentPane().add(apkPathText);
		
		
		btnChooseApk = new JButton("Choose apk");
		btnChooseApk.setBounds(555, 16, 117, 25);
		btnChooseApk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				loadApkFunction();
			}
		});
		frame.getContentPane().add(btnChooseApk);
		
		// --------------------- Pre-Set --------------------------
		lblDecompilingStep = new JLabel("Decompiling Step");
		lblDecompilingStep.setBounds(33, 116, 189, 15);
		frame.getContentPane().add(lblDecompilingStep);

			
		btnCreateWorkspace = new JButton("Create Workspace");
		btnCreateWorkspace.setBounds(33, 71, 168, 26);
		btnCreateWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createFolderIntoWorkspace();
			}
		});
		frame.getContentPane().add(btnCreateWorkspace);
		
		btnSetWorkspace = new JButton("Set Workspace");
		btnSetWorkspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setWorkspaceFolder();
			}
		});
		btnSetWorkspace.setBounds(219, 71, 151, 26);
		frame.getContentPane().add(btnSetWorkspace);
		
		btnOpenFolder = new JButton("Open Folder");
		btnOpenFolder.setBounds(382, 71, 132, 26);
		btnOpenFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					openWorkspaceFolder();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnOpenFolder);
		
		//---------------------decompile--------------------------
		btnOnekeydecompile = new JButton("one-key-decompile");
		btnOnekeydecompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeDecompileProcess();
			}
		});
		btnOnekeydecompile.setBounds(33, 143, 168, 26);
		btnOnekeydecompile.setToolTipText("Tools: apktool/dex2jar/jd-gui");
		frame.getContentPane().add(btnOnekeydecompile);
		
		JButton btnJdgui = new JButton("JD-GUI");
		btnJdgui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				launchJdgui();
			}
		});
		btnJdgui.setBounds(33, 270, 117, 25);
		frame.getContentPane().add(btnJdgui);
		
		// --------------------Logs------------------------------
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 320, 694, 119);
		frame.getContentPane().add(separator);
				
		JLabel lblMessages = new JLabel("Messages:");
		lblMessages.setBounds(17, 327, 102, 15);
		frame.getContentPane().add(lblMessages);
		
		MessagePane = new JTextPane();
		MessagePane.setBounds(17, 343, 689, 96);
		MessagePane.setEditable(false);
		MessagePane.setVisible(true);
		JScrollPane scroll = new JScrollPane (MessagePane, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(17, 343, 689, 96);
		frame.getContentPane().add(scroll);
		
		//----------------------Launch-----------------------------
		JLabel lblLaunch = new JLabel("Launch");
		lblLaunch.setBounds(33, 188, 70, 15);
		frame.getContentPane().add(lblLaunch);
		
		JButton btnBurpsuite = new JButton("Burpsuite");
		btnBurpsuite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				launchBurpsuite();
			}
		});
		btnBurpsuite.setBounds(33, 215, 117, 25);
		frame.getContentPane().add(btnBurpsuite);
		
		chckbxUseHttpProxy = new JCheckBox("Use HTTP Proxy");
		chckbxUseHttpProxy.setBounds(168, 241, 151, 23);
		frame.getContentPane().add(chckbxUseHttpProxy);
		
		JButton btnEmulator = new JButton("Emulator");
		btnEmulator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				launchEmulator();
			}
		});
		btnEmulator.setBounds(168, 215, 117, 25);
		frame.getContentPane().add(btnEmulator);
		
		JButton btnSqliteBrowser = new JButton("SQLite Browser");
		btnSqliteBrowser.setBounds(162, 270, 143, 25);
		btnSqliteBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchSqliteBrowser();
			}
		});
		frame.getContentPane().add(btnSqliteBrowser);
		
		JButton btnDrozer = new JButton("Drozer");
		btnDrozer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchDrozer();
			}
		});
		btnDrozer.setBounds(317, 270, 117, 25);
		btnDrozer.setToolTipText("Please remember to turn on drozer server on the mobile device.");
		frame.getContentPane().add(btnDrozer);
		
		JButton btnWireshark = new JButton("Wireshark");
		btnWireshark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchWireshark();
			}
		});
		btnWireshark.setBounds(308, 215, 117, 25);
		frame.getContentPane().add(btnWireshark);
		
	}
	// New thread for output
	private class StreamGobbler extends Thread {
	    InputStream is;
	    String type;

	    private StreamGobbler(InputStream is, String type) {
	        this.is = is;
	        this.type = type;
	    }

	    @Override
	    public void run() {
	        try {
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line = null;
	            while ((line = br.readLine()) != null){
	                if(type.compareTo("ERROR") == 0 ){
	                	showErrorMessages(line);
	                }
	                else if(type.compareTo("LOG") == 0){
	                	showLogMessages(line);
	                }
	            }
	        }
	        catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	    }
	}
	// When press btnChooseApk Button...
	private void loadApkFunction() {
		// let user choose apk.
		String apkfilesPath = "/home/apprisk/Desktop";
		//UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		JFileChooser chooser = new JFileChooser(apkfilesPath);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Android app achieve files(*.apk)", "apk");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		 
		int returnVal = chooser.showOpenDialog(btnChooseApk);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			apkPath = chooser.getSelectedFile().getAbsolutePath();
			packageName = runCmdReturnResult(new String[]{"ruby", scriptPath+"getpkg.rb", apkPath});
			apkPathText.setText(apkPath);
			showLogMessages("choosen file: " + apkPath);
			showLogMessages("package name: " + packageName);
		}
	}
	
	// When press btnCreateWorkspace Button
	private void createFolderIntoWorkspace(){
		if(CheckFileExists(apkPath)){
			sha256Sum = runCmdReturnResult(new String[] {"ruby", scriptPath+"getSha256.rb", apkPath});
			workDir = "/Tools/Data/workspace/"+packageName+"/"+sha256Sum;
			runCmd(new String[]{"mkdir", "-p", workDir});
			//runCmd(new String[]{"cp",apkPath,workDir});
				
			showLogMessages("Create Directory Successfully: "+workDir);
			btnOpenFolder.setVisible(true);
		}
		else{
			showWarningMessages("File not exist!");
		}
	}
	// When press btnSetWorkspace
	private void setWorkspaceFolder(){
		JFileChooser chooser = new JFileChooser("/Tools/Data/workspace/");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		int returnVal = chooser.showOpenDialog(btnSetWorkspace);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			workDir = chooser.getSelectedFile().getAbsolutePath();
			showLogMessages("Set Workspace:" + workDir);
		}
	}
	
	// When press btnOpenFolder
	private void openWorkspaceFolder() throws IOException{
		if(CheckFileExists(workDir)){
			File dir = new File(workDir);
			if (Desktop.isDesktopSupported()) {
			    Desktop.getDesktop().open(dir);
			}
		}
		else{
			showWarningMessages("Please set workspace path.");
		}
	}
	//When press btnOneKeyDecompile
	private void executeDecompileProcess() {
		if( CheckFileExists(apkPath) && CheckFileExists(workDir)){
			runCmd(new String[]{"/bin/bash", "-c", scriptPath+"exeDecompile.sh "+ apkPath+" "+ workDir+" "+packageName});
			showLogMessages("Decompile Start!");
		}
		else{
			showWarningMessages("Please choose correct apk file/workspace.");
		}
	}
		
	//When press btnEmulator
	private void launchEmulator(){
		if( chckbxUseHttpProxy.isSelected() ){
			runCmd(new String[]{"/bin/bash","-c",scriptPath+"launchEmulator.sh proxy"});
			showLogMessages("Launch Android Emulator(CHTSOC_4.1.2) with http proxy 127.0.0.1:8080");
		}
		else {
			runCmd(new String[]{"/bin/bash","-c",scriptPath+"launchEmulator.sh"});
			showLogMessages("Launch Android Emulator(CHTSOC_4.1.2)");
		}
	}
	
	// When press btnBurpsuite
	private void launchBurpsuite(){
		runCmd(new String[]{"/bin/bash","-c",scriptPath+"launchBurp.sh"});
		showLogMessages("Launch Burpsuite Successfully!");
	}
	
	//When press btnSqlitebrowser
	private void launchSqliteBrowser(){
		runCmd(new String[]{"/bin/bash","-c","sqlitebrowser&"});
		showLogMessages("Launch Sqlite Browser Successfully!");
	}
	
	// When press btnJdgui
	private void launchJdgui(){
		runCmd(new String[]{"/bin/bash", "-c", "/Tools/decompiler/jd-gui-0.3.5.linux.i686/jd-gui&"});
		showLogMessages("Launch JD-GUI Sucessfully!");
	}
	
	//When press btnDrozer
	private void launchDrozer(){
		if(CheckEmulatorExists()){
			String s = (String)JOptionPane.showInputDialog(
                    frame,
                    "Choose one emulator:", //Text
                    "Drozer", //Title
                    JOptionPane.PLAIN_MESSAGE,
                    null, //Icon
                    emulators, //Options
                    null);
			if(s != null){
				runCmd(new String[]{"/bin/bash","-c",scriptPath+"launchDrozer.sh "+s});
				runCmd(new String[]{"gnome-terminal", "-e", "bash -c \"drozer console connect; exec bash\""});
			}
			showLogMessages(s + " is selected.");
		}
		else{
			showWarningMessages("Launch emulator before launch Drozer.");
		}
	}
	
	// When press btnWireshark
	private void launchWireshark(){
		runCmd(new String[]{"gnome-terminal", "-e", "sudo wireshark&"}); //TODO: sudo
		showLogMessages("Launch Wireshark Successfully!");
	}
	// customize append function of JTextPane
	private void append(Color c, String s) { // better implementation--uses StyleContext
		StyledDocument doc = MessagePane.getStyledDocument();
		Style style = MessagePane.addStyle("Log", null);
		StyleConstants.setForeground(style, c);
		try { 
			doc.insertString(doc.getLength(), s, style); 
		} catch (BadLocationException e){}
	}
	
	//Show Runtime Message.
	private void showLogMessages(String s){
		append( Color.BLACK, "[Info]" + s + "\n");
	}
	private void showErrorMessages(String s){
		append(Color.RED, "[Error]" + s + "\n");
	}
	private void showWarningMessages(String s){
		append(Color.BLUE, "[Warning]"+s+"\n");
	}
	// Run command and return the result
	private String runCmdReturnResult(String[] cmd){
		String result ="";
		ProcessBuilder pb = new ProcessBuilder(cmd);
		Process proc;
		try {
			proc = pb.start();
			BufferedReader buf = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			//BufferedReader error =new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			String line = null;
            while ((line = buf.readLine()) != null){
            	result += line;
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Run command with new thread
	private void runCmd(String[] cmd){
		
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectErrorStream(true); // separate the thread.
		//Runtime rt = Runtime.getRuntime();
		//String [] env = { "PATH="+System.getenv("PATH")+":/Tools/adt-bundle-linux-x86_64-20140702/sdk/build-tools/21.1.1/"};
		//Process proc = rt.start(cmd, env);
		Process proc;
		try {
			proc = pb.start();
			
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "LOG");

			// start gobblers
			outputGobbler.start();
			errorGobbler.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Check file exist or not
	private boolean CheckFileExists(String s){
		File tmp = new File(s);
		if(s.length() > 0 && tmp.exists()){
			return true;
		}
		else{
			return false;
		}
	}
	
	// Check Emulator is launched or not
	private boolean CheckEmulatorExists(){
		String device = runCmdReturnResult(new String[]{"ruby", scriptPath+"checkDevices.rb"});
		if(device.isEmpty()){
			return false;
		}
		else{
			// convert string to string array.
			emulators = device.split("\tdevice");
			//emulators = Array.copyOf(ary, ary.length, Object[].class);
			//String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
			return true;
		}
	}
}
