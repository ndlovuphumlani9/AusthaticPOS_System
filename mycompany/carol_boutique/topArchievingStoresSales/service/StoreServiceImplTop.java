/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.topArchievingStoresSales.service;

import com.mycompany.carol_boutique.topArchievingStoresSales.dao.StoreDaoImplTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.dao.StoreDaoTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.DailySales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.EmployeeSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.ProductSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.StoreTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.TopSalesPerson;
import java.util.List;

/**
 *
 * @author Poloko
 */
public class StoreServiceImplTop implements StoreServiceTop {
    private StoreDaoTop storeDaoTop;

    public StoreServiceImplTop(StoreDaoTop storeDaoTop) {
        this.storeDaoTop = new StoreDaoImplTop(); 
    }

    @Override
    public List<StoreTop> getTopAchievingStores() {
        return storeDaoTop.getTopAchievingStores();
    }
    
     @Override
    public List<StoreTop> getTopRatedStores() {
        return storeDaoTop.fetchTopRatedStores();
    }
    
    @Override
    public List<StoreTop> getSalesReportForStore(int storeId, int month, int year) {
        return storeDaoTop.getSalesReportForStore(storeId, month, year);
    }
    
     @Override
    public List<EmployeeSales> getTopSellingTellers() {
        return storeDaoTop.getTopSellingTellers();
    }

    @Override
    public List<EmployeeSales> getTopSellingTellersByStore(int storeId) {
        return storeDaoTop.getTopSellingTellersByStore(storeId);
    }
    
    public List<ProductSales> getTopSellingProducts() {
    return storeDaoTop.getTopSellingProducts();
}
    
     @Override
    public List<SalesReport> getSalesReportByProduct(int productId) {
        return storeDaoTop.getSalesReportByProduct(productId);
    }

    @Override
    public TopSalesPerson getTopSalesPersonByProduct(int productId) {
        return storeDaoTop.getTopSalesPersonByProduct(productId);
    }
    
    public List<DailySales> getDailySales(int storeId) {
    return storeDaoTop.getDailySales(storeId);
}


}
