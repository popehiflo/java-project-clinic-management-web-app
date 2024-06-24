package io.github.popehiflo.clinica.security;

import io.github.popehiflo.clinica.entity.Usuario;
import io.github.popehiflo.clinica.entity.UsuarioRole;
import io.github.popehiflo.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrarAdmin = "admin";
        String passSinCifrarUser = "user";
        String passCifradoAdmin = passwordEncoder.encode(passSinCifrarAdmin);
        String passCifradoUser = passwordEncoder.encode(passSinCifrarUser);
        Usuario admin = new Usuario("Admin", UsuarioRole.ROLE_ADMIN, passCifradoAdmin, "admin@admin.com", "admin");
        Usuario user = new Usuario("Pool", UsuarioRole.ROLE_USER, passCifradoUser, "user@user.com", "popehiflo");
        System.out.println("pass cifrado ADMIN: " + passCifradoAdmin);
        System.out.println("pass cifrado USER: " + passCifradoUser);
        usuarioRepository.save(admin);
        usuarioRepository.save(user);
    }
}
