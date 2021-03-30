package assemble.controllers;

import assemble.domain.Meeting;
import assemble.dto.MeetingData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 모임 컨트롤러.
 */
@RestController
@RequestMapping("/meetings")
public class MeetingController {
    @GetMapping
    public List<Meeting> list() {
        return null;
    }

    @GetMapping("{id}")
    public Meeting detail(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Meeting create(@RequestBody MeetingData meetingData) {
        return null;
    }

    @PatchMapping("{id}")
    public Meeting update(
            @PathVariable Long id,
            @RequestBody MeetingData meetingData
    ) {
        return null;
    }

    @DeleteMapping("{id}")
    public void destroy(@PathVariable Long id) {
        //
    }
}