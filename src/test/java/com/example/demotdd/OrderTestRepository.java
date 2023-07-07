package com.example.demotdd;

import com.example.demotdd.model.TddOrder;
import com.example.demotdd.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class OrderTestRepository {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        // CREATING A NEW ORDER OBJECT, SAVING TO REPO---------
        TddOrder order = new TddOrder();
        order.setCustomerName("Tommy Thomas");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("999 Something St");
        order.setTotal(20.0);

        TddOrder savedOrder = orderRepository.save(order);
        //ASSERT THAT ORDER IS SAVED
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals(order.getCustomerName(), savedOrder.getCustomerName());
        Assertions.assertEquals(order.getOrderDate(), savedOrder.getOrderDate());
        Assertions.assertEquals(order.getShippingAddress(), savedOrder.getShippingAddress());
        Assertions.assertEquals(order.getTotal(), savedOrder.getTotal());
    }

    @Test
    public void testFindOrderById() {
        TddOrder order = new TddOrder();
        order.setCustomerName("Kelly Kelli");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("888 Somebody St");
        order.setTotal(40.0);

        TddOrder savedOrder = orderRepository.save(order);

        Optional<TddOrder> foundOrder = orderRepository.findById(savedOrder.getId());

        Assertions.assertNotNull(foundOrder);
        assertTrue(foundOrder.isPresent());
        assertEquals(savedOrder.getId(), foundOrder.get().getId());
        assertEquals("Kelly Kelli", foundOrder.get().getCustomerName());
        assertEquals(LocalDate.now(), foundOrder.get().getOrderDate());
        assertEquals("888 Somebody St", foundOrder.get().getShippingAddress());
        assertEquals(40.0, foundOrder.get().getTotal());
    }

    @Test
    public void testUpdateOrder() {
        TddOrder order = new TddOrder();
        order.setCustomerName("Tommy Thomas");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("999 Something St");
        order.setTotal(20.0);

        TddOrder savedOrder = orderRepository.save(order);

        savedOrder.setCustomerName("Kelly Kelli");
        savedOrder.setShippingAddress("888 Somebody St");
        savedOrder.setTotal(40.0);
        TddOrder updatedOrder = orderRepository.save(savedOrder);

        Assertions.assertEquals(savedOrder.getId(), updatedOrder.getId());
        Assertions.assertEquals("Kelly Kelli", updatedOrder.getCustomerName());
        Assertions.assertEquals(LocalDate.now(), updatedOrder.getOrderDate());
        Assertions.assertEquals("888 Somebody St", updatedOrder.getShippingAddress());
        Assertions.assertEquals(40.0, updatedOrder.getTotal(), 0.01);
    }


    @Test
    public void testDeleteOrder() {
        TddOrder order = new TddOrder();
        order.setCustomerName("Tommy Thomas");
        order.setOrderDate(LocalDate.now());
        order.setShippingAddress("999 Something St");
        order.setTotal(20.0);

        TddOrder savedOrder = orderRepository.save(order);
        orderRepository.delete(savedOrder);
        Optional<TddOrder> retrievedOrder = orderRepository.findById(savedOrder.getId());

        assertFalse(retrievedOrder.isPresent());
    }

    // TEST UPDATE TO A NONEXISTING ORDER
    @Test
    public void testUpdateNonExistingOrder() {
        // Attempt to update a non-existing order
        Long nonExistingOrderId = 91L;

        TddOrder order = new TddOrder();
        order.setId(nonExistingOrderId);

        assertThrows(NoSuchElementException.class, () -> {
            orderRepository.save(order);
        });
    }
    // TEST DELETE THE NON EXISTING ORDER
    @Test
    public void testDeleteNonExistingOrder() {

        Long nonExistingOrderId = 91L;

        assertThrows(NoSuchElementException.class, () -> {
            orderRepository.deleteById(nonExistingOrderId);
        });
    }

}