/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.product.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.ibt.model.Item;
import com.mycompany.carol_boutique.product.dao.ProductDao;
import com.mycompany.carol_boutique.product.model.Barcode;
import com.mycompany.carol_boutique.product.model.Product;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Mrqts
 */
public class ProductServiceImpl implements ProductService {
    ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public int addProduct(Product product) {
        return productDao.addProduct(product);
    }

    @Override
    public Item getItemByBarcode(String barcodeData) {
        return productDao.getItemByBarcode(barcodeData);
    }

    @Override
    public Barcode generateBarcode(int productId, String size, String colour) throws IOException{
        try {
            String barcodeData = String.format("%s-%s-%s", productId, size, colour);
            
            File barcodeImage = generateBarcodeImage(barcodeData, "C:\\Users\\Tau\\Downloads\\AAAAAAA\\AAAAAAA\\com.mycompany_Carol_BC\\com.mycompany_Carol_Boutique_war_1.0-SNAPSHOT\\src\\main\\java\\com\\mycompany\\carol_boutique\\barcodes\\"+barcodeData+".png");
            
            return new Barcode(barcodeData, barcodeImage);
        } catch (WriterException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    public static File generateBarcodeImage(String barcodeData, String filePath)
            throws WriterException, IOException {
        int width = 400;
        int height = 100;

        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeData, BarcodeFormat.CODE_128, width, height);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        File outputFile = new File(filePath);
        ImageIO.write(bufferedImage, "png", outputFile);
        return outputFile;       
    }


    @Override
    public boolean addKeepAside(String barcode2, String email, Employee employee) {
        return productDao.addKeepAside(barcode2, email, employee);
    }

    @Override
    public int process(List<Item> items, Employee employee, double total, String email, Long accountNo) {
        return productDao.process(items, employee, total, email, accountNo);
    }

    @Override
    public Employee manager(String username, String password) {
        return productDao.manager(username, password);
    }

    @Override
    public String storeAddress(int storeId) {
        return productDao.storeAddress(storeId);
    }

    @Override
    public int getStoreId(int ibtId3) {
        return productDao.getStoreId(ibtId3);
    }
    
}
