package com.fpt.duantn.ui.model.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerRequest {

    private boolean emailVerificationStatus;

    private String firstName;

    private String lastName;

    private String email;

    private String emailVerificationToken;

    private String encryptedPassword;

    private int gender;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String phoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}
