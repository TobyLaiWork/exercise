package com.todolist.staff;

import com.todolist.entity.Staff;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;


    public Staff login(Staff staff) {
        //Assume no password validation
        return staffRepository.findByEmail(staff.getEmail());
    }

    public Staff get(Integer id){
        Optional<Staff> staff = staffRepository.findById(id);

        return staff.isPresent()? staff.get() : null;
    }

    public Staff get(String idString){
        return this.get(Integer.parseInt(idString));
    }


    public List<Staff> getAllStaff() {
        return (List<Staff>) staffRepository.findAll();
    }

    public Staff save(Staff staff){
        return staffRepository.save(staff);
    }

    public Staff getStaffFromCookie(HttpServletRequest request){
        Cookie staffIdCookie = WebUtils.getCookie(request, "staffId");
        if (staffIdCookie == null){
            return null;
        }
        Staff staff = get(staffIdCookie.getValue());
        return staff;
    }

}
