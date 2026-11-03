package com.app.policebandobastapi.enums;

public final class Enums {

    private Enums() {
    }

    public enum Role {
        SUPER_ADMIN, HEADQUARTER, POLICE_STATION, SENIOR_OFFICER, STAFF
    }

    public enum RecordStatus {
        ACTIVE, INACTIVE
    }

    public enum ShiftType {
        DAY, NIGHT, FULL_DAY_24HR
    }

    public enum ForceCategory {
        SR_OFFICER, DCP, SR_PI, PI, API, HC, PC_MALE, PC_FEMALE, TRAFFIC, GENERAL_FORCE, HOME_GUARD, WARDEN
    }

    public enum BandobastStatus {
        PENDING, APPROVED, REJECTED, COMPLETED
    }

    public enum ForceResponseStatus {
        PENDING, APPROVED, PARTIAL, REJECTED,
    }

    public enum DocumentType {
        DOCUMENT, ORDER, OTHER
    }

    public enum TemplateType {
        REJECTION_REASON, APPROVAL_REMARK
    }

    public enum AttendanceStatus {
        PENDING_TODAY, PRESENT, CHECKED_OUT, ABSENT,
    }

    public enum GpsStatus {
        TAGGED, NO_GPS, OUT_OF_RANGE
    }

    public enum noticeStatus {
        ISSUED, CLOSED
    }

    public enum NoticeDecision {
        SATISFACTORY_CLOSED, WARNING_CLOSED, ONE_DAY_PAY_CUT, PRELIMINARY_INQUIRY, DEPARTMENTAL_INQUIRY
    }

    public enum NoticeReason {
        ABSENT_ON_DUTY, LATE_ARRIVAL, LEFT_POINT, DERELICTION, MISCONDUCT, OTHER
    }

    public enum GpsTagMode {
        OPTIONAL, REQUIRED, OFF
    }

    public enum InkColor {
        BLUE, BLACK
    }
}
