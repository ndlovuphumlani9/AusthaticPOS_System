package com.mycompany.carol_boutique.report.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.carol_boutique.report.dao.ReportDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class ReportService {
    private ReportDao reportDao = new ReportDao();

    public ByteArrayOutputStream getTopSellingProductsPDF(String period) throws SQLException, IOException, DocumentException {
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Date endDate = new Date(now.getTime());
        Date startDate = new Date(now.getTime());

        switch (period.toLowerCase()) {
            case "daily":
                calendar.add(Calendar.DATE, -1);
                startDate = new Date(calendar.getTime().getTime());
                break;
            case "monthly":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startDate = new Date(calendar.getTime().getTime());
                break;
            case "quarterly":
                int quarter = calendar.get(Calendar.MONTH) / 3;
                calendar.set(Calendar.MONTH, quarter * 3);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startDate = new Date(calendar.getTime().getTime());
                break;
            case "yearly":
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                startDate = new Date(calendar.getTime().getTime());
                break;
            default:
                throw new IllegalArgumentException("Invalid period specified");
        }

        ResultSet rs = reportDao.getTopSellingProducts(startDate, endDate);

        // PDF Generation using iText 5.x
        Document document = new Document();
        PdfWriter.getInstance(document, pdfStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Top Selling Products Report", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Product Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Total Quantity Sold", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        while (rs.next()) {
            table.addCell(rs.getString("productName"));
            table.addCell(String.valueOf(rs.getInt("totalQuantity")));
        }

        document.add(table);
        document.close();

        return pdfStream;
    }
}
