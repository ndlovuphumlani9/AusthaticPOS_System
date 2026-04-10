/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.sale.service;

import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.sale.dao.SaleDao;
import java.util.List;

/**
 *
 * @author Mrqts
 */
public class SaleServiceImpl implements SaleService {
    SaleDao saleDao;

    public SaleServiceImpl(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @Override
    public boolean processSale(List<Item> items) {
        return saleDao.processSale(items);
    }
    
}
