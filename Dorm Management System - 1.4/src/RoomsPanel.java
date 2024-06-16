import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RoomsPanel extends JPanel {
    private DormManager dormManager;
    private JTextArea roomList;
    private JComboBox<String> roomNumberComboBox;
    private JComboBox<String> typeComboBox;
    private JTextField rentField;
    private JCheckBox availabilityCheck;

    public RoomsPanel(DormManager manager) {
        this.dormManager = manager;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Room List
        roomList = new JTextArea();
        roomList.setEditable(false); // Make the text area read-only
        roomList.setFont(new Font("Arial", Font.BOLD, 14));
        add(new JScrollPane(roomList), BorderLayout.CENTER);

        // Controls
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Room Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Room Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        String[] roomNumbers = generateRoomNumbers(); // Generate room numbers
        roomNumberComboBox = new JComboBox<>(roomNumbers); // Create a combo box for room number selection
        controlPanel.add(roomNumberComboBox, gbc); // Add combo box for room number

        // Type
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Type:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        typeComboBox = new JComboBox<>(new String[]{"Air-Conditioned", "Non AC"});
        controlPanel.add(typeComboBox, gbc);

        // Rent
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Rent:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        rentField = new JTextField(10);
        controlPanel.add(rentField, gbc);

        // Availability
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Availability:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        availabilityCheck = new JCheckBox("Available");
        controlPanel.add(availabilityCheck, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add Room");
        JButton updateButton = new JButton("Update Room");
        JButton deleteButton = new JButton("Delete Room");
        JButton viewAllRoomsButton = new JButton("View All Rooms");
        JButton searchButton = new JButton("Search");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllRoomsButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        controlPanel.add(buttonPanel, gbc);

        // Search Button
        gbc.gridx = 0;
        gbc.gridy = 6;
        controlPanel.add(searchButton, gbc); // Save space

        add(controlPanel, BorderLayout.WEST);

        // Add button actions
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

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSearchDialog();
            }
        });
        updateRoomList(); // Initial update of the room list display
    }

    // Added. The Rooms.
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
            int roomNumber = Integer.parseInt((String) roomNumberComboBox.getSelectedItem());
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
        String roomNumberText = (String) roomNumberComboBox.getSelectedItem();
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
        roomNumberComboBox.setSelectedIndex(0);
        rentField.setText("");
        typeComboBox.setSelectedIndex(0);
        availabilityCheck.setSelected(false);
    }

    // ADDED VIEWING ROOMS
    // Helper method to show search dialog
    private void showSearchDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        // Room Number dropdown
        panel.add(new JLabel("Room Number:"));
        JComboBox<String> searchRoomNumberComboBox = new JComboBox<>(generateRoomNumbers());
        panel.add(searchRoomNumberComboBox);


        int result = JOptionPane.showConfirmDialog(this, panel, "Search Room",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedRoomNumber = (String) searchRoomNumberComboBox.getSelectedItem();

            ArrayList<Room> searchResults = new ArrayList<>();
            if (selectedRoomNumber != null && !selectedRoomNumber.isEmpty()) {
                try {
                    int roomNumber = Integer.parseInt(selectedRoomNumber);
                    Room room = dormManager.findRoomByNumber(roomNumber);
                    if (room != null) {
                        searchResults.add(room);
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Invalid room number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!searchResults.isEmpty()) {
                displaySearchResults("Room Search Results", searchResults);
            } else {
                showMessage("No matching room found!", "Search Room", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Helper method to display search results
    private void displaySearchResults(String title, ArrayList<Room> results) {
        StringBuilder builder = new StringBuilder();
        for (Room room : results) {
            builder.append(room.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, builder.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }
}
