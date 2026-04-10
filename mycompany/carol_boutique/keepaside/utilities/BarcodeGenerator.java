/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.keepaside.utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.awt.image.BufferedImage;

/**
 *
 * @author Mrqts
 */
public class BarcodeGenerator {
    public static BufferedImage generateBarcode(String productId, String size, String colour) throws Exception {
        String barcodeText = productId + "-" + size; // Concatenate productId and size

        // Use Code 128 barcode format
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, barcodeFormat, 200, 100);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage barcodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                barcodeImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF); // Set pixel color
            }
        }
        return barcodeImage;
    }
}
