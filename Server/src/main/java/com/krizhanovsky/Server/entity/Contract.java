package com.krizhanovsky.Server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "number")
    private String number;
    @Column(name = "date_of_creation")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="GMT-4")
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;
    @Column(name = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="GMT-4")
    @Temporal(TemporalType.DATE)
    private Date upDate;
}
