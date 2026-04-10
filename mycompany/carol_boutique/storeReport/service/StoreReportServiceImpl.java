/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.storeReport.service;

import com.mycompany.carol_boutique.storeReport.dao.StoreReportDao;
import com.mycompany.carol_boutique.storeReport.model.LeastPerformingStore;
import com.mycompany.carol_boutique.storeReport.model.StoreReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class StoreReportServiceImpl implements StoreReportService
{
    private StoreReportDao storeReportDao;
    public StoreReportServiceImpl(StoreReportDao storeReportDao) {
        this.storeReportDao =  storeReportDao;
    }
    @Override
    public List<StoreReport> getStoresAchievingTarget(String monthYear) {
        return storeReportDao.getStoresAchievingTarget(monthYear);
    }

    @Override
    public List<LeastPerformingStore> getLeastPerformingStores(LocalDate startDate3Months, LocalDate endDate3Months, LocalDate startDate6Months, LocalDate endDate6Months) {
        return storeReportDao.getLeastPerformingStores(startDate3Months, endDate3Months, startDate6Months, endDate6Months);
    }

    @Override
    public List<SalesReport> getMonthlySalesReport(int storeId, int year, int month) 
    {
        return storeReportDao.getMonthlySalesReport(storeId, year, month);
    }
    
}
