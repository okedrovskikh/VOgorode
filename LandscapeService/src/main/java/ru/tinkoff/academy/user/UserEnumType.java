package ru.tinkoff.academy.user;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserEnumType extends EnumType<UserType> {
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if(value == null) {
            st.setNull( index, Types.OTHER );
        } else {
            st.setObject( index, value.toString().toLowerCase(), Types.OTHER );
        }
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        String label = rs.getString(names[0]);
        return UserType.valueOf(label.toUpperCase());
    }
}
