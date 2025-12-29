package com.quiz.config;

import com.quiz.model.*;
import com.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles
        if (roleRepository.count() == 0) {
            Role userRole = new Role(ERole.ROLE_USER);
            Role adminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }

        // Create admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", "admin@quiz.com", passwordEncoder.encode("admin123"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow());
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Create sample user
        if (!userRepository.existsByUsername("user")) {
            User user = new User("user", "user@quiz.com", passwordEncoder.encode("user123"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow());
            user.setRoles(roles);
            userRepository.save(user);
        }

        // Create sample Java quiz with 20 questions
        if (quizRepository.count() == 0) {
            Quiz javaQuiz = new Quiz();
            javaQuiz.setTitle("Java Programming Quiz");
            javaQuiz.setDescription("Test your knowledge of Java programming concepts");
            javaQuiz.setCategory("Programming");
            javaQuiz.setDifficultyLevel(DifficultyLevel.MEDIUM);
            javaQuiz.setTimeLimitMinutes(30);
            Quiz savedQuiz = quizRepository.save(javaQuiz);

            // Question 1
            Question q1 = new Question(
                    "What is the default value of a boolean variable in Java?",
                    Arrays.asList("true", "false", "null", "0"),
                    1,
                    1
            );
            q1.setQuiz(savedQuiz);

            // Question 2
            Question q2 = new Question(
                    "Which keyword is used to inherit a class in Java?",
                    Arrays.asList("implements", "extends", "inherits", "super"),
                    1,
                    1
            );
            q2.setQuiz(savedQuiz);

            // Question 3
            Question q3 = new Question(
                    "What is the size of int in Java?",
                    Arrays.asList("16 bits", "32 bits", "64 bits", "Depends on platform"),
                    1,
                    1
            );
            q3.setQuiz(savedQuiz);

            // Question 4
            Question q4 = new Question(
                    "Which method is used to start a thread in Java?",
                    Arrays.asList("run()", "start()", "begin()", "execute()"),
                    1,
                    1
            );
            q4.setQuiz(savedQuiz);

            // Question 5
            Question q5 = new Question(
                    "What is the output of: System.out.println(5 + 3 + \"Java\");",
                    Arrays.asList("53Java", "8Java", "5+3Java", "Error"),
                    1,
                    1
            );
            q5.setQuiz(savedQuiz);

            // Question 6
            Question q6 = new Question(
                    "Which of these is NOT a primitive data type in Java?",
                    Arrays.asList("int", "String", "char", "boolean"),
                    1,
                    1
            );
            q6.setQuiz(savedQuiz);

            // Question 7
            Question q7 = new Question(
                    "What does JVM stand for?",
                    Arrays.asList("Java Virtual Machine", "Java Variable Method", "Java Virtual Memory", "Just Very Modern"),
                    0,
                    1
            );
            q7.setQuiz(savedQuiz);

            // Question 8
            Question q8 = new Question(
                    "Which collection class allows duplicate elements?",
                    Arrays.asList("Set", "Map", "List", "TreeMap"),
                    2,
                    1
            );
            q8.setQuiz(savedQuiz);

            // Question 9
            Question q9 = new Question(
                    "What is the parent class of all classes in Java?",
                    Arrays.asList("Object", "Class", "Super", "Parent"),
                    0,
                    1
            );
            q9.setQuiz(savedQuiz);

            // Question 10
            Question q10 = new Question(
                    "Which access modifier provides the widest accessibility?",
                    Arrays.asList("private", "protected", "default", "public"),
                    3,
                    1
            );
            q10.setQuiz(savedQuiz);

            // Question 11
            Question q11 = new Question(
                    "What is the purpose of the 'finally' block?",
                    Arrays.asList("To handle exceptions", "To execute code after try-catch", "To declare variables", "To create loops"),
                    1,
                    1
            );
            q11.setQuiz(savedQuiz);

            // Question 12
            Question q12 = new Question(
                    "Which method is used to compare two strings in Java?",
                    Arrays.asList("compare()", "equals()", "compareTo()", "Both equals() and compareTo()"),
                    3,
                    1
            );
            q12.setQuiz(savedQuiz);

            // Question 13
            Question q13 = new Question(
                    "What is method overriding?",
                    Arrays.asList("Defining a method with same name in same class", "Defining a method in subclass with same signature", "Calling a method multiple times", "Renaming a method"),
                    1,
                    1
            );
            q13.setQuiz(savedQuiz);

            // Question 14
            Question q14 = new Question(
                    "Which interface does ArrayList implement?",
                    Arrays.asList("Set", "Map", "List", "Queue"),
                    2,
                    1
            );
            q14.setQuiz(savedQuiz);

            // Question 15
            Question q15 = new Question(
                    "What is the purpose of 'static' keyword?",
                    Arrays.asList("To make a method final", "To create a constant", "To allow access without creating object", "To prevent inheritance"),
                    2,
                    1
            );
            q15.setQuiz(savedQuiz);

            // Question 16
            Question q16 = new Question(
                    "Which operator is used for object instantiation?",
                    Arrays.asList("new", "create", "instance", "make"),
                    0,
                    1
            );
            q16.setQuiz(savedQuiz);

            // Question 17
            Question q17 = new Question(
                    "What is polymorphism in Java?",
                    Arrays.asList("Having multiple methods with same name", "Ability of object to take many forms", "Creating multiple objects", "Using multiple classes"),
                    1,
                    1
            );
            q17.setQuiz(savedQuiz);

            // Question 18
            Question q18 = new Question(
                    "What is the default value of an object reference?",
                    Arrays.asList("null", "0", "empty", "undefined"),
                    0,
                    1
            );
            q18.setQuiz(savedQuiz);

            // Question 19
            Question q19 = new Question(
                    "Which package contains the Scanner class?",
                    Arrays.asList("java.lang", "java.util", "java.io", "java.net"),
                    1,
                    1
            );
            q19.setQuiz(savedQuiz);

            // Question 20
            Question q20 = new Question(
                    "What is the result of: int x = 10; System.out.println(x++);",
                    Arrays.asList("10", "11", "12", "Error"),
                    0,
                    1
            );
            q20.setQuiz(savedQuiz);

            questionRepository.saveAll(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10,
                    q11, q12, q13, q14, q15, q16, q17, q18, q19, q20));
        }
    }
}

