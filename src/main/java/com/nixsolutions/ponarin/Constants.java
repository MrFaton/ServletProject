package com.nixsolutions.ponarin;

public abstract class Constants {
    public static final String PAGE_MAIN = "/";
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_LOGOUT = "/logout.jsp";
    public static final String PAGE_ADMIN = "/admin/admin.jsp";
    public static final String PAGE_CREATE_UPDATE_USER = "/admin/create_update_user.jsp";
    public static final String PAGE_USER = "/user/user.jsp";
    public static final String PAGE_ERROR = "/error.jsp";
    
    public static final String ATTR_USER = "user";
    public static final String ATTR_USER_FORM = "user_form";
    public static final String ATTR_ERROR_MESSAGE_TITLE = "error_message_title";
    public static final String ATTR_ERROR_MESSAGE = "error_message";
    
    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_PASSWORD = "password";
    
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
}
