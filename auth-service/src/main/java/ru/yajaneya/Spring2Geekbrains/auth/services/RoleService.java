package ru.yajaneya.Spring2Geekbrains.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.auth.entities.Role;
import ru.yajaneya.Spring2Geekbrains.auth.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByName (String name) {
        return roleRepository.findByName(name);
    }

}
