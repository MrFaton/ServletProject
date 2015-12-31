package com.nixsolutions.ponarin.tag;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.dao.impl.JdbcUserDao;
import com.nixsolutions.ponarin.entity.User;

public class UsersTableTag extends SimpleTagSupport {
    private String group;
    private List<User> userList;
    private UserDao userDao = new JdbcUserDao();

    @Override
    public void doTag() throws JspException, IOException {
        userList = userDao.findAll();

        PageContext pageContext = (PageContext) getJspContext();
        String editControllerPath = pageContext.getServletContext()
                .getContextPath() + "/admin/edit.do";
        JspWriter out = pageContext.getOut();
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("<table>");
        strBuilder.append("<tr>");

        strBuilder.append("<th>Login</th>");
        strBuilder.append("<th>First Name</th>");
        strBuilder.append("<th>Last Name</th>");
        strBuilder.append("<th>Age</th>");
        strBuilder.append("<th>Role</th>");
        strBuilder.append("<th>Actions</th>");

        strBuilder.append("</tr>");

        for (User user : userList) {
            strBuilder.append("<tr>");
            strBuilder.append("<td>" + user.getLogin() + "</td>");
            strBuilder.append("<td>" + user.getFirstName() + "</td>");
            strBuilder.append("<td>" + user.getLastName() + "</td>");
            strBuilder.append("<td>" + getAge(user.getBirthDay()) + "</td>");
            strBuilder.append("<td>" + user.getRole().getName() + "</td>");

            strBuilder.append("<td>");
            strBuilder.append("<form action=\"" + editControllerPath
                    + "\" method=\"post\">");
            strBuilder.append("<div>");
            strBuilder.append(
                    "<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
            strBuilder.append(
                    "<input type=\"hidden\" name=\"edit\" value=\"true\"/>");
            strBuilder.append("<input type=\"hidden\" name=\"login\" value=\""
                    + user.getLogin() + "\"/>");
            strBuilder.append("<input type=\"submit\" value=\"Edit\"/>");
            strBuilder.append("</div>");
            strBuilder.append("</form>");

            strBuilder.append(
                    "<form action=\"" /* + Constants.SERVLET_CREATE_EDIT */
                            + "\" method=\"post\">");
            strBuilder.append("<div>");
            strBuilder.append(
                    "<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
            strBuilder.append("<input type=\"hidden\" name=\"login\" value=\""
                    + user.getLogin() + "\"/>");
            strBuilder.append("<input type=\"submit\" value=\"Delete\"/>");
            strBuilder.append("</div>");
            strBuilder.append("</form>");

            strBuilder.append("</tr>");
        }

        strBuilder.append("</table>");
        out.println(strBuilder.toString());

    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private int getAge(Date birthDay) {
        long currentDateInMillis = System.currentTimeMillis();
        long birthDayInMills = birthDay.getTime();
        long difference = currentDateInMillis - birthDayInMills;
        return (int) difference / (1000 * 60 * 60 * 24 * 30 * 12);
    }

}
