package ru.vsu.cs.nsavchenko.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.vsu.cs.nsavchenko.dao.TaskDAO;
import ru.vsu.cs.nsavchenko.model.Task;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO = new TaskDAO();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String studentId = request.getParameter("studentId");
        if (studentId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Student ID is required");
            return;
        }

        try {
            List<Task> tasks = taskDAO.getTasksByStudentId(Long.parseLong(studentId));
            mapper.writeValue(response.getWriter(), tasks);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Long taskId = Long.parseLong(pathInfo.substring(1));
            JsonNode node = mapper.readTree(request.getReader());
            boolean completed = node.get("completed").asBoolean();
            
            taskDAO.updateTaskStatus(taskId, completed);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
} 