import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardersPanel extends JPanel {
    private DormManager dormManager;
    private JTextArea boarderList;
    private JTextField nameField, idField, contactField;
    private JComboBox<String> roomNumberComboBox;

    public BoardersPanel(DormManager manager) {
        this.dormManager = manager;
        initComponents();
    }

    private String[] generateRoomNumbers() {
        String[] roomNumbers = new String[20];
        int index = 0;
        for (int i = 101; i <= 110; i++) {
            roomNumbers[index++] = String.valueOf(i);
        }
        for (int i = 200; i <= 209; i++) {
            roomNumbers[index++] = String.valueOf(i);
        }
        return roomNumbers;
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Boarder List
        boarderList = new JTextArea();
        boarderList.setEditable(false);
        boarderList.setFont(new Font("Arial", Font.BOLD, 14));
        add(new JScrollPane(boarderList), BorderLayout.CENTER);

        // Controls
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nameField = new JTextField(15);
        controlPanel.add(nameField, gbc);

        // ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        idField = new JTextField(15);
        controlPanel.add(idField, gbc);

        // Contact
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Contact:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        contactField = new JTextField(15);
        controlPanel.add(contactField, gbc);

        // Room Number
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Room Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        String[] roomNumbers = generateRoomNumbers(); // Generate room numbers
        roomNumberComboBox = new JComboBox<>(roomNumbers); // Create a combo box for room number selection
        controlPanel.add(roomNumberComboBox, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add Boarder");
        JButton updateButton = new JButton("Update Boarder");
        JButton deleteButton = new JButton("Delete Boarder");
        JButton viewAllButton = new JButton("View All Boarders");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(buttonPanel, gbc);

        add(controlPanel, BorderLayout.WEST);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBoarder();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBoarder();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBoarder();
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllBoarders();
            }
        });

        updateBoarderList(); // Initial update of the boarder list display
    }

    private void addBoarder() {
        try {
            String name = nameField.getText();
            String id = idField.getText();
            String contact = contactField.getText();
            String roomNumberText = (String) roomNumberComboBox.getSelectedItem(); // Get selected room number

            if (name.isEmpty() || id.isEmpty() || contact.isEmpty() || roomNumberText == null) {
                showMessage("All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int roomNumber = Integer.parseInt(roomNumberText);

            dormManager.addBoarder(name, id, contact, roomNumber);
            updateBoarderList();
            clearInputFields();
            showMessage("Boarder added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            showMessage("Invalid room number! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBoarder() {
        String id = idField.getText();
        if (id.isEmpty()) {
            showMessage("ID field must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText();
        String contact = contactField.getText();
        String roomNumberText = (String) roomNumberComboBox.getSelectedItem();

        try {
            Integer roomNumber = roomNumberText.isEmpty() ? null : Integer.parseInt(roomNumberText);

            dormManager.updateBoarder(id, name, contact, roomNumber);
            updateBoarderList();
            clearInputFields();
            showMessage("Boarder updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            showMessage("Invalid room number! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBoarder() {
        String id = idField.getText();
        if (id.isEmpty()) {
            showMessage("ID field must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this boarder?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dormManager.deleteBoarder(id);
            updateBoarderList();
            clearInputFields();
            showMessage("Boarder deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateBoarderList() {
        boarderList.setText("");
        for (Boarder boarder : dormManager.viewBoarders()) {
            boarderList.append("ID: " + boarder.getId() + ", Name: " + boarder.getName() +
                    ", Contact: " + boarder.getContactInfo() +
                    ", Assigned Room: " + (boarder.getAssignedRoom() != null ? boarder.getAssignedRoom().getRoomNumber() : "None") + "\n");
        }
    }

    private void viewAllBoarders() {
        StringBuilder boardersInfo = new StringBuilder();
        for (Boarder boarder : dormManager.viewBoarders()) {
            boardersInfo.append("ID: ").append(boarder.getId())
                    .append(", Name: ").append(boarder.getName())
                    .append(", Contact: ").append(boarder.getContactInfo())
                    .append(", Assigned Room: ").append(boarder.getAssignedRoom() != null ? boarder.getAssignedRoom().getRoomNumber() : "None")
                    .append("\n");
        }

        JOptionPane.showMessageDialog(this, boardersInfo.toString(), "All Boarders", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearInputFields() {
        nameField.setText("");
        idField.setText("");
        contactField.setText("");
        roomNumberComboBox.setSelectedIndex(0); // Reset combo box selection
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
