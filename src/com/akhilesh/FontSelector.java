package com.akhilesh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * FontSelector class is the class which can be used to change the font of the textArea after choosing from different font styles and size.
 * This class is created because by default, java doesn't provides any fontChooser class to perform the task of changing font
 * FontSelector class has a private method which is used to determine the font which is selected by the user and then the respective font is set to the textArea of our notepad program
 */
class FontSelector {

    private static TextField fontNameTextField = null;
    private static TextField fontStyleTextField = null;
    private static TextField fontSizeTextField = null;
    private static Font fontToReturn = null;
    private static JFrame frame = null;

    private JTextArea sampleTextArea = null;

    /**
     * FontSelector() Constructor initialise the FontSelector UI and all data related to font
     */
    private FontSelector() {
        frame = new JFrame("Choose Font");
        frame.setSize(500, 600);
        frame.setLocation(Notepad.getMainFrame().getWidth(), Notepad.getMainFrame().getHeight() / 2);
        frame.setLayout(null);

        JLabel fontNameLabel = new JLabel("Font:");
        fontNameLabel.setBounds(20, 20, 50, 30);
        Font componentsFont = new Font("Calibri", Font.PLAIN, 16);
        fontNameLabel.setFont(componentsFont);

        JLabel fontStyleLabel = new JLabel("Font Style:");
        fontStyleLabel.setBounds(fontNameLabel.getX() + 200, 20, 80, 30);
        fontStyleLabel.setFont(componentsFont);

        JLabel fontSizeLabel = new JLabel("Size:");
        fontSizeLabel.setBounds(fontStyleLabel.getX() + 150, 20, 50, 30);
        fontSizeLabel.setFont(componentsFont);

        fontNameTextField = new TextField("Calibri");
        fontNameTextField.setBounds(fontNameLabel.getX(), fontNameLabel.getY() + 30, 150, 25);
        fontNameTextField.setFont(componentsFont);
        fontNameTextField.setEditable(false);

        fontStyleTextField = new TextField("Regular");
        fontStyleTextField.setBounds(fontStyleLabel.getX(), fontStyleLabel.getY() + 30, 100, 25);
        fontStyleTextField.setFont(componentsFont);
        fontStyleTextField.setEditable(false);

        fontSizeTextField = new TextField("14");
        fontSizeTextField.setBounds(fontSizeLabel.getX(), fontSizeLabel.getY() + 30, 100, 25);
        fontSizeTextField.setFont(componentsFont);

        // setting the data for all font names
        String[] fontNameArray = new String[]{
                "Arial",
                "Bookman Old Style",
                "Calibri",
                "Cambria",
                "Century",
                "Courier",
                "Georgia",
                "Roman",
                "Segoe UI",
                "Tahoma",
                "Times New Roman",
                "Trebuchet MS",
                "Verdana"
        };
        JPanel fontNamePanel = new JPanel();
        fontNamePanel.setBackground(Color.WHITE);

        for (String s : fontNameArray) {
            JLabel label = new JLabel(s);
            label.setFont(componentsFont);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    label.setBackground(Color.BLUE);
                    label.setForeground(Color.WHITE);
                    fontNameTextField.setText(label.getText());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                    fontNameTextField.setText(label.getText());
                    sampleTextArea.setFont(getFontSelector());
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            fontNamePanel.add(label);
        }

        // setting the data for all font styles
        String[] fontStyleArray = new String[]{
                "Regular",
                "Bold",
                "Italic",
                "Bold Italic"
        };
        JPanel fontStylePanel = new JPanel();
        fontStylePanel.setBackground(Color.WHITE);

        for (String s : fontStyleArray) {
            JLabel label = new JLabel(s);
            label.setFont(componentsFont);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    label.setBackground(Color.BLUE);
                    label.setForeground(Color.WHITE);
                    fontStyleTextField.setText(label.getText());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                    fontStyleTextField.setText(label.getText());
                    sampleTextArea.setFont(getFontSelector());
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            fontStylePanel.add(label);
        }

        // setting the data for all font sizes
        String[] fontSizeArray = new String[]{
                "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"
        };
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setBackground(Color.WHITE);
        for (String s : fontSizeArray) {
            JLabel label = new JLabel(s);
            label.setFont(componentsFont);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    label.setBackground(Color.BLUE);
                    label.setForeground(Color.WHITE);
                    fontSizeTextField.setText(label.getText());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                    fontSizeTextField.setText(label.getText());
                    sampleTextArea.setFont(getFontSelector());
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            fontSizePanel.add(label);
        }
        fontSizeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Font font = getFontSelector();
                    sampleTextArea.setFont(new Font(font.getFamily(), font.getStyle(), Integer.parseInt(fontSizeTextField.getText())));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        BoxLayout nameBoxLayout = new BoxLayout(fontNamePanel, BoxLayout.Y_AXIS);
        fontNamePanel.setLayout(nameBoxLayout);
        JScrollPane fontNameScrollPane = new JScrollPane(fontNamePanel);
        fontNameScrollPane.setBounds(fontNameTextField.getX(), fontNameTextField.getY() + 25, 150, 150);
        fontNameScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        BoxLayout styleBoxLayout = new BoxLayout(fontStylePanel, BoxLayout.Y_AXIS);
        fontStylePanel.setLayout(styleBoxLayout);
        JScrollPane fontStyleScrollPane = new JScrollPane(fontStylePanel);
        fontStyleScrollPane.setBounds(fontStyleTextField.getX(), fontStyleTextField.getY() + 25, 100, 150);
        fontStyleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        BoxLayout fontBoxLayout = new BoxLayout(fontSizePanel, BoxLayout.Y_AXIS);
        fontSizePanel.setLayout(fontBoxLayout);
        JScrollPane fontSizeScrollPane = new JScrollPane(fontSizePanel);
        fontSizeScrollPane.setBounds(fontSizeTextField.getX(), fontSizeTextField.getY() + 25, 100, 150);
        fontStyleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel sampleLabel = new JLabel("Sample");
        sampleLabel.setBounds(fontNameScrollPane.getX(), fontNameScrollPane.getY() + 175, 50, 25);

        sampleTextArea = new JTextArea("AaBbYyZz");
        sampleTextArea.setBounds(sampleLabel.getX(), sampleLabel.getY() + 30, frame.getWidth() - 60, 150);
        sampleTextArea.setEditable(false);

        JButton okButton = new JButton("OK");
        okButton.setBounds(frame.getWidth() / 2 + 50, frame.getHeight() - 100, 75, 30);
        okButton.addActionListener(e -> {
            fontToReturn = getFontSelector();
            Notepad.textArea.setFont(fontToReturn);
            frame.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(okButton.getX() + 90, frame.getHeight() - 100, 75, 30);
        cancelButton.addActionListener(e -> {
            fontToReturn = null;
            frame.dispose();
        });

        frame.add(fontNameLabel);
        frame.add(fontStyleLabel);
        frame.add(fontSizeLabel);
        frame.add(fontNameTextField);
        frame.add(fontStyleTextField);
        frame.add(fontSizeTextField);
        frame.add(fontNameScrollPane);
        frame.add(fontStyleScrollPane);
        frame.add(fontSizeScrollPane);
        frame.add(sampleLabel);
        frame.add(sampleTextArea);
        frame.add(okButton);
        frame.add(cancelButton);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Initialise Font Selector UI and change font of notepad text according to the selection done by the user.
     * User have to only call this method to handle font related stuff
     */
    static void getFontManager() {
        FontSelector fontSelector = new FontSelector();
    }

    /**
     * getFontSelector method is used to get the respective Font object according to user selection
     *
     * @return Font object containing the font according to the user selections
     */
    private Font getFontSelector() {
        String fontNameText = fontNameTextField.getText();
        String fontStyleText = fontStyleTextField.getText();
        int fontSize;
        try {
            fontSize = Integer.parseInt(fontSizeTextField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Font Size, Default size 14 will be set");
            fontSizeTextField.setText("14");
            fontSize = 14;  // setting default font size
        }

        // "Regular", "Bold", "Italic", "Bold Italic"
        switch (fontStyleText) {
            case "Regular":
                return new Font(fontNameText, Font.PLAIN, fontSize);

            case "Bold":
                return new Font(fontNameText, Font.BOLD, fontSize);

            case "Italic":
                return new Font(fontNameText, Font.ITALIC, fontSize);

            case "Bold Italic":
                return new Font(fontNameText, Font.BOLD + Font.ITALIC, fontSize);
        }
        return new Font("Times New Roman", Font.PLAIN, 14); //default font which is almost impossible to get executed according to programming logic
    }
}