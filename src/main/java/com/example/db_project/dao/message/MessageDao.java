package com.example.db_project.dao.message;

import com.example.db_project.model.studyGroup.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDao {

    List<Message> getAllMessages();

    List<Message> getMessagesByStudyGroupId(int studyGroup);

    List<Message> getMessagesByStudentId(int student);

    Message getMessageById(int id);

    Optional<Message> createMessage(Message Message);
}
