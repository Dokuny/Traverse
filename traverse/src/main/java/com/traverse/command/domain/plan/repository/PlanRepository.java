package com.traverse.command.domain.plan.repository;

import com.traverse.command.domain.plan.entity.Plan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanRepository extends JpaRepository<Plan, Long> {

	@Query("select distinct p from Plan p join fetch p.destinations where p.id = :planId")
	Optional<Plan> findByIdWithDestinations(@Param("planId") Long planId);

}
