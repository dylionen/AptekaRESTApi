package apteka.functionality;

import apteka.tables.WHMList;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

public class PDFCreator {
    private java.util.List<WHMList> WHMData;
    private PdfPTable table;
    private Document document;
    private byte[] documentByte = null;
    private ByteArrayOutputStream byteArrayOutputStream;
    private String reportOfDate;


    public PDFCreator(java.util.List<WHMList> whmList, String date) {
        WHMData = whmList;
        table = new PdfPTable(6);
        reportOfDate = date;
        byteArrayOutputStream = new ByteArrayOutputStream();
        makePDF();
        documentByte = byteArrayOutputStream.toByteArray();
    }


    public void makePDF() {
        document = new Document();
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK);

        try {
            // PdfWriter.getInstance(document, new FileOutputStream("Article" +
            //     WHMData.get(0).getIdArticle().getIdArticle()+ ".pdf"));

            PdfWriter.getInstance(document, byteArrayOutputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.open();

        try {
            document.add(new Paragraph("Article id: " + WHMData.get(0).getIdArticle().getIdArticle(), font));
            document.add(new Paragraph("Article name: " + WHMData.get(0).getIdArticle().getName(), font));
            document.add(new Paragraph("Warehouse ID: " + WHMData.get(0).getIdWHM().getIdLocalization().getIdLocalization(), font));
            document.add(new Paragraph("Warehouse Name: " + WHMData.get(0).getIdWHM().getIdLocalization().getName(), font));
            document.add(new Paragraph("Raport na dzień: " + reportOfDate, font));
            makeTable();
            document.add(new Paragraph("Dokument utworzono dnia: " + new Date().toString()));
            document.add(Chunk.NEWLINE);
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

    }

    void makeTable() {
        addTableHeader(table);
        for (WHMList element : WHMData) {
            addRows(table, element);
        }
    }

    private void addRows(PdfPTable table, WHMList whmElement) {
        table.addCell(whmElement.getIdWHM().getNumberString().isEmpty() ? "ASD" : whmElement.getIdWHM().getNumberString());
        table.addCell(String.valueOf(whmElement.getValue()));
        table.addCell(String.valueOf(whmElement.getPrice()));
        table.addCell(String.valueOf(whmElement.getPriceB()));
        table.addCell(whmElement.getIdWHM().getIdTypeWHM().getValue());
        table.addCell(String.valueOf(whmElement.getIdWHM().getCreatedDate()));
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Nazwa dokumentu", "Ilosc", "Cena Netto", "Cena Brutto", "Typ", "Data utworzenia")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public byte[] getDocumentByte() {
        return documentByte;
    }

    public boolean isDocumentByte() {
        if (documentByte == null) {
            return false;
        } else {
            return false;
        }
    }

}


