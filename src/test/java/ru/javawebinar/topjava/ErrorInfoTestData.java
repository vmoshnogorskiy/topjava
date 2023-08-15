package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.exception.ErrorInfo;

import static ru.javawebinar.topjava.util.exception.ErrorType.VALIDATION_ERROR;

public class ErrorInfoTestData {
    public static MatcherFactory.Matcher<ErrorInfo> ERROR_INFO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ErrorInfo.class, "ErrorType");

    public static final ErrorInfo errInfoUser = new ErrorInfo("http://localhost/rest/admin/users/100000", VALIDATION_ERROR, "[password] size must be between 5 and 128");
    public static final ErrorInfo errInfoProfile = new ErrorInfo("http://localhost/rest/profile", VALIDATION_ERROR, "[password] length must be between 5 and 32 characters");
    public static final ErrorInfo errInfoMeal1 = new ErrorInfo("http://localhost/rest/profile/meals/100003", VALIDATION_ERROR, "[calories] must be between 10 and 5000");
    public static final ErrorInfo errInfoMeal2 = new ErrorInfo("http://localhost/rest/profile/meals/100003", VALIDATION_ERROR, "[calories] must be between 10 and 5000");
}