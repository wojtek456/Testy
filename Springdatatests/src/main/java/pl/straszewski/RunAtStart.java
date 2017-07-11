package pl.straszewski;

import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by wojtek on 2017-06-04.
 */

@Component
public class RunAtStart {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AddressRepository addressRepository;

    public RunAtStart() {
    }

//    @PostConstruct
    public void generateUser() {
        Users user = new Users();
        List<Address> address = new ArrayList<Address>();
        Address address1 = new Address();
        Address address2 = new Address();
        address1.setStreet("sssss");
        address2.setStreet("ssaasdasd");
        address.add(address1);
        address.add(address2);
        addressRepository.save(address);

        user.setFirstName("Wojciech");
        user.setLastName("Straszewski");
        user.setSalary(1000f);
        user.setAddress(address);


        usersRepository.save(user);


    }


    @Transactional
    public void generateRandomUsers() {
        FileReader fileFirstNameReader = null;
        FileReader fileLastNamereader = null;
        try {
            fileFirstNameReader = new FileReader("C:\\Users\\wojtek\\Documents\\git\\sologo" +
                    "\\Springdatatests\\src\\main\\resources\\firstnames.txt");
            fileLastNamereader = new FileReader
                    ("C:\\Users\\wojtek\\Documents\\git\\sologo" +
                            "\\Springdatatests\\src\\main\\resources\\lastnames.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReaderFirstNames = new BufferedReader(fileFirstNameReader);
        BufferedReader bufferedReaderLastNames = new BufferedReader(fileLastNamereader);
        String currentLineName;
        try {
            while ((currentLineName = bufferedReaderFirstNames.readLine()) != null) {
                Users user = new Users();
                Random random = new Random();
                user.setFirstName(currentLineName);
                user.setLastName(bufferedReaderLastNames.readLine());
                user.setSalary(random.nextFloat() * 10000);

                usersRepository.save(user);
                System.out.println(user.getFirstName() + "--" + user.getLastName() + "--" + user.getSalary());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void getAll() {
        List<Users> usersList;

        usersList = usersRepository.findAll();
        System.out.println(usersRepository.count());
        usersRepository.delete(2L);

        for (Users user : usersList) {
            System.out.println(user.getFirstName() + "--" + user.getLastName() + "--" + user.getSalary());

        }
    }

    public void getSalaryByName(String firstName) {
        List<Float> usersList;
        usersList = usersRepository.getUserSalaryByName(firstName);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");

        } else {
            for (Float var : usersList) {
                System.out.println(var);
            }
        }
    }


    public void getUsersBySalary(float minSalary, float maxSalary) {
        List<Users> usersList;

        usersList = usersRepository.getUsersBySalary(minSalary, maxSalary);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
            System.out.println("Minimalne wynagrodzenie synosi: " + usersRepository.getMinSalary2());

        } else {
            System.out.println("Liczba wyników: " + usersList.size());
            for (Users user : usersList
                    ) {
                System.out.println(user.getFirstName() + "---" + user.getLastName() + "---" + user.getSalary());

            }
        }

    }

    @Transactional
    public void getUserById(Long id) {
        Users users = new Users();
        users = usersRepository.findById(id);
        System.out.println(users);

    }

    public void getUsersByFirstName(String firstName) {
        List<Users> usersList;
        usersList = usersRepository.findByFirstName(firstName);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
        } else {

            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
        }
    }

    public void getUsersByFirstNameAndLastName(String firstName, String lastName) {
        List<Users> usersList;
        usersList = usersRepository.findByFirstNameAndLastName(firstName, lastName);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
        } else {

            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
        }
    }

    public void getUsersByFirstNameAndSalaryLessThan(String firstName, float salary) {
        List<Users> usersList;
        usersList = usersRepository.findByFirstNameAndSalaryLessThan(firstName, salary);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
        } else {

            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
        }
    }


    public void getUsersByFirstNameAndSalaryBetween(String firstName, float minSalary, float maxSalary) {
        List<Users> usersList;
        usersList = usersRepository.findByFirstNameAndSalaryBetween(firstName, minSalary, maxSalary);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
        } else {

            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
        }
    }

    public void getUsersByFirstNameAndOrderBySalary(String firstName) {
        List<Users> usersList;
        usersList = usersRepository.findByFirstNameOrderBySalaryDesc(firstName);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
        } else {

            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
        }
    }

    public void getUsersByFirstNameStartingWith(String firstName) {
        List<Users> usersList;
        usersList = usersRepository.findByFirstNameStartingWith(firstName);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
        } else {

            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
        }
    }

    public void getUsersByListOfFirstNames(List<String> listWithNames) {
        List<Users> usersList;
        usersList = usersRepository.findByFirstNameInOrderBySalary(listWithNames);
        if (usersList.isEmpty() == true) {
            System.out.println("Brak wyników dla tego zapytania");
        } else {

            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
        }
    }

public Page<Users> getPageWithUsers(float minSalary,float maxSalary,int numerStrony){
    Page<Users> pages;
            pages = usersRepository.findBySalaryBetween(
                    minSalary,
                   maxSalary,
                    new PageRequest(numerStrony, 10));
            return pages;
}

    public void getUsersSalaryBetween(float minSalary, float maxSalary) {
        int numerStrony = 0;
        Page<Users> pages;
        List<Users> usersList;
        do {
            pages = usersRepository.findBySalaryBetween(
                    minSalary,
                    maxSalary,
                    new PageRequest(numerStrony, 10));
            usersList = pages.getContent();
            if(pages.hasContent()){
                System.out.println("Strona numer: " + numerStrony);
            for (Users user : usersList
                    ) {
                System.out.println(user);
            }
            numerStrony += 1;}
            else break;
        } while (pages.hasContent());


    }


    @PreDestroy
    public void destroyBean() {
        System.out.println("Destroyed bean");
    }
}
