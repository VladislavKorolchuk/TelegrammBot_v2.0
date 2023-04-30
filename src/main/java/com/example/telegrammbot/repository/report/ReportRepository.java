package com.example.telegrammbot.repository.report;

import js6team3.tbot.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Implement report access layer for daily reports
 *
 * @author zalex14
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
}