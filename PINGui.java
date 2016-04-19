import java.awt.*;
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
import javax.swing.*;

public class PINGui {
    private static boolean ready;
    public static void showGui(){
        ready = false;
        final JFrame frame = new JFrame("Enter Pin...");
        //frame.setPreferredSize(new Dimension(300, 90));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        JLabel label;
        if (new File(DynOSor.rootDirectory + File.separator + "pin.keystore").exists()) {
            label = new JLabel("Enter 4 Digit Pin");
        } else {
            label = new JLabel("Create a Digit Pin");
        }
        Image image = DynOSor.getLogo();
        label.setIcon(new ImageIcon(image.getScaledInstance(75,75,Image.SCALE_DEFAULT)));
        label.setFont(new Font("Lucida",Font.BOLD,24));
        final JTextField textBox = new JTextField();
        textBox.setPreferredSize(new Dimension(75, textBox.getPreferredSize().height));
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (new File(DynOSor.rootDirectory + File.separator + "pin.keystore").exists()) {
                        PinCode pin = new PinCode();
                        if (!pin.allowAccess(textBox.getText())) {
                            JOptionPane.showMessageDialog(frame, "Wrong pin, please try again");
                        }else{
                            frame.dispose();
                            ready = true;
                        }

                    } else {
                        if (textBox.getText().length() != 4) {
                            JOptionPane.showMessageDialog(frame, "Invalid Pin Number...");
                        }
                        PinCode pin = new PinCode(Integer.parseInt(textBox.getText()));
                        frame.dispose();
                        ready = true;
                    }
                } catch (NoSuchAlgorithmException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | KeyStoreException | UnrecoverableEntryException e1) {
                    e1.printStackTrace();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(frame, "Invalid Pin Number...");
                }

            }
        });
        frame.getContentPane().add(label);
        frame.getContentPane().add(textBox);
        frame.getContentPane().add(continueButton);
        frame.pack();
        frame.setVisible(true);
        try {
            waitForPassword();
        } catch (QuitException e){
            frame.dispose();
            System.exit(0);
        }
    }

    private static void waitForPassword() throws QuitException {
        while (!ready) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new QuitException();
            }
        }
        ready = false;
    }
}