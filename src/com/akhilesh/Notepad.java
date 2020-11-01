package com.akhilesh;

import com.sun.java.swing.plaf.motif.MotifScrollBarUI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Notepad {

    static JTextArea textArea = null;
    private static JFrame frame = null;
    private static JMenuBar notepadMenuBar = null;
    private static JMenuBar menuBar = null;
    private static JMenu fileMenu = null;
    private static JMenuItem newMenuItem = null;
    private static JMenuItem openMenuItem = null;
    private static JMenuItem saveMenuItem = null;
    private static JMenuItem exitMenuItem = null;
    private static JMenu editMenu = null;
    private static JMenuItem undoMenuItem = null;
    private static JMenuItem cutMenuItem = null;
    private static JMenuItem copyMenuItem = null;
    private static JMenuItem pasteMenuItem = null;
    private static JMenuItem findMenuItem = null;
    private static JMenuItem replaceMenuItem = null;
    private static JMenuItem selectAllMenuItem = null;
    private static JMenu formatMenu = null;
    private static JCheckBoxMenuItem wordWrapMenuItem = null;
    private static JMenuItem fontMenuItem = null;
    private static JMenu themeMenu = null;
    private static JMenuItem lightTheme = null;
    private static JMenuItem darkTheme = null;
    private static JMenu helpMenu = null;
    private static JMenuItem helpMenuItem = null;
    private static JMenuItem aboutMenuItem = null;
    private static JScrollPane scrollPane = null;
    private static JTextArea rowsCountTextArea = null;
    private static JPanel bottomPanel = null;
    private static JLabel bottomLabel = null;
    private static UndoManager undoManager = null;
    private static boolean isTextAreaContentEdited; // by default value is false
    private static ArrayList<Color> defaultColors = null;
    private static Color defaultScrollColor = null;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle("Notepad");
        frame.setSize(700, 600);
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setLocation(frame.getHeight() / 2, frame.getWidth() / 3);

        BorderLayout borderLayout = new BorderLayout();
        frame.setLayout(borderLayout);

        undoManager = new UndoManager();

        textArea = new JTextArea(); // textArea for writing text and other stuff
        textArea.setFont(new Font("Consolas", Font.PLAIN, 18));
        textArea.getDocument().addUndoableEditListener(undoManager);    //associating undo manager with textArea
        scrollPane = new JScrollPane(textArea); // scrollPane to enable scrolling if text exceeds the border bounds
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);   // always showing the vertical ScrollBar even if it is not necessary
        rowsCountTextArea = new JTextArea(" "); // just giving little bit space in left side of the textArea (only for good visuals, nothing else)
        rowsCountTextArea.setEditable(false);   // disabling editing of textArea

        notepadMenuBar = getNotepadMenuBar();
        frame.add(notepadMenuBar, BorderLayout.NORTH);    //adding menuBar with menuItems
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(rowsCountTextArea, BorderLayout.WEST);

        bottomPanel = new JPanel();
        bottomLabel = new JLabel("© Akhilesh Garg");
        bottomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomLabel.setFocusable(false);
        bottomPanel.add(bottomLabel);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        if (defaultScrollColor == null) {
            scrollPane.getVerticalScrollBar().setUI(new MotifScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    super.configureScrollBarColors();
                    defaultScrollColor = thumbColor;
                }
            });
            scrollPane.getHorizontalScrollBar().setUI(new MotifScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    super.configureScrollBarColors();
                    defaultScrollColor = thumbColor;
                }
            });
        }

        if (defaultColors == null) {
            defaultColors = new ArrayList<>();
            defaultColors.add(textArea.getBackground());
            defaultColors.add(notepadMenuBar.getBackground());
            defaultColors.add(fileMenu.getBackground());
            defaultColors.add(bottomPanel.getBackground());
            defaultColors.add(bottomLabel.getBackground());
            defaultColors.add(rowsCountTextArea.getBackground());

            defaultColors.add(textArea.getCaretColor());

            defaultColors.add(textArea.getForeground());
            defaultColors.add(notepadMenuBar.getForeground());
            defaultColors.add(fileMenu.getForeground());
            defaultColors.add(bottomPanel.getForeground());
            defaultColors.add(bottomLabel.getForeground());
            defaultColors.add(rowsCountTextArea.getForeground());

            defaultColors.add(scrollPane.getVerticalScrollBar().getBackground());
            defaultColors.add(defaultScrollColor);
        }

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

    static JMenuBar getNotepadMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setToolTipText("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu = new JMenu("Edit");
        editMenu.setToolTipText("Edit");
        undoMenuItem = new JMenuItem("Undo");
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        findMenuItem = new JMenuItem("Find");
        replaceMenuItem = new JMenuItem("Replace All");
        selectAllMenuItem = new JMenuItem("Select All");
        editMenu.add(undoMenuItem);
        editMenu.addSeparator();
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.addSeparator();
        editMenu.add(findMenuItem);
        editMenu.add(replaceMenuItem);
        editMenu.addSeparator();
        editMenu.add(selectAllMenuItem);

        formatMenu = new JMenu("Format");
        wordWrapMenuItem = new JCheckBoxMenuItem("Word Wrap");
        fontMenuItem = new JMenuItem("Font");
        formatMenu.add(wordWrapMenuItem);
        formatMenu.add(fontMenuItem);

        themeMenu = new JMenu("Theme");
        lightTheme = new JMenuItem("Light");
        darkTheme = new JMenuItem("Dark");
        themeMenu.add(lightTheme);
        themeMenu.add(darkTheme);

        helpMenu = new JMenu("Help");
        helpMenuItem = new JMenuItem("Help");
        aboutMenuItem = new JMenuItem("About");
        helpMenu.add(helpMenuItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(themeMenu);
        menuBar.add(helpMenu);

        setAllAccelerators();
        setAllListeners();

        return menuBar;
    }

    static void setAllAccelerators() {
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));

        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        findMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
        replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
        selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));

        helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
    }

    static void setAllListeners() {

        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                textArea.requestFocus();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                if (isTextAreaContentEdited) {
                    JOptionPane.showMessageDialog(frame, "Please save your file before closing application", "Warning", JOptionPane.ERROR_MESSAGE);
                    isTextAreaContentEdited = false;
                } else {
                    int returnCode = JOptionPane.showConfirmDialog(frame, "Are you sure want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);

                    switch (returnCode) {
                        case 0: //user decide to close the application
                            System.exit(0); // closing JVM with 0 status code (ultimately close all open frame, pane, release all memory allocated to our program)
                            break;

                        case 1:
                        case -1:
                            // do nothing because user didn't want to close the notepad

                        default:
                            //do nothing
                    }
                }
            }
        });

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER) {
                    isTextAreaContentEdited = true;
                    textArea.getHighlighter().removeAllHighlights();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        textArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu contextMenu = new JPopupMenu();
                    JMenuItem cutJMenuItem = new JMenuItem("Cut");
                    JMenuItem copyJMenuItem = new JMenuItem("Copy");
                    JMenuItem pasteJMenuItem = new JMenuItem("Paste");
                    JMenuItem selectAllJMenuItem = new JMenuItem("Select All");
                    JMenuItem deleteJMenuItem = new JMenuItem("Delete");

                    cutJMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textArea.cut();
                        }
                    });

                    copyJMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textArea.copy();
                        }
                    });

                    pasteJMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textArea.paste();
                        }
                    });

                    selectAllJMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textArea.selectAll();
                        }
                    });

                    deleteJMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // automation kind of class in java AWT package
                            Robot robot = null;
                            try {
                                robot = new Robot();
                                robot.keyPress(KeyEvent.VK_DELETE);
                            } catch (AWTException awtException) {
                                awtException.printStackTrace();
                            }
                        }
                    });

                    cutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
                    copyJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
                    pasteJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
                    selectAllJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
                    deleteJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

                    contextMenu.add(cutJMenuItem);
                    contextMenu.add(copyJMenuItem);
                    contextMenu.add(pasteJMenuItem);
                    contextMenu.addSeparator();
                    contextMenu.add(selectAllJMenuItem);
                    contextMenu.add(deleteJMenuItem);

                    contextMenu.show(frame, e.getX(), e.getY() + 50);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTextAreaContentEdited) {
                    JOptionPane.showMessageDialog(frame, "Please save your file before opening new document", "Warning", JOptionPane.ERROR_MESSAGE);
                    isTextAreaContentEdited = false;
                } else {
                    frame.setTitle("Untitled - Notepad");
                    textArea.setText("");
                }
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTextAreaContentEdited) {
                    JOptionPane.showMessageDialog(frame, "Please save your file before opening document", "Warning", JOptionPane.ERROR_MESSAGE);
                    isTextAreaContentEdited = false;
                } else {
                    // file chooser used to open dialog which helps us to open the dialog box to open file
                    JFileChooser fileChooser = new JFileChooser();

                    // changing title of chooser dialog box which will be opened
                    fileChooser.setDialogTitle("Open File");

                    // file filter helps to filter the content which we want to see with extension.
                    // for example, if we have thousands of file and we want only .txt extension file then we can use filter to show only files having txt extension
                    FileFilter fileFilter = new FileNameExtensionFilter("Text Document (.txt)", "txt");

                    // adding file chooser filter to our file chooser
                    fileChooser.addChoosableFileFilter(fileFilter);

                    // displaying file chooser dialog and passing its parent's reference (i.e. JFrame here)
                    // return 0 on success otherwise returns 1
                    fileChooser.showOpenDialog(frame);

                    BufferedReader bufferedReader;
                    if (fileChooser.getSelectedFile() != null && fileChooser.getSelectedFile().getName().contains(".txt")) {
                        try {
                            bufferedReader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                            isTextAreaContentEdited = true;
                            String data;
                            textArea.setText("");
                            while ((data = bufferedReader.readLine()) != null) {
                                textArea.append(data + "\n");
                            }

                            frame.setTitle(fileChooser.getSelectedFile().getName() + " - Notepad");

                            bufferedReader.close();
                        } catch (IOException exception) {
                            JOptionPane.showMessageDialog(frame, "Error opening file");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Unsupported file format");
                    }
                }
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setDialogTitle("Save File");
                FileFilter fileFilter = new FileNameExtensionFilter("Text Document (.txt)", "txt");

                fileChooser.addChoosableFileFilter(fileFilter);

                int returnCode = fileChooser.showSaveDialog(frame);

                if (returnCode == 0) {
                    File file = fileChooser.getSelectedFile();

                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                        bufferedWriter.write(textArea.getText());
                        JOptionPane.showMessageDialog(frame, "File Successfully Saved");
                        bufferedWriter.close();
                        frame.setTitle(fileChooser.getSelectedFile().getName() + " - Notepad");
                        isTextAreaContentEdited = false;
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(frame, "Error Saving file");
                    }
                }
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTextAreaContentEdited) {
                    JOptionPane.showMessageDialog(frame, "Please save your file before closing application", "Warning", JOptionPane.ERROR_MESSAGE);
                    isTextAreaContentEdited = false;
                } else {
                    int returnCode = JOptionPane.showConfirmDialog(frame, "Are you sure want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);

                    switch (returnCode) {
                        case 0: //user decide to close the application
                            System.exit(0); // closing JVM with 0 status code (ultimately close all open frame, pane, release all memory allocated to our program)
                            break;

                        case 1:
                        case -1:
                            // do nothing because user didn't want to close the notepad

                        default:
                            //do nothing
                    }
                }
            }
        });

        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });

        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });

        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });

        findMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchData = JOptionPane.showInputDialog(frame, "Enter Text to find");

                if (searchData != null) {
                    String text = textArea.getText();
                    int index = text.indexOf(searchData);
                    int length = searchData.length();
                    textArea.getHighlighter().removeAllHighlights();

                    while (index != -1)
                        try {
                            textArea.getHighlighter().addHighlight(index, index + length, new DefaultHighlighter.DefaultHighlightPainter(Color.getHSBColor(120, 203, 241)));
                            int tempIndex = index;
                            index = text.indexOf(searchData, tempIndex + 1);
                        } catch (BadLocationException badLocationException) {
                            badLocationException.printStackTrace();
                        }
                }
            }
        });

        replaceMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField previousTextField = new JTextField();

                Object[] inputFields = {"Find what", previousTextField, "Replace with"};

                String newValue = JOptionPane.showInputDialog(frame, inputFields, "Enter replace values", JOptionPane.WARNING_MESSAGE);
                String oldValue = previousTextField.getText();

                textArea.setText(textArea.getText().replaceAll(oldValue, newValue));
            }
        });

        selectAllMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });

        wordWrapMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setLineWrap(wordWrapMenuItem.isSelected());
                textArea.setWrapStyleWord(true);
            }
        });

        fontMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FontSelector.getFontManager();
            }
        });

        lightTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textArea != null) {
                    textArea.setBackground(defaultColors.get(0));
                    textArea.setCaretColor(defaultColors.get(6));
                    textArea.setSelectedTextColor(Color.BLACK);
                    notepadMenuBar.setBackground(defaultColors.get(1));
                    fileMenu.setBackground(defaultColors.get(2));
                    editMenu.setBackground(defaultColors.get(2));
                    formatMenu.setBackground(defaultColors.get(2));
                    themeMenu.setBackground(defaultColors.get(2));
                    helpMenu.setBackground(defaultColors.get(2));
                    bottomPanel.setBackground(defaultColors.get(3));
                    bottomLabel.setBackground(defaultColors.get(4));
                    rowsCountTextArea.setBackground(defaultColors.get(5));
                    textArea.setForeground(defaultColors.get(7));
                    notepadMenuBar.setForeground(defaultColors.get(8));
                    fileMenu.setForeground(defaultColors.get(9));
                    editMenu.setForeground(defaultColors.get(9));
                    formatMenu.setForeground(defaultColors.get(9));
                    themeMenu.setForeground(defaultColors.get(9));
                    helpMenu.setForeground(defaultColors.get(9));
                    bottomPanel.setForeground(defaultColors.get(10));
                    bottomLabel.setForeground(defaultColors.get(11));
                    rowsCountTextArea.setForeground(defaultColors.get(12));

                    scrollPane.getVerticalScrollBar().setBackground(defaultColors.get(13));
                    scrollPane.getHorizontalScrollBar().setBackground(defaultColors.get(13));

                    scrollPane.getVerticalScrollBar().setUI(new MotifScrollBarUI() {
                        @Override
                        protected void configureScrollBarColors() {
                            this.thumbColor = defaultScrollColor;
                        }
                    });

                    scrollPane.getHorizontalScrollBar().setUI(new MotifScrollBarUI() {
                        @Override
                        protected void configureScrollBarColors() {
                            this.thumbColor = defaultScrollColor;
                        }
                    });
                }
            }
        });

        darkTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textArea != null) {
                    Color darkColor = Color.decode("#383837");
                    textArea.setBackground(darkColor);
                    textArea.setForeground(Color.WHITE);
                    textArea.setCaretColor(Color.WHITE);
                    textArea.setSelectedTextColor(Color.WHITE);
                    notepadMenuBar.setBackground(darkColor);
                    fileMenu.setForeground(Color.WHITE);
                    editMenu.setForeground(Color.WHITE);
                    formatMenu.setForeground(Color.WHITE);
                    themeMenu.setForeground(Color.WHITE);
                    helpMenu.setForeground(Color.WHITE);
                    bottomPanel.setBackground(darkColor);
                    bottomLabel.setForeground(Color.WHITE);
                    rowsCountTextArea.setBackground(darkColor);
                    rowsCountTextArea.setForeground(Color.WHITE);

                    scrollPane.getVerticalScrollBar().setBackground(darkColor);
                    scrollPane.getHorizontalScrollBar().setBackground(darkColor);

                    scrollPane.getVerticalScrollBar().setUI(new MotifScrollBarUI() {
                        @Override
                        protected void configureScrollBarColors() {
                            this.thumbColor = Color.GRAY;
                        }
                    });

                    scrollPane.getHorizontalScrollBar().setUI(new MotifScrollBarUI() {
                        @Override
                        protected void configureScrollBarColors() {
                            this.thumbColor = Color.GRAY;
                        }
                    });
                }
            }
        });

        helpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnCode = JOptionPane.showConfirmDialog(frame, "Redirect to browser?", "Confirm Redirect", JOptionPane.YES_NO_OPTION);
                // yes = 0, no = 1, exit = -1;

                switch (returnCode) {
                    case 0:
                        try {
                            Desktop.getDesktop().browse(new URL("https://go.microsoft.com/fwlink/?LinkId=834783").toURI());
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(frame, "Failed to open the browser.....", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    case 1:
                    case -1:
                        // do nothing because user decide to not do anything
                        break;

                    default:
                        // do nothing
                }
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dialogMessage = "Notepad v1.0\nDeveloped using Java Swing\nDeveloper : Akhilesh Garg\nCopyright © Akhilesh Garg\n";
                JOptionPane.showMessageDialog(frame, dialogMessage, "About Notepad", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    static JFrame getMainFrame() {
        return frame;
    }
}