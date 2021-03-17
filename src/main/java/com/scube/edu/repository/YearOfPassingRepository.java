package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scube.edu.model.PassingYearMaster;


public interface YearOfPassingRepository extends JpaRepository<PassingYearMaster, Long> {


<<<<<<< Updated upstream

=======
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UserMasterEntity;

public interface YearOfPassingRepository extends JpaRepository<PassingYearMaster, Long> {
>>>>>>> Stashed changes

}
