package pl.straszewski;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wojtek on 2017-06-04.
 */
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(DatabaseConfiguration.class);
        annotationConfigApplicationContext.registerShutdownHook();
        RunAtStart bean = annotationConfigApplicationContext.getBean(RunAtStart.class);
        bean.generateUser();
        RunAtStart runAtStart = annotationConfigApplicationContext.getBean(RunAtStart.class);
       // runAtStart.getUsersBySalary(5f,10000f);
        //runAtStart.getUserById(3L);
        // runAtStart.getUsersByFirstName("Kstaion");
        //runAtStart.getUsersByFirstNameAndLastName("Thumikin", "Pawelski");
//       runAtStart.getUsersByFirstNameAndSalaryLessThan("Thumikin", 8000f);
//       runAtStart.getUsersByFirstNameAndSalaryBetween("Thumikin", 800f, 11000f );
//       runAtStart.getUsersByFirstNameAndOrderBySalary("Thumikin");
       // runAtStart.getUsersByFirstNameStartingWith("T");

//        List<String> firstNames = new ArrayList<String>();
//        firstNames.add("Thumikin");
//        firstNames.add("Kstaion");
//        runAtStart.getUsersByListOfFirstNames(firstNames);

       // runAtStart.getAll();


       // runAtStart.getUsersSalaryBetween(10f, 10000f);


    }
}
