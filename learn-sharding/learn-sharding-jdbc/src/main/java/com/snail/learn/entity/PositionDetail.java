package com.snail.learn.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "position_detail")
public class PositionDetail implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "pid")
    private long pid;
    @Column(name = "description")
    private String description;


}
