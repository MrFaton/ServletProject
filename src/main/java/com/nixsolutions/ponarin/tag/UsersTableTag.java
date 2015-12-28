package com.nixsolutions.ponarin.tag;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.dao.impl.JdbcUserDao;
import com.nixsolutions.ponarin.entity.User;

public class UsersTableTag extends SimpleTagSupport {
    private String aligin;
    private int border;
    private int cellpadding;
    private int cellspacing;
    private List<User> userList;
    private UserDao userDao = new JdbcUserDao();

    @Override
    public void doTag() throws JspException, IOException {
        userList = userDao.findAll();
        
        PageContext pageContext = (PageContext) getJspContext();
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
            strBuilder.append("<td>").append(user.getLogin()).append("</td>");
            strBuilder.append("<td>").append(user.getFirstName()).append("</td>");
            strBuilder.append("<td>").append(user.getLastName()).append("</td>");
            strBuilder.append("<td>").append(getAge(user.getBirthDay())).append("</td>");
            strBuilder.append("<td>").append(user.getRole().getName()).append("</td>");
            
            strBuilder.append("<td>");
            
        }
        
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            strBuilder.append("<tr>");
            strBuilder.append("<td>" + user.getLogin() + "</td>");
            strBuilder.append("<td>" + user.getEmail() + "</t"
                    + ""
                    + ""
                    + ""
                    
                    
                    strBuilder.append("</td>");
            strBuilder.append("</tr>");
        }
        strBuilder.append("</table>");      
        writer.println(strBuilder.toString());
        
    }

    public void setAligin(String aligin) {
        this.aligin = aligin;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public void setCellpadding(int cellpadding) {
        this.cellpadding = cellpadding;
    }

    public void setCellspacing(int cellspacing) {
        this.cellspacing = cellspacing;
    }

    private int getAge(Date birthDay) {
        long currentDateInMillis = System.currentTimeMillis();
        long birthDayInMills = birthDay.getTime();
        long difference = currentDateInMillis - birthDayInMills;
        return (int) difference / (1000 * 60 * 60 * 24 * 30 * 12);
    }

}
