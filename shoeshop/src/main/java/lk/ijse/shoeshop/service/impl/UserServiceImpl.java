package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.repo.UserRepo;
import lk.ijse.shoeshop.service.UserService;
import lk.ijse.shoeshop.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final Mapping mapping;
    @Override
    public UserDetailsService userDetailsService() {
        return username ->
                userRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        mapping.toEmployeeDTO(userRepo.save(mapping.toEmployeeEntity(employeeDTO)));
    }
}
