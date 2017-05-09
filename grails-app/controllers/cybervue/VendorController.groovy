package cybervue

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured('ROLE_ADMIN')
class VendorController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Vendor.list(params), model:[vendorCount: Vendor.count()]
    }

    def show(Vendor vendor) {
        respond vendor
    }

    @Transactional
    def save(Vendor vendor) {
        if (vendor == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (vendor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vendor.errors, view:'create'
            return
        }

        vendor.save flush:true

        respond vendor, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Vendor vendor) {
        if (vendor == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (vendor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vendor.errors, view:'edit'
            return
        }

        vendor.save flush:true

        respond vendor, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Vendor vendor) {

        if (vendor == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        vendor.delete flush:true

        render status: NO_CONTENT
    }
}
