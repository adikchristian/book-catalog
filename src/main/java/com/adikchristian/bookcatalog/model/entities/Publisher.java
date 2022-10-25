package com.adikchristian.bookcatalog.model.entities;

import java.io.Serializable;
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
@Table(name = "publisher")
@SQLDelete(sql = "UPDATE publisher SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Publisher implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Column(name = "address", length = 500, nullable = true)
    private String address;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToMany(mappedBy = "publishers", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Book> books;
}
