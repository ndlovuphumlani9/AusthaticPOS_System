package com.mycompany.carol_boutique.topArchievingStoresSales.dao;

import com.mycompany.carol_boutique.topArchievingStoresSales.model.DailySales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.EmployeeSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.ProductSales;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.SalesReport;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.StoreTop;
import com.mycompany.carol_boutique.topArchievingStoresSales.model.TopSalesPerson;
import java.util.List;

public interface StoreDaoTop {

    List<StoreTop> getTopAchievingStores();
    List<StoreTop> fetchTopRatedStores();
    List<StoreTop> getSalesReportForStore(int storeId, int month, int year);
    List<EmployeeSales> getTopSellingTellers();
    List<EmployeeSales> getTopSellingTellersByStore(int storeId);
    List<ProductSales> getTopSellingProducts();
    List<SalesReport> getSalesReportByProduct(int productId);
    TopSalesPerson getTopSalesPersonByProduct(int productId);
    List<DailySales> getDailySales(int storeId);
}
