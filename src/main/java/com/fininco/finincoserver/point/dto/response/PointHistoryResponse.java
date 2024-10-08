package com.fininco.finincoserver.point.dto.response;

import com.fininco.finincoserver.point.entity.HistoryType;
import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PointHistoryResponse(
        long id,
        HistoryType historyType,
        BigDecimal amount,
        User user

) {

    public static PointHistoryResponse from(PointHistory pointHistory) {
        return new PointHistoryResponse(
                pointHistory.getId(),
                pointHistory.getHistoryType(),
                pointHistory.getAmount(),
                pointHistory.getUser()
        );
    }
}
