package cybervue

class Software {

    String name
    String version

    static belongsTo = [manufacturer: Vendor]
    static constraints = {
        manufacturer nullable: true
    }
}
