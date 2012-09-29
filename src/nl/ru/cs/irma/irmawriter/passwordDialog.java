package nl.ru.cs.irma.irmawriter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;

import java.util.Properties;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class passwordDialog extends JDialog {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("nl.ru.cs.irma.irmawriter.messages"); //$NON-NLS-1$

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private Properties props = null;
	private IOException thrownException = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			passwordDialog dialog = new passwordDialog(null);
			dialog.showDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public passwordDialog(Frame owner) {
		super(owner, "Enter password", true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 111);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		{
			JLabel lblPassword = new JLabel(BUNDLE.getString("passwordDialog.lblPassword.text")); //$NON-NLS-1$
			contentPanel.add(lblPassword);
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(BUNDLE.getString("okButton.text")); //$NON-NLS-1$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						EncryptedLoader loader = new EncryptedLoader(passwordField.getPassword(), ConfigDialog.CONFIG_FILENAME);
						try {
							props = loader.load();
							setVisible(false);
						} catch (org.jasypt.exceptions.EncryptionOperationNotPossibleException e) {
							JOptionPane.showMessageDialog(passwordDialog.this, BUNDLE.getString("passwordDialog.mbox.decryptError"));
						} catch (IOException e) {
							thrownException = e;
							setVisible(false);
						}
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
			{
				JButton btnConfigure = new JButton(BUNDLE.getString("passwordDialog.btnConfigure.text")); //$NON-NLS-1$
				btnConfigure.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ConfigDialog configDialog = new ConfigDialog(passwordDialog.this);
						try {
							props = configDialog.showDialog();
							if(props != null) {
								setVisible(false);
							}
						} catch (IOException e) {
							thrownException = e;
						}
					}
				});
				buttonPane.add(btnConfigure);
			}
		}
	}
	
	public Properties showDialog() throws IOException {
		setVisible(true);
		if(thrownException != null) {
			throw thrownException;
		}
		return props;
	}

}
