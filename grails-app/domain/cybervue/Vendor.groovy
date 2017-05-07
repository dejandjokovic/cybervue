package cybervue

class Vendor {

    String name
    String description
    String pointOfContactName
    String pointOfContactEmail

    List manufacturedSoftwares
    static hasMany = [manufacturedSoftwares: Software]

    static constraints = {
    }
}
