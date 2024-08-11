package com.fininco.finincoserver.point.entity;

import com.fininco.finincoserver.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * 원화 포인트 내역 Entity
 *
 * 아이디, 유형 (충전, 소진), 금액, (최종수정)일자, 사용자 ID
 *
 */


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PointHistory") // point_history
public class PointHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사기, 팔기
    @Enumerated(EnumType.STRING)
    private HistoryType historyType;

    // 금액
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PointHistory(HistoryType historyType, BigDecimal amount, User user) {
        this.historyType = historyType;
        this.amount = amount;
        this.user = user;
    }

}
