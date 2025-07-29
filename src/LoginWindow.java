import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginWindow extends JFrame implements ActionListener { //Class Declaration
    private final JFrame frame;
    private JPanel LoginForm;
    private JLabel FormName;
    private JTextField userTxt;
    private JPasswordField passTxt;
    private JLabel Username;
    private JLabel Password;
    private JButton loginBtn;
    private JButton quitBtn;

    public LoginWindow() { //Constructors
        frame = new JFrame("Login Authentication");
        frame.add(LoginForm);
        frame.setPreferredSize(new Dimension(310, 250));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loginBtn.addActionListener(this);
        quitBtn.addActionListener(this);

        passTxt.addKeyListener(new KeyAdapter() { //Method for "Enter" Key Pressed
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginBtn.doClick();
                }
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == quitBtn) {
            System.exit(0);
        } else {
            String userVal = userTxt.getText();
            String passVal = new String(passTxt.getPassword());

            try {
                boolean loggedIn = false;
                BufferedReader reader = new BufferedReader(new FileReader("login.txt"));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String fileUser = parts[0].trim();
                        String filePass = parts[1].trim();

                        if (userVal.equals(fileUser) && passVal.equals(filePass)) {
                            loggedIn = true;
                            break;
                        }
                    }
                }

                reader.close();

                if (loggedIn) {
                    MenuList nextMenuList = new MenuList();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect Username/Password", "WARNING!", JOptionPane.WARNING_MESSAGE);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error reading login file", "ERROR!", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
