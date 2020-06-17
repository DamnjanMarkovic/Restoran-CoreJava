package com.code.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface CommonDomain {

    String returnTableName();
    String saveReturnColums();
    String saveReturnQuestionMarks();

    PreparedStatement setInsertValues(PreparedStatement preparedStatement);


    Object readObjects(ResultSet resultSet);

    String returnIDQuestionMarks();

    String idColumnName();

    String familiarColumns();

    PreparedStatement returnIDPreparedStatement(PreparedStatement preparedStatement);

    String returnColumnforDelete();

    String return1QuestionMark();

    PreparedStatement returnDeletePreparedStatement(PreparedStatement preparedStatement);
}
