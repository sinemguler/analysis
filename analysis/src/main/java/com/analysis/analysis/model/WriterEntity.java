package com.analysis.analysis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "writers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "article")
public class WriterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<ArticleEntity> article;
}
