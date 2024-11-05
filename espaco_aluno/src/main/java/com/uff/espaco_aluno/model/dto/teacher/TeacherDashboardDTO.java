package com.uff.espaco_aluno.model.dto.teacher;

import java.util.Map;

public class TeacherDashboardDTO {
    private int totalClasses;
    private int conductedClasses;
    private double attendanceAverage;
    private double classAverage;
    private Map<String, Long> gradeDistribution;
    private int totalStudents;
    private int approvedStudents;
    private double approvalRate;
    private int remainingClasses;

    public TeacherDashboardDTO(int totalClasses, int conductedClasses, double attendanceAverage,
                               double classAverage, Map<String, Long> gradeDistribution, int totalStudents,
                               int approvedStudents, double approvalRate, int remainingClasses) {
        this.totalClasses = totalClasses;
        this.conductedClasses = conductedClasses;
        this.attendanceAverage = attendanceAverage;
        this.classAverage = classAverage;
        this.gradeDistribution = gradeDistribution;
        this.totalStudents = totalStudents;
        this.approvedStudents = approvedStudents;
        this.approvalRate = approvalRate;
        this.remainingClasses = remainingClasses;
    }

    // Getters e Setters

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getConductedClasses() {
        return conductedClasses;
    }

    public void setConductedClasses(int conductedClasses) {
        this.conductedClasses = conductedClasses;
    }

    public double getAttendanceAverage() {
        return attendanceAverage;
    }

    public void setAttendanceAverage(double attendanceAverage) {
        this.attendanceAverage = attendanceAverage;
    }

    public double getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(double classAverage) {
        this.classAverage = classAverage;
    }

    public Map<String, Long> getGradeDistribution() {
        return gradeDistribution;
    }

    public void setGradeDistribution(Map<String, Long> gradeDistribution) {
        this.gradeDistribution = gradeDistribution;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getApprovedStudents() {
        return approvedStudents;
    }

    public void setApprovedStudents(int approvedStudents) {
        this.approvedStudents = approvedStudents;
    }

    public double getApprovalRate() {
        return approvalRate;
    }

    public void setApprovalRate(double approvalRate) {
        this.approvalRate = approvalRate;
    }

    public int getRemainingClasses() {
        return remainingClasses;
    }

    public void setRemainingClasses(int remainingClasses) {
        this.remainingClasses = remainingClasses;
    }
}

