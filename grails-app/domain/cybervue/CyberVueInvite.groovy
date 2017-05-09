package cybervue

class CyberVueInvite {

    String invitationEmail
    String token
    Date validTo
    String password

    static constraints = {
        invitationEmail email:true
        password nullable: true
        validTo nullable: true
    }
}
