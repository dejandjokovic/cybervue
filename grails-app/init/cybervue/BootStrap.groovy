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

        UserRole.findAll().each{
            it.delete(flush:true, failOnError:true)
        }
        User.findAll().each{
            it.delete(flush:true, failOnError:true)
        }
        Role.findAll().each{
            it.delete(flush:true, failOnError:true)
        }

        def adminRole = new Role(authority: 'ROLE_ADMIN').save()
        def userRole = new Role(authority: 'ROLE_USER').save()

        def testUser = new User(username: 'me', password: 'password').save()

        UserRole.create testUser, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 1
        assert Role.count() == 2
        assert UserRole.count() == 1

    }
    def destroy = {
    }
}
