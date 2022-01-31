package ru.yajaneya.Spring2Geekbrains.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.core.entities.Role;
import ru.yajaneya.Spring2Geekbrains.core.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByName (String name) {
        return roleRepository.findByName(name);
    }

}
