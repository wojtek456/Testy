package pl.straszewski.SpringBootMVCTests.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.straszewski.SpringBootMVCTests.model.Employee;

import java.util.List;

/**
 * Created by wojtek on 2017-06-16.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Employee findByEmail(String email);
    public List<Employee> findByImageName(String imageName);
}
