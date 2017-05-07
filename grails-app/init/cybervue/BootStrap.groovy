package cybervue

class BootStrap {

    def init = { servletContext ->
//        new Vendor(name:"Apple", description: "Apple vendor.").save()
//        def microsoft = new Vendor(name:"Microsoft", description: "Microsoft vendor.")
//        def windows = new Software(name: "Windows", version: "10")
//        def excel = new Software(name:"Excel", version: "12")
//        microsoft.addToManufacturedSoftwares(windows)
//        microsoft.addToManufacturedSoftwares(excel)
//        microsoft.save()
//        new Software(name: "Scoopz", version:"0.0.1").save()


        String fileName = "/import/vendors.xlsx"
        def vendorImportService = new VendorImportService(fileName)

        def vendorList = vendorImportService.getVendors()

        vendorList.each { Map vendorParams ->
            def newVendor = new Vendor(vendorParams)
            if (!newVendor.save()) {
                println "Vendor not saved, errors = ${newVendor.errors}"
            }
        }

    }
    def destroy = {
    }
}
