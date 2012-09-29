package nl.ru.cs.irma.irmawriter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JTextArea;

public class ConfigDialog extends JDialog {
	private static final long serialVersionUID = 3564813686350167114L;

	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("nl.ru.cs.irma.irmawriter.messages"); //$NON-NLS-1$

	public static final String CONFIG_FILENAME = "config.encrypted";
	private final JPanel contentPanel = new JPanel();
	private JTextField tbFrom;
	private JTextField tbSmtp;
	private JTextField tbMailSubject;
	private JTextField tbDbUrl;
	private JTextField tbDbUsername;
	private JTextField tbDbPassword;
	private JPasswordField passwordField;

	private Properties props = null;

	private JTextArea tbMailBody;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConfigDialog dialog = new ConfigDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConfigDialog(JDialog owner) {
		super(owner, true);
		setBounds(100, 100, 633, 417);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblMail = new JLabel(BUNDLE.getString("ConfigDialog.lblMail.text")); //$NON-NLS-1$
			lblMail.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblMail = new GridBagConstraints();
			gbc_lblMail.anchor = GridBagConstraints.WEST;
			gbc_lblMail.insets = new Insets(0, 0, 5, 5);
			gbc_lblMail.gridx = 0;
			gbc_lblMail.gridy = 0;
			contentPanel.add(lblMail, gbc_lblMail);
		}
		{
			JLabel lblFromAdres = new JLabel(BUNDLE.getString("ConfigDialog.lblFromAdres.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblFromAdres = new GridBagConstraints();
			gbc_lblFromAdres.insets = new Insets(0, 0, 5, 5);
			gbc_lblFromAdres.anchor = GridBagConstraints.EAST;
			gbc_lblFromAdres.gridx = 0;
			gbc_lblFromAdres.gridy = 1;
			contentPanel.add(lblFromAdres, gbc_lblFromAdres);
		}
		{
			tbFrom = new JTextField();
			GridBagConstraints gbc_tbFrom = new GridBagConstraints();
			gbc_tbFrom.insets = new Insets(0, 0, 5, 0);
			gbc_tbFrom.fill = GridBagConstraints.HORIZONTAL;
			gbc_tbFrom.gridx = 1;
			gbc_tbFrom.gridy = 1;
			contentPanel.add(tbFrom, gbc_tbFrom);
			tbFrom.setColumns(10);
		}
		{
			JLabel lblSmtpServer = new JLabel(BUNDLE.getString("ConfigDialog.lblSmtpServer.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblSmtpServer = new GridBagConstraints();
			gbc_lblSmtpServer.anchor = GridBagConstraints.EAST;
			gbc_lblSmtpServer.insets = new Insets(0, 0, 5, 5);
			gbc_lblSmtpServer.gridx = 0;
			gbc_lblSmtpServer.gridy = 2;
			contentPanel.add(lblSmtpServer, gbc_lblSmtpServer);
		}
		{
			tbSmtp = new JTextField();
			GridBagConstraints gbc_tbSmtp = new GridBagConstraints();
			gbc_tbSmtp.insets = new Insets(0, 0, 5, 0);
			gbc_tbSmtp.anchor = GridBagConstraints.NORTH;
			gbc_tbSmtp.fill = GridBagConstraints.HORIZONTAL;
			gbc_tbSmtp.gridx = 1;
			gbc_tbSmtp.gridy = 2;
			contentPanel.add(tbSmtp, gbc_tbSmtp);
			tbSmtp.setColumns(10);
		}
		{
			JLabel lblMailSubject = new JLabel(BUNDLE.getString("ConfigDialog.lblMailSubject.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblMailSubject = new GridBagConstraints();
			gbc_lblMailSubject.anchor = GridBagConstraints.EAST;
			gbc_lblMailSubject.insets = new Insets(0, 0, 5, 5);
			gbc_lblMailSubject.gridx = 0;
			gbc_lblMailSubject.gridy = 3;
			contentPanel.add(lblMailSubject, gbc_lblMailSubject);
		}
		{
			tbMailSubject = new JTextField();
			GridBagConstraints gbc_tbMailSubject = new GridBagConstraints();
			gbc_tbMailSubject.insets = new Insets(0, 0, 5, 0);
			gbc_tbMailSubject.fill = GridBagConstraints.HORIZONTAL;
			gbc_tbMailSubject.gridx = 1;
			gbc_tbMailSubject.gridy = 3;
			contentPanel.add(tbMailSubject, gbc_tbMailSubject);
			tbMailSubject.setColumns(10);
		}
		{
			JLabel lblBody = new JLabel(BUNDLE.getString("ConfigDialog.lblBody.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblBody = new GridBagConstraints();
			gbc_lblBody.anchor = GridBagConstraints.EAST;
			gbc_lblBody.insets = new Insets(0, 0, 5, 5);
			gbc_lblBody.gridx = 0;
			gbc_lblBody.gridy = 4;
			contentPanel.add(lblBody, gbc_lblBody);
		}
		{
			tbMailBody = new JTextArea();
			GridBagConstraints gbc_tbMailBody = new GridBagConstraints();
			gbc_tbMailBody.gridheight = 2;
			gbc_tbMailBody.insets = new Insets(0, 0, 5, 0);
			gbc_tbMailBody.fill = GridBagConstraints.BOTH;
			gbc_tbMailBody.gridx = 1;
			gbc_tbMailBody.gridy = 4;
			contentPanel.add(tbMailBody, gbc_tbMailBody);
		}
		{
			JLabel lblDatabase = new JLabel(BUNDLE.getString("ConfigDialog.lblDatabase.text")); //$NON-NLS-1$
			lblDatabase.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblDatabase = new GridBagConstraints();
			gbc_lblDatabase.insets = new Insets(0, 0, 5, 5);
			gbc_lblDatabase.gridx = 0;
			gbc_lblDatabase.gridy = 6;
			contentPanel.add(lblDatabase, gbc_lblDatabase);
		}
		{
			JLabel lblUrl = new JLabel(BUNDLE.getString("ConfigDialog.lblUrl.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblUrl = new GridBagConstraints();
			gbc_lblUrl.anchor = GridBagConstraints.EAST;
			gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
			gbc_lblUrl.gridx = 0;
			gbc_lblUrl.gridy = 7;
			contentPanel.add(lblUrl, gbc_lblUrl);
		}
		{
			tbDbUrl = new JTextField();
			GridBagConstraints gbc_tbDbUrl = new GridBagConstraints();
			gbc_tbDbUrl.insets = new Insets(0, 0, 5, 0);
			gbc_tbDbUrl.fill = GridBagConstraints.HORIZONTAL;
			gbc_tbDbUrl.gridx = 1;
			gbc_tbDbUrl.gridy = 7;
			contentPanel.add(tbDbUrl, gbc_tbDbUrl);
			tbDbUrl.setColumns(10);
		}
		{
			JLabel lblUsername = new JLabel(BUNDLE.getString("ConfigDialog.lblUsername.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.anchor = GridBagConstraints.EAST;
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 0;
			gbc_lblUsername.gridy = 8;
			contentPanel.add(lblUsername, gbc_lblUsername);
		}
		{
			tbDbUsername = new JTextField();
			GridBagConstraints gbc_tbDbUsername = new GridBagConstraints();
			gbc_tbDbUsername.insets = new Insets(0, 0, 5, 0);
			gbc_tbDbUsername.fill = GridBagConstraints.HORIZONTAL;
			gbc_tbDbUsername.gridx = 1;
			gbc_tbDbUsername.gridy = 8;
			contentPanel.add(tbDbUsername, gbc_tbDbUsername);
			tbDbUsername.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel(BUNDLE.getString("ConfigDialog.lblPassword.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 9;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			tbDbPassword = new JTextField();
			GridBagConstraints gbc_tbDbPassword = new GridBagConstraints();
			gbc_tbDbPassword.insets = new Insets(0, 0, 5, 0);
			gbc_tbDbPassword.fill = GridBagConstraints.HORIZONTAL;
			gbc_tbDbPassword.gridx = 1;
			gbc_tbDbPassword.gridy = 9;
			contentPanel.add(tbDbPassword, gbc_tbDbPassword);
			tbDbPassword.setColumns(10);
		}
		{
			JLabel lblEncryption = new JLabel(BUNDLE.getString("ConfigDialog.lblEncryption.text")); //$NON-NLS-1$
			lblEncryption.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblEncryption = new GridBagConstraints();
			gbc_lblEncryption.anchor = GridBagConstraints.WEST;
			gbc_lblEncryption.insets = new Insets(0, 0, 5, 5);
			gbc_lblEncryption.gridx = 0;
			gbc_lblEncryption.gridy = 11;
			contentPanel.add(lblEncryption, gbc_lblEncryption);
		}
		{
			JLabel lblEncPassword = new JLabel(BUNDLE.getString("ConfigDialog.lblPassword_1.text")); //$NON-NLS-1$
			GridBagConstraints gbc_lblEncPassword = new GridBagConstraints();
			gbc_lblEncPassword.anchor = GridBagConstraints.EAST;
			gbc_lblEncPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblEncPassword.gridx = 0;
			gbc_lblEncPassword.gridy = 12;
			contentPanel.add(lblEncPassword, gbc_lblEncPassword);
		}
		{
			passwordField = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = new Insets(0, 0, 5, 0);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 1;
			gbc_passwordField.gridy = 12;
			contentPanel.add(passwordField, gbc_passwordField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(BUNDLE.getString("okButton.text")); //$NON-NLS-1$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Properties config = new Properties();
						//Mail:
						config.setProperty("fromAdres", tbFrom.getText());
						config.setProperty("smtpServer", tbSmtp.getText());
						config.setProperty("mailSubject", tbMailSubject.getText());
						config.setProperty("mailBody", tbMailBody.getText());
						//Database:
						config.setProperty("database_url", tbDbUrl.getText());
						config.setProperty("database_username", tbDbUsername.getText());
						config.setProperty("database_password", tbDbPassword.getText());
						
						props = config;
						
						EncryptedLoader loader = new EncryptedLoader(passwordField.getPassword(), CONFIG_FILENAME);
						try {
							loader.save(config);
						} catch (IOException e) {
							Logger.log("Error while writing config", e);
							JOptionPane.showMessageDialog(ConfigDialog.this, BUNDLE.getString("ConfigDialog.mbox.writeError"));
							e.printStackTrace();
						}
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(BUNDLE.getString("cancelButton.text")); //$NON-NLS-1$
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public Properties showDialog() throws IOException {
		setVisible(true);
		return props;
	}
}
