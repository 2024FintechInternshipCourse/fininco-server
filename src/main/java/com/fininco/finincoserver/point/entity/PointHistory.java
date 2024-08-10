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
    private Long amount;

    // 최근 수정날짜 -> 내역
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PointHistory(HistoryType historyType, Long amount, LocalDateTime modifiedDate, User user) {
        this.historyType = historyType;
        this.amount = amount;
        this.modifiedDate = modifiedDate;
        this.user = user;
    }
}