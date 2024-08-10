package com.fininco.finincoserver.point.dto.response;

import com.fininco.finincoserver.point.entity.HistoryType;
import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.user.entity.User;

import java.time.LocalDateTime;

public record PointHistoryResponse(
        long id,
        HistoryType historyType,
        long amount,
        LocalDateTime modifiedDate,
        User user

) {

    public static PointHistoryResponse from(PointHistory pointHistory) {
        return new PointHistoryResponse(
                pointHistory.getId(),
                pointHistory.getHistoryType(),
                pointHistory.getAmount(),
                pointHistory.getModifiedDate(),
                pointHistory.getUser()
        );
    }
}
