package librarysystem.panels;

import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

public class AddBookCopyPanel implements MessageableWindow {
    private final ControllerInterface sc = SystemController.INSTANCE;
    private final JPanel mainPanel;
    private JPanel topPanel;
    private JPanel outerMiddle;


    private JTextField isbn;

    public AddBookCopyPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineOuterMiddle();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(outerMiddle, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void clearData() {
        isbn.setText("");
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AddBookLabel = new JLabel("Add Book Copy");
        Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(AddBookLabel);
    }

    public void defineOuterMiddle() {
        outerMiddle = new JPanel();
        outerMiddle.setLayout(new BorderLayout());

        //set up left and right panels
        JPanel middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel bookIsbn = new JLabel("Enter ISBN");

        isbn = new JTextField(10);

        leftPanel.add(bookIsbn);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));


        rightPanel.add(isbn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        middlePanel.add(leftPanel);
        middlePanel.add(rightPanel);
        outerMiddle.add(middlePanel, BorderLayout.NORTH);

        //add button at bottom
        JButton addBookButton = new JButton("Add Book Copy");
        attachAddBookButtonListener(addBookButton);
        JPanel addBookButtonPanel = new JPanel();
        addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addBookButtonPanel.add(addBookButton);
        outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);

    }

    private void attachAddBookButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            String bookId = isbn.getText();

            if (bookId.length() == 0) {
                displayError("Error!: ISBN should not be empty");
            } else {
                Book book = sc.getBookById(bookId);

                if (book != null) {
                    book.addCopy();
                    try {
                        sc.updateBook(book);
                        displayInfo("The book copy has been created");
                    } catch (LibrarySystemException e) {
                        e.printStackTrace();
                        displayError("Book does not exist");
                    }

                } else {
                    displayError("Book does not exist");
                }
            }

        });
    }

    public void updateData() {}
}
