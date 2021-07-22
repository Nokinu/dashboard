package com.czhang.dashboard.dashboard.respositories;

import com.czhang.dashboard.dashboard.model.User;
import com.czhang.dashboard.dashboard.model.UserRoleType;
import com.czhang.dashboard.dashboard.repositories.UserRespository;
import com.czhang.dashboard.dashboard.utils.UtilsFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("integratedTest")
@ExtendWith(SpringExtension.class)
public class UserRespositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRespository userRespository;

    @Test
    @DisplayName("Find user info by username")
    @Order(1)
    public void findUserByUsername_positive() {
        //Given
       User user = UtilsFactory.createUser(null);
       testEntityManager.persistAndFlush(user);

       //When
       User actualUser = userRespository.findByUsername("Admin");

       //Then
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    @DisplayName("Save User Data")
    @Order(2)
    public void saveUser_positive(){
        //Given
        User user = UtilsFactory.createUser(null);

        //When
        User actualUser = userRespository.save(user);

        //Then
        assertThat(actualUser.getId()).isNotNull();
        assertThat(actualUser.getUsername()).isEqualTo("Admin");
    }
}
