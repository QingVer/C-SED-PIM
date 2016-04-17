import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PINGui {
	public static void main(String args[])
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, KeyStoreException, CertificateException, IOException, UnrecoverableEntryException {

		JFrame frame = new JFrame("Enter Pin...");
		frame.setPreferredSize(new Dimension(300, 90));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		JLabel label;
		if (new File("pin.keystore").exists() == true) {
			label = new JLabel("Enter 4 Digit Pin");
		} else {
			label = new JLabel("Create a Digit Pin");
		}
		JTextField textBox = new JTextField();
		textBox.setPreferredSize(new Dimension(75, textBox.getPreferredSize().height));
		JButton continueButton = new JButton("Continue");
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (new File("pin.keystore").exists() == true) {
						PinCode pin = new PinCode();
						if (!pin.allowAccess(textBox.getText())) {
							JOptionPane.showMessageDialog(frame, "Wrong pin, please try again");
						}else{
							frame.dispose();
						}
						
					} else {
						if (textBox.getText().length() != 4) {
							JOptionPane.showMessageDialog(frame, "Invalid Pin Number...");
						}
						PinCode pin = new PinCode(Integer.parseInt(textBox.getText()));
						frame.dispose();
					}
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} catch (UnrecoverableEntryException e1) {
					e1.printStackTrace();
				} catch (KeyStoreException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (NoSuchPaddingException e1) {
					e1.printStackTrace();
				} catch (IllegalBlockSizeException e1) {
					e1.printStackTrace();
				} catch (BadPaddingException e1) {
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame, "Invalid Pin Number...");
				} catch (CertificateException e1) {
					e1.printStackTrace();
				}

			}
		});
		frame.getContentPane().add(label);
		frame.getContentPane().add(textBox);
		frame.getContentPane().add(continueButton);

		frame.pack();
		frame.setVisible(true);
	}
}
