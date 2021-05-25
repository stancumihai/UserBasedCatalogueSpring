package com.example.db_project.service.message;

import com.example.db_project.dao.message.MessageDao;
import com.example.db_project.model.studyGroup.Message;
import com.example.db_project.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageDao messageDao;
    private final StudentService studentService;

    @Autowired
    public MessageService(@Qualifier("MessageRepo") MessageDao messageDao, StudentService studentService) {
        this.messageDao = messageDao;
        this.studentService = studentService;
    }

    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    public List<Message> getMessagesByStudentId(int student) {
        return messageDao.getMessagesByStudentId(student);
    }

    public List<Message> getMessagesByStudyGroupId(int studyGroup) {
        List<Message> messageList = messageDao.getMessagesByStudyGroupId(studyGroup);
        messageList.forEach(this::setMessageCompleteStudent);

        return messageList;
    }

    public Message getMessageById(int id) {
        return messageDao.getMessageById(id);
    }

    public Optional<Message> createMessage(Message message) {
        Optional<Message> createdMessage = messageDao.createMessage(message);
        createdMessage.ifPresent(this::setMessageCompleteStudent);

        return createdMessage;
    }

    private void setMessageCompleteStudent(Message message) {
        message.setStudent(studentService.getStudentById(message.getStudent().getId()).get());
    }
}
