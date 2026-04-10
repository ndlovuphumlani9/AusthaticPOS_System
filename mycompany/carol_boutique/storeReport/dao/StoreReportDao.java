/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.storeReport.dao;

import com.mycompany.carol_boutique.storeReport.model.LeastPerformingStore;
import com.mycompany.carol_boutique.storeReport.model.StoreReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface StoreReportDao {
    List<StoreReport> getStoresAchievingTarget(String monthYear);
    List<LeastPerformingStore> getLeastPerformingStores(LocalDate startDate3Months, LocalDate endDate3Months, LocalDate startDate6Months, LocalDate endDate6Months) ;
    List<SalesReport> getMonthlySalesReport(int storeId, int year, int month);
    
}
