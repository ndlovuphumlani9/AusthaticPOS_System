package com.mycompany.carol_boutique.employee.service;

import com.mycompany.carol_boutique.employee.dao.EmployeeDao;
import com.mycompany.carol_boutique.employee.model.Employee;

import com.mycompany.carol_boutique.verification.model.Otp;
import com.mycompany.carol_boutique.util.GuestMail;
import com.mycompany.carol_boutique.verification.dao.OtpDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;
    private OtpDao otpDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao, OtpDao otpDao) {
        this.employeeDao = employeeDao;
        this.otpDao = otpDao;
    }

    @Override
    public boolean registerEmployee(Employee employee) {
        String otpCode = generateOtpCode();
        Timestamp expiryTime = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000); // 5 minutes
//        Duration.ofMinutes(5);
// LocalDate,Localtime,LocalDateTime
        Otp otp = new Otp(employee.getEmail(), otpCode, expiryTime);
        otpDao.saveOtp(otp);
        GuestMail.sendOtpMail(employee.getEmail(), otpCode);
//cookies
        return true; // Indicate that OTP was sent successfully
    }

    @Override
    public boolean verifyOtp(String email, String otpCode, Employee employee) {
        Otp otp = otpDao.getOtpByEmail(email);
        if (otp != null && otp.getOtpCode().equals(otpCode) && otp.getExpiryTime().after(new Timestamp(System.currentTimeMillis()))) {
            otpDao.deleteOtpByEmail(email); // Remove OTP after successful verification

            // Proceed with employee registration
            //Employee employee = new Employee(); // Fetch employee data from temporary storage if needed
            String hashedPassword = doHashing(employee.getPassword());
            employee.setPassword(hashedPassword);
            boolean success = employeeDao.addEmployee(employee);
            if (success) {
                sendRegistrationEmail(employee.getEmail(), employee.getFirstName(), employee.getRole().toString(), employee.getPassword());
            }
            return success;
        }
        return false;
    }

    public boolean addManager(Employee employee) {
        String hashedPassword = doHashing(employee.getPassword());
        String unhashed = employee.getPassword();
        employee.setPassword(hashedPassword);
        boolean success = employeeDao.addManager(employee);

        if (success) {
            sendRegistrationEmail(employee.getEmail(), employee.getFirstName(), employee.getRole().toString(),unhashed);

            return true;
        } else {
            return false;
        }

    }

    @Override
    public Employee authenticate(int username, String password) {
        Employee employee = employeeDao.getEmployeeByUsername(username);
        if (employee != null && employee.getPassword().equals(doHashing(password))) {
            return employee;
        }
        throw new RuntimeException("Authentication failed");
    }

    @Override
    public boolean resetPassword(String email) {
        Employee employee = employeeDao.getEmployeeByEmail(email);
        if (employee != null) {
            String newPassword = generateRandomPassword();
            String hashedPassword = doHashing(newPassword);

            boolean success = employeeDao.updatePassword(employee.getEmployeeId(), hashedPassword);
            if (success) {
                sendPasswordResetEmail(email, newPassword);
                return true;
            }
        }
        return false;
    }

    private String generateOtpCode() {
        Random random = new Random();
        int otpCode = 100000 + random.nextInt(900000);
        return String.valueOf(otpCode);
    }

    @Override
    public String generateRandomPassword() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    public static String doHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRegistrationEmail(String toEmail, String firstName, String role, String password) {
        GuestMail.sendRegistrationMail(toEmail, firstName, role, password);
    }

    private void sendPasswordResetEmail(String toEmail, String newPassword) {
        String subject = "Password Reset";
        String body = "Dear User,\n\n"
                + "Your password has been reset. Your new password is: " + newPassword + "\n\n"
                + "Please change your password after logging in.\n\n"
                + "Regards,\nCarol's Boutique Team";

        GuestMail.sendMail(toEmail, subject, body);
    }

    @Override
    public boolean updatePassword(int employeeId, String newPassword) {
        String hashedPassword = doHashing(newPassword);
        return employeeDao.updatePassword(employeeId, hashedPassword);
    }

    @Override
    public List<Employee> getAllManagers() {
        return employeeDao.getAllManagers();
    }

    @Override
    public boolean deleteManager(int employeeId) {
        return employeeDao.deleteManager(employeeId);
    }

    @Override
    public List<Employee> getAllTellers() {

        return employeeDao.getAllTellers();
    }

//    @Override
//    public boolean addTeller(Employee employee) {
//    String hashedPassword = doHashing(employee.getPassword());
//    employee.setPassword(hashedPassword);
//    boolean success = employeeDao.addTeller(employee);
//
//    if (success) {
//        sendRegistrationEmail(employee.getEmail(), employee.getFirstName(), employee.getRole().toString());
//        return true;
//    } else {
//        return false;
//    }
}
   

