import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomsPanel extends JPanel {
    private DormManager dormManager;

    public RoomsPanel(DormManager manager) {
        this.dormManager = manager;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Room List
        JTextArea roomList = new JTextArea();
        roomList.setEditable(false);
        add(new JScrollPane(roomList), BorderLayout.CENTER);

        // Controls
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Room Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Room Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField roomNumberField = new JTextField(10);
        controlPanel.add(roomNumberField, gbc); // END OF GRID

        // Type
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Type:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField typeField = new JTextField(10); // Set preferred size
        controlPanel.add(typeField, gbc); // END OF GRID

        // Rent
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Rent:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JTextField rentField = new JTextField(10); // Set preferred size
        controlPanel.add(rentField, gbc);

        // Availability
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Availability:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JCheckBox availabilityCheck = new JCheckBox("Available");
        controlPanel.add(availabilityCheck, gbc); // END OF GRID

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton addButton = new JButton("Add Room");
        controlPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton updateButton = new JButton("Update Room");
        controlPanel.add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton deleteButton = new JButton("Delete Room");
        controlPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        JButton viewAllRoomsButton = new JButton("View All Rooms");
        controlPanel.add(viewAllRoomsButton, gbc);

        add(controlPanel, BorderLayout.WEST);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    String type = typeField.getText();
                    double rent = Double.parseDouble(rentField.getText());
                    boolean isAvailable = availabilityCheck.isSelected();
                    dormManager.addRoom(roomNumber, type, rent, isAvailable);
                    updateRoomList(roomList);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid input! Please enter valid numbers.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    String type = typeField.getText();
                    double rent = Double.parseDouble(rentField.getText());
                    boolean isAvailable = availabilityCheck.isSelected();
                    dormManager.updateRoom(roomNumber, type, rent, isAvailable);
                    updateRoomList(roomList);
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,
                            "Invalid input! Please enter valid numbers.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                dormManager.deleteRoom(roomNumber);
                updateRoomList(roomList);
            }
        });

        viewAllRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // DIKO NA ALAM...
            }
        });
    }

    private void updateRoomList(JTextArea roomList) {
        StringBuilder builder = new StringBuilder();
        for (Room room : dormManager.viewRooms()) {
            builder.append("Room ").append(room.getRoomNumber()).append(": ")
                    .append(room.getType()).append(", ₱").append(room.getRent())
                    .append(", ").append(room.isAvailable() ? "Available" : "Occupied")
                    .append("\n");
        }
        roomList.setText(builder.toString());
    }
}
