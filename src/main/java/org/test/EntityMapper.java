package org.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EntityMapper implements RowMapper<Entity> {

    @Override
    public Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Entity(rs.getString("name"), rs.getString("label"));
    }

}
