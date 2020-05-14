package utilities;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Allows adding log elements to a text area.
 */
@Plugin(
        name = "TextAreaAppender",
        category = "Core",
        elementType = "appender",
        printObject = true)
public final class TextAreaAppender extends AbstractAppender {

    private static TextArea textArea;


    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();


    /**
     * Instantiates a new Text area appender.
     *
     * @param name             the name
     * @param filter           the filter
     * @param layout           the layout
     * @param ignoreExceptions the ignore exceptions
     */
    protected TextAreaAppender(String name, Filter filter,
                               Layout<? extends Serializable> layout,
                               final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }


    @Override
    public void append(LogEvent event) {
        readLock.lock();

        final String message = new String(getLayout().toByteArray(event));

        if (textArea != null) {
            if (textArea.getText().length() == 0) {
                textArea.setText(message);
            } else {
                textArea.selectEnd();
                textArea.insertText(textArea.getText().length(),
                        message);
            }
        }
    }

    /**
     * Create appender text area appender.
     *
     * @param name   the name
     * @param layout the layout
     * @param filter the filter
     * @return the text area appender
     */
    @PluginFactory
    public static TextAreaAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TextAreaAppender(name, filter, layout, true);
    }

    /**
     * Sets text area.
     *
     * @param textArea the text area
     */
    public static void setTextArea(TextArea textArea) {
        TextAreaAppender.textArea = textArea;
    }
}