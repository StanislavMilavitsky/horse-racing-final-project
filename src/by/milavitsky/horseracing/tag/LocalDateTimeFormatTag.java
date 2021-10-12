package by.milavitsky.horseracing.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFormatTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(LocalDateTimeFormatTag.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private String dateTime;

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime.trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            String formatDateTime = localDateTime.format(formatter);
            out.print(formatDateTime);
        } catch (IOException e) {
            logger.error("Time format tag exception!", e);
        }
        return SKIP_BODY;
    }
}
