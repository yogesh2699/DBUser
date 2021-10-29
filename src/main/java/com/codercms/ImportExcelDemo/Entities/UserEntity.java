package com.codercms.ImportExcelDemo.Entities;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {


    public String firstname;

    public String email;



   // @Column(columnDefinition = "BINARY(16)")
    public String password;
   // public String password;

    public String contact;

    @Id
    public String username;
}
