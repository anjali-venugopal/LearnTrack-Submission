package com.airtribe.learntrack.constants;

public final class MenuOptions {
    public static final int EXIT = 0;
    public static final int BACK = 0;

    public static final int MAIN_STUDENT_MANAGEMENT = 1;
    public static final int MAIN_COURSE_MANAGEMENT = 2;
    public static final int MAIN_ENROLLMENT_MANAGEMENT = 3;

    public static final int STUDENT_ADD = 1;
    public static final int STUDENT_VIEW_ALL = 2;
    public static final int STUDENT_SEARCH_BY_ID = 3;
    public static final int STUDENT_UPDATE = 4;
    public static final int STUDENT_DEACTIVATE = 5;

    public static final int COURSE_ADD = 1;
    public static final int COURSE_VIEW_ALL = 2;
    public static final int COURSE_ACTIVATE = 3;
    public static final int COURSE_DEACTIVATE = 4;

    public static final int ENROLLMENT_ENROLL = 1;
    public static final int ENROLLMENT_VIEW_BY_STUDENT = 2;
    public static final int ENROLLMENT_VIEW_ALL = 3;
    public static final int ENROLLMENT_MARK_COMPLETED = 4;
    public static final int ENROLLMENT_MARK_CANCELLED = 5;

    private MenuOptions() {
    }
}
