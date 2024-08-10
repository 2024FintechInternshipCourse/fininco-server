package com.fininco.finincoserver.point.dto.request;

import com.fininco.finincoserver.point.entity.HistoryType;
import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.user.entity.User;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record PointHistoryCreateRequest(
        HistoryType historyType,
        long amount,
        LocalDateTime modifiedDate,
        //User user
        String userId
) {
    public PointHistory toEntity(User user) {

        return PointHistory.builder()
                .historyType(historyType)
                .amount(amount)
                .modifiedDate(modifiedDate)
                .user(user)
                .build();
    }
}
