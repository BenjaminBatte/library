package librarysystem;

import librarysystem.panels.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class LibrarySystem extends JFrame implements MessageableWindow {

    JPanel cards;
    JList<ListItem> linkList;
    public static JTextArea statusBar = new JTextArea("Welcome to the Library System!");
    public final static LibrarySystem INSTANCE = new LibrarySystem();

    LoginPanel lp;
    AllBookIDs abip;
    LandingPanel landingPanel;

    //list items
    ListItem splashScreenItem = new ListItem("Splash", true);
    ListItem loginListItem = new ListItem("Login/Logout", true);
    ListItem addBookItem = new ListItem("Add Book", false);
    ListItem addMember = new ListItem("Add Member", false);
    ListItem viewTitlesItem = new ListItem("All Book IDs", false);
    ListItem checkoutBook = new ListItem("Checkout Book", false);
    ListItem checkMemberRecord = new ListItem("View Checkout Record", false);

    ListItem addBookCopyItem = new ListItem("Add Book Copy", false);


    ListItem[] loginItems = { loginListItem };
    ListItem[] librarianItems = {
            checkoutBook,
            loginListItem,
            viewTitlesItem,
            checkMemberRecord,
    };
    ListItem[] adminItems = {
            addMember,
            addBookItem,
            loginListItem,
            viewTitlesItem,
            addBookCopyItem,
    };
    ListItem[] allItems = {
            addMember,
            addBookItem,
            checkoutBook,
            loginListItem,
            viewTitlesItem,
            addBookCopyItem,
            checkMemberRecord,
    };

    public ListItem[] getAdminItems() {
        return adminItems;
    }

    public ListItem[] getAllItems() {
        return allItems;
    }

    public ListItem[] getLibrarianItems() {
        return librarianItems;
    }

    public ListItem[] getLoginItems() {
        return loginItems;
    }

    public JList<ListItem> getLinkList() {
        return linkList;
    }

    private LibrarySystem() {
    }

    public void init() {
        Util.adjustLabelFont(statusBar, Util.DARK_BLUE, true);
        setSize(650, 550);

        createLinkLabels();
        createMainPanels();
        CardLayout cl = (CardLayout) (cards.getLayout());
        linkList.addListSelectionListener(event -> {
            String value = linkList.getSelectedValue().getItemName();
            boolean allowed = linkList.getSelectedValue().highlight();
            System.out.println(value + " " + allowed);

            // cl = (CardLayout)(cards.getLayout());
            statusBar.setText("");
            if (!allowed) {
                value = splashScreenItem.getItemName();
                linkList.setSelectedIndex(0);
            }
            if (value.equals("All Book IDs")) abip.updateData();
            cl.show(cards, value);
        });

        // set up split panes
        JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
        innerPane.setDividerLocation(180);
        JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, statusBar);
        outerPane.setDividerLocation(450);
        add(outerPane, BorderLayout.CENTER);
        lp.setLibrarySystem(this);
    }

    public JTextArea getStatusBar() {
        return statusBar;
    }

    public void createLinkLabels() {
        DefaultListModel<ListItem> model = new DefaultListModel<>();
        model.addElement(loginListItem);
        model.addElement(viewTitlesItem);
        model.addElement(addBookItem);
        model.addElement(addMember);
        model.addElement(checkoutBook);
        model.addElement(addBookCopyItem);
        model.addElement(checkMemberRecord);

        linkList = new JList<>(model);
        linkList.setCellRenderer(new DefaultListCellRenderer() {

            @SuppressWarnings("rawtypes")
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
                if (value instanceof ListItem) {
                    ListItem nextItem = (ListItem) value;
                    setText(nextItem.getItemName());
                    if (nextItem.highlight()) {
                        setForeground(Util.LINK_AVAILABLE);
                    } else {
                        setForeground(Util.LINK_NOT_AVAILABLE);
                    }
                    if (isSelected) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(236, 243, 245));
                        //setBackground(Color.WHITE);
                    }
                }
                return c;
            }

        });
    }

    public void createMainPanels() {
        // splash
        landingPanel = new LandingPanel();
        JPanel splashScreen = landingPanel.getMainPanel();

        //login
        lp = new LoginPanel();
        JPanel loginPanel = lp.getMainPanel();

        //add book
        AddBookPanel abp = new AddBookPanel();
        JPanel addBookPanel = abp.getMainPanel();

        //add book copy
        AddBookCopyPanel abcp = new AddBookCopyPanel();
        JPanel addBookCopyPanel = abcp.getMainPanel();

        //all book IDs
        abip = new AllBookIDs();
        JPanel allTitlesPanel = abip.getMainPanel();

        // add member
        NewMemberPanel newMemberPanel = new NewMemberPanel();
        JPanel newMember = newMemberPanel.getMainPanel();

        // checkout book
        CheckoutBookPanel checkoutBookPanel = new CheckoutBookPanel();
        JPanel myCheckoutBook = checkoutBookPanel.getMainPanel();

        // checkout Record
        CheckoutRecordPanel checkoutRecordPanel = new CheckoutRecordPanel();
        JPanel myCheckoutRecord = checkoutRecordPanel.getMainPanel();

        cards = new JPanel(new CardLayout());
        cards.add(splashScreen, splashScreenItem.getItemName());
        cards.add(loginPanel, loginListItem.getItemName());
        cards.add(newMember, addMember.getItemName());
        cards.add(allTitlesPanel, viewTitlesItem.getItemName());
        cards.add(addBookPanel, addBookItem.getItemName());
        cards.add(myCheckoutBook, checkoutBook.getItemName());
        cards.add(myCheckoutRecord, checkMemberRecord.getItemName());
        cards.add(addBookCopyPanel, addBookCopyItem.getItemName());
    }

    @Override
    public void updateData() {
        // nothing to do
    }
}
