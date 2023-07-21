package org.cinema.models.enums;

public enum MailTemplate {
  MAIL_VERIFY_ACCOUNT("verify-account"),
  ;

  private final String templateName;

  MailTemplate(String templateName) {
    this.templateName = templateName;
  }

  public String getTemplateName() {
    return templateName;
  }
}
