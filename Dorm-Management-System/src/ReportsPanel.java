import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsPanel extends JPanel {
    private DormManager dormManager;

    public ReportsPanel(DormManager manager) {
        this.dormManager = manager;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Report area
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton profitButton = new JButton("Generate Profit Report");
        JButton occupancyButton = new JButton("Generate Occupancy Report");

        buttonPanel.add(profitButton);
        buttonPanel.add(occupancyButton);

        add(buttonPanel, BorderLayout.SOUTH);


    }
}
