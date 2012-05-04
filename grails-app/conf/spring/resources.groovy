// Place your Spring DSL code here
beans = {
    javaMailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
        host="mail.inapesca.gob.mx"
        port="587"
        username="sistema.sicpa@inapesca.gob.mx"
        password='WkL789bGLDMe'
        protocol="smtp"
        javaMailProperties=["mail.smtp.auth":true]
    }
}
