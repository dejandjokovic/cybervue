package cybervue

class Vendor {

    String name
    String description

    List manufacturedSoftwares
    static hasMany = [manufacturedSoftwares: Software]

    static constraints = {
    }
}
