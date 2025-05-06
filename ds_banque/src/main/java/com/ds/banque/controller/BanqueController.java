package com.ds.banque.controller;

import com.ds.banque.entitie.Compte;
import com.ds.banque.repository.CompteRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BanqueController {
	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping("/comptes")
    public String listeComptes(Model model) {
        model.addAttribute("comptes", compteRepository.findAll());
        return "listeComptes";
    }

    @PostMapping("/comptes/add")
    public String ajouter(@RequestParam String titulaire, @RequestParam double solde) {
        Compte c = new Compte(titulaire, solde);
        compteRepository.save(c);
        return "redirect:/comptes";
    }

    @GetMapping("/ajouter")
    public String add() {
        return "ajouterCompte";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable int id, Model model) {
        Compte compte = compteRepository.findById(id).orElse(null);
        model.addAttribute("c", compte);
        return "detailsCompte";
    }

    @PostMapping("/depot/{id}")
    public String depot(@PathVariable int id, @RequestParam double montant) {
        Compte c = compteRepository.findById(id).orElse(null);
        if (c != null && montant > 0) {
            c.setSolde(c.getSolde() + montant);
            compteRepository.save(c);
        }
        return "redirect:/details/" + id;
    }

    @PostMapping("/retrait/{id}")
    public String retrait(@PathVariable int id, @RequestParam double montant) {
        Compte c = compteRepository.findById(id).orElse(null);
        if (c != null && montant > 0 && c.getSolde() >= montant) {
            c.setSolde(c.getSolde() - montant);
            compteRepository.save(c);
        }
        return "redirect:/details/" + id;
    }
    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable int id) {
        compteRepository.deleteById(id);
        return "redirect:/comptes";
    }
   // @GetMapping("/comptes/reset")
   // public String resetComptes() {
     //   compteRepository.deleteAll();
       // jdbcTemplate.execute("ALTER TABLE compte AUTO_INCREMENT = 1"); // Ou adapté à H2
       // return "redirect:/comptes";
    //}
    @PostMapping("/comptes/reset-ids")
    public String reordonnerComptes() {
        List<Compte> anciens = compteRepository.findAll();

        // 1. Supprimer tous les comptes
        compteRepository.deleteAll();

        // 2. Réinitialiser l’auto-incrément à 1 (pour MySQL)
        jdbcTemplate.execute("ALTER TABLE compte AUTO_INCREMENT = 1");

        // 3. Réinsérer les comptes (sans les anciens IDs)
        for (Compte c : anciens) {
            Compte nouveau = new Compte(c.getTitulaire(), c.getSolde());
            compteRepository.save(nouveau);
        }

        return "redirect:/comptes";
    }

    }


    


