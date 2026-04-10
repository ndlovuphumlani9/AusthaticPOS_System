package com.mycompany.carol_boutique.ibt.utilities;

import static com.mycompany.carol_boutique.customer.utilities.RatingPage.generateRatingLink;
import com.mycompany.carol_boutique.ibt.model.Item;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class Receipt {

    public static void sendEmail(String toEmail, String store, double totalAmount, List<Item> items, int saleId, int storeId) {
        String fileName = "receipt.pdf";
        System.out.println("in the email thing");
        generateReceipt(fileName, store, totalAmount, items, saleId);
        File file = new File(fileName);

        if (!file.exists()) {
            System.err.println("Error: File does not exist at path: " + fileName);
            return;
        }

        DataSource dataSource = new FileDataSource(file);
        String ratingLink = generateRatingLink(storeId);
        try {
            Email email = EmailBuilder.startingBlank()
                    .from("Carol's Boutique", "azyeki01@outlook.com")
                    .to(toEmail)
                    .withSubject("Sale Receipt")
                    .withPlainText("Dear Customer,\n\nPlease find your sale receipt attached. Rate us here " + ratingLink + ".\n\nRegards,\nCarol's Boutique")
                    .withAttachment(fileName, dataSource)
                    .buildEmail();

            Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.office365.com", 587, "azyeki01@outlook.com", "Lukhanyo1")
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .buildMailer();

            mailer.sendMail(email);
            System.out.println("Email sent successfully to " + toEmail);

            if (file.exists()) {
                file.delete();
                System.out.println("Temporary PDF file deleted: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void generateReceipt(String fileName, String store, double totalAmount, List<Item> items, int saleId) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float yPosition = yStart;
            int rowHeight = 20;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float tableHeight = yStart - 200;

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Sale Receipt");
            contentStream.endText();
            yPosition -= 20;

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText(store);
            contentStream.endText();
            yPosition -= 20;

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Date: " + dateFormat.format(date));
            contentStream.endText();
            yPosition -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Receipt #: " + saleId);
            contentStream.endText();
            yPosition -= 20;

            // Drawing table header line
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.moveTo(margin, yPosition);
            contentStream.lineTo(margin + tableWidth, yPosition);
            contentStream.stroke();
            yPosition -= rowHeight;

            // Adding items to the receipt
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Item");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 200, yPosition);
            contentStream.showText("Price");
            contentStream.endText();
            yPosition -= rowHeight;

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            for (Item item : items) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(item.getName());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 200, yPosition);
                contentStream.showText(String.valueOf(item.getPrice()));
                contentStream.endText();
                yPosition -= rowHeight;
            }

            // Total amount
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Total Amount:");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 200, yPosition);
            contentStream.showText("R " + totalAmount);
            contentStream.endText();
            yPosition -= rowHeight + 10;

            // Refund information
            String refundText = "You can get a refund within 10 days. Bring your receipt.";
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText(refundText);
            contentStream.endText();
            yPosition -= 20;

            // Drawing final line under the table
            contentStream.moveTo(margin, yPosition);
            contentStream.lineTo(margin + tableWidth, yPosition);
            contentStream.stroke();

            contentStream.close();

            document.save(new File(fileName));
            document.close();

            System.out.println("PDF receipt generated successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
