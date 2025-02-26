package com.mph.salelaptop.service.impl;

import com.mph.salelaptop.dto.RoleDTO;
import com.mph.salelaptop.model.Role;
import com.mph.salelaptop.repository.RoleRepository;
import com.mph.salelaptop.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        return role != null ? modelMapper.map(role, RoleDTO.class) : null;
    }

    @Override
    public RoleDTO getRoleByName(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        return role != null ? modelMapper.map(role, RoleDTO.class) : null;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        Role savedRole = roleRepository.save(role);
        return modelMapper.map(savedRole, RoleDTO.class);
    }

    @Override
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            modelMapper.map(roleDTO, existingRole);
            Role updatedRole = roleRepository.save(existingRole);
            return modelMapper.map(updatedRole, RoleDTO.class);
        }
        return null;
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}