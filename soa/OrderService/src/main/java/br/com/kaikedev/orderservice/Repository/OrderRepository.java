package br.com.kaikedev.orderservice.Repository;

import br.com.kaikedev.orderservice.Entity.Enum.OrderEnum;
import br.com.kaikedev.orderservice.Entity.OrderEntity;
import br.com.kaikedev.orderservice.Entity.OrderItemEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// OrderRepository.java
@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<OrderEntity> orderRowMapper = (rs, rowNum) -> {
        OrderEntity order = new OrderEntity();
        order.setId(rs.getInt("id"));
        order.setCustomerId(rs.getInt("user_id"));
        order.setStatus(OrderEnum.valueOf(rs.getString("status")));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setOrderDate(rs.getTimestamp("created_at").toLocalDateTime());
        order.setOrderDate(rs.getTimestamp("updated_at").toLocalDateTime());
        return order;
    };

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OrderEntity save(OrderEntity order) {
        if (order.getId() == null) {
            return insert(order);
        } else {
            return update(order);
        }
    }

    private OrderEntity insert(OrderEntity order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO orders (user_id, status, total_amount, created_at, updated_at) VALUES (?, ?, ?, ?, ?) RETURNING id";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getCustomerId());
            ps.setString(2, order.getStatus().toString());
            ps.setDouble(3, order.getTotalAmount());
            ps.setTimestamp(4, Timestamp.valueOf(order.getOrderDate()));
            ps.setTimestamp(5, Timestamp.valueOf(order.getOrderDate()));
            return ps;
        }, keyHolder);

        order.setId(keyHolder.getKey().intValue());
        saveItems(order);
        return order;
    }

    private OrderEntity update(OrderEntity order) {
        jdbcTemplate.update(
                "UPDATE orders SET user_id = ?, updated_at = ?, status = ?, total_amount = ? WHERE id = ?",
                order.getCustomerId(),
                Timestamp.valueOf(LocalDateTime.now()),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getId()
        );

        // Remove todos os itens antigos e insere os novos
        jdbcTemplate.update("DELETE FROM order_items WHERE order_id = ?", order.getId());
        saveItems(order);

        return order;
    }

    private void saveItems(OrderEntity order) {
        String sql = "INSERT INTO order_items (order_id, product_id, product_name, unit_price, quantity, sub_total) VALUES (?, ?, ?, ?, ?, ?)";

        if (order.getItens() != null) {
            for (OrderItemEntity item : order.getItens()) {
                jdbcTemplate.update(
                        sql,
                        order.getId(),
                        item.getProductId(),
                        item.getProductName(),
                        item.getProductUnitPrice(),
                        item.getQuantity(),
                        item.getSubTotalPrice()
                );
            }
        }
    }

    public Optional<OrderEntity> findById(Integer id) {
        String sql = "SELECT id, user_id, status, total_amount, created_at, updated_at FROM orders WHERE id = ?";
        String sql2 = "SELECT id, order_id, product_id, product_name, unit_price, quantity, sub_total FROM order_items WHERE order_id = ?";

        try {
            OrderEntity order = jdbcTemplate.queryForObject(
                    sql,
                    orderRowMapper,
                    id
            );

            if (order != null) {
                List<OrderItemEntity> items = jdbcTemplate.query(sql2,
                        (rs, rowNum) -> {
                            OrderItemEntity item = new OrderItemEntity();
                            item.setId(rs.getInt("id"));
                            item.setOrderId(rs.getInt("order_id"));
                            item.setProductId(rs.getInt("product_id"));
                            item.setProductName(rs.getString("product_name"));
                            item.setProductUnitPrice(rs.getDouble("unit_price"));
                            item.setQuantity(rs.getInt("quantity"));
                            item.setSubTotalPrice(rs.getDouble("sub_total"));
                            return item;
                        },
                        id
                );
                order.setItens(items);
            }

            return Optional.ofNullable(order);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<OrderEntity> findByUserId(Integer userId) {
        List<OrderEntity> orders = jdbcTemplate.query(
                "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC",
                orderRowMapper,
                userId
        );

        for (OrderEntity order : orders) {
            List<OrderItemEntity> items = jdbcTemplate.query(
                    "SELECT * FROM order_items WHERE order_id = ?",
                    (rs, rowNum) -> {
                        OrderItemEntity item = new OrderItemEntity();
                        item.setId(rs.getInt("id"));
                        item.setOrderId(rs.getInt("order_id"));
                        item.setProductId(rs.getInt("product_id"));
                        item.setProductName(rs.getString("product_name"));
                        item.setProductUnitPrice(rs.getDouble("unit_price"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setSubTotalPrice(rs.getDouble("sub_total"));
                        return item;
                    },
                    order.getId()
            );
            order.setItens(items);
        }

        return orders;
    }
}
