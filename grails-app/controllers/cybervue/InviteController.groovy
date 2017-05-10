package cybervue

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import grails.transaction.Transactional
import groovy.time.TimeCategory

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.NOT_FOUND


@Transactional(readOnly = true)
@Secured('ROLE_ADMIN')
class InviteController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [admin: "POST", user: "POST"]

    @Transactional
    def admin(CyberVueInvite invite) {
        if (invite == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        String confirmCode = UUID.randomUUID().toString()
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
        def validTo = new Date()
        use( TimeCategory ) {
            validTo = validTo + 60.minutes
        }
        def adminRole = Role.findByAuthority("ROLE_ADMIN")
        invite.validTo = validTo
        invite.token = confirmCode
        invite.role = adminRole
        invite.save flush:true

        sendMail{
            to invite.invitationEmail
            subject "CyberVue admin invitation"
            text 'Dear\r\n\r\n You have been invited to CyberVue portal. \r\n\r\n Please use this token ' + confirmCode + ' for creating password. \r\n\r\n Regards from CyberVue team.'
        }

        respond invite, [status: CREATED, view:"show"]
    }

    @Transactional
    def user(CyberVueInvite invite) {
        if (invite == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        String confirmCode = UUID.randomUUID().toString()
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
        def validTo = new Date()
        use( TimeCategory ) {
            validTo = validTo + 60.minutes
        }
        def userRole = Role.findByAuthority("ROLE_USER")
        invite.validTo = validTo
        invite.token = confirmCode
        invite.role = userRole
        invite.save flush:true

        sendMail{
            to invite.invitationEmail
            subject "CyberVue user invitation"
            text 'Dear\r\n\r\n You have been invited to CyberVue portal. \r\n\r\n Please use this token ' + confirmCode + ' for creating password. \r\n\r\n Regards from CyberVue team.'
        }

        respond invite, [status: CREATED, view:"show"]
    }

    def show(CyberVueInvite invite) {
        respond invite
    }
}
