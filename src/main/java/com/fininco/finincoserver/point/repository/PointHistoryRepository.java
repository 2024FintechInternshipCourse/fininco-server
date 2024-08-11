package com.fininco.finincoserver.point.repository;

import com.fininco.finincoserver.point.entity.PointHistory;
import com.fininco.finincoserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
   // List<PointHistory> findByUserAndModifiedDateAfterOrderByModifiedDateDesc(User user);

    List<PointHistory> findByUser(User user);
}
