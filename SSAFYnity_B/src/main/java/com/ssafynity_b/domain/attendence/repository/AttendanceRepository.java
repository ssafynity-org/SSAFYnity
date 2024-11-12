package com.ssafynity_b.domain.attendence.repository;

import com.ssafynity_b.domain.attendence.document.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

}
