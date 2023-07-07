package com.example.demotdd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class TddOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Customer name required")
    private String customerName;

    @NotNull(message = "Order date required")
    private LocalDate orderDate;

    @NotEmpty(message = "Shipping address required")
    private String shippingAddress;

    @Positive(message = "Total must be positive")
    private Double total;


//VALIDATIONS:
//@NotEmpty ANNOTATION- ENSURE CUSTOMER NAME IS AND SHIP ADDRESS ARE NOT EMPTY
//@NotNull ANNOTATION- ENSURE THAT ORDERDATE IS NOT NULL
//@Positive ANNOTATION- ENSURE TOTAL IS A POSITIVE VALUE


        // GETTER SETTERS----------------
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }


        public String getCustomerName() {
                return customerName;
            }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public LocalDate getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }
    }