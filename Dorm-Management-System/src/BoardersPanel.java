import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardersPanel extends JPanel {
    private DormManager dormManager;
    private JTextField nameField, idField, contactField, roomNumberField;
    private JTextArea boarderList;

    public BoardersPanel(DormManager manager) {
        this.dormManager = manager;
        initComponents();
        
    }
    
    
    private void initComponents() {
        setLayout(new BorderLayout());

        // Boarder List
        boarderList = new JTextArea(10, 30);
        boarderList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(boarderList);
        add(scrollPane, BorderLayout.CENTER);

        // Controls Panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Name:"), gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        controlPanel.add(nameField, gbc);

        // ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("ID:"), gbc);

        idField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        controlPanel.add(idField, gbc);

        // Contact
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Contact:"), gbc);

        contactField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        controlPanel.add(contactField, gbc);

        // Room Number
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Room Number:"), gbc);

        roomNumberField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        controlPanel.add(roomNumberField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton1 = new JButton("Add Boarder");
        JButton updateButton1 = new JButton("Update Boarder");
        JButton deleteButton1 = new JButton("Delete Boarder");
        JButton viewAllButton1 = new JButton("View All Boarders");        

        buttonPanel.add(addButton1);
        buttonPanel.add(updateButton1);
        buttonPanel.add(deleteButton1);
        buttonPanel.add(viewAllButton1);


        addButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBoarder();
            }
        });

        updateButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBoarder();
            }
        });

        deleteButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBoarder();
            }
        });

        viewAllButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBoarderList();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(buttonPanel, gbc);

        add(controlPanel, BorderLayout.WEST);
    }
    private void addBoarder() {
        try {
            String name = nameField.getText();
            String id = idField.getText();
            String contact = contactField.getText();
            int roomNumber = Integer.parseInt(roomNumberField.getText());
    
            if (name.isEmpty() || id.isEmpty() || contact.isEmpty()) {
                showMessage("All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Log input values for debugging
            System.out.println("Adding Boarder - Name: " + name + ", ID: " + id + ", Contact: " + contact + ", Room Number: " + roomNumber);
    
            dormManager.addBoarder(name, id, contact, roomNumber);
            updateBoarderList();
            clearInputFields();
            showMessage("Boarder added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            showMessage("Invalid room number! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateBoarder() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            String contact = contactField.getText();
            int roomNumber = Integer.parseInt(roomNumberField.getText());

            if (name.isEmpty() || id.isEmpty() || contact.isEmpty()) {
                showMessage("All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Log input values for debugging
            System.out.println("Updating Boarder - ID: " + id + ", Name: " + name + ", Contact: " + contact + ", Room Number: " + roomNumber);

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

        // Log input values for debugging
        System.out.println("Deleting Boarder - ID: " + id);

        dormManager.deleteBoarder(id);
        updateBoarderList();
        clearInputFields();
        showMessage("Boarder deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateBoarderList() {
        boarderList.setText("");
        for (Boarder boarder : dormManager.viewBoarders()) {
            boarderList.append("ID: " + boarder.getId() + ", Name: " + boarder.getName() +
                               ", Contact: " + boarder.getContactInfo() +
                               ", Assigned Room: " + (boarder.getAssignedRoom() != null ? boarder.getAssignedRoom().getRoomNumber() : "None") + "\n");
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        idField.setText("");
        contactField.setText("");
        roomNumberField.setText("");
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
