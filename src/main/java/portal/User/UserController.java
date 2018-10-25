package portal.User;

import org.jboss.logging.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
class UserController {

    @CrossOrigin
    @PostMapping
    Boolean createNewUser(@RequestBody Map<String, String> json){
        User admin = new User(json.get("username"), json.get("password"));
        return userDatabase.saveAdminToSpanner(admin);
    }

    @CrossOrigin
    @GetMapping
    Boolean login(@Param String username, @Param String password){
        User admin = new User(username, password);
        return userDatabase.getAdminFromSpanner(admin);
    }


}

