/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.productReport.service;

import com.mycompany.carol_boutique.productReport.dao.SalesReportDAO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface SalesReportService {
    List<Map<String, Object>> getTopSellingProducts(int limit);
    Map<String, Object> getTopSellingStore();
   
}
