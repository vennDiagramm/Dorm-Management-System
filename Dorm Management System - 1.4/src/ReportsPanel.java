import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsPanel extends JPanel {
    private DormManager dormManager;
    private JTextArea reportArea;

    public ReportsPanel(DormManager manager) {
        this.dormManager = manager;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Report area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Arial", Font.BOLD, 14));
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // Controls
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton profitButton = new JButton("Generate Profit Report");
        JButton occupancyButton = new JButton("Generate Occupancy Report");

        buttonPanel.add(profitButton);
        buttonPanel.add(occupancyButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        controlPanel.add(buttonPanel, gbc);

        add(controlPanel, BorderLayout.WEST);

        // Button actions
        profitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String profit = dormManager.generateProfitReport();
                reportArea.setText("Total Rent Collected: ₱" + profit);
            }
        });

        occupancyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String report = dormManager.generateOccupancyReport();
                reportArea.setText(report);
            }
        });
    }
}
