/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.productReport.service;

import com.mycompany.carol_boutique.productReport.dao.SalesReportDAO;
import com.mycompany.carol_boutique.storeReport.dao.StoreReportDaoImpl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class SalesReportServiceImpl implements SalesReportService{
     private SalesReportDAO salesReportDAO;

    public SalesReportServiceImpl(StoreReportDaoImpl storeReportDaoImpl) {
        this.salesReportDAO =  salesReportDAO;
    }
    
    @Override
    public List<Map<String, Object>> getTopSellingProducts(int limit) {
         return salesReportDAO.getTopSellingProducts(limit);
    }

    @Override
    public Map<String, Object> getTopSellingStore() {
        return salesReportDAO.getTopSellingStore();
    }
    
}
