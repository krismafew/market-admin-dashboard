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

// 将Json字符串和数组对象直接相互映射
public class JsonStringArrayTypeHandler extends BaseTypeHandler<String[]> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String[] stringArr, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, toJsonString(stringArr));
    }


    @Override
    public String[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return toStringArr(resultSet.getString(s));
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return toStringArr(resultSet.getString(i));
    }

    @Override
    public String[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return toStringArr(callableStatement.getString(i));
    }

    // 将Json字符串映射成一个String数组
    private String[] toStringArr(String string){
        if(!StringUtils.isEmpty(string)){
            try {
                return objectMapper.readValue(string, String[].class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    // 将String数组映射成一个Json字符串
    private String toJsonString(String[] stringArr){
        try {
            return objectMapper.writeValueAsString(stringArr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



}
