package com.vsc.exam;

import com.vsc.exam.Role;
import com.vsc.exam.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.management.relation.RoleNotFoundException;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getUserRole() throws RoleNotFoundException {
        return getByName("ROLE_USER");
    }

    public Role getByName(String authority) throws RoleNotFoundException {
        return roleRepository.findFirstByAuthority(authority)
                .orElseThrow(() -> new RoleNotFoundException("Cannot find role: " + authority));
    }

    @PostConstruct
    private void initRoles() {
        String[] allRoleNames = { "ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR" };

        for (String roleName : allRoleNames) {
            if (!roleRepository.existsByAuthority(roleName)) {
                Role role = new Role();
                role.setAuthority(roleName);
                roleRepository.save(role);
            }
        }
    }
}
