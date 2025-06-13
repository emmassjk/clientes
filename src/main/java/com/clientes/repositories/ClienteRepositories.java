package com.clientes.repositories;

import com.clientes.models.ClienteModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositories extends JpaRepository<ClienteModels, Integer> {
    

}
