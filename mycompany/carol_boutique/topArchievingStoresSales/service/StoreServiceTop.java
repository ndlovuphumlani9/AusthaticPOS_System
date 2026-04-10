/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carol_boutique.topArchievingStoresSales.service;

/**
 *
 * @author Poloko
 */



import com.mycompany.carol_boutique.topArchievingStoresSales.model.DailySales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.EmployeeSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.ProductSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.StoreTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.TopSalesPerson;
import java.util.List;

public interface StoreServiceTop {
    List<StoreTop> getTopAchievingStores();
    List<StoreTop> getTopRatedStores();
    List<StoreTop> getSalesReportForStore(int storeId, int month, int year);
    List<EmployeeSales> getTopSellingTellers();
    List<EmployeeSales> getTopSellingTellersByStore(int storeId);
    List<ProductSales> getTopSellingProducts();
    List<SalesReport> getSalesReportByProduct(int productId);
    TopSalesPerson getTopSalesPersonByProduct(int productId);
    public List<DailySales> getDailySales(int storeId);
}
