package autonomy;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textField_messagecount;
	private JTextField textField_randomconcat;
	private JTextField textField_messageinterval;
	private JButton btnBegin;
	
	String txtfilepath;
	int sentences = 0;
	int wordsInSentence = 0;
	int interval = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Autonomy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 251, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_messageCount = new JLabel("Message Count:");
		lbl_messageCount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lbl_messageCount.setToolTipText("How many messages you would like to be sent.");
		lbl_messageCount.setBounds(10, 64, 114, 14);
		contentPane.add(lbl_messageCount);
		
		JLabel lbl_randomConcat = new JLabel("Random Concatenation: ");
		lbl_randomConcat.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lbl_randomConcat.setToolTipText("Strings together a random phrase from the given text. Leave at 1 if you do not intend on using this feature.");
		lbl_randomConcat.setBounds(10, 89, 138, 14);
		contentPane.add(lbl_randomConcat);
		
		JLabel lbl_interval = new JLabel("Message Interval:");
		lbl_interval.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lbl_interval.setToolTipText("Delay between messages in milliseconds.");
		lbl_interval.setBounds(10, 114, 114, 14);
		contentPane.add(lbl_interval);
		
		textField_messagecount = new JTextField();
		textField_messagecount.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		textField_messagecount.setText("1");
		textField_messagecount.setBounds(139, 61, 86, 20);
		contentPane.add(textField_messagecount);
		textField_messagecount.setColumns(10);
		
		textField_randomconcat = new JTextField();
		textField_randomconcat.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		textField_randomconcat.setText("1");
		textField_randomconcat.setBounds(139, 86, 86, 20);
		contentPane.add(textField_randomconcat);
		textField_randomconcat.setColumns(10);
		
		textField_messageinterval = new JTextField();
		textField_messageinterval.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		textField_messageinterval.setText("1000");
		textField_messageinterval.setBounds(139, 111, 86, 20);
		contentPane.add(textField_messageinterval);
		textField_messageinterval.setColumns(10);
		
		JLabel lblStatus = new JLabel("Status: Waiting for text file");
		lblStatus.setForeground(new Color(105, 105, 105));
		lblStatus.setBounds(10, 236, 215, 14);
		contentPane.add(lblStatus);
		
		JLabel lblAutonomy = new JLabel("AUTONOMY");
		lblAutonomy.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblAutonomy.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutonomy.setBounds(10, 11, 215, 39);
		contentPane.add(lblAutonomy);
		
		JLabel lbl_textfile = new JLabel("Text File:");
		lbl_textfile.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lbl_textfile.setBounds(10, 145, 114, 14);
		contentPane.add(lbl_textfile);
		
		JButton btnSelectFile = new JButton("Browse text file...");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("C:/"));
				fc.setDialogTitle("Find text file");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(fc.showOpenDialog(btnSelectFile) == JFileChooser.APPROVE_OPTION) {
					
				}
				txtfilepath = fc.getSelectedFile().getAbsolutePath();
				btnSelectFile.setText(txtfilepath);
				lblStatus.setText("Status: Ready");
				btnBegin.setEnabled(true);
			}
		});
		btnSelectFile.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnSelectFile.setBounds(65, 142, 160, 23);
		contentPane.add(btnSelectFile);
		
		btnBegin = new JButton("Begin");
		btnBegin.setEnabled(false);
		btnBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				sentences = Integer.parseInt(textField_messagecount.getText());
				wordsInSentence = Integer.parseInt(textField_randomconcat.getText());
				interval = Integer.parseInt(textField_messageinterval.getText());
				
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							btnSelectFile.setEnabled(false);
							btnBegin.setEnabled(false);
							btnBegin.setText("Working");
							
							String sentence = "";
							String[] words = ChatUtilities.readLines(txtfilepath);
							
							for(int i = 0; i < sentences; i++) {
								sentence = ChatUtilities.generatePhrase(wordsInSentence, words);
								lblStatus.setText("Status: " + "PASTING (" + sentence + ")");
								try {
									Thread.sleep(interval);
								} catch (InterruptedException e2) {
									e2.printStackTrace();
								}
								StringSelection stringSelection = new StringSelection(sentence);
								Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
								clipboard.setContents(stringSelection, stringSelection);
								
								ChatUtilities.sendMessage();
								
							}
							
							btnSelectFile.setEnabled(true);
							btnBegin.setEnabled(true);
							btnBegin.setText("Begin");
							lblStatus.setText("Status: Ready (Finished last run)");
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				});
				thread.start();
			}
		});
		btnBegin.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnBegin.setBounds(75, 186, 89, 23);
		contentPane.add(btnBegin);
	}
}
