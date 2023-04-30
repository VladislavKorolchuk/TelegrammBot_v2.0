package com.example.telegrammbot.service;

import com.example.telegrammbot.entity.report.Report;
import com.example.telegrammbot.repository.report.ReportRepository;
import com.example.telegrammbot.service.report.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * The mockito tests for the daily report service
 *
 * @author zalex14
 */
@ContextConfiguration(classes = {ReportService.class})
@ExtendWith(SpringExtension.class)
public class ReportServiceTest {

    public static final Long ID = 100L;
    Report report = new Report(ID, LocalDateTime.now(), "Котенок Аиза, 6 месяцев", "Любит кит-и-кэт",
            "Шерстка здоровая", "Активно прибавляет в росте и весе", 12L, "Отчет 1");
    public static final String DIET1 = "Теперь любит вискас";
    public static final String HEALTH1 = "Хвостик пушистый";
    public static final String BEHAVIOR1 = "Любит прыгать и догонять мышку";

    @Mock
    private ReportRepository reportRepositoryMock;
    @InjectMocks
    private ReportService reportService;

    /**
     * Test to add for new report
     */
    @Test
    void shouldReturnWhenAddNewReport() {
        Mockito.when(reportRepositoryMock.save(report)).thenReturn(report);
        Report createdReport = reportService.addReport(report);

        Mockito.verify(reportRepositoryMock, Mockito.times(1)).save(report);
        Assertions.assertEquals(report.getId(), createdReport.getId());
        Assertions.assertEquals(report.getInfo(), createdReport.getInfo());
        Assertions.assertEquals(report.getDiet(), createdReport.getDiet());
        Assertions.assertEquals(report.getHealth(), createdReport.getHealth());
    }

    /**
     * Test to edit the report
     */
    @Test
    void shouldReturnWhenEditReport() {
        when(reportRepositoryMock.save(any())).thenReturn(report);
        when(reportRepositoryMock.findById(any())).thenReturn(Optional.of(report));

        Report report1 = report;
        report1.setDiet(DIET1);
        report1.setHealth(HEALTH1);
        report1.setBehavior(BEHAVIOR1);

        assertSame(report, reportService.updateReport(ID, report1));
        verify(reportRepositoryMock).save(any());
        verify(reportRepositoryMock).findById(any());
    }

    /**
     * Test to get all reports
     */
    @Test
    void shouldReturnWhenGetAllShelters() {
        ArrayList<Report> allReports = new ArrayList<>();
        when(reportRepositoryMock.findAll()).thenReturn(allReports);

        Collection<Report> actualReports = reportService.allDailyReports();

        assertSame(allReports, actualReports);
        assertTrue(actualReports.isEmpty());
        verify(reportRepositoryMock).findAll();
    }

}