package librarysystem.panels;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibrarySystem;

import javax.swing.*;
import java.awt.*;

public class LandingPanel implements MessageableWindow {
    LibrarySystem librarySystem;

    private final ControllerInterface sc = SystemController.INSTANCE;

    public void setLibrarySystem(LibrarySystem librarysystem) {
        librarySystem = librarysystem;
    }

    private JPanel mainPanel;

    String pathToImage;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public LandingPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        setPathToImage();
        insertSplashImage();
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        pathToImage = currDirectory + "/src/librarysystem/library.png";
    }

    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }

    @Override
    public void updateData() {

    }
}
