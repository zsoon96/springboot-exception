package com.example.exceptionprac.delivery;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    @NotEmpty
    @Column(name = "address1", nullable = false)
    private String address1;

    @NotEmpty
    @Column(name = "address2", nullable = false)
    private String address2;

    @NotEmpty
    @Column(name = "zip", nullable = false)
    private String zip;

    @Builder
    public Address(String address1, String address2, String zip) {
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
    }
}
