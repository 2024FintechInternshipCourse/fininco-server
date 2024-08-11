package com.fininco.finincoserver.exchange.entity;

import com.fininco.finincoserver.global.entity.BaseEntity;
import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "applied_rate_id")
    ExchangeRate appliedRate;

    @OneToOne
    @JoinColumn(name = "point_history_id")
    PointHistory pointHistory;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    ExchangeReservation reservation;

    @Builder
    public ExchangeHistory(User user, ExchangeRate appliedRate, PointHistory pointHistory, ExchangeReservation reservation) {
        this.user = user;
        this.appliedRate = appliedRate;
        this.pointHistory = pointHistory;
        this.reservation = reservation;
    }

}
