package ru.tinkoff.academy.order.status;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;
import ru.tinkoff.academy.order.status.OrderStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class OrderStatusEnumType extends EnumType<OrderStatus> {
    @Override
    public void nullSafeSet(PreparedStatement st, OrderStatus value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if(value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.toString(), Types.OTHER);
        }
    }

    @Override
    public OrderStatus nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        String label = rs.getString(position);
        return OrderStatus.valueOf(label);
    }
}
