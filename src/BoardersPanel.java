import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardersPanel extends JPanel {
    private DormManager dormManager;

    public BoardersPanel(DormManager manager) {
        this.dormManager = manager;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Boarder List
        JTextArea boarderList = new JTextArea();
        boarderList.setEditable(false);
        add(new JScrollPane(boarderList), BorderLayout.CENTER);

        // Controls
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField nameField = new JTextField(15);
        controlPanel.add(nameField, gbc);

        // ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField idField = new JTextField(15);
        controlPanel.add(idField, gbc);

        // Contact
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Contact:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JTextField contactField = new JTextField(15);
        controlPanel.add(contactField, gbc);

        // Room Num
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Room Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JTextField roomNumberField = new JTextField(15);
        controlPanel.add(roomNumberField, gbc);

        // Buttons
        JButton addButton = new JButton("Add Boarder");
        JButton updateButton = new JButton("Update Boarder");
        JButton deleteButton = new JButton("Delete Boarder");
        JButton viewAllButton = new JButton("View All Boarders");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        controlPanel.add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        controlPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        controlPanel.add(viewAllButton, gbc);

        add(controlPanel, BorderLayout.WEST);

        // Button actions

    }
}
