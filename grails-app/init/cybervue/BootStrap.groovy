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


        // for MAC
        //String fileNameExcel = "/import/vendors.xlsx"

        // for PC
        //String fileNameExcel = "D:\\import\\vendors.xlsx"

        //def vendorImportExcelService = new VendorImportExcelService(fileNameExcel)

        //def vendorListFromExcel = vendorImportExcelService.getVendors()

//        vendorListFromExcel.each { Map vendorParams ->
//            def newVendor = new Vendor(vendorParams)
//            if (!newVendor.save()) {
//                println "Vendor not saved, errors = ${newVendor.errors}"
//            }
//        }

        def adminRole = Role.findByAuthority("ROLE_ADMIN")
        if(adminRole == null) {
            adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        }

        def userRole = Role.findByAuthority("ROLE_USER")
        if(userRole == null) {
            userRole = new Role(authority: 'ROLE_USER').save(flush: true)
        }

        def superAdmin = User.findByUsername("admin")
        if(superAdmin == null) {
            superAdmin = new User(username: 'admin', password: 'admin').save(flush: true)
        }

        def adminUserRelation = UserRole.findByUser(superAdmin)
        if(adminUserRelation == null){
            UserRole.create superAdmin, adminRole
            UserRole.withSession {
                it.flush()
                it.clear()
            }
        }

    }
    def destroy = {
    }
}
