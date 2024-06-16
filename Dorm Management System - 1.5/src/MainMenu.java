import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {
    private static final String DATA_FILE = "dorm_data.txt";
    private DormManager dormManager;

    public MainMenu() {
        dormManager = new DormManager();
        dormManager.loadData(DATA_FILE);
        initComponents();
    }

    private void initComponents() {
        setTitle("EverGreen Dorm Management System");
        setSize(1000, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTabbedPane tabbedPane = new JTabbedPane();
        RoomsPanel roomsPanel = new RoomsPanel(dormManager);
        BoardersPanel boardersPanel = new BoardersPanel(dormManager);
        ReportsPanel reportsPanel = new ReportsPanel(dormManager);

        tabbedPane.addTab("Rooms", roomsPanel);
        tabbedPane.addTab("Boarders", boardersPanel);
        tabbedPane.addTab("Reports", reportsPanel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dormManager.saveData(DATA_FILE);
                super.windowClosing(e);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}
