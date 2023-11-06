package com.stephane.firstApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private  final  CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }
    record  NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }


    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);


    }

    @PutMapping("{id}")
    public void updateCustomer (@PathVariable Integer id,
                                @RequestBody NewCustomerRequest data ){
        customerRepository.findById(id).map((custoner) -> {
            custoner.setAge(data.age);
            custoner.setEmail(data.email);
            custoner.setName(data.name);
            return customerRepository.save(custoner);
        });


    }
    @DeleteMapping("{customerId}")
    public  void deleteCustomer(@PathVariable("customerId") Integer id){
         customerRepository.deleteById(id);
    }
}
