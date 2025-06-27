package org.yearup.data;

import org.yearup.models.OrderLineItem;

public interface OrderLineItemDao {
    OrderLineItem create(OrderLineItem orderLineItem);
}