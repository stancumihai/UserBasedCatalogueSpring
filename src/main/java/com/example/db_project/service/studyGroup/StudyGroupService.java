package com.example.db_project.service.studyGroup;


import com.example.db_project.dao.studyGroup.StudyGroupDao;
import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.GroupActivityState;
import com.example.db_project.model.course.StudentActivity;
import com.example.db_project.model.studyGroup.EnrolledStudent;
import com.example.db_project.model.studyGroup.StudentActivityGroup;
import com.example.db_project.model.studyGroup.StudentStudyGroup;
import com.example.db_project.model.studyGroup.StudyGroup;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
import com.example.db_project.service.activity.ActivityService;
import com.example.db_project.service.professor.ProfessorService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.studentSubject.StudentSubjectService;
import com.example.db_project.service.subject.SubjectService;
import com.example.db_project.service.user.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupService {
    private final StudyGroupDao studyGroupDao;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final UserService userService;
    private final StudentSubjectService studentSubjectService;
    private final ActivityService activityService;

    @Autowired
    public StudyGroupService(@Qualifier("StudyGroupRepo") StudyGroupDao studyGroupDao,
                             SubjectService subjectService, StudentService studentService,
                             ProfessorService professorService, UserService userService, StudentSubjectService studentSubjectService, ActivityService activityService) {
        this.studyGroupDao = studyGroupDao;
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.professorService = professorService;
        this.userService = userService;
        this.studentSubjectService = studentSubjectService;
        this.activityService = activityService;
    }

    public List<StudyGroup> getStudyGroupsBySubjectId(int subjectId) {
        List<StudyGroup> studyGroupList = studyGroupDao.getStudyGroupsBySubjectId(subjectId);
        studyGroupList.forEach(studyGroup
                -> studyGroup.setSubject(subjectService.getSubjectById(studyGroup.getSubject().getId())));
        studyGroupList.forEach(studyGroup
                -> studyGroup.setCreator(userService.getUserById(studyGroup.getCreator().getId()).get()));
        return studyGroupList;
    }

    public List<StudentActivityGroup> getStudyGroupActivities(int studyGroupId) {
        List<StudentActivityGroup> studentActivityGroupList = studyGroupDao.getActivitiesByGroupId(studyGroupId);
        studentActivityGroupList.forEach(this::setActivityInitiator);
        studentActivityGroupList.forEach(this::setActivityProfessor);
        studentActivityGroupList.forEach(this::updateActivityState);

        return studentActivityGroupList;
    }

    public List<StudentStudyGroup> getStudyGroupStudents(int studyGroupId) {
        List<StudentStudyGroup> studentStudyGroupList = studyGroupDao.getStudyGroupStudents(studyGroupId);
        studentStudyGroupList.forEach(this::setStudentOnStudyGroup);

        return studentStudyGroupList;
    }

    public List<StudentStudyGroup> getStudentStudyGroups(int studentId) {
        List<StudentStudyGroup> studentStudyGroupList = studyGroupDao.getStudentStudyGroups(studentId);
        studentStudyGroupList.forEach(this::setStudyGroupCompleteObject);
        return studentStudyGroupList;
    }

    public StudyGroup getStudyGroupById(int studyGroupId) {
        StudyGroup studyGroup = studyGroupDao.getStudyGroupById(studyGroupId);
        studyGroup.setSubject(subjectService.getSubjectById(studyGroup.getSubject().getId()));
        studyGroup.setCreator(userService.getUserById(studyGroup.getCreator().getId()).get());
        return studyGroup;
    }

    public StudyGroup createStudyGroup(StudyGroup studyGroup) {
        StudyGroup created = studyGroupDao.createStudyGroup(studyGroup);
        created.setCreator(userService.getUserById(created.getCreator().getId()).get());
        created.setSubject(subjectService.getSubjectById(created.getSubject().getId()));
        return created;
    }

    public StudentStudyGroup getEnrolledStatus(int studyGroupId, int studentId) {
        return studyGroupDao.getEnrolledStatus(studyGroupId, studentId);
    }

    public StudentStudyGroup enrollStudentToStudyGroup(StudentStudyGroup studentStudyGroup) {

        return studyGroupDao.enrollStudentToStudyGroup(studentStudyGroup);
    }

    public StudentActivityGroup createStudyGroupActivity(StudentActivityGroup studentActivityGroup) {
        StudentActivityGroup groupActivity = studyGroupDao.createStudyGroupActivity(studentActivityGroup);
        int professorId = studentActivityGroup.getProfessor().getId();
        if (professorId != 0) {
            int enrolled = enrollProfessorToStudentActivityGroup(groupActivity.getId(), professorId);
            if (enrolled == 1) {
                groupActivity.setProfessor(professorService.getProfessorById(professorId).get());
            }
        } else {
            groupActivity.setProfessor(null);
        }
        setActivityInitiator(groupActivity);
        return groupActivity;
    }

    public int enrollProfessorToStudentActivityGroup(int activityId, int profId) {
        return studyGroupDao.enrollProfessorToStudentActivityGroup(activityId, profId);
    }

    public EnrolledStudent enrollStudentToGroupActivity(int groupActivityId, int studentId) {
        Optional<StudentActivityGroup> studentActivityGroup = studyGroupDao.getGroupActivityInfoById(groupActivityId);
        Optional<Student> student = studentService.getStudentById(studentId);
        Optional<User> user = userService.getUserById(student.get().getUser().getId());
        List<StudentActivity> studentActivityList = studentSubjectService.getStudentActivities(user.get());
        if (!groupActivityOverlapsSchedule(studentActivityGroup.get(), studentActivityList)) {
            EnrolledStudent enrolledStudent = studyGroupDao.enrollStudentToGroupActivity(groupActivityId, studentId);
            enrolledStudent.setStudent(studentService.getStudentById(enrolledStudent.getStudent().getId()).get());
            enrolledStudent.setActivity(studyGroupDao.getGroupActivityInfoById(enrolledStudent.getActivity().getId()).get());
            return enrolledStudent;
        }
        return null;

    }

    public List<EnrolledStudent> getGroupActivityEnrolledStudents(int groupActivityId) {
        List<EnrolledStudent> enrolledStudents = studyGroupDao.getGroupActivityEnrolledStudents(groupActivityId);
        enrolledStudents.forEach(enrolledStudent -> enrolledStudent.setStudent(
                studentService.getStudentById(enrolledStudent.getStudent().getId()).get()
        ));

        return enrolledStudents;
    }

    public List<Student> getStudyGroupSuggestions(int studyGroupId) {
        List<Student> studentList = studyGroupDao.getSuggestionsForStudyGroup(studyGroupId);
        studentList.forEach(student -> student.setUser(userService.getUserById(student.getUser().getId()).get()));
        return studentList;
    }

    public int deleteStudyGroup(int id) {
        return studyGroupDao.deleteStudyGroup(id);
    }

    public int quitGroupActivity(int id) {
        return studyGroupDao.quitGroupActivity(id);
    }

    public int quitStudyGroup(int id) {
        return studyGroupDao.quitStudyGroup(id);
    }

    private void setActivityInitiator(StudentActivityGroup studentActivityGroup) {
        studentActivityGroup.setInitiator(studentService.getStudentById(studentActivityGroup.getInitiator().getId()).get());
    }

    private void setStudentOnStudyGroup(StudentStudyGroup studentStudyGroup) {
        studentStudyGroup.setStudent(studentService.getStudentById(studentStudyGroup.getStudent().getId()).get());
    }

    private void setStudyGroupCompleteObject(StudentStudyGroup studentStudyGroup) {
        studentStudyGroup.setStudyGroup(getStudyGroupById(studentStudyGroup.getStudyGroup().getId()));
    }

    private void setActivityProfessor(StudentActivityGroup studentActivityGroup) {
        if (studentActivityGroup.getProfessor().getId() != 0) {
            studentActivityGroup.setProfessor(professorService.getProfessorById(studentActivityGroup.getProfessor().getId()).get());
        } else
            studentActivityGroup.setProfessor(null);
    }

    private void updateActivityState(StudentActivityGroup studentActivityGroup) {
        if (studentActivityGroup.getState() != GroupActivityState.SOON)
            return;
        List<EnrolledStudent> enrolledStudents = getGroupActivityEnrolledStudents(studentActivityGroup.getId());
        int noOfEnrolledStudents = enrolledStudents.size();
        int cmp = studentActivityGroup.getEnrollmentDeadline().toLocalDateTime().compareTo(LocalDateTime.now());
        if (cmp < 0 && noOfEnrolledStudents < studentActivityGroup.getMinAttendees()) {
            studentActivityGroup.setState(GroupActivityState.CANCELLED);
            studyGroupDao.updateGroupActivity(studentActivityGroup);
        }
        int cmp2 = studentActivityGroup.getStartDate().toLocalDateTime().compareTo(LocalDateTime.now());
        if (cmp2 < 0) {
            studentActivityGroup.setState(GroupActivityState.PAST);
            studyGroupDao.updateGroupActivity(studentActivityGroup);
        }
    }


    private boolean groupActivityOverlapsSchedule(StudentActivityGroup studentActivityGroup, List<StudentActivity> studentActivities) {
        LocalDateTime startTime1 = studentActivityGroup.getStartDate().toLocalDateTime();
        long duration1InSeconds = (long) (studentActivityGroup.getDuration() * 60 * 60);
        LocalDateTime endTime1 = startTime1.plusSeconds(duration1InSeconds);

        for (StudentActivity studentActivity : studentActivities) {
            Activity activity = activityService.getActivityById(studentActivity.getActivity().getId());

            int weekDay = activity.getWeekDay();
            DayOfWeek weekDay2 = startTime1.getDayOfWeek();

            if (weekDay == weekDay2.getValue() - 1) {
                LocalTime startTime2 = activity.getTime().toLocalTime();
                long duration2InSeconds = (long) (activity.getDuration() * 60 * 60);
                LocalTime endTime2 = startTime2.plusSeconds(duration2InSeconds);
                int cmp1 = startTime1.toLocalTime().compareTo(endTime2);
                int cmp2 = endTime1.toLocalTime().compareTo(startTime2);

                if (!(cmp1 > 0 || cmp2 < 0)) {
                    return true;
                }

            }
        }
        return false;
    }
}
