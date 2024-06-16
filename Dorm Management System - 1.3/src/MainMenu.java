import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private DormManager dormManager;

    public MainMenu() {
        dormManager = new DormManager();
        initComponents();
    }

    private void initComponents() {
        setTitle("EverGreen Dorm Management System"); // Set the title of the window
        setSize(1000, 768); // Set the size of the window
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
        setResizable(false); // Do not allow the window to be resized

        // Set Nimbus Look and Feel
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

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create instances of panels
        RoomsPanel roomsPanel = new RoomsPanel(dormManager);
        BoardersPanel boardersPanel = new BoardersPanel(dormManager);
        ReportsPanel reportsPanel = new ReportsPanel(dormManager);

        // Add panels to the tabbed pane
        tabbedPane.addTab("Rooms", roomsPanel);
        tabbedPane.addTab("Boarders", boardersPanel);
        tabbedPane.addTab("Reports", reportsPanel);

        // Add the tabbed pane to the content pane of the frame
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread: creating and showing this application's GUI
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}
