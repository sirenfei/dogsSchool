package com.dog.school.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="tb_dogs")
public class Dogs implements Serializable {

    public Dogs() {
        super();
    }
    public Dogs(String name) {
        super();
        this.name = name;
    }
    
    private static final long serialVersionUID = -4743963004615713587L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    @Size(min = 1, max = 255)
    private String name;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
 

}
