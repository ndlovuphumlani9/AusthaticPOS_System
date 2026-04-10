package com.mycompany.carol_boutique.product.utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BarcodeGenerator {

    public static void main(String[] args) {
        String productCode = "123456789012";
        generateBarcode(productCode, "Barcode.png", 300, 150);
    }

    public static void generateBarcode(String barcodeText, String filePath, int width, int height) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        Code128Writer barcodeWriter = new Code128Writer();
        try {
            BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height, hints);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println("Barcode generated successfully: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
