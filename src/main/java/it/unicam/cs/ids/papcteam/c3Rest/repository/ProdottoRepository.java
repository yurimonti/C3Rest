package it.unicam.cs.ids.papcteam.c3Rest.repository;

import it.unicam.cs.ids.papcteam.c3Rest.entity.ProdottoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoRepository extends JpaRepository<ProdottoEntity,Long> {
}
