package com.example.db_project.mapper.user;

import com.example.db_project.model.users.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet resultSet, int i) throws SQLException {
        Address address = new Address();

        address.setId(resultSet.getInt("id"));
        address.setUserId(resultSet.getInt("userId"));
        address.setContractNr(resultSet.getInt("contractNr"));
        address.setEmail(resultSet.getString("email"));
        address.setPhone(resultSet.getString("phone"));
        address.setIBAN(resultSet.getString("IBAN"));
        address.setStreet(resultSet.getString("street"));
        address.setNumber(resultSet.getInt("number"));
        return address;
    }
}
