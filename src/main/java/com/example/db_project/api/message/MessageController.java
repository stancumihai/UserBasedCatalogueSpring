package com.example.db_project.api.message;

import com.example.db_project.model.studyGroup.Message;
import com.example.db_project.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getAllMessages() {

        return messageService.getAllMessages();
    }

    @GetMapping(path = "/{id}")
    public Message getMessageById(@PathVariable("id") int id) {
        return messageService.getMessageById(id);

    }

    @GetMapping(path = "/{student}/student")
    List<Message> getMessagesByStudentId(@PathVariable("student") int student) {
        return messageService.getMessagesByStudentId(student);
    }

    @GetMapping(path = "/{studyGroup}/group")
    List<Message> getMessagesByStudyGroupId(@PathVariable("studyGroup") int studyGroup) {
        return messageService.getMessagesByStudyGroupId(studyGroup);
    }

    @PostMapping()
    Optional<Message> createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }
}
