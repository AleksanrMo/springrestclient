package org.rest;


import org.rest.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {

@Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://localhost:8080/api/employees";

    public List<Employee> getAll() {
        ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Employee>>() {});
        return responseEntity.getBody();
    }

    public Employee getEmp(int id) {

    return restTemplate.getForObject(URL + "/" + id, Employee.class);
    }

    public void save(Employee employee) {
        int id = employee.getId();
        if (id == 0) {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, employee, String.class);
            System.out.println("Employee was added to DB");
            System.out.println(responseEntity.getBody());
        } else {
            restTemplate.put(URL, employee);
            System.out.println("Employee with id = " + id + " was updated");
        }
    }

    public void delete(int id) {
        restTemplate.delete(URL + "/" + id);
    }
}
