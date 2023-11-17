package com.example.minicase.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "question_words")
public class QuestionWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;


    @ManyToOne(fetch = FetchType.LAZY)
    private Word word;

    public QuestionWord(Question question, Word word) {
        this.question = question;
        this.word = word;
    }


}
