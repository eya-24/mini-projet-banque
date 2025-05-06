package com.ds.banque.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ds.banque.entitie.Compte;

public interface CompteRepository extends JpaRepository<Compte, Integer> {


}
