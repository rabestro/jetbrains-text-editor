package editor;

import editor.component.AppMenu;
import editor.component.TextPane;
import editor.component.Toolbar;
import editor.events.CommandEvent;
import editor.service.FileService;
import editor.service.SearchService;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.util.logging.Logger;

public class TextEditor extends JFrame {
    private static final Logger log = Logger.getLogger(TextEditor.class.getName());

    private final TextPane textPane = new TextPane();
    private final Toolbar toolbar = new Toolbar(this::processCommand);
    private final SearchService searchService = new SearchService(textPane);
    private final FileService fileService = new FileService(textPane);

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setTitle("The Text Editor");

        add(fileService);
        add(textPane, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        setJMenuBar(new AppMenu(this::processCommand));
        setVisible(true);
    }

    private void processCommand(final CommandEvent event) {
        switch (event.getCommand()) {
            case EXIT:
                this.dispose();
                return;
            case OPEN:
                fileService.open();
                searchService.reset();
                return;
            case SAVE:
                fileService.save();
                return;
            case START_SEARCH:
                searchService.startSearch(toolbar.getPattern());
                return;
            case PREVIOUS:
                searchService.previous();
                return;
            case NEXT:
                searchService.next();
                return;
            case USE_REGEX:
                toolbar.useRegex();
                return;
            default:
                log.warning("Unimplemented action occurs");
        }
    }

}
