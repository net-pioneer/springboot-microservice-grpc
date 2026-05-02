package com.pouya.javatest.Console.Seeds;

import com.pouya.javatest.Models.Currency;
import com.pouya.javatest.Models.User;
import com.pouya.javatest.Models.UserRolesMap;
import com.pouya.library.Enums.UserRoles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.pouya.library.Translations.MessageHelper._pt;
import static com.pouya.library.Translations.MessageHelper._t;

@Component
//gradlew.bat bootRun || gradlew.bat bootRun --args="--seed_user"
public class DatabaseSeeder implements ApplicationRunner {
    @Autowired
    PasswordEncoder _encoder;
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void run(ApplicationArguments args) throws Exception {
        if(args.containsOption("seed_user")){
            _pt("seeder.start");
            User _user = new User();
            _user.setEmail("poya.p1994@gmail.com");
            _user.setPassword(_encoder.encode("p123"));
            _user.setCreatedAt(LocalDateTime.now());

            UserRolesMap _role1 = new UserRolesMap();
            _role1.setUser(_user);
            _role1.setRole(UserRoles.USER);

            UserRolesMap _role2 = new UserRolesMap();
            _role2.setUser(_user);
            _role2.setRole(UserRoles.SUPPLIER);

            List<UserRolesMap> _roles = new ArrayList<UserRolesMap>();
            _roles.add(_role1);
            _roles.add(_role2);

            _user.setRoles(_roles);

            em.persist(_user);

            _pt("seeder.done");
        }
        if(args.containsOption("seed_cn")){
            _pt("seeder.start");
            Currency _rial = new Currency("Rial","IRT",0L);
            Currency _tether = new Currency("Tether","USDT",180000L);
            Currency _euro = new Currency("Euro","EUR",200000L);
            em.persist(_rial);
            em.persist(_tether);
            em.persist(_euro);
            _pt("seeder.done");
        }
        else{
            _pt("arg.help");
        }


    }
}