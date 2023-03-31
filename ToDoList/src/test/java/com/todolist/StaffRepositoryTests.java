package com.todolist;

import com.todolist.entity.Staff;
import com.todolist.staff.StaffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class StaffRepositoryTests {
    @Autowired
    private StaffRepository repo;
    @Test
    public void testCreateStaff(){
        Staff s1 = new Staff("test@test.com", "password", "Staff 1", true, false);
        Staff savedUser = repo.save(s1);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateAdminStaff(){
        Staff s2 = new Staff("testAdmin@test.com", "password", "Admin Staff 1", true, true);
        Staff savedUser = repo.save(s2);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testfindByEmail(){
        Staff s =repo.findByEmail("test@test.com");
        assertThat(s.getId()).isGreaterThan(0);
    }





}
