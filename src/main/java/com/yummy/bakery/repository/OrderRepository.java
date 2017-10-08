package com.yummy.bakery.repository;

import com.yummy.bakery.entity.Order;
import com.yummy.bakery.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alalwani on 02/07/17.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
