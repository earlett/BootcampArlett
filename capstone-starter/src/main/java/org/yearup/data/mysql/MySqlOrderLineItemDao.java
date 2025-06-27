package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderLineItemDao;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlOrderLineItemDao extends MySqlDaoBase implements OrderLineItemDao {

    public MySqlOrderLineItemDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public OrderLineItem create(OrderLineItem orderLineItem) {
        String sql = "INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, orderLineItem.getOrderId());
            statement.setInt(2, orderLineItem.getProductId());
            statement.setBigDecimal(3, orderLineItem.getSalesPrice());
            statement.setInt(4, orderLineItem.getQuantity());
            statement.setBigDecimal(5, orderLineItem.getDiscount());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order line item failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderLineItemId = generatedKeys.getInt(1);
                    orderLineItem.setOrderLineItemId(orderLineItemId);
                    return orderLineItem;
                } else {
                    throw new SQLException("Creating order line item failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}