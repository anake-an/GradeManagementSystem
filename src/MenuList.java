import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuList extends JFrame implements ActionListener { //Class Declaration
    private final JFrame frame;
    private JPanel MenuForm;
    private JButton newStdRegBtn;
    private JButton listStdRegBtn;
    private JButton quitBtn;

    public MenuList() { //Constructors
        frame = new JFrame("Menu List");
        frame.add(MenuForm);
        frame.setPreferredSize(new Dimension(420, 250));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        newStdRegBtn.addActionListener(this);
        listStdRegBtn.addActionListener(this);
        quitBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) { //Method for every button assign
        if (ae.getSource() == newStdRegBtn) {
            new Registration();
            frame.dispose();
        } else if (ae.getSource() == listStdRegBtn) {
            new StudentList();
            frame.dispose();
        } else if (ae.getSource() == quitBtn) {
            System.exit(0);
        }
    }
}
