package com.mindex.challenge.data;

import java.util.List;
import java.util.Date;

public class Compensation {
    private Employee employee;
    private double salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public Compensation(Compensation compensation) {
        this.employee = compensation.employee;
        this.salary = compensation.salary;
        this.effectiveDate = compensation.effectiveDate;
    }
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    // set the date to right now
    public void setEffectiveDate() {
        this.effectiveDate = new Date();
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
