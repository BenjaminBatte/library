package librarysystem.panels;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

public class CheckoutBookPanel implements MessageableWindow {
    ControllerInterface sc = SystemController.INSTANCE;

    private final JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JTextField isbnField;
    private JTextField memberIdField;

    private JPanel innerPanel;

    private JButton checkoutButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CheckoutBookPanel() {
        mainPanel = new JPanel();
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void defineTopPanel() {
        topPanel = new JPanel();
        JLabel label = new JLabel("Checkout Book");
        Util.adjustLabelFont(label, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(label);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        middlePanel.setLayout(bl);

        innerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        innerPanel.setLayout(fl);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel memberIdLabel = new JLabel("Member ID");
        JLabel isbnLabel = new JLabel("ISBN");

        memberIdField = new JTextField(10);
        isbnField = new JTextField(10);

        leftPanel.add(memberIdLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(isbnLabel);

        rightPanel.add(memberIdField);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(isbnField);

        innerPanel.add(leftPanel);
        innerPanel.add(rightPanel);
        middlePanel.add(innerPanel, BorderLayout.NORTH);
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
        lowerPanel.setLayout(fl);
        checkoutButton = new JButton("Checkout");
        addCheckoutButtonListener(checkoutButton);
        lowerPanel.add(checkoutButton);
    }

    private void addCheckoutButtonListener(JButton checkoutBtn) {
        checkoutBtn.addActionListener(evt -> {
            String bkISBN = isbnField.getText().trim();
            String memberID = memberIdField.getText().trim();

            if (bkISBN.length() == 0 || memberID.length() == 0) {
                displayError("ISBN and Member ID may not be empty");
            } else {
                try {
                    sc.checkoutBook(memberID, bkISBN);
                    displayInfo("Checkout successful");
                } catch (LibrarySystemException e) {
                    displayError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void updateData() {

    }
}
