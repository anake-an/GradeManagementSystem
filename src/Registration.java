import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Registration extends JFrame implements ActionListener { //Class Declaration
    private final JFrame frame;
    private JPanel RegistrationForm;
    private JTextField stdIDTxt;
    private JTextField stdNameTxt;
    private JComboBox<String> yearCB;
    private JTextField bmETxt;
    private JTextField bmMTxt;
    private JTextField bmFTxt;
    private JTextField biETxt;
    private JTextField biMTxt;
    private JTextField biFTxt;
    private JTextField sejETxt;
    private JTextField sejMTxt;
    private JTextField sejFTxt;
    private JTextField mtETxt;
    private JTextField mtMTxt;
    private JTextField mtFTxt;
    private JButton confirmBtn;
    private JButton returnBtn;

    public Registration() { //Constructors
        frame = new JFrame("New Student !");
        frame.add(RegistrationForm);
        frame.setPreferredSize(new Dimension(520, 350));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        confirmBtn.addActionListener(this);
        returnBtn.addActionListener(this);
    }

    public static void main(String[] args) { //Main Method
        SwingUtilities.invokeLater(Registration::new);
    }

    public void actionPerformed(ActionEvent ae) { //Method for every button assign
        if (ae.getSource() == confirmBtn) {
            saveDataToFile();
        } else if (ae.getSource() == returnBtn) {
            new MenuList();
            frame.dispose();
        }
    }

    private boolean isStudentIDExists(String studentID) { //Method for Check Student ID Exist or Not
        File file = new File("StudentsDetails/" + studentID + ".txt");
        return file.exists();
    }

    private void saveDataToFile() { //Method to Save Data into File by .txt format
        String studentID = stdIDTxt.getText();
        String studentName = stdNameTxt.getText();
        String studentYear = (String) yearCB.getSelectedItem();

        if (studentID.isEmpty() || studentName.isEmpty() || Objects.requireNonNull(studentYear).isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please Fill in All Details.", "WARNING!",JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (isStudentIDExists(studentID)) {
            JOptionPane.showMessageDialog(frame, "Student ID " + studentID + " Already Exists.", "WARNING!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder data = new StringBuilder();
        data.append("============================").append("\nStudent ID : ").append(studentID).append("\n");
        data.append("Student Name : ").append(studentName).append("\n");
        data.append("Student Year : ").append(studentYear).append("\n");

        data.append("\nBAHASA MELAYU SCORES   \n");
        data.append("Early Exam : ").append(bmETxt.getText()).append("\n");
        data.append("MidTerm Exam : ").append(bmMTxt.getText()).append("\n");
        data.append("Final Exam : ").append(bmFTxt.getText()).append("\n");

        data.append("\nBAHASA INGGERIS SCORES   \n");
        data.append("Early Exam : ").append(biETxt.getText()).append("\n");
        data.append("MidTerm Exam : ").append(biMTxt.getText()).append("\n");
        data.append("Final Exam : ").append(biFTxt.getText()).append("\n");

        data.append("\nSEJARAH SCORES   \n");
        data.append("Early Exam : ").append(sejETxt.getText()).append("\n");
        data.append("MidTerm Exam : ").append(sejMTxt.getText()).append("\n");
        data.append("Final Exam : ").append(sejFTxt.getText()).append("\n");

        data.append("\nMATEMATIK SCORES   \n");
        data.append("Early Exam : ").append(mtETxt.getText()).append("\n");
        data.append("MidTerm Exam : ").append(mtMTxt.getText()).append("\n");
        data.append("Final Exam : ").append(mtFTxt.getText()).append("\n");

        data.append("\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("StudentsDetails/" + studentID + ".txt"))) {
            writer.write(data.toString());
            JOptionPane.showMessageDialog(frame,"Data Save Successfully","CONGRATULATIONS!",JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new MenuList();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error Saving Student Data : " + e.getMessage(),"WARNING!", JOptionPane.WARNING_MESSAGE);
        }
    }
}
