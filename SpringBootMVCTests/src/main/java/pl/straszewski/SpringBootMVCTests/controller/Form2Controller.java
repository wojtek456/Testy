package pl.straszewski.SpringBootMVCTests.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.straszewski.SpringBootMVCTests.model.Employee;
import pl.straszewski.SpringBootMVCTests.repo.EmployeeRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wojtek on 2017-06-16.
 */
@MultipartConfig
@Controller
public class Form2Controller {
    @Autowired
    EmployeeRepository employeeRepository;


    @RequestMapping(value = "/Employee", method = RequestMethod.GET)
    public String EmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "newEmployeeForm";
    }

    @RequestMapping(value = "/Employee", method = RequestMethod.POST)
    public String saveNewEmployee(Employee employee, Model model, HttpServletRequest request)
            throws IOException, ServletException {

        Employee employeeEmailFromDB = employeeRepository.findByEmail(employee.getEmail());

        if (employeeEmailFromDB == null) {
            Part filePart = request.getPart("file");
            String fileName = Paths.get(filePart.getSubmittedFileName()).toString();
            InputStream fileContent = filePart.getInputStream();
            byte[] bFile = IOUtils.toByteArray(fileContent);

            if (bFile.length != 0) {
                String randomUuid = UUID.randomUUID().toString().replaceAll("-", "");
                String fileNameToDB = randomUuid + fileName;
                String savePath = "C:\\Users\\wojtek\\Documents\\git\\Testy\\SpringBootMVCTests\\src\\main\\resources" +
                        "\\static\\img\\" + fileNameToDB;
                FileUtils.writeByteArrayToFile(new File(savePath), bFile);
                employee.setImagePath(savePath);
                employee.setImageName(fileNameToDB);
                employee.setUploadedImage(true);
            }
            model.addAttribute("employee", employee);
            employeeRepository.save(employee);
            return "employeeDashboard";
        } else {
            return "alreadyExists";
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String verifyEmployeeAccount() {
        return "login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public String checkEmailAndPassword(Model model, HttpServletRequest request) {

        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, String> map = new HashMap<>();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValue = request.getParameterValues(paramName);
            map.put(paramName, paramValue[0]);
        }
        Employee employee = employeeRepository.findByEmail(map.get("email"));

        if (employee != null && employee.getPassword().equals(map.get("password"))) {
            model.addAttribute("employee", employee);
            return "employeeDashboard";

        } else {
            return "FailedVerification";
        }
    }

    @RequestMapping(value = "/employee/editing/{employeeId}", method = RequestMethod.GET)
    public String goToEditingEmployeeForm(@PathVariable("employeeId") Long id, Model model) {
        Employee employee = new Employee();
        employee = employeeRepository.getOne(id);
        model.addAttribute("employee", employee);
        return "editingEmployeeForm";
    }

    @RequestMapping(value = "/employee/editing/{employeeId}", method = RequestMethod.PUT)
    public String saveEditingEployeeToDB(@PathVariable("employeeId") Long id, Employee employeeFromWeb, Model model,
                                         HttpServletRequest request) throws IOException, ServletException {
        Employee employee = employeeRepository.getOne(id);
        employee.setFirstName(employeeFromWeb.getFirstName());
        employee.setLastName(employeeFromWeb.getLastName());
        employee.setPassword(employeeFromWeb.getPassword());
        employee.setAge(employeeFromWeb.getAge());

        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).toString();
        InputStream fileContent = filePart.getInputStream();
        byte[] bFile = IOUtils.toByteArray(fileContent);

        if (bFile.length != 0) {
            if (employee.getImagePath() != null) {
                File file = new File(employee.getImagePath());
                file.delete();
            }
            String randomUuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileNameToDB = randomUuid + fileName;
            String savePath = "C:\\Users\\wojtek\\Documents\\git\\Testy\\SpringBootMVCTests\\src\\main\\resources" +
                    "\\static\\img\\" + fileNameToDB;
            FileUtils.writeByteArrayToFile(new File(savePath), bFile);
            employee.setImagePath(savePath);
            employee.setImageName(fileNameToDB);
            employee.setUploadedImage(true);
        }

        employeeRepository.save(employee);
        model.addAttribute("employee", employee);
        return "employeeDashboard";
    }

    @RequestMapping(value = "/employee/deleting/{employeeId}", method = RequestMethod.DELETE)
    public String deleteEmployeeFromDB(@PathVariable("employeeId") Long userId, Model model) {
        model.addAttribute(employeeRepository.findOne(userId));
        Employee employee = employeeRepository.getOne(userId);
        String imagePath = employee.getImagePath();
        if (imagePath != null) {
            File file = new File(employee.getImagePath());
            file.delete();
        }
        employeeRepository.delete(userId);
        return "deletingEmployeeComplete";

    }

    @RequestMapping(value = "/employee/getFile/{employeeId}", method = RequestMethod.GET)
    public void getFile(@PathVariable("employeeId") Long id, HttpServletResponse response) throws IOException {
        Employee employee = new Employee();
        employee = employeeRepository.getOne(id);
        String imagePath = employee.getImagePath();
        File file = new File(imagePath);
        byte[] bFile = FileUtils.readFileToByteArray(file);
        // byte[] bFile = employee.getImage();
        response.addHeader("Content-Disposition", "attachment; filename=" + employee.getImageName());
        response.getOutputStream();
        response.getOutputStream().write(bFile);

    }

    @RequestMapping(value = "/employee/avatar/{employeeId}", method = RequestMethod.GET)
    public void getAvatar(@PathVariable("employeeId") Long id, HttpServletResponse response) {
        Employee employee = employeeRepository.getOne(id);
        String imagePath = employee.getImagePath();
        if (imagePath == null) {
            imagePath = "C:\\Users\\wojtek\\Documents\\git\\Testy\\SpringBootMVCTests\\src\\main\\resources" +
                    "\\static\\img\\emptyAvatar.jpg";
        }
        File file = new File(imagePath);

        try {
            byte[] bFile = FileUtils.readFileToByteArray(file);
            response.setContentType("image/jpeg");
            response.getOutputStream().write(bFile);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/employee/deletingAvatar/{employeeID}", method = RequestMethod.DELETE)
    public String deleteAvatar(@PathVariable("employeeID") Long employeeId, Model model) {
        Employee employee = employeeRepository.findOne(employeeId);
        String imagePath = employee.getImagePath();
        if (imagePath != null) {
            employee.setImagePath(null);
            employee.setImageName(null);
            employee.setUploadedImage(false);
            File file = new File(imagePath);
            file.delete();
            employeeRepository.save(employee);

        }
        model.addAttribute("employee",employee);
        return "employeeDashboard";
    }

}
