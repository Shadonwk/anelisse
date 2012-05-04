package mx.com.robertoleon.anelisse.utils

import javax.mail.internet.MimeMessage
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.MimeMessageHelper

/**
 * Created by IntelliJ IDEA.
 * Usuario: Shadonwk
 * Date: 24/04/12
 * Time: 05:35 PM
 */
class EmailUtils {
    private ApplicationContext applicationContext = ServletContextHolder.getServletContext().getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
    def javaMailSender = this.applicationContext.getBean('javaMailSender')
    String subject
    String from
    String contentText
    String contentHtml
    String []recipients
    String []recipientsBcc
    private Boolean isMultipart = true
    def resources = []

    void sendEmail() {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper messageContent = new MimeMessageHelper(message, isMultipart);
        messageContent.setSubject(this.subject);
        messageContent.setFrom(this.from);
        if (this.recipientsBcc !=  null && this.recipientsBcc.length > 0) {
            messageContent.setBcc(this.recipientsBcc)
        }
        if (this.contentHtml && this.contentText) {
            messageContent.setText(this.contentText, this.contentHtml)
        } else if (this.contentHtml) {
            messageContent.setText(this.contentHtml, this.contentHtml)
        } else if (this.contentText) {
            messageContent.setText(this.contentText, this.contentText)
        } else {
            return
        }
        messageContent.setTo(this.recipients);

        this.resources.each { resource ->
            FileSystemResource res = new FileSystemResource(new File(resource.filePath));
            messageContent.addInline(resource.identifier, res);
        }
        this.javaMailSender.send(message);
    }

}
