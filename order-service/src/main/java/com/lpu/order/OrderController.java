package com.lpu.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private BookClient bookClient;

    @GetMapping
    public List<OrderEntity> getOrders(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public OrderEntity getOrderById(@PathVariable Long id){
        Optional<OrderEntity> order = repo.findById(id);
        return order.orElse(null);
    }

    @PostMapping
    public OrderEntity placeOrder(@RequestBody OrderEntity order){

        BookDTO book = bookClient.getBookById(order.getBookId());

        order.setTotalPrice(book.getPrice() * order.getQuantity());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        return repo.save(order);
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrderStatus(@PathVariable Long id, @RequestBody OrderEntity updatedOrder){

        Optional<OrderEntity> existingOrder = repo.findById(id);

        if(existingOrder.isPresent()){
            OrderEntity order = existingOrder.get();
            order.setStatus(updatedOrder.getStatus());
            return repo.save(order);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        repo.deleteById(id);
    }
}