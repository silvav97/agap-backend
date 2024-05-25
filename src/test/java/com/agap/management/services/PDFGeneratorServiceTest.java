package com.agap.management.services;

import com.agap.management.application.ports.IExpenseService;
import com.agap.management.application.ports.IReportService;
import com.agap.management.application.services.PDFGeneratorService;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IProjectReportRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PDFGeneratorServiceTest {

    @Mock
    private IProjectReportRepository projectReportRepository;

    @Mock
    private IReportService reportService;

    @Mock
    private IExpenseService expenseService;

    @InjectMocks
    private PDFGeneratorService pdfGeneratorService;

    private HttpServletResponse response;
    private ByteArrayOutputStream baos;

    @BeforeEach
    void setUp() throws IOException {
        response = mock(HttpServletResponse.class, withSettings().lenient());
        baos = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(baos);
        when(response.getOutputStream()).thenReturn(servletOutputStream);
    }

    @Test
    void testExportProjectReport_Failure_ProjectReportNotFound() {
        when(projectReportRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundByFieldException.class, () -> pdfGeneratorService.exportProjectReport(1, response));
    }

}
