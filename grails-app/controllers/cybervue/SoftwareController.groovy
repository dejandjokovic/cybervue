package cybervue

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SoftwareController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Software.list(params), model:[softwareCount: Software.count()]
    }

    def show(Software software) {
        respond software
    }

    @Transactional
    def save(Software software) {
        if (software == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (software.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond software.errors, view:'create'
            return
        }

        software.save flush:true

        respond software, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Software software) {
        if (software == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (software.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond software.errors, view:'edit'
            return
        }

        software.save flush:true

        respond software, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Software software) {

        if (software == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        software.delete flush:true

        render status: NO_CONTENT
    }
}
