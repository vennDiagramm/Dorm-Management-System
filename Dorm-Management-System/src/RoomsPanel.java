import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomsPanel extends JPanel {
    private DormManager dormManager; // Reference to the DormManager
    private JTextArea roomList; // Text area to display the list of rooms
    private JTextField roomNumberField; // Text field for room number input
    private JComboBox<String> typeComboBox; // Combo box for room type selection
    private JTextField rentField; // Text field for room rent input
    private JCheckBox availabilityCheck; // Check box for room availability status

    public RoomsPanel(DormManager manager) {
        this.dormManager = manager; // Initialize DormManager
        initComponents(); // Initialize UI components
    }

    private void initComponents() {
        setLayout(new BorderLayout()); // Set the layout manager to BorderLayout

        // Room List
        roomList = new JTextArea(); // Create a text area to display the list of rooms
        roomList.setEditable(false); // Make the text area read-only
        roomList.setFont(new Font("Arial", Font.BOLD, 14)); // Set the font to Arial
        add(new JScrollPane(roomList), BorderLayout.CENTER); // Add the text area to the center of the panel with a scroll pane

        // Controls
        JPanel controlPanel = new JPanel(new GridBagLayout()); // Create a panel with GridBagLayout for the controls
        GridBagConstraints gbc = new GridBagConstraints(); // Create GridBagConstraints for component placement
        gbc.insets = new Insets(5, 5, 5, 5); // Set insets for spacing
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure components fill the cell

        // Room Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Room Number:"), gbc); // Add label for room number

        gbc.gridx = 1;
        gbc.gridy = 0;
        roomNumberField = new JTextField(10); // Create a text field for room number input
        controlPanel.add(roomNumberField, gbc); // Add text field for room number

        // Type
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Type:"), gbc); // Add label for room type

        gbc.gridx = 1;
        gbc.gridy = 1;
        typeComboBox = new JComboBox<>(new String[]{"Air-Conditioned", "Non AC"}); // Create a combo box for room type selection
        controlPanel.add(typeComboBox, gbc); // Add combo box for room type

        // Rent
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Rent:"), gbc); // Add label for room rent

        gbc.gridx = 1;
        gbc.gridy = 2;
        rentField = new JTextField(10); // Create a text field for room rent input
        controlPanel.add(rentField, gbc); // Add text field for room rent

        // Availability
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Availability:"), gbc); // Add label for room availability

        gbc.gridx = 1;
        gbc.gridy = 3;
        availabilityCheck = new JCheckBox("Available"); // Create a check box for room availability
        controlPanel.add(availabilityCheck, gbc); // Add check box for room availability

        // Buttons
        JPanel buttonPanel = new JPanel(); // Create a panel for buttons
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout for button panel

        JButton addButton = new JButton("Add Room"); // Create button to add room
        JButton updateButton = new JButton("Update Room"); // Create button to update room
        JButton deleteButton = new JButton("Delete Room"); // Create button to delete room
        JButton viewAllRoomsButton = new JButton("View All Rooms"); // Create button to view all rooms

        buttonPanel.add(addButton); // Add add button to button panel
        buttonPanel.add(updateButton); // Add update button to button panel
        buttonPanel.add(deleteButton); // Add delete button to button panel
        buttonPanel.add(viewAllRoomsButton); // Add view all rooms button to button panel

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(buttonPanel, gbc); // Add button panel to control panel

        add(controlPanel, BorderLayout.WEST); // Add control panel to the left side of the main panel
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRoom();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRoom();
            }
        });

        viewAllRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllRooms(); // Show all rooms in a JOptionPane
            }
        });

        updateRoomList(); // Initial update of the room list display
    }
    private void addRoom() {
        try {
            Room room = getRoomFromInput();
            if (room != null) {
                dormManager.addRoom(room.getRoomNumber(), room.getType(), room.getRent(), room.isAvailable());
                updateRoomList();
                clearInputFields();
                showMessage("Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            showMessage("Invalid input! Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateRoom() {
        try {
            Room room = getRoomFromInput();
            if (room != null) {
                dormManager.updateRoom(room.getRoomNumber(), room.getType(), room.getRent(), room.isAvailable());
                updateRoomList();
                clearInputFields();
                showMessage("Room updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            showMessage("Invalid input! Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteRoom() {
        try {
            int roomNumber = Integer.parseInt(roomNumberField.getText());
            dormManager.deleteRoom(roomNumber);
            updateRoomList();
            clearInputFields();
            showMessage("Room deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            showMessage("Invalid input! Please enter a valid room number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            showMessage("Room number does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Room getRoomFromInput() {
        String roomNumberText = roomNumberField.getText().trim();
        String rentText = rentField.getText().trim();
        if (roomNumberText.isEmpty() || rentText.isEmpty()) {
            showMessage("All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int roomNumber = Integer.parseInt(roomNumberText);
        String type = (String) typeComboBox.getSelectedItem();
        double rent = Double.parseDouble(rentText);
        boolean isAvailable = availabilityCheck.isSelected();

        if (rent <= 0) {
            showMessage("Rent must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return new Room(roomNumber, type, rent, isAvailable);
    }

    private void updateRoomList() {
        // Update the room list displayed in the JTextArea
        StringBuilder builder = new StringBuilder();
        for (Room room : dormManager.viewRooms()) {
            builder.append("Room ").append(room.getRoomNumber()).append(": ")
                    .append(room.getType()).append(", ₱").append(room.getRent())
                    .append(", ").append(room.isAvailable() ? "Available" : "Occupied")
                    .append("\n");
        }
        roomList.setText(builder.toString());
    }

    private void showAllRooms() {
        // Display all rooms in a JOptionPane
        updateRoomList();
        JOptionPane.showMessageDialog(this, roomList.getText(), "All Rooms", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessage(String message, String title, int messageType) {
        // Show a message dialog with the specified message, title, and message type
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void clearInputFields() {
        roomNumberField.setText("");
        rentField.setText("");
        typeComboBox.setSelectedIndex(0);
        availabilityCheck.setSelected(false);
    }
}