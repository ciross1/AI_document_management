package com.ciro.AI_document_danagemnt.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String fileName;



    @Column
    String filePath;

    @Column
    Long fileSize;

    @Column
    Date uploadTime;



}
