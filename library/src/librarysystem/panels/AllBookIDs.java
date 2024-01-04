package librarysystem.panels;

import business.ControllerInterface;
import business.SystemController;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class AllBookIDs implements MessageableWindow {
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private TextArea textArea;
    private final ControllerInterface sc = SystemController.INSTANCE;

    public AllBookIDs() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel titlesLabel = new JLabel("All Book IDs");
        Util.adjustLabelFont(titlesLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(titlesLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        textArea = new TextArea(8, 20);
        updateData();
        middlePanel.add(textArea);
    }

    public void updateData() {
        StringBuilder sb = new StringBuilder();
        List<String> bookIds = sc.allBookIds();
        Collections.sort(bookIds);
        for (String s : bookIds) {
            sb.append(s).append("\n");
        }
        textArea.setText(sb.toString());
        mainPanel.repaint();
    }
}
