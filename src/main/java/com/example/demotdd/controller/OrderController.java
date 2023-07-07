package com.example.demotdd.controller;

import com.example.demotdd.model.TddOrder;
import com.example.demotdd.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    //AUTO INJECT INSTANCE OF ORDERREPOSITORY INTO THE CONSTRUCTOR
    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //MAP AN HTTP POST REQUEST TO CREATEORDER METHOD
    @PostMapping
    public ResponseEntity<TddOrder> createOrder(@RequestBody @Valid TddOrder order) {
        TddOrder createdOrder = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    //MAP AN HTTP GET REQUEST TO GETORDERBYID METHOD, RETRIEVE ORDER BY ID
    @GetMapping("/{orderId}")
    public ResponseEntity<TddOrder> getOrderById(@PathVariable Long orderId) {
        Optional<TddOrder> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            TddOrder order = optionalOrder.get();
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //METHOD UPDATES AN EXISTING ORDER BY ITS ID
    @PutMapping("/{orderId}")
    public ResponseEntity<TddOrder> updateOrder(@PathVariable Long orderId, @RequestBody TddOrder order) {
        Optional<TddOrder> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            TddOrder existingOrder = optionalOrder.get();
            existingOrder.setCustomerName(order.getCustomerName());
            existingOrder.setOrderDate(order.getOrderDate());
            existingOrder.setShippingAddress(order.getShippingAddress());
            existingOrder.setTotal(order.getTotal());

            TddOrder updatedOrder = orderRepository.save(existingOrder);
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //METHOD DELETES AN ORDER BY ITS ID
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        Optional<TddOrder> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            orderRepository.delete(optionalOrder.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

//ORDERCONTROLLER-HANDLE HTTP REQUESTS

//OPTIONAL<TDDORDER>-USED WHEN SEARCHING FOR AN ORDER IN REPOSITORY
//OPTIONALORDER.ISPRESENT()-TO CHECK IF THERE IS AN ORDER
//IF THERE IS AN ORDER-WE GET IT BY USING OPTIONALORDER.GET()
//USING OPTIONAL CLASS HELPS PREVENT ERRORS AND ENSURES WE HANDLE CASES WHERE THERE MAY BE NO VALUE. AVOIDING NULL REFERENCES

