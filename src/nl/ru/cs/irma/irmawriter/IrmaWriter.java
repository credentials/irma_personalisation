package nl.ru.cs.irma.irmawriter;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import javax.mail.MessagingException;
import javax.smartcardio.CardException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JProgressBar;

import nl.ru.cs.irma.irmawriter.CardWriter.MUtilException;
import credentials.CredentialsException;
import javax.swing.JSeparator;

public class IrmaWriter {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("nl.ru.cs.irma.irmawriter.messages"); //$NON-NLS-1$

	private JFrame frame;
	private Vector<Card> cards;
	private int selectedCard = 0;
	private ImageIcon noCardImage;

	private JLabel lblInfo;

	private JLabel lblImage;

	private JLabel lblCardNumber;

	private DefaultListModel cardsListModel;

	private JList cardsList;

	private JLabel lblPersonalisedWarning;

	private JProgressBar progressBar;

	private JButton btnWriteButton;

	private JButton btnSkip;
	private JSeparator separator;
	private JButton btnClear;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				IrmaWriter window = null;
				try {
					window = new IrmaWriter();
					if(window.frame != null) {
						window.frame.setVisible(true);
					}
				} catch (Exception e) {
					if(window!=null && window.frame != null) {
						window.error(e);
					}
					else {
						JOptionPane.showMessageDialog(null, BUNDLE.getString("IrmaWriter.lblInfo.error"));
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IrmaWriter() {
		initialize();
		try {
			cards = DatabaseConnection.loadFromPrinterCards();
		} catch (Exception e) {
			error(e, BUNDLE.getString("IrmaWriter.lblInfo.cardLoadError"));
		}
		if(cards != null) {
			fillCardList();
			showCard();
			if(cards.size() == 0) {
				lblInfo.setText(BUNDLE.getString("IrmaWriter.lblInfo.nocards"));
			}
		}
	}

	private void fillCardList() {
		for(Card card : cards) {
			cardsListModel.addElement(card.getCardId());
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 619, 411);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, rightPanel);
		
		cardsListModel = new DefaultListModel();
		cardsList = new JList(cardsListModel);
		cardsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedCard = cardsList.getSelectedIndex();
				showCard();
			}
		});
		cardsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(cardsList);
		
		JLabel lblCards = new JLabel(BUNDLE.getString("IrmaWriter.lblCards.text")); //$NON-NLS-1$
		scrollPane.setColumnHeaderView(lblCards);
		
		
		lblInfo = new JLabel();
		lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(lblInfo);
		
		JLabel lblScanNext = new JLabel(BUNDLE.getString("IrmaWriter.lblScanNext.text"));
		lblScanNext.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(lblScanNext);
		
		noCardImage = new ImageIcon(getClass().getResource("/resources/nophoto.png"));
		lblImage = new JLabel(noCardImage);
		lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblImage.setSize(128, 256);
		lblImage.setBorder(BorderFactory.createLineBorder(SystemColor.windowBorder));
		rightPanel.add(lblImage);
		
		lblCardNumber = new JLabel();
		lblCardNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(lblCardNumber);
		
		btnWriteButton = new JButton(BUNDLE.getString("IrmaWriter.btnWriteButton.text"));
		btnWriteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnWriteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					final CardWriter writer = new CardWriter();
					writer.addObserver(new Observer(){
						@Override
						public void update(Observable arg0, Object arg1) {
							progressBar.setValue(writer.getProgress());
						}
					});
					Thread thread = new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								setAllEnabled(false);
								writer.Write(cards.get(selectedCard));
								nextCard();
								lblInfo.setText(BUNDLE.getString("IrmaWriter.lblInfo.writeSuccess"));
							} catch (Exception e) {
								progressBar.setValue(0);
								error(e, BUNDLE.getString("IrmaWriter.lblInfo.writeCardError"));
							}
							finally {
								setAllEnabled(true);
							}
							
						}
					});
					thread.start();
				} catch (Exception e) {
					error(e, BUNDLE.getString("IrmaWriter.lblInfo.writeCardError"));
				}
			}
		});
		
		lblPersonalisedWarning = new JLabel(BUNDLE.getString("IrmaWriter.lblPersonalisedWarning.text"));
		lblPersonalisedWarning.setVisible(false);
		lblPersonalisedWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(lblPersonalisedWarning);
		rightPanel.add(btnWriteButton);
		
		btnSkip = new JButton(BUNDLE.getString("IrmaWriter.btnSkip.text"));
		btnSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextCard();
			}
		});
		btnSkip.setAlignmentX(Component.CENTER_ALIGNMENT);
		rightPanel.add(btnSkip);
		
		progressBar = new JProgressBar();
		rightPanel.add(progressBar);
		
		separator = new JSeparator();
		rightPanel.add(separator);
		
		btnClear = new JButton(BUNDLE.getString("IrmaWriter.btnClear.text")); //$NON-NLS-1$
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setAllEnabled(false);
					CardWriter.clearCard();
					lblInfo.setText(BUNDLE.getString("IrmaWriter.lblInfo.clearSuccess"));
				} catch (Exception e) {
					error(e, BUNDLE.getString("IrmaWriter.lblInfo.clearError"));
				}
				finally {
					setAllEnabled(true);
				}
			}
		});
		rightPanel.add(btnClear);
		
		frame.getContentPane().add(splitPane);
	}

	private void showCard() {
		if(selectedCard >= 0 && selectedCard < cards.size()) {
			lblInfo.setText("");
			Card card = cards.get(selectedCard);
			lblImage.setIcon(new ImageIcon(card.getPhoto()));
			lblCardNumber.setText(Integer.toString(card.getCardId()));
			lblPersonalisedWarning.setVisible(card.isPersonalised());
			if(card.isPersonalised()) {
				progressBar.setValue(100);
			}
			else {
				progressBar.setValue(0);
			}
			cardsList.setSelectedIndex(selectedCard);
		}
		else {
			lblImage.setIcon(noCardImage);
			lblCardNumber.setText(BUNDLE.getString("IrmaWriter.lblCardNumber.noCardSelected"));
			lblPersonalisedWarning.setVisible(false);
			cardsList.clearSelection();
		}
	}

	private void nextCard() {
		selectedCard++;
		if(selectedCard >= cards.size()) {
			lblInfo.setText(BUNDLE.getString("IrmaWriter.lblInfo.allCardsDone"));
		}
		showCard();
	}
	
	private void setAllEnabled(boolean enabled) {
		btnClear.setEnabled(enabled);
		btnSkip.setEnabled(enabled);
		btnWriteButton.setEnabled(enabled);
		cardsList.setEnabled(enabled);
	}
	
	private void error(Exception e) {
		error(e, BUNDLE.getString("IrmaWriter.lblInfo.error"));
	}
	
	private void error(Exception e, String message) {
		lblInfo.setText(message);
		e.printStackTrace();
		Logger.log(message, e);
	}
}
