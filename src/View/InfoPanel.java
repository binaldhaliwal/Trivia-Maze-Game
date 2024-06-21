package View;

import javax.swing.*;
import java.awt.*;

/**
 * Represents an information panel to display game instructions and guidelines.
 *
 * @author Binal Dhaliwal
 * @version 1.0
 */
public class InfoPanel extends JPanel {

    /**
     * Constructs an InfoPanel with layout, border, background, and components for
     * displaying game information.
     */
    public InfoPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(204, 255, 255));

        final JLabel infoLabel = new JLabel("<html><h3>Game Information</h3><p></p></html>");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(infoLabel, BorderLayout.NORTH);

        final JTextPane infoTextPane = new JTextPane();
        infoTextPane.setContentType("text/html");
        infoTextPane.setEditable(false);
        infoTextPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final String textBuilder = "<html><body>" +
                "<b>Keyboard Navigation Instructions:</b><br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Up: Up arrow key<br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Down: Down arrow key<br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Left: Left arrow key<br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Right: Right arrow key<br><br>" +
                "<b>Key Notes:</b><br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Pink Line: Unlocked door<br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;White Line: Closed door<br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Blue Line: Locked door<br><br>" +
                "<b>How to Win:</b><br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;- Answer all trivia <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;   questions correctly <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;- Navigate through the <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;   maze to reach the exit <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;- Don't get trapped in the <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;       maze<br>" +
                "</body></html>";
        infoTextPane.setText(textBuilder);

        add(new JScrollPane(infoTextPane), BorderLayout.CENTER);
    }
}