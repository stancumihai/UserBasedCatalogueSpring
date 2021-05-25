package com.example.db_project.dao.studyGroup;

import com.example.db_project.model.studyGroup.EnrolledStudent;
import com.example.db_project.model.studyGroup.StudentActivityGroup;
import com.example.db_project.model.studyGroup.StudentStudyGroup;
import com.example.db_project.model.studyGroup.StudyGroup;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;

import java.util.List;
import java.util.Optional;

public interface StudyGroupDao {

    List<StudentActivityGroup> getActivitiesByGroupId(int studyGroupId);

    List<StudyGroup> getStudyGroupsBySubjectId(int subjectId);

    StudyGroup getStudyGroupById(int studyGroupId);

    StudyGroup createStudyGroup(StudyGroup studyGroup);

    StudentStudyGroup enrollStudentToStudyGroup(StudentStudyGroup studentStudyGroup);

    Optional<StudentActivityGroup> getGroupActivityInfoById(int groupActivityId);

    StudentActivityGroup createStudyGroupActivity(StudentActivityGroup studentActivityGroup);

    EnrolledStudent enrollStudentToGroupActivity(int groupActivityId, int studentId);

    List<StudentStudyGroup> getStudyGroupStudents(int studyGroupId);

    List<StudentStudyGroup> getStudentStudyGroups(int studentId);

    List<EnrolledStudent> getGroupActivityEnrolledStudents(int groupActivityId);

    int quitGroupActivity(int id);

    int quitStudyGroup(int id);

    StudentStudyGroup getEnrolledStatus(int studyGroupId, int studentId);

    int enrollProfessorToStudentActivityGroup(int id, int profId);

    List<Student> getSuggestionsForStudyGroup(int studyGroup);

    public int deleteStudyGroup(int id);

    void updateGroupActivity(StudentActivityGroup studentActivityGroup);
}


