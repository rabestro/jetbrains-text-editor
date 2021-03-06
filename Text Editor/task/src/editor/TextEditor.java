package editor;

import editor.component.AppMenu;
import editor.component.AppToolbar;
import editor.events.CommandEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class TextEditor extends JFrame {
    private static final Logger log = Logger.getLogger(TextEditor.class.getName());

    private final JFileChooser fileChooser = new JFileChooser();
    private final JTextArea textArea = new JTextArea();

    {
        textArea.setName("TextArea");
        textArea.setBounds(0, 0, 300, 300);
        textArea.setFont(new Font("Serif", Font.ITALIC, 24));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    private final JScrollPane scrollPane = new JScrollPane(textArea);

    {
        scrollPane.setName("ScrollPane");
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(250, 250));
    }

    private final AppToolbar toolbar = new AppToolbar(this::processCommand);

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setTitle("The text editor");

        add(scrollPane, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);

        fileChooser.setVisible(false);
//        add(fileChooser);
        setJMenuBar(new AppMenu(this::processCommand));
        setVisible(true);
    }

    private void processCommand(final CommandEvent event) {
        switch (event.getCommand()) {
            case EXIT:
                this.dispose();
                return;
            case OPEN:
                log.info("Open a document");
                return;
            case SAVE:
                log.info("Save a document");
                return;
            case START_SEARCH:
                log.info("Start search");
                return;
            case PREVIOUS:
                log.info("Previous search");
                return;
            case NEXT:
                log.info("Next search");
                return;
            case USE_REGEX:
                log.info("Use regular expressions");
                return;
            default:
                log.info("Unimplemented action occurs");
        }
    }

    public void load(final ActionEvent actionEvent) {
        final var filePath = toolbar.getFile();
        try {
            textArea.setText(Files.readString(filePath));
        } catch (IOException e) {
            textArea.setText("");
            log.warning(e::getMessage);
        }
    }

    public void save(final ActionEvent actionEvent) {
        final var filePath = toolbar.getFile();
        try {
            Files.writeString(filePath, textArea.getText(), CREATE, WRITE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            log.warning(e::getMessage);
        }
    }
}
