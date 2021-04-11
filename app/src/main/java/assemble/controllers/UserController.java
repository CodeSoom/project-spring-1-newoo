package assemble.controllers;

import assemble.dto.UserModificationData;
import assemble.dto.UserRegistrationData;
import assemble.dto.UserResultData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 정보 컨트롤러.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResultData create(@RequestBody UserRegistrationData registrationData) {
        return null;
    }

    @PatchMapping("{id}")
    UserResultData update(
            @PathVariable Long id,
            @RequestBody UserModificationData modificationData) {
        return null;
    }

    @DeleteMapping("{id}")
    void destroy(@PathVariable Long id) {
        //
    }
}
