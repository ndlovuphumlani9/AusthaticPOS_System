package com.mycompany.carol_boutique.report.controller;

import com.itextpdf.text.DocumentException;
import com.mycompany.carol_boutique.report.service.ReportService;
import java.io.ByteArrayOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/report")
public class ReportController extends HttpServlet {
    private ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String period = request.getParameter("period");
        try {
            ByteArrayOutputStream pdfStream = reportService.getTopSellingProductsPDF(period);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=top_selling_products.pdf");

            OutputStream out = response.getOutputStream();
            pdfStream.writeTo(out);
            out.flush();
        } catch (SQLException | DocumentException e) {
            throw new ServletException("Error fetching report data", e);
        }
    }
}
