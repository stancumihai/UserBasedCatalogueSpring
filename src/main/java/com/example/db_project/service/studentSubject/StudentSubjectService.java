package com.example.db_project.service.studentSubject;

import com.example.db_project.dao.activity.ActivityDao;
import com.example.db_project.dao.userSubject.UserSubjectDao;
import com.example.db_project.model.course.*;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.users.Session;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
import com.example.db_project.service.activity.ActivityService;
import com.example.db_project.service.auth.AuthService;
import com.example.db_project.service.auth.SessionService;
import com.example.db_project.service.professor.ProfessorService;
import com.example.db_project.service.professorSubject.ProfessorSubjectService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentSubjectService {
    private final UserSubjectDao userSubjectDao;
    private final AuthService authService;
    private final ActivityDao activityDao;
    private final ProfessorSubjectService professorSubjectService;
    private final SessionService sessionService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final ActivityService activityService;
    private final ProfessorService professorService;

    @Autowired
    public StudentSubjectService(@Qualifier("UserSubjectRepo") UserSubjectDao userSubjectDao,
                                 AuthService authService, ActivityDao activityDao, ProfessorSubjectService professorService, SessionService sessionService, StudentService studentService,
                                 SubjectService subjectService, ActivityService activityService, ProfessorService professorService1) {
        this.authService = authService;
        this.userSubjectDao = userSubjectDao;
        this.activityDao = activityDao;
        this.professorSubjectService = professorService;
        this.sessionService = sessionService;
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.activityService = activityService;
        this.professorService = professorService1;
    }

    public int enrollStudentToSubject(int subjectId, String sessionKey) {
        Optional<Session> session = sessionService.getSessionBySessionKey(sessionKey);
        if (session.isEmpty() || !authService.isStudent(sessionKey)) {
            return -2;
        }

        Optional<Student> student = studentService.getStudentByUserId(session.get().getUserId());
        Subject subject = subjectService.getSubjectById(subjectId);
        List<SubjectStudent> subjectStudents = getSubjectStudentsBySubjectId(subjectId);
        if (subject.getMaxStudents() < subjectStudents.size())
            return -1;
        if (!assignStudentToActivities(student.get(), subject))
            return 0;

        return userSubjectDao.enrollStudentToSubject(subjectId, student.get().getId());
    }

    public boolean getEnrolledStatus(int subjectId, int studentId) {
        return userSubjectDao.isEnrolledToSubject(subjectId, studentId);
    }

    public List<SubjectStudent> getStudentSubjects(User user) {
        Optional<Student> student = studentService.getStudentByUserId(user.getId());
        if (student.isEmpty())
            return null;

        return userSubjectDao.getStudentSubjectsByStudentId(student.get().getId());
    }

    public List<StudentActivity> getStudentActivities(User user) {
        Optional<Student> student = studentService.getStudentByUserId(user.getId());
        if (student.isEmpty()) {
            return null;
        }
        List<StudentActivity> studentActivityList = userSubjectDao.getStudentActivityByStudentId(student.get().getId());

        for (StudentActivity studentActivity : studentActivityList) {
            studentActivity.setStudent(student.get());
            studentActivity.setActivity(activityService.getActivityById(studentActivity.getActivity().getId()));
        }
        return studentActivityList;
    }

    public int deleteStudentFromSubject(int subjectId, User user) {
        Optional<Student> student = studentService.getStudentByUserId(user.getId());
        if (student.isEmpty()) {
            return 0;
        }
        return userSubjectDao.deleteStudentFromSubject(subjectId, student.get().getId());
    }

    public List<SubjectStudent> getSubjectStudentsBySubjectId(int subjectId) {
        List<SubjectStudent> subjectStudentList = userSubjectDao.getSubjectStudentsBySubjectId(subjectId);
        for (SubjectStudent subjectStudent : subjectStudentList) {
            setSubjectStudentData(subjectStudent);
        }
        return subjectStudentList;
    }

    public void setActivityGrade(float newGrade, int id) {
        userSubjectDao.setActivityGrade(newGrade, id);
    }

    public StudentActivity updateStudentActivity(StudentActivity studentActivity) {
        return userSubjectDao.updateStudentActivity(studentActivity);
    }


    public List<Activity> getAvailableActivities(Student student, Subject subject) {
        List<Activity> activities = getStudentActivityList(student);
        List<Activity> subjectActivities = activityDao.getSubjectActivities(subject.getId());
        List<Activity> availableActivities = new ArrayList<>();
        for (Activity activity : subjectActivities) {
            if (!activityOverlapsSchedule(activities, activity)) {
                availableActivities.add(activity);
            }
        }
        availableActivities.forEach(activity
                -> activity.setProfessor(professorService.getProfessorById(activity.getProfessor().getId()).get()));
        return availableActivities;
    }


    public boolean assignStudentToActivities(Student student, Subject subject) {
        List<Activity> subjectActivities = getAvailableActivities(student, subject);

        subjectActivities.sort(new SortByProfessorNumberOfHours());
        subjectActivities.sort(new SortByActivityDuration());

        boolean hasCourse = subjectHasActivityType(subject, ActivityType.COURSE);
        boolean hasSeminary = subjectHasActivityType(subject, ActivityType.SEMINARY);
        boolean hasLaboratory = subjectHasActivityType(subject, ActivityType.LABORATORY);


        List<Activity> courses = subjectActivities.stream()
                .filter(activity -> activity.getType().equals(ActivityType.COURSE)).collect(Collectors.toList());
        boolean hasAvailableCourses = courses.size() > 0;

        List<Activity> seminaries = subjectActivities.stream()
                .filter(activity -> activity.getType().equals(ActivityType.SEMINARY)).collect(Collectors.toList());
        boolean hasAvailableSeminaries = seminaries.size() > 0;

        List<Activity> laboratories = subjectActivities.stream()
                .filter(activity -> activity.getType().equals(ActivityType.LABORATORY)).collect(Collectors.toList());
        boolean hasAvailableLaboratories = laboratories.size() > 0;

        if (hasCourse && !hasAvailableCourses) return false;
        if (hasSeminary && !hasAvailableSeminaries) return false;
        if (hasLaboratory && !hasAvailableLaboratories) return false;

        List<List<Activity>> lists = new ArrayList<>();
        lists.add(courses);
        lists.add(seminaries);
        lists.add(laboratories);
        List<List<Activity>> result = new ArrayList<>();
        List<Activity> current = new ArrayList<>();
        GeneratePermutations.generatePermutations(lists, result, 0, current);

        List<Activity> validTuple = findValidTuple(result);
        if (validTuple == null) return false;

        for (Activity activity : validTuple) {
            userSubjectDao.enrollStudentToActivity(student.getId(), activity.getId());
        }

        return true;
    }


    private List<Activity> findValidTuple(List<List<Activity>> list) {
        for (List<Activity> tuple : list) {
            boolean isValid = true;
            for (int i = 0; i < tuple.size() - 1; i++) {
                for (int j = i + 1; j < tuple.size(); j++) {
                    Activity activity = tuple.get(i);
                    Activity activity1 = tuple.get(j);
                    if (overlaps(activity1, activity)) {
                        isValid = false;
                    }
                }
            }
            if (isValid) {
                return tuple;
            }
        }
        return null;
    }

    private boolean subjectHasActivityType(Subject subject, ActivityType activityType) {
        List<Activity> subjectActivities = activityDao.getSubjectActivities(subject.getId());
        for (Activity activity : subjectActivities) {
            if (activity.getType().equals(activityType)) {
                return true;
            }
        }

        return false;
    }


    private boolean activityOverlapsSchedule(List<Activity> studentActivities, Activity subjectActivity) {
        boolean overlaps = false;

        if (studentActivities.size() == 0) {
            return false;
        }

        for (Activity activity : studentActivities) {
            if (activity.getId() == subjectActivity.getId()) {
                return true;
            }
            overlaps |= overlaps(activity, subjectActivity);
        }

        return overlaps;
    }


    public boolean overlaps(Activity activity1, Activity activity2) {
        if (activity1.getWeekDay() != activity2.getWeekDay()) return false;
        LocalTime startTime1 = activity1.getTime().toLocalTime();
        LocalTime startTime2 = activity2.getTime().toLocalTime();
        long duration1InSeconds = (long) (activity1.getDuration() * 60 * 60);
        long duration2InSeconds = (long) (activity2.getDuration() * 60 * 60);
        LocalTime endTime1 = startTime1.plusSeconds(duration1InSeconds);
        LocalTime endTime2 = startTime2.plusSeconds(duration2InSeconds);
        int cmp1 = startTime1.compareTo(endTime2);
        int cmp2 = startTime2.compareTo(endTime1);
        return !(cmp1 >= 0 || cmp2 >= 0);
    }

    class SortByProfessorNumberOfHours implements Comparator<Activity> {

        @Override
        public int compare(Activity a, Activity b) {
            return getProfessorNumberOfSeconds(a.getProfessor()) - getProfessorNumberOfSeconds(b.getProfessor());
        }

        private int getProfessorNumberOfSeconds(Professor professor) {
            // getProfessorNumberOfHours: returneaza numarul de studenti al unui prof
            // TODO: modifica numele metodei
            return (int) (professorSubjectService.getProfessorNumberOfHours(professor.getId()) * 60 * 60);
        }
    }

    static class SortByActivityDuration implements Comparator<Activity> {

        @Override
        public int compare(Activity a, Activity b) {
            return (a.getDuration() - b.getDuration()) <= 0.001 ? 0 : (a.getDuration() - b.getDuration()) > 0 ? 1 : -1;
        }
    }

    static class GeneratePermutations {

        public static void generatePermutations(List<List<Activity>> lists, List<List<Activity>> result, int depth,
                                                List<Activity> current) {
            if (depth == lists.size()) {
                List<Activity> copyCurrent = new ArrayList<>(current);
                result.add(copyCurrent);
                return;
            }

            if (lists.get(depth).size() == 0 && depth < lists.size() - 1) {
                generatePermutations(lists, result, depth + 1, current);
            }

            if (lists.get(depth).size() == 0 && depth == lists.size() - 1) {
                List<Activity> copyCurrent = new ArrayList<>(current);
                result.add(copyCurrent);
                return;
            }

            for (int i = 0; i < lists.get(depth).size(); i++) {
                Activity person = lists.get(depth).get(i);
                List<Activity> copyCurrent = new ArrayList<>(current);
                copyCurrent.add(person);
                generatePermutations(lists, result, depth + 1, copyCurrent);
            }

        }
    }


    private List<Activity> getStudentActivityList(Student student) {
        List<StudentActivity> studentActivityList = getStudentActivities(student.getUser());
        List<Activity> activities = new ArrayList<>();
        for (StudentActivity studentActivity : studentActivityList) {
            activities.add(activityDao.getActivityById(studentActivity.getActivity().getId()));
        }
        return activities;
    }


    private void setSubjectStudentData(SubjectStudent subjectStudent) {
        int studentId = subjectStudent.getStudent().getId();
        Optional<Student> student = studentService.getStudentById(studentId);
        subjectStudent.setStudent(student.get());

        int subjectId = subjectStudent.getSubject().getId();
        subjectStudent.setSubject(subjectService.getSubjectById(subjectId));
    }


}
