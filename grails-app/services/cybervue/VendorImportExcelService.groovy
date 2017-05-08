package cybervue

import grails.transaction.Transactional
import org.grails.plugins.excelimport.ExcelImportService
import org.grails.plugins.excelimport.*
import org.springframework.beans.factory.annotation.Autowired

@Transactional
class VendorImportExcelService extends AbstractExcelImporter {

    static scope = "prototype"

    static Map CONFIG_VENDOR_COLUMN_MAP = [
            sheet    : 'Vendors',
            startRow : 1,
            columnMap: [
                    'A': 'name',
                    'B': 'description',
                    'C': 'pointOfContactName',
                    'D': 'pointOfContactEmail'
            ]
    ]

    VendorImportExcelService(fileName) {
        read(fileName)
    }

    List<Map> getVendors() {
        def excelImportService = new ExcelImportService()
        def vendorList = excelImportService.convertColumnMapConfigManyRows(workbook, CONFIG_VENDOR_COLUMN_MAP)
        return vendorList
    }

    def serviceMethod() {

    }
}
