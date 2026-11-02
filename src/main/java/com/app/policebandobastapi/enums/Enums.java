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

    public enum noticeStatus {
        ISSUED, CLOSED
    }
}
