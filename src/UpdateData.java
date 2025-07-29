import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class UpdateData extends JFrame implements ActionListener { //Class Declaration
    private final JFrame frame;
    private final String studentID;
    private final String studentName;
    private final String studentYear;
    private JPanel UpdateForm;
    private JTextField stdNameTxt;
    private JComboBox yearCB;
    private JTextField bmETxt;
    private JTextField bmMTxt;
    private JTextField biETxt;
    private JTextField biMTxt;
    private JTextField sejETxt;
    private JTextField sejMTxt;
    private JTextField mtETxt;
    private JTextField mtMTxt;
    private JTextField stdIDTxt;
    private JTextField bmFTxt;
    private JTextField biFTxt;
    private JTextField sejFTxt;
    private JTextField mtFTxt;
    private JButton updateBtn;
    private JButton returnBtn;

    public UpdateData(String studentID, String studentName, String studentYear) { //Constructors

        this.studentID = studentID;
        this.studentName = studentName;
        this.studentYear = studentYear;

        frame = new JFrame("Update Student Details");
        frame.add(UpdateForm);
        frame.setPreferredSize(new Dimension(520, 350));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeFields();
        loadStudentGrades();
        updateBtn.addActionListener(this);
        returnBtn.addActionListener(this);
    }

    private void initializeFields() { //Method for Initializes Form Fields and Makes the Fields Non-Editable
        stdIDTxt.setText(studentID);
        stdIDTxt.setEditable(false);
        stdNameTxt.setText(studentName);
        stdNameTxt.setEditable(false);
        yearCB.setSelectedItem(studentYear);
        yearCB.setEnabled(false);
    }

    private void loadStudentGrades() { //Method to load Student Information and Score by Line and Index
        File file = new File("StudentsDetails/" + studentID + ".txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("BAHASA MELAYU")) {
                        bmETxt.setText(reader.readLine().substring(13));
                        bmMTxt.setText(reader.readLine().substring(14));
                        bmFTxt.setText(reader.readLine().substring(13));
                    } else if (line.startsWith("BAHASA INGGERIS")) {
                        biETxt.setText(reader.readLine().substring(13));
                        biMTxt.setText(reader.readLine().substring(14));
                        biFTxt.setText(reader.readLine().substring(13));
                    } else if (line.startsWith("SEJARAH")) {
                        sejETxt.setText(reader.readLine().substring(13));
                        sejMTxt.setText(reader.readLine().substring(14));
                        sejFTxt.setText(reader.readLine().substring(13));
                    } else if (line.startsWith("MATEMATIK")) {
                        mtETxt.setText(reader.readLine().substring(13));
                        mtMTxt.setText(reader.readLine().substring(14));
                        mtFTxt.setText(reader.readLine().substring(13));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent ae) { //Method for every button assign
        if (ae.getSource() == updateBtn) {
            saveStudentGrades();
        } else if (ae.getSource() == returnBtn) {
            new StudentList();
            frame.dispose();
        }
    }

    private void saveStudentGrades() { //Method to Save Student Score after Update the Data to a File
        String studentID = stdIDTxt.getText();
        String studentName = stdNameTxt.getText();
        String studentYear = (String) yearCB.getSelectedItem();

        if (studentID.isEmpty() || studentName.isEmpty() || Objects.requireNonNull(studentYear).isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please Fill in All Details.","WARNING!",JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("StudentsDetails/" + studentID + ".txt"))) {
            writer.write("Student ID : " + studentID + "\n");
            writer.write("Student Name : " + studentName + "\n");
            writer.write("Student Year : " + studentYear + "\n\n");

            writer.write("BAHASA MELAYU SCORES : \n");
            writer.write("Early Exam : " + bmETxt.getText() + "\n");
            writer.write("MidTerm Exam : " + bmMTxt.getText() + "\n");
            writer.write("Final Exam : " + bmFTxt.getText() + "\n\n");

            writer.write("BAHASA INGGERIS SCORES : \n");
            writer.write("Early Exam : " + biETxt.getText() + "\n");
            writer.write("MidTerm Exam : " + biMTxt.getText() + "\n");
            writer.write("Final Exam : " + biFTxt.getText() + "\n\n");

            writer.write("SEJARAH SCORES : \n");
            writer.write("Early Exam : " + sejETxt.getText() + "\n");
            writer.write("MidTerm Exam : " + sejMTxt.getText() + "\n");
            writer.write("Final Exam : " + sejFTxt.getText() + "\n\n");

            writer.write("MATEMATIK SCORES : \n");
            writer.write("Early Exam : " + mtETxt.getText() + "\n");
            writer.write("MidTerm Exam : " + mtMTxt.getText() + "\n");
            writer.write("Final Exam : " + mtFTxt.getText() + "\n\n");

            JOptionPane.showMessageDialog(frame,"Data Update Successfully","CONGRATULATIONS!",JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new MenuList();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error Saving The Data : " + e.getMessage(), "WARNING!",JOptionPane.WARNING_MESSAGE);
        }
    }
}
