package it.unicam.cs.ids.papcteam.c3Rest.repository;

import it.unicam.cs.ids.papcteam.c3Rest.entity.LockerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerRepository extends JpaRepository<LockerEntity,Long> {

}
