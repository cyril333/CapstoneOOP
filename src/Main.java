import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        GradeManager gradeManager = new GradeManager();
        loadDataFromFile(gradeManager);
        SwingUtilities.invokeLater(() -> createAndShowGUI(gradeManager));
    }

    private static void createAndShowGUI(GradeManager gradeManager) {

        JFrame frame = new JFrame("CIT-U Grade Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout(10, 10));

        // taas

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(230, 220, 255));
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField nameField = new JTextField(15);
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isLetter(c) && c != ' ' && c != ',' && c != '.') {
                    e.consume();
                }
            }
        });

        JTextField idField = new JTextField(10);
        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '-') {
                    e.consume();
                }

                if (idField.getText().length() >= 11) {
                    e.consume();
                }
            }
        });


        JTextField gradeField = new JTextField(10);
        gradeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gradeField.getText().length() >= 3) {
                    e.consume();
                }
            }
        });

        JTextField subjectField = new JTextField(10);
        subjectField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (subjectField.getText().length() >= 10) {
                    e.consume();
                }
            }
        });

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; inputPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1; inputPanel.add(nameField, gbc);
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.WEST;
        JLabel exampleLabel = new JLabel("ex. Antolijao, Ave Cyril G.");
        exampleLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        inputPanel.add(exampleLabel, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; inputPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; inputPanel.add(idField, gbc);
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.WEST;
        JLabel exampleLabel2 = new JLabel("ex. 21-4147-391");
        exampleLabel2.setFont(new Font("Arial", Font.ITALIC, 12));
        inputPanel.add(exampleLabel2, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; inputPanel.add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1; inputPanel.add(subjectField, gbc);
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.WEST;
        JLabel exampleLabel3 = new JLabel("ex. CSIT227");
        exampleLabel3.setFont(new Font("Arial", Font.ITALIC, 12));
        inputPanel.add(exampleLabel3, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; inputPanel.add(new JLabel("Grade:"), gbc);
        gbc.gridx = 1; inputPanel.add(gradeField, gbc);
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.WEST;
        JLabel exampleLabel4 = new JLabel("ex. 5.0");
        exampleLabel4.setFont(new Font("Arial", Font.ITALIC, 12));
        inputPanel.add(exampleLabel4, gbc);

        frame.add(inputPanel, BorderLayout.NORTH);

        // center

        JTextArea displayArea = new JTextArea();

        displayArea.setEditable(false);
        displayArea.setForeground(new Color(30, 30, 30));
        displayArea.setBackground(new Color(230, 220, 255));
        displayArea.setFont(new Font("Monospaced", Font.BOLD, 18));

        JScrollPane scrollPane = new JScrollPane(displayArea);

        scrollPane.setBorder(BorderFactory.createTitledBorder("Records"));

        frame.add(scrollPane, BorderLayout.CENTER);

        // ubos

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addStudentButton = new JButton("Add Student");
        JButton addGradeButton = new JButton("Add Grade");
        JButton removeButton = new JButton("Remove Student");

        addStudentButton.setBackground(new Color(0,0,0));
        addStudentButton.setForeground(Color.WHITE);

        addGradeButton.setBackground(new Color(0,0,0));
        addGradeButton.setForeground(Color.WHITE);

        removeButton.setBackground(new Color(0,0,0));
        removeButton.setForeground(Color.WHITE);

        buttonPanel.add(addStudentButton);
        buttonPanel.add(addGradeButton);
        buttonPanel.add(removeButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        addStudentButton.addActionListener(_ -> {
            String studentId = idField.getText().trim();
            String name = nameField.getText().trim();
            String id = idField.getText().trim();

            if (name.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Student Name and ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gradeManager.getStudentById(id) != null) {
                JOptionPane.showMessageDialog(frame, "Student ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (studentId.length() < 11) {
                JOptionPane.showMessageDialog(frame, "Incomplete Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            gradeManager.addStudent(new Student(name, id));
            nameField.setText("");
            idField.setText("");
            JOptionPane.showMessageDialog(frame, "Student Added.");
            updateDisplay(gradeManager, displayArea);
        });

        addGradeButton.addActionListener(_ -> {
            String studentId = idField.getText().trim();
            String subject = subjectField.getText().trim().toUpperCase();
            String gradeInput = gradeField.getText().trim();

            if (subject.isEmpty() || gradeInput.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Missing Subject / Grade.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Student student = gradeManager.getStudentById(studentId);
                if (student == null) {
                    throw new IllegalArgumentException("Student ID " + studentId + " not found.");
                }

                boolean isSubjectAdded = false;
                for (Grade g : student.getGrades()) {
                    if (g.getSubject().equalsIgnoreCase(subject)) {
                        isSubjectAdded = true;
                        break;
                    }
                }

                if (isSubjectAdded) {
                    JOptionPane.showMessageDialog(frame, "Subject already added.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                student.addGrade(subject, gradeInput);
                subjectField.setText("");
                gradeField.setText("");
                JOptionPane.showMessageDialog(frame, "Grade Added.");
                updateDisplay(gradeManager, displayArea);

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeButton.addActionListener(_ -> {
            String studentId = idField.getText().trim();
            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Student ID to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = gradeManager.removeStudent(studentId);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Student Removed.");
            } else {
                JOptionPane.showMessageDialog(frame, "Student ID " + studentId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            updateDisplay(gradeManager, displayArea);
        });

        frame.setVisible(true);
    }


    private static void updateDisplay(GradeManager gradeManager, JTextArea displayArea) {
        displayArea.setText(gradeManager.displayAllStudents());
    }

    private static void loadDataFromFile(GradeManager gradeManager) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            Object object = ois.readObject();

            if (object instanceof ArrayList<?> list) {
                if (!list.isEmpty() && list.getFirst() instanceof Student) {
                    ArrayList<Student> students = (ArrayList<Student>) list;
                    gradeManager.setStudents(students);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No data file found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}