class Person{
    constructor(personId, firstName, middleName, lastName, username, password, email, phoneNumber, address, dateOfBirth, isAdmin, isFirstLogin, authorities){
        this.personId = personId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isAdmin = isAdmin;
        this.isFirstLogin = isFirstLogin;
        this.authorities = authorities;
    }

}

/**
 * 
 * 
  {
    "personId":552,
    "firstName":"Charles",
    "middleName":"",
    "lastName":"Thomas",
    "username":"user",
    "password":"$2a$10$lTVNRR1UBPMwKmqxUtQe6e/9rlw6LtFC6JgKFr1BpsHbk2qK18Dpi",
    "email":"charles.thomas@example.com",
    "phoneNumber":"1234567890",
    "address":"BLR",
    "dateOfBirth":"01/01/1999",
    "isAdmin":false,
    "isFirstLogin":true,
    "authorities":[{"authority":"ROLE_USER"}],
    "accountNonExpired":true,
    "accountNonLocked":true,
    "credentialsNonExpired":true,
    "enabled":true
   }
 */