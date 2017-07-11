package pl.straszewski;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Created by wojtek on 2017-06-04.
 */
public interface UsersRepository extends JpaRepository<Users,Long> {

    @Query("SELECT u.salary FROM Users u where u.firstName = :firstName")
    List<Float> getUserSalaryByName(@Param("firstName") String firstName);

    @Query(value = "SELECT * FROM Users u WHERE u.pensja BETWEEN :min AND :max ORDER BY u.nazwisko ASC, " +
            "u.pensja ASC", nativeQuery = true)
    List<Users> getUsersBySalary(@Param("min") float minSalary,
                                 @Param("max") float maxSalary);

    @Query(value = "SELECT u.pensja FROM Users u ORDER BY u.pensja ASC LIMIT 1", nativeQuery = true)
    List<Float> getMinSalary();

    @Query(value = "SELECT min(u.pensja) FROM Users u", nativeQuery = true)
    List<Float> getMinSalary2();

    public Users findById(Long id);

    public List<Users> findByFirstName (String firstName);

    public List<Users> findByFirstNameAndLastName (String firstName, String lastName);
    public List<Users> findByFirstNameAndSalaryLessThan (String firstName, float salary);
    public List<Users> findByFirstNameAndSalaryBetween (String firstName, float minSalary, float maxSalary);
    public List<Users> findByFirstNameOrderBySalaryDesc (String firstName);
    public List<Users> findByFirstNameStartingWith (String firstName);
    public List<Users> findByFirstNameIn (List<String> firstName);
    public List<Users> findByFirstNameInOrderBySalary (List<String> firstName);
    public Page<Users> findBySalaryBetween (float minSalary, float maxSalary, Pageable pageable);


}
