package com.example.demotdd;

import com.example.demotdd.controller.OrderController;
import com.example.demotdd.model.TddOrder;
import com.example.demotdd.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.Optional;


@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    private TddOrder testOrder;

    @BeforeEach
    public void setup() {
        testOrder = new TddOrder();
        testOrder.setId(1L);
        testOrder.setCustomerName("Tommy Thomas");
        testOrder.setOrderDate(LocalDate.now());
        testOrder.setShippingAddress("999 Something St");
        testOrder.setTotal(20.0);

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        Mockito.when(orderRepository.save(Mockito.any(TddOrder.class))).thenAnswer(invocation -> {
            TddOrder savedOrder = invocation.getArgument(0);
            savedOrder.setId(1L);
            return savedOrder;
        });
    }

    @Test
    public void testCreateOrder() throws Exception {
        String jsonOrder = "{\"customerName\":\"Tommy Thomas\",\"orderDate\":\"2023-07-07\",\"shippingAddress\":\"999 Something St\",\"total\":20.0}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Update this line
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertTrue(response.contains("\"customerName\":\"Tommy Thomas\""));
        Assertions.assertTrue(response.contains("\"shippingAddress\":\"999 Something St\""));
        Assertions.assertTrue(response.contains("\"total\":20.0"));
    }


    @Test
    public void testGetOrderById() throws Exception {

        TddOrder savedOrder = orderRepository.save(testOrder);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", savedOrder.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertTrue(response.contains("\"customerName\":\"Tommy Thomas\""));
        Assertions.assertTrue(response.contains("\"shippingAddress\":\"999 Something St\""));
        Assertions.assertTrue(response.contains("\"total\":20.0"));

    }

    @Test
    public void testUpdateOrder() throws Exception {
        // SAVING TEST ORDER TO REPO AND THEN MODIFYING NAME OF TEST ORDER
        TddOrder savedOrder = orderRepository.save(testOrder);

        testOrder.setCustomerName("Kelly Kelli");

        // JSON REPRESENTATION OF THE UPDATE ORDER
        String jsonOrder = "{\"customerName\":\"Kelly Kelli\",\"orderDate\":\"2023-07-07\",\"shippingAddress\":\"888 Somebody St\",\"total\":40.0}";

        // PUT REQUEST TO UPDATE ORDER
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/orders/{id}", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertTrue(response.contains("\"customerName\":\"Kelly Kelli\""));
        Assertions.assertTrue(response.contains("\"shippingAddress\":\"888 Somebody St\""));
        Assertions.assertTrue(response.contains("\"total\":40.0"));

    }

    @Test
    public void testDeleteOrder() throws Exception {
        // SAVING REST ORDER TO REPO THEN DELETE REQUEST BY ID
        TddOrder savedOrder = orderRepository.save(testOrder);

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", savedOrder.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // ASSET THAT ORER IS NO LONGER IN REPO
        TddOrder deletedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        Assertions.assertNull(deletedOrder);
    }

    @Test
    public void testCreateOrder_ValidationErrors() throws Exception {
        // SETTING EMPTY STRING FOR CUSTOMERNAME & INCOMPLETE ORDER DATE-VALIDATION ERROR
        String jsonOrder = "{\"customerName\":\"\",\"orderDate\":\"2023-07-";

        // POST REQUEST TO CREATE ORDER
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        // GETTING RESPONSE AS A STRING
        String response = result.getResponse().getContentAsString();

        // ASSERT THAT RESPONSE CONTAINS ERROR MESSAGE
        Assertions.assertTrue(response.contains("Validation error: customerName is required"));
    }
}