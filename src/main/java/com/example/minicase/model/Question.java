package com.example.minicase.model;

import com.example.minicase.model.enums.ECategory;
import com.example.minicase.model.enums.EType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vietnamese;

    private String english;

    private String content;

    @Enumerated(EnumType.STRING)
    private EType type;

    @Enumerated(EnumType.STRING)
    private ECategory category;


    @OneToMany(mappedBy = "question")
    private List<QuestionWord> questionWords;

    @OneToMany(mappedBy = "question")
    private List<CustomerQuestion> customerQuestions;

    public Question(Long id) {
        this.id = id;
    }


}
