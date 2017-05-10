package cybervue

class CyberVueInvite {

    String invitationEmail
    String token
    Date validTo
    boolean valid = true
    Role role

    static constraints = {
        invitationEmail email:true
        validTo nullable: true
        token nullable:true
        role nullable: true
    }
}
