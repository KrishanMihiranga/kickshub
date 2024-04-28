package lk.ijse.shoeshop.service.secutiry;

import lk.ijse.shoeshop.reqAndResponse.response.JwtAuthResponse;
import lk.ijse.shoeshop.reqAndResponse.secure.SignIn;
import lk.ijse.shoeshop.reqAndResponse.secure.SignUp;

public interface AuthenticationService {
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse signUp(SignUp signUp);
    JwtAuthResponse refreshToken(String accessToken);
}
