import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private DormManager dormManager;

    public MainMenu() {
        dormManager = new DormManager();
        initComponents();
    }

    private void initComponents() {
        setTitle("Dorm Manager");
        setSize(600, 600);
        setLocationRelativeTo(null); // pang center
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Pang Max
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Rooms", new RoomsPanel(dormManager));
        tabbedPane.addTab("Boarders", new BoardersPanel(dormManager));
        tabbedPane.addTab("Reports", new ReportsPanel(dormManager));

        add(tabbedPane, BorderLayout.CENTER);
    }


}
