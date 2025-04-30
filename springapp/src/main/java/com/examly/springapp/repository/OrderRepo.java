package com.examly.springapp.repository;
 
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 
import com.examly.springapp.model.Order;
import com.examly.springapp.model.OrderItem;
 
@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    @Query("select order from Order order where order.user.userId=?1")
    List<Order> findByUserId(long userId);

    @Query("select order from OrderItem order where order.order.orderId=?1")
    List<OrderItem> findByOrderId(long orderId);   
}