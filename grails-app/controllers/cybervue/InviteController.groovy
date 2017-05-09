package cybervue

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import grails.transaction.Transactional
import groovy.time.TimeCategory

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND


@Transactional(readOnly = true)
@Secured('ROLE_ADMIN')
class InviteController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [admin: "POST", submitted: "POST"]

    @Transactional
    def admin(String email) {
        def userEmail = "djdejan@gmail.com"
        if (!userEmail) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        String confirmCode = UUID.randomUUID().toString()
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
        def now = new Date()
        use( TimeCategory ) {
            now = now + 60.minutes
        }
        def invite = new CyberVueInvite(invitationEmail: userEmail, token: confirmCode, validTo: now)
        invite.save flush:true

        sendMail{
            to userEmail
            subject "CyberVue invitation"
            text 'You have been invited to CyberVue portal. Please use this token ' + confirmCode + ' for creating password'
        }

        respond invite, [status: CREATED, view:"show"]
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def submitted(CyberVueInvite invited){
        if (invited == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        def invite = CyberVueInvite.findByToken(invited.token)

        def user = new User(username: invite.invitationEmail, password: invited.password).save true
        def adminRole = Role.findByAuthority("ROLE_ADMIN")

        UserRole.create user, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        respond user, [status: CREATED, view:"show"]
    }
}
