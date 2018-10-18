package portal.User;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
class UserController {

    @PostMapping
    void newUser(@RequestBody User user){
        user.getInfo();
    }

    @GetMapping
    String helloWorld(){
        return "Hello World";
    }


}

