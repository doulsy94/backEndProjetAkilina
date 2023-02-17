package com.sy.backEndApiAkilina.controllers;

import com.sy.backEndApiAkilina.models.ERole;
import com.sy.backEndApiAkilina.models.Role;
import com.sy.backEndApiAkilina.models.User;
import com.sy.backEndApiAkilina.payload.request.LoginRequest;
import com.sy.backEndApiAkilina.payload.request.SignupRequest;
import com.sy.backEndApiAkilina.payload.response.JwtResponse;
import com.sy.backEndApiAkilina.payload.response.MessageResponse;
import com.sy.backEndApiAkilina.repository.UserRepository;
import com.sy.backEndApiAkilina.repository.RoleRepository;
import com.sy.backEndApiAkilina.security.jwt.JwtUtils;
import com.sy.backEndApiAkilina.security.services.UserService;
import com.sy.backEndApiAkilina.serviceImpl.UserDetailsImpl;
import com.sy.backEndApiAkilina.utils.EmailConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.sy.backEndApiAkilina.configuration.SaveImage.serveruser;


@Api(value = "authentification", description = "Inscription et connexion des users")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8100"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    EmailConstructor emailConstructor;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @ApiOperation(value = "Connexion de l'utilisateur")
    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrNumero(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        JwtResponse jwtResponse = new JwtResponse(userDetails.getId_user(),
                userDetails.getUsername(),
                userDetails.getNumero(),
                userDetails.getEmail(),
                roles);
        String values[] = jwtCookie.toString().split(";");
        jwtResponse.setAccessToken(values[0]);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(jwtResponse);
    }

    @ApiOperation(value = "Creation de compte de l'utilisateur")
    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Nom d'utilisateur déjà pris!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: L'Email existe déjà"));
        }
        if (userRepository.existsByNumero(signupRequest.getNumero())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le numero existe déjà"));
        }

        if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            User user = new User(signupRequest.getUsername(),
                    signupRequest.getEmail(),
                    signupRequest.getNumero(),
                    signupRequest.getAddresse(),
                    encoder.encode(signupRequest.getPassword()),
                    encoder.encode(signupRequest.getConfirmPassword()));

            Set<String> strRoles = signupRequest.getRole();
            Set<Role> roles = new HashSet<>();

            //VERIFICATION DU ROLE ENTREZ PAR L'UTILISATEUR
            //SI C'EST NULL ON AFFECTE DIRECTEMENT LE ROLE USER A CE COLLABORATEUR
            //SINON RECUPERE C'EST DIFFERENT ROLE ET ON VERIFIE SI CA EXISTE DANS LA BASE DE DONNEE
            // DANS LE CAS CONTRAIRE ON AFFECTE LE ROLE USER A CE COLLABORATEUR


            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                            roles.add(adminRole);

                            break;

                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                            roles.add(userRole);
                    }
                });

            }
            user.setRoles(roles);
            userRepository.save(user);
            mailSender.send(emailConstructor.constructNewUserEmail(user));

            return ResponseEntity.ok(new MessageResponse("Utilisateur enrégistrer avec succès"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Verifier les mots de passe "));
        }
    }

    @PostMapping("/photo/upload/{id_user}")
    public ResponseEntity<String> fileUpload(@RequestParam("imageuser") MultipartFile multipartFile, @PathVariable Long id_user) {
        User user = userRepository.findById(id_user).get();
        if (user == null) {
            return new ResponseEntity<>("Cet user n'existe pas", HttpStatus.BAD_REQUEST);
        }
        try {
            user.setImageuser(serveruser+user.getId_user().toString()+".png");
            userService.saveUserImage(multipartFile, user.getId_user());
            return new ResponseEntity<>("User Picture Saved!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User Picture Not Saved", HttpStatus.BAD_REQUEST);
        }
    }

    //::::::::::::::::::::::::::::::REINITIALISER PASSWORD::::::::::::::::::::::::::::::::::::::::::://

    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<String>("Email non fourni", HttpStatus.BAD_REQUEST);
        }
        userService.resetPassword(user);
        return new ResponseEntity<String>("Email envoyé!", HttpStatus.OK);
    }

    //::::::::::::::::::::::::::::::::::::::::Changer mot de passe:::::::::::::::::::::::::::::::::::::::::::::::://

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody HashMap<String, String> request) {
        String emailOrNumero = request.get("emailOrNumero");
        User user = userRepository.findByNumero(emailOrNumero);

        if (user == null) {
            return new ResponseEntity<>("Utilisateur non fourni!", HttpStatus.BAD_REQUEST);
        }
        String currentPassword = request.get("currentpassword");
        String newPassword = request.get("newpassword");
        String confirmpassword = request.get("confirmpassword");

        if (newPassword == null || newPassword.isEmpty() || StringUtils.isEmpty(newPassword)) {
            return new ResponseEntity<>("New password is null or empty!", HttpStatus.BAD_REQUEST);
        }

        if (!newPassword.equals(confirmpassword)) {
            return new ResponseEntity<>("PasswordNotMatched", HttpStatus.BAD_REQUEST);
        }

        String userPassword = user.getPassword();
        try {
            if (bCryptPasswordEncoder.matches(currentPassword, userPassword)) {
                userService.updateUserPassword(user, newPassword);
                return new ResponseEntity<>("Mot de passe changé avec succès!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("IncorrectCurrentPassword", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error Occured: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Déconnexion d'un nouveau user
    @ApiOperation(value = "Déconnexion de l'utilisateur")
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Vous avez été déconnecté!"));
    }

}
