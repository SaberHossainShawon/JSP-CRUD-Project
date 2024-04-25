package com.idb.Student;

import ExamPractise.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDAO {

    public static List<Student> readDataAsList() {
        List<Student> students = new ArrayList<>();
        Connection connection = config.getConnection();
        Statement state = null;
        ResultSet result = null;
        try {
            state = connection.createStatement();
            boolean Execute = state.execute("Select * from students");
            if (Execute) {
                result = state.getResultSet();
                while (result.next()) {
                    Student student = new Student();
                    student.setId(result.getInt(1));
                    student.setName(result.getString(2));
                    student.setRoll(result.getString(3));
                    student.setSubject(result.getString(4));
                    student.setGpa(result.getString(5));
                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            config.closeStatement(state, result);
        }
        return students;
    }

    public static void creat(Student student) {
        String sql = "insert into students (name,roll,subject,gpa) values (?,?,?,?)";
        Connection connection = config.getConnection();
        PreparedStatement state = null;
        try {

            state = connection.prepareStatement(sql);

            state.setString(1, student.getName());
            state.setString(2, student.getRoll());
            state.setString(3, student.getSubject());
            state.setString(4, student.getGpa());
            state.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.closeStatement(state, null);
            config.closeConnection();
        }
    }

    public static void update(Student student) {
        String sql = "update students set name = ?, roll = ?, subject = ?, gpa = ? where id = ?";
        Connection connection = config.getConnection();
        PreparedStatement state = null;
        try {
            state = connection.prepareStatement(sql);

            state.setString(1, student.getName());
            state.setString(2, student.getRoll());
            state.setString(3, student.getSubject());
            state.setString(4, student.getGpa());
            state.setInt(5, student.getId());
            int count = state.executeUpdate();
            if (count > 0) {
                System.out.println("Data Update Successfully");
            } else {
                System.out.println("Data couldn't Find that you want to update");
            }
            state.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.closeStatement(state, null);
            config.closeConnection();
        }
    }

    public static void delete(int id) {
        String sql = "delete from students where id=?";
        Connection connection = config.getConnection();
        PreparedStatement state = null;
        try {

            state = connection.prepareStatement(sql);
            state.setInt(1, id);
            int count = state.executeUpdate();
            if (count > 0) {
                System.out.println("Data delete Successfully");
            } else {
                System.out.println("Data couldn't Find that you want to delete");
            }
            state.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.closeStatement(state, null);
            config.closeConnection();
        }
    }

    public static Student findByID(int id) {
        Student student = new Student();
        Connection connection = config.getConnection();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.prepareStatement("select * from students where id = ?");
            stmt.setInt(1, id);
            boolean isExecuted = stmt.execute();
            if (isExecuted) {
                resultSet = stmt.getResultSet();
                if (resultSet.next()) {
                    student.setId(resultSet.getInt(1));
                    student.setName(resultSet.getString(2));
                    student.setRoll(resultSet.getString(3));
                    student.setSubject(resultSet.getString(4));
                    student.setGpa(resultSet.getString(5));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.closeStatement(stmt, resultSet);
        }

        return student;
    }
}
