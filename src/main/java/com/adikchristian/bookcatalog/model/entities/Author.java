package com.adikchristian.bookcatalog.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Author implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", length = 100)
    private String name;

    @Column(name = "brith_date")
    private LocalDate brithDate;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Book> books;
}
