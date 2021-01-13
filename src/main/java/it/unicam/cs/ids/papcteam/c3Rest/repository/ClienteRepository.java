package it.unicam.cs.ids.papcteam.c3Rest.repository;

import it.unicam.cs.ids.papcteam.c3Rest.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {
}
