package com.ssafinity_b.domain.attendence.repository;

import com.ssafinity_b.domain.attendence.entity.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

}
