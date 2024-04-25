/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.idb.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shawon
 */
@WebServlet(name = "StudentServlet", urlPatterns = {"/student/*", "/student"})
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null) {
            if (path.endsWith("find")) {
                finestudent(request, response);
            } else if (path.endsWith("edit")) {
                goToForm(request, response);
            }else if (path.endsWith("form")) {
                goToForm(request, response);
            } else if (path.endsWith("delete")) {
                delete(request, response);
            } else {
                goToList(request, response);
            }
        } else {
            goToList(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String roll = request.getParameter("roll");
        String subject = request.getParameter("subject");
        String gpa = request.getParameter("gpa");

        Student student = new Student();
        student.setName(name);
        student.setRoll(roll);
        student.setSubject(subject);
        student.setGpa(gpa);
        if (id != null && id.length() > 0 && !id.equals("0")) {
            student.setId(Integer.parseInt(id));
            StudentDAO.update(student);
        } else {
            StudentDAO.creat(student);
        }
        response.sendRedirect(request.getContextPath() + "/student");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void finestudent(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            int sid = Integer.parseInt(id);
            Student student = StudentDAO.findByID(sid);
            response.getOutputStream().write(student.toString().getBytes());
        } catch (Exception e) {

        }
    }

    private void goToForm(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {

            Student student = new Student();
            if (id != null) {
                int sid = Integer.parseInt(id);
                student = StudentDAO.findByID(sid);
            }
            request.setAttribute("student", student);
            request.getRequestDispatcher("/student_form.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
       String id = request.getParameter("id");
        try {
            if (id != null) {
                int sid = Integer.parseInt(id);
                StudentDAO.delete(sid);
            }
            goToList(request, response);
        } catch (Exception e) {
        }

    }

    private void goToList(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            List<Student> students = StudentDAO.readDataAsList();
            request.setAttribute("students", students);
            request.getRequestDispatcher("/student_list.jsp").forward(request, response);
        } catch (IOException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
