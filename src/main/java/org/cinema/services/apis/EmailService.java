package org.cinema.services.apis;

import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import io.quarkus.mailer.Mail;

@ApplicationScoped
@Default
@Component
public class EmailService {
  @Inject
  private Mailer mailer;
  @Autowired
  private ClassLoaderTemplateResolver resolver;


  private String getMailBodyFromTemplate(String templateName, Map<String, Object> vars) {
    TemplateEngine htmlTemplateEngine = new TemplateEngine();
    htmlTemplateEngine.setTemplateResolver(resolver);

    // create context
    final Context ctx = new Context();
//    for (String key : vars.keySet()) {
//      ctx.setVariable(key, vars.get(key));
//    }

    return htmlTemplateEngine.process(templateName, ctx);
  }

  public void sendMail(String templateName) {
    String content = this.getMailBodyFromTemplate(templateName, null);

    // send mail by MailHog
    Mail mailContent = Mail.withHtml(
            "testmai@gmail.com", "tieu de test mail", content);

    mailer.send(mailContent);
  }
}
