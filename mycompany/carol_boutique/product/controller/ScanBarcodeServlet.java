package com.mycompany.carol_boutique.product.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet(name = "ScanBarcodeServlet", urlPatterns = {"/ScanBarcodeServlet"})
public class ScanBarcodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.QVGA.getSize());
        webcam.open();
        BufferedImage image = webcam.getImage();
        webcam.close();

        String decodedText = decodeBarcode(image);
        request.setAttribute("barcode", decodedText);
        request.getRequestDispatcher("scan.jsp").forward(request, response);
    }

    private String decodeBarcode(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
