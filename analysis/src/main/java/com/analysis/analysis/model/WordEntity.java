package com.analysis.analysis.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "words")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
public class WordEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "writer_article_words", joinColumns = @JoinColumn(name = "writer_article_id"))
    @Column(name = "word")
    private List<String> word;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private WriterEntity writer;

}
