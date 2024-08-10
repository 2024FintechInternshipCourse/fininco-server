package com.fininco.finincoserver.point.repository;

import com.fininco.finincoserver.point.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointHistory, Long> {
}
