package cybervue

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.NOT_FOUND

@Transactional
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class RegisterController {
	static responseFormats = ['json', 'xml']
    static allowedMethods = [index: "POST"]
	
    def index(CyberVueRegistration registration) {
        if (registration == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        def invite = CyberVueInvite.findByToken(registration.registrationToken)
        def now = new Date()
        if(!invite.valid || invite.validTo < now){
            transactionStatus.setRollbackOnly()
            render status: NOT_ACCEPTABLE
            return
        }

        def user = new User(username: registration.registrationEmail, password: registration.password).save true
        def adminRole = invite.role

        UserRole.create user, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        invite.valid = false;
        invite.save(flush:true)

        sendMail{
            to registration.registrationEmail
            subject "CyberVue registration"
            text 'Dear\r\n\r\nYou have been successfully registered to CyberVue portal. \r\nWelcome!!!\r\n\r\nRegards from CyberVue team.'
        }

        respond user, [status: CREATED, view:"show"]
    }

    def show(User user) {
        respond user
    }
}
