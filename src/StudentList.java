import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class StudentList extends JFrame implements ActionListener { //Class Declaration
    private final JFrame frame;
    private JPanel StudentListForm;
    private JButton delBtn;
    private JButton returnBtn;
    private JTable stdDetailTB;
    private JTextField stdIDTxt;
    private JButton upBtn;
    private JButton printBtn;

    public StudentList(){ //Constructors
        createTable();
        populateTable();
        frame = new JFrame("Student List");
        frame.add(StudentListForm);
        frame.setPreferredSize(new Dimension(440, 250));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        delBtn.addActionListener(this);
        upBtn.addActionListener(this);
        returnBtn.addActionListener(this);
        printBtn.addActionListener(this);
    }

    public static void main(String[] args) { //Main Method
        SwingUtilities.invokeLater(StudentList::new);
    }

    public void actionPerformed(ActionEvent ae) { //Method for every button assign
        if (ae.getSource() == delBtn) {
            deleteStudentData();
        } else if (ae.getSource() == upBtn) {
            updateStudentData();
        } else if (ae.getSource() == returnBtn) {
            new MenuList();
            frame.dispose();
        } else if (ae.getSource() == printBtn) {
            printStudentdata();
        }
    }

    private void deleteStudentData() { //Method for Delete Data based on Student ID from File Directory
        String studentID = stdIDTxt.getText().trim();

        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please Enter Student ID.","WARNING!",JOptionPane.WARNING_MESSAGE);
            return;
        }

        File dir = new File("StudentsDetails");
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.startsWith(studentID) && name.endsWith(".txt"));
            if (files != null && files.length > 0) {
                boolean deleted = files[0].delete();
                if (deleted) {
                    JOptionPane.showMessageDialog(frame, "Student Data Deleted Successfully.","DELETED!",JOptionPane.WARNING_MESSAGE);
                    ((DefaultTableModel) stdDetailTB.getModel()).setRowCount(0);
                    populateTable();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error Deleting Student Data.","WARNING!",JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No Student Data Found For The Given Student ID.","WARNING!",JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void updateStudentData() { //Method for Update Student Data based on Student ID that listed in table
        String studentID = stdIDTxt.getText().trim();
        DefaultTableModel model = (DefaultTableModel) stdDetailTB.getModel();
        boolean studentFound = false;
        String studentName = "";
        String studentYear = "";

        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(studentID)) {
                studentName = model.getValueAt(i, 1).toString();
                studentYear = model.getValueAt(i, 2).toString();
                studentFound = true;
                break;
            }
        }
        if (studentFound) {
            new UpdateData(studentID, studentName, studentYear);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Please Enter Student ID/No Student ID Found.","WARNING!",JOptionPane.WARNING_MESSAGE);
        }
    }

    public void printStudentdata() { //Method Print & Save Student Data and Detail Exam Grade based on Student ID
        String studentID = stdIDTxt.getText().trim();
        File file = new File("StudentsDetails/" + studentID + ".txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "Please Enter Student ID/No Student ID Found.","WARNING!",JOptionPane.WARNING_MESSAGE);
            return;
        }

        File transcriptFolder = new File("StudentTranscript");
        if (!transcriptFolder.exists()) {
            transcriptFolder.mkdir();
        }

        StringBuilder transcript = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("BAHASA MELAYU SCORES") ||
                        line.startsWith("BAHASA INGGERIS SCORES") ||
                        line.startsWith("SEJARAH SCORES") ||
                        line.startsWith("MATEMATIK SCORES")) {
                    transcript.append(line).append("\n");

                    String earlyScoreLine = reader.readLine();
                    String midScoreLine = reader.readLine();
                    String finalScoreLine = reader.readLine();

                    if (earlyScoreLine != null && !earlyScoreLine.isBlank()) {

                        String[] earlyScores = extractScores(earlyScoreLine);
                        if (earlyScores.length >= 3) {
                            transcript.append("Early Exam Grade : ").append(calculateGrade(earlyScores[2])).append("\n");
                        } else {
                            transcript.append("Empty score for ").append(earlyScoreLine).append("\n");
                        }
                    } else {
                        transcript.append("Early Exam Scores not found\n");
                    }

                    if (midScoreLine != null && !midScoreLine.isBlank()) {
                        String[] midScores = extractScores(midScoreLine);
                        if (midScores.length >= 3) {
                            transcript.append("Mid Exam Grade   : ").append(calculateGrade(midScores[2])).append("\n");
                        } else {
                            transcript.append("Empty score for ").append(midScoreLine).append("\n");
                        }
                    } else {
                        transcript.append("Mid Exam Scores not found\n");
                    }

                    if (finalScoreLine != null && !finalScoreLine.isBlank()) {
                        String[] scores = extractScores(finalScoreLine);
                        if (scores.length >= 3) {
                            transcript.append("Final Exam Grade : ").append(calculateGrade(scores[2])).append("\n\n");
                        } else {
                            transcript.append("Empty score for ").append(finalScoreLine).append("\n\n");
                        }
                    } else {
                        transcript.append("Final Exam Scores not found\n");
                    }
                } else if (line.startsWith("Student ID : ")) {
                    transcript.append(line).append("\n");
                } else if (line.startsWith("Student Name : ")) {
                    transcript.append(line).append("\n");
                } else if (line.startsWith("Student Year : ")) {
                    transcript.append(line).append("\n\n");
                }
            }
            JOptionPane.showMessageDialog(frame, transcript.toString(), "Transcript for Student ID " + studentID, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error Reading Student Data : " + e.getMessage(),"WARNING!", JOptionPane.WARNING_MESSAGE);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(transcriptFolder, studentID + "_Transcript.txt")))) {
            writer.write(transcript.toString());
            JOptionPane.showMessageDialog(frame, "Transcript Saved To StudentTranscript/" + studentID + "_Transcript.txt", "CONGRATULATIONS!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error Saving Student Data : " + e.getMessage(),"WARNING!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String[] extractScores(String scoreLine) { //Method for Extract the score/result
        String[] scores = scoreLine.split("\\s+");
        return Arrays.copyOfRange(scores, 1, scores.length);
    }

    private char calculateGrade(String scoreString) { //Method for Calculate Score that Convert from Strings to Integer and Return Grade
        if (!scoreString.isEmpty()) {
            try {
                int score = Integer.parseInt(scoreString);
                if (score >= 80 && score <= 100) {
                    return 'A';
                } else if (score >= 70 && score <= 79) {
                    return 'B';
                } else if (score >= 60 && score <= 69) {
                    return 'C';
                } else if (score >= 50 && score <= 59) {
                    return 'D';
                } else if (score >= 40 && score <= 49) {
                    return 'E';
                } else {
                    return 'F';
                }

            } catch (NumberFormatException e) {
                System.err.println("Empty Score ! " + scoreString);
                return ' ';
            }
        }
        return ' ';
    }

    public void createTable() { //Method for Create Table
        DefaultTableModel model = new DefaultTableModel(
                null,
                new String[]{"Student ID", "Student Name", "Student Year"}
        );
        stdDetailTB.setModel(model);
        stdDetailTB.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        stdDetailTB.setRowSorter(sorter);
    }

    public void populateTable() { //Method to Populate Student Data by Reads the Data from File Directory
        DefaultTableModel model = (DefaultTableModel) stdDetailTB.getModel();
        File dir = new File("StudentsDetails");
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        String studentID = null;
                        String studentName = null;
                        String studentYear = null;

                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Student ID : ")) {
                                studentID = line.substring(13).trim();
                            } else if (line.startsWith("Student Name : ")) {
                                studentName = line.substring(15).trim();
                            } else if (line.startsWith("Student Year : ")) {
                                studentYear = line.substring(15).trim();
                            }
                        }
                        if (studentID != null && studentName != null && studentYear != null) {
                            model.addRow(new Object[]{studentID, studentName, studentYear});
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
