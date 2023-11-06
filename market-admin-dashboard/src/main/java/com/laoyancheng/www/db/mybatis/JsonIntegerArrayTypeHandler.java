package com.laoyancheng.www.db.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonIntegerArrayTypeHandler extends BaseTypeHandler<Integer[]> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Integer[] integers, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, toJsonString(integers));
    }

    @Override
    public Integer[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return toIntegerArr(resultSet.getString(s));
    }

    @Override
    public Integer[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return toIntegerArr(resultSet.getString(i));
    }

    @Override
    public Integer[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return toIntegerArr(callableStatement.getString(i));
    }

    private Integer[] toIntegerArr(String string) {
        if(!StringUtils.isEmpty(string)){
            try {
                return objectMapper.readValue(string, Integer[].class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    private String toJsonString(Integer[] integers) {
        try {
            return objectMapper.writeValueAsString(integers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
