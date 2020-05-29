package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received reportingStructureService create request for id [{}]", id);

        ReportingStructure reportingStructure = new ReportingStructure();

        //get the employee that is at the top of the requested reporting structure
        reportingStructure.setEmployee(employeeService.read(id));
        
        // check that there are any reports to the relevant employee before counting
        if (reportingStructure.getEmployee().getDirectReports() == null) {
            reportingStructure.setNumberOfReports(0);
        }
        else {
            reportingStructure.setNumberOfReports(countReports(reportingStructure.getEmployee()));
        }

        return reportingStructure;
    }

    // return the number of employees that report to the head of the reporting structure, directly or indirectly
    public int countReports(Employee head) {
        int reportCount = head.getDirectReports().size(); // initialize report count to head's direct reports
        List<String> reportingEmployees = new ArrayList<String>(); // employee ids that will have their direct reports counted

        // add the head's direct report's ids to reportingEmployee list
        for (int i = 0; i < head.getDirectReports().size(); i++) {
            reportingEmployees.add(head.getDirectReports().get(i).getEmployeeId());
        }

        // count indirect reports to head
        while (reportingEmployees.size() > 0) {
            // get the current employee, removing it from reportingEmployees
            Employee currentEmployee = employeeService.read(reportingEmployees.remove(0));

            // if the current employee has reports, add that much to the count and their ids to the bookkeeping structure
            if(currentEmployee.getDirectReports() != null) {
                reportCount += currentEmployee.getDirectReports().size();
                
                for (Employee currentDirectReport : currentEmployee.getDirectReports()) {
                    reportingEmployees.add(currentDirectReport.getEmployeeId());
                }
            }
        }       
        return reportCount;
    }
}
