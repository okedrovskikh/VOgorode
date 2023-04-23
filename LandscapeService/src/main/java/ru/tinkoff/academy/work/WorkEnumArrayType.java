package ru.tinkoff.academy.work;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserTypeSupport;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

public class WorkEnumArrayType extends UserTypeSupport<Object> {
    public WorkEnumArrayType() {
        super(WorkEnum[].class, Types.ARRAY);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if(value == null) {
            st.setNull(index, Types.ARRAY);
        } else {
            Array array;
            try (Connection connection = session.getJdbcConnectionAccess().obtainConnection()) {
                array = connection.createArrayOf("work", (WorkEnum[])value);
            }
            st.setObject(index, array, Types.ARRAY);
        }
    }

    @Override
    public WorkEnum[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        Array array = rs.getArray(position);
        if (array == null) {
            return null;
        }
        String[] values = (String[]) array.getArray();
        return Arrays.stream(values).map(WorkEnum::valueOf).toList().toArray(WorkEnum[]::new);
    }
}
