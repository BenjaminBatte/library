package librarysystem.panels;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import dataaccess.Auth;
import librarysystem.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class LoginPanel implements MessageableWindow {
    LibrarySystem librarySystem;

    private final ControllerInterface sc = SystemController.INSTANCE;

    public void setLibrarySystem(LibrarySystem librarysystem) {
        librarySystem = librarysystem;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JPanel upperHalf;
    private JPanel middleHalf;
    private JPanel lowerHalf;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JPanel leftTextPanel;
    private JPanel rightTextPanel;

    private JLabel label;
    private JTextField userId;
    private JTextField password;
    private JButton loginButton;
    private JButton logoutButton;

    public LoginPanel() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
    }

    private void defineUpperHalf() {
        upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        upperHalf.add(topPanel, BorderLayout.NORTH);
        upperHalf.add(middlePanel, BorderLayout.CENTER);
        upperHalf.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        middleHalf.add(Box.createRigidArea(new Dimension(0,250)));
        middleHalf.add(s, BorderLayout.NORTH);
        defineLowerHalf();
        middleHalf.add(lowerHalf, BorderLayout.CENTER);
    }

    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new BorderLayout());

        JLabel logoutLabel = new JLabel("Logout");
        Util.adjustLabelFont(logoutLabel, Color.BLUE.darker(), true);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.add(logoutLabel);

        logoutButton = new JButton("Logout");
        addLogoutButtonListener(logoutButton);

        JPanel logoutButtonPanel = new JPanel();
        logoutButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoutButtonPanel.add(logoutButton);


        lowerHalf.add(logoutPanel, BorderLayout.NORTH);
        lowerHalf.add(logoutButtonPanel, BorderLayout.CENTER);
    }

    private void defineTopPanel() {
        topPanel = new JPanel();

        JLabel loginLabel = new JLabel("Login");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);

        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(loginLabel);
    }


    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        loginButton = new JButton("Login");
        addLoginButtonListener(loginButton);
        lowerPanel.add(loginButton);
    }

    private void defineLeftTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        userId = new JTextField(10);
        label = new JLabel("Username");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(userId);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText, BorderLayout.NORTH);
        leftTextPanel.add(bottomText, BorderLayout.CENTER);
    }

    private void defineRightTextPanel() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        password = new JPasswordField(10);
        label = new JLabel("Password");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(password);
        bottomText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText, BorderLayout.NORTH);
        rightTextPanel.add(bottomText, BorderLayout.CENTER);
    }

    private void addLoginButtonListener(JButton btn) {
        btn.addActionListener(evt -> {
            String uID = userId.getText().trim();
            String pwd = password.getText().trim();
            if (uID.length() == 0 || pwd.length() == 0) {
                displayError("Id and Password fields must be nonempty");
            } else {
                try {
                    sc.login(uID, pwd);
                    updateLeftPanel(SystemController.currentAuth);
                    displayInfo("Login successful");
                    librarySystem.repaint();
                } catch (LibrarySystemException e) {
                    displayError("Error! " + e.getMessage());
                }
            }
        });
    }

    private void addLogoutButtonListener(JButton logoutButton) {
        logoutButton.addActionListener(event -> {
            clearFields();
            updateLeftPanel(null);
            librarySystem.repaint();
            displayInfo("Logout successful");
        });
    }

    private void clearFields() {
        userId.setText("");
        password.setText("");
    }

    private void updateLeftPanel(Auth auth) {
        if (auth == Auth.BOTH) bothItems();
        else if (auth == Auth.ADMIN) adminItems();
        else if (auth == Auth.LIBRARIAN) librarianItems();
        else logoutItems();
    }

    private void adminItems() {
        ListItem[] adminItems = librarySystem.getAdminItems();
        updateList(adminItems);
    }

    private void librarianItems() {
        ListItem[] librarianItems = librarySystem.getLibrarianItems();
        updateList(librarianItems);
    }

    private void logoutItems() {
        updateList(librarySystem.getLoginItems());
    }

    private void bothItems() {
        updateList(librarySystem.getAllItems());
    }

    @SuppressWarnings("unchecked")
    private void updateList(ListItem[] items) {
        JList<ListItem> linkList = librarySystem.getLinkList();
        DefaultListModel<ListItem> model = (DefaultListModel) linkList.getModel();
        int size = model.getSize();
        if (items != null) {
            java.util.List<Integer> indices = new ArrayList<>();
            for (ListItem item : items) {
                int index = model.indexOf(item);
                indices.add(index);
                ListItem next = model.get(index);
                next.setHighlight(true);
            }
            for (int i = 0; i < size; ++i) {
                if (!indices.contains(i)) {
                    ListItem next = model.get(i);
                    next.setHighlight(false);
                }
            }
        } else {
            for (int i = 0; i < size; ++i) {
                ListItem next = model.get(i);
                next.setHighlight(true);
            }
        }
    }

    @Override
    public void updateData() {}

    private static final long serialVersionUID = 3618976789175941432L;
}
