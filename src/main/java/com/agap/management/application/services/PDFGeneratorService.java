package com.agap.management.application.services;

import com.agap.management.application.ports.IExpenseService;
import com.agap.management.application.ports.IReportService;
import com.agap.management.domain.dtos.response.CropReportResponseDTO;
import com.agap.management.domain.dtos.response.ExpenseResponseDTO;
import com.agap.management.domain.entities.ProjectReport;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IProjectReportRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class PDFGeneratorService {

    private final IProjectReportRepository projectReportRepository;
    private final IReportService reportService;
    private final IExpenseService expenseService;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    private static final DecimalFormat profitabilityFormat = new DecimalFormat("0.###");


    public void exportProjectReport(Integer projectReportId, HttpServletResponse response) throws IOException {
        ProjectReport projectReport = projectReportRepository.findById(projectReportId).orElseThrow(()-> new EntityNotFoundByFieldException("ProjectReport", "id", projectReportId.toString()));
        List<CropReportResponseDTO> cropReportsByProject = reportService.findAllCropReportsByProjectId(projectReport.getProject().getId());


        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph("Reporte de Proyecto", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add project report attributes to table
        table.addCell("Ventas Totales");
        table.addCell(decimalFormat.format(projectReport.getTotalSale()));
        table.addCell("Presupuesto Total");
        table.addCell(decimalFormat.format(projectReport.getTotalBudget()));
        table.addCell("Gastos Esperados");
        table.addCell(decimalFormat.format(projectReport.getExpectedExpense()));
        table.addCell("Gastos Reales");
        table.addCell(decimalFormat.format(projectReport.getRealExpense()));
        table.addCell("Ganancias");
        table.addCell(decimalFormat.format(projectReport.getProfit()));
        table.addCell("Rentabilidad");
        table.addCell(profitabilityFormat.format(projectReport.getProfitability()));

        document.add(table);

        // Add crop reports table
        Paragraph cropReportsTitle = new Paragraph("Reporte de Cultivos", fontTitle);
        cropReportsTitle.setAlignment(Paragraph.ALIGN_CENTER);
        cropReportsTitle.setSpacingBefore(20f);
        document.add(cropReportsTitle);

        PdfPTable cropTable = new PdfPTable(7);
        cropTable.setWidthPercentage(100);
        cropTable.setSpacingBefore(10f);
        cropTable.setSpacingAfter(10f);

        // Crop table headers
        cropTable.addCell(new PdfPCell(new Paragraph("Finca", fontParagraph)));
        cropTable.addCell(new PdfPCell(new Paragraph("Ventas Totales", fontParagraph)));
        cropTable.addCell(new PdfPCell(new Paragraph("Presupuesto Asignado", fontParagraph)));
        cropTable.addCell(new PdfPCell(new Paragraph("Gastos Esperados", fontParagraph)));
        cropTable.addCell(new PdfPCell(new Paragraph("Gastos Reales", fontParagraph)));
        cropTable.addCell(new PdfPCell(new Paragraph("Ganancias", fontParagraph)));
        cropTable.addCell(new PdfPCell(new Paragraph("Rentabilidad", fontParagraph)));

        // Add crop report data to table
        for (CropReportResponseDTO cropReport : cropReportsByProject) {
            cropTable.addCell(cropReport.getCrop().getProjectApplication().getFarmName());
            cropTable.addCell(decimalFormat.format(cropReport.getTotalSale()));
            cropTable.addCell(decimalFormat.format(cropReport.getAssignedBudget()));
            cropTable.addCell(decimalFormat.format(cropReport.getExpectedExpense()));
            cropTable.addCell(decimalFormat.format(cropReport.getRealExpense()));
            cropTable.addCell(decimalFormat.format(cropReport.getProfit()));
            cropTable.addCell(profitabilityFormat.format(cropReport.getProfitability()));
        }

        document.add(cropTable);

        document.close();
    }

    public void exportExpensesReport(Integer cropId, HttpServletResponse response) throws IOException {
        List<ExpenseResponseDTO> expenseReportsByCrop = expenseService.findAllByCropId(Pageable.unpaged(), cropId).getContent();
        String farmName = expenseReportsByCrop.get(0).getCrop().getProjectApplication().getFarmName();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph(String.format("Reporte de Gastos %s", farmName), fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        PdfPTable table = new PdfPTable(3); // Adjust the number of columns
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Table headers
        table.addCell(new PdfPCell(new Paragraph("Descripción", fontParagraph)));
        table.addCell(new PdfPCell(new Paragraph("Valor", fontParagraph)));
        table.addCell(new PdfPCell(new Paragraph("Fecha", fontParagraph)));

        // Add expense report data to table
        for (ExpenseResponseDTO expense : expenseReportsByCrop) {
            table.addCell(expense.getExpenseDescription().toString());
            table.addCell(decimalFormat.format(expense.getExpenseValue()));
            table.addCell(expense.getExpenseDate().toString());
        }

        document.add(table);
        document.close();
    }
}
