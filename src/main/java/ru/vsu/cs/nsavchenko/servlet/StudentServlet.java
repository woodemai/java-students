package ru.vsu.cs.nsavchenko.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.vsu.cs.nsavchenko.dao.StudentDAO;
import ru.vsu.cs.nsavchenko.model.Student;

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                // Получить всех студентов
                List<Student> students = studentDAO.getAllStudents();
                mapper.writeValue(response.getWriter(), students);
            } else {
                // Получить конкретного студента по ID
                Long studentId = Long.valueOf(pathInfo.substring(1));
                Student student = studentDAO.getStudentById(studentId);
                if (student != null) {
                    mapper.writeValue(response.getWriter(), student);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Student student = mapper.readValue(request.getReader(), Student.class);
        try {
            studentDAO.addStudent(student);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException | ClassNotFoundException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
