package com.mph.salelaptop.service;

import com.mph.salelaptop.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO getRoleById(Long id);
    RoleDTO getRoleByName(String roleName);
    List<RoleDTO> getAllRoles();
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(Long id, RoleDTO roleDTO);
    void deleteRole(Long id);
}