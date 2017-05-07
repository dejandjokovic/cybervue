package cybervue

import grails.transaction.Transactional
import org.grails.plugins.excelimport.ExcelImportService
import org.grails.plugins.excelimport.*
import org.springframework.beans.factory.annotation.Autowired

@Transactional
class VendorImportService extends AbstractExcelImporter{

    static scope = "prototype"

    static Map CONFIG_VENDOR_COLUMN_MAP = [
            sheet:'Vendors',
            startRow: 2,
            columnMap:  [
                    'A':'name',
                    'B':'description',
                    'C':'pointOfContactName',
                    'D':'pointOfContactEmail'
            ]
    ]

    VendorImportService(fileName){
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
