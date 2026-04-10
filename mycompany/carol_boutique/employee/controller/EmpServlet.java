package com.mycompany.carol_boutique.employee.controller;

import com.mycompany.carol_boutique.employee.dao.EmployeeDaoImpl;
import com.mycompany.carol_boutique.employee.model.Employee;
import com.mycompany.carol_boutique.employee.model.Role;
import com.mycompany.carol_boutique.employee.service.EmployeeService;
import com.mycompany.carol_boutique.employee.service.EmployeeServiceImpl;

import com.mycompany.carol_boutique.util.GuestMail;
import com.mycompany.carol_boutique.verification.dao.OtpDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EmpServlet", urlPatterns = {"/EmpServlet"})
public class EmpServlet extends HttpServlet {

    private EmployeeService employeeService;
    Employee employee;

    @Override
    public void init() throws ServletException {
        super.init();
        this.employeeService = new EmployeeServiceImpl(new EmployeeDaoImpl(), new OtpDaoImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleListManagers(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "register":
                    handleRegister(request, response);
                    break;
                case "verifyOtp":
                    handleVerifyOtp(request, response);
                    break;
                case "login":
                    handleLogin(request, response);
                    break;
                case "addManager":
                    handleAddManager(request, response);
                    break;
//                case "addTeller":
//                    handleAddTeller(request, response);
//                    break;
                case "forgotPassword":
                    handleForgotPassword(request, response);
                    break;
                case "changePassword":
                    handleChangePassword(request, response);
                    break;
                case "deleteManager":
                    handleDeleteManager(request, response);
                    break;

//                case "listManagers":
//                    handleListManagers(request, response);
//                    break;    
                default:
                    response.sendRedirect("login.jsp");
                    break;
            }
        } else {
            response.sendRedirect("login.jsp");
        }

    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Role role = Role.valueOf(request.getParameter("role").toUpperCase());
        String email = request.getParameter("email").toLowerCase();
        String phone = request.getParameter("phone").toLowerCase();
        String password = request.getParameter("password");
        Integer storeId = 0;

        Employee employee = new Employee(firstName, lastName, role, email, phone, password, storeId);
        this.employee = employee;

        boolean otpSent = employeeService.registerEmployee(employee);

        if (otpSent) {
            request.setAttribute("email", email);
            request.getRequestDispatcher("/verifyOtp.jsp").forward(request, response);
        } else {
            response.sendRedirect("register.jsp");
        }
    }

    private void handleVerifyOtp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String otpCode = request.getParameter("otpCode");

        boolean verified = employeeService.verifyOtp(email, otpCode, this.employee);
        if (verified) {
            request.setAttribute("successMessage", "Registration successful!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Invalid or expired OTP. Please try again.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/verifyOtp.jsp").forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usernameParam = request.getParameter("username");
        int employeeId = 0;
        if (usernameParam != null && !usernameParam.isEmpty()) {
            employeeId = Integer.parseInt(usernameParam);
        }
        String password = request.getParameter("password");

        try {
            Employee employee = employeeService.authenticate(employeeId, password);

            if (employee != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("employee", employee);

                if (Role.ADMIN.equals(employee.getRole())) {
                    response.sendRedirect("dashboard.jsp");
                } else if (Role.MANAGER.equals(employee.getRole())) {
                    response.sendRedirect("managerDashboard.jsp");
                } else if (Role.TELLER.equals(employee.getRole())) {
                    response.sendRedirect("home.jsp");
                } else {
                    response.sendRedirect("login.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username or password."); // Set error message
                request.setAttribute("username", usernameParam); // Retain username input
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid username or password."); // Handle invalid username format
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", "Invalid username or password. Please try again"); // Handle other exceptions
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void handleAddManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = (Employee) request.getSession(false).getAttribute("employee");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = employeeService.generateRandomPassword();
        int storeId = 0;
        try {
            storeId = Integer.parseInt(request.getParameter("storeId"));
        } catch (NumberFormatException e) {
            storeId = employee.getStoreId();
        }
        Role role = null;

        if (employee.getRole() == Role.MANAGER) {
            role = Role.TELLER;
        } else if (employee.getRole() == Role.ADMIN) {
            role = Role.MANAGER;
        }

        Employee manager = new Employee(firstName, lastName, role, email, phone, password, storeId);
        boolean success = employeeService.addManager(manager);
        if (success) {
            if (employee.getRole() == Role.MANAGER) {
                request.getRequestDispatcher("/managerDashboard.jsp").forward(request, response);
            } else if (employee.getRole() == Role.ADMIN) {
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            }
        }
    }

    private void handleForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        boolean success = employeeService.resetPassword(email);

        if (success) {
            response.sendRedirect("login.jsp?reset=true");
        } else {
            response.sendRedirect("forgotPassword.jsp?error=true");
        }
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Employee loggedInEmployee = (Employee) session.getAttribute("employee");

        if (loggedInEmployee != null) {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "New passwords do not match.");
                request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                return;
            }

            // Verify current password
            Employee authenticatedEmployee = employeeService.authenticate(loggedInEmployee.getEmployeeId(), currentPassword);

            if (authenticatedEmployee == null) {
                request.setAttribute("errorMessage", "Current password is incorrect.");
                request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
            } else {
                // Update password
                boolean passwordUpdated = employeeService.updatePassword(loggedInEmployee.getEmployeeId(), newPassword);

                if (passwordUpdated) {
                    request.setAttribute("successMessage", "Password updated successfully.");
                    request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Failed to update password. Please try again.");
                    request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                }
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private void handleListManagers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> managers = employeeService.getAllManagers();
        request.setAttribute("managers", managers);
        request.getRequestDispatcher("/listManagers.jsp").forward(request, response);
    }

    private void handleDeleteManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));

        boolean success = employeeService.deleteManager(employeeId);

        if (success) {
            request.setAttribute("successMessage", "Manager deleted successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to delete manager. Please try again.");
        }

        handleListManagers(request, response);
    }

//    // Assuming you have a method in the servlet to handle form submissions for adding employees
//    protected void handleAddTeller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        String password = request.getParameter("password");
//        int storeId = Integer.parseInt(request.getParameter("storeId"));
//        Role role = Role.TELLER;
//
//        // Create Employee object
//        Employee employee = new Employee(firstName, lastName,role, email, phone, password, storeId);
//        boolean success = employeeService.addTeller(employee);
//
//        // Handle response
//        if (success) {
//            response.sendRedirect("addTeller.jsp");
//        } else {
//            response.sendRedirect("errorPage.jsp");
//        }
//    }
}
