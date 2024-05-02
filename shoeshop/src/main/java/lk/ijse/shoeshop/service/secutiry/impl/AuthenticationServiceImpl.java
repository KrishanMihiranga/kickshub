package lk.ijse.shoeshop.service.secutiry.impl;

import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.dto.ResponseEmployeeDTO;
import lk.ijse.shoeshop.repo.UserRepo;
import lk.ijse.shoeshop.reqAndResponse.response.JwtAuthResponse;
import lk.ijse.shoeshop.reqAndResponse.secure.SignIn;
import lk.ijse.shoeshop.reqAndResponse.secure.SignUp;
import lk.ijse.shoeshop.service.secutiry.AuthenticationService;
import lk.ijse.shoeshop.service.secutiry.JWTService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final JWTService jwtService;
    private final Mapping mapping;
    //utils
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var userByEmail = userRepo.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(userByEmail);
        var employee = mapping.toEmployeeDTO(userByEmail);

        ResponseEmployeeDTO responseEmployeeDTO = null;
        responseEmployeeDTO = ResponseEmployeeDTO.builder()
                .employeeCode(employee.getEmployeeCode())
                .name(employee.getName())
                .profilePic(employee.getProfilePic())
                .gender(employee.getGender())
                .status(employee.getStatus())
                .designation(employee.getDesignation())
                .role(employee.getRole())
                .dob(employee.getDob())
                .joinedDate(employee.getJoinedDate())
                .branch(employee.getBranch())
                .addressNo(employee.getAddressNo())
                .addressLane(employee.getAddressLane())
                .addressCity(employee.getAddressCity())
                .addressState(employee.getAddressState())
                .postalCode(employee.getPostalCode())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .guardianOrNominatedPerson(employee.getGuardianOrNominatedPerson())
                .emergencyContact(employee.getEmergencyContact())
                .build();
        System.out.println(employee.getProfilePic());
        return JwtAuthResponse.builder().token(generatedToken).employee(responseEmployeeDTO).build();
    }


    @Override
    public JwtAuthResponse signUp(SignUp signUp) {
        var buildUser = EmployeeDTO.builder()
                .profilePic(signUp.getProfilePic())
                .employeeCode(UtilMatters.generateId())
                .email(signUp.getEmail())
                .name(signUp.getName())
                .gender(signUp.getGender())
                .status(signUp.getStatus())
                .dob(signUp.getDob())
                .joinedDate(signUp.getJoinedDate())
                .branch(signUp.getBranch())
                .addressNo(signUp.getAddressNo())
                .addressLane(signUp.getAddressLane())
                .addressCity(signUp.getAddressCity())
                .addressState(signUp.getAddressState())
                .postalCode(signUp.getPostalCode())
                .phone(signUp.getPhone())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .guardianOrNominatedPerson(signUp.getGuardianOrNominatedPerson())
                .emergencyContact(signUp.getEmergencyContact())
                .designation(signUp.getDesignation())
                .role(signUp.getRole())
                .build();

        var savedUser = userRepo.save(mapping.toEmployeeEntity(buildUser));
        var genToken = jwtService.generateToken(savedUser);
        return JwtAuthResponse.builder().token(genToken).build();
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        var userName = jwtService.extractUserName(accessToken);
        var userEntity = userRepo.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.generateToken(userEntity);
        return JwtAuthResponse.builder().token(refreshToken).build();
    }

}
