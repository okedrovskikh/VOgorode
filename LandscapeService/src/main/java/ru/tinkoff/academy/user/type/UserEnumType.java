package ru.tinkoff.academy.user.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserEnumType extends EnumType<UserType> {
    @Override
    public void nullSafeSet(PreparedStatement st, UserType value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if(value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.toString(), Types.OTHER);
        }
    }

    @Override
    public UserType nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        String label = rs.getString(position);
        return UserType.valueOf(label);
    }
}
