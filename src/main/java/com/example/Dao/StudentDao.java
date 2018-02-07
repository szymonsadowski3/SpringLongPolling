package com.example.Dao;

import com.example.Entity.Student;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentDao {

    private static Map<Integer, Student> students;

    static {
        students = new HashMap<Integer, Student>() {
            {
                put(1, new Student(1, "Said", "Computer Science"));
                put(2, new Student(1, "Alex", "Finance"));
                put(3, new Student(1, "Anna", "Math"));
            }
        };
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Student getStudentById(int id) {
        return students.get(id);
    }

    public void removeStudentById(int id) {
        students.remove(id);
    }

    public void updateStudent(Student student) {
        Student oldStudent = students.get(student.getId());
        oldStudent.setCourse(student.getCourse());
        oldStudent.setName(student.getName());
        students.put(student.getId(), student);
    }

    public void insertStudentToDb(Student student) {
        students.put(student.getId(), student);
    }
}
