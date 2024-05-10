package com.ispan.recordshop.cochordnuts.service;

import java.util.Date;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.util.DatetimeConverter;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    public boolean existByEmail(String email) {
        if (email != null && email.length() != 0) {
            long result = memberRepo.countByEmail(email);
            if (result != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean existByPhone(String phone) {
        if (phone != null && phone.length() != 0) {
            long result = memberRepo.countByPhone(phone);
            if (result != 0) {
                return true;
            }
        }
        return false;
    }

    // 登入
    public Member login(String email, String password) {
        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            Optional<Member> optional = memberRepo.findByEmail(email);
            if (optional.isPresent()) {
                Member member = optional.get();
                String storedPasswordHash = member.getPassword();

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, storedPasswordHash)) {
                    return member;
                }
            }
        }
        return null;
    }

    // 新增
    public Member create(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            String name = obj.isNull("name") ? null : obj.getString("name");
            String email = obj.isNull("email") ? null : obj.getString("email");
            String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
            String address = obj.isNull("address") ? null : obj.getString("address");
            String phone = obj.isNull("phone") ? null : obj.getString("phone");

            if (email != null && phone != null) {
                Optional<Member> optional = memberRepo.findByEmailAndPhone(email, phone);
                if (optional.isEmpty()) {
                    Member insert = new Member();
                    insert.setName(name);
                    String bcryptPassword = bcrypt.encode(obj.getString("password"));
                    insert.setPassword(bcryptPassword);
                    insert.setEmail(email);

                    if (birthday != null && birthday.length() != 0) {
                        java.util.Date temp = DatetimeConverter.parse(birthday, "yyyy-MM-dd");
                        insert.setBirthday(temp);
                    } else {
                        insert.setBirthday(null);
                    }

                    insert.setAddress(address);
                    insert.setRegisterTime(new Date());
                    insert.setPhone(phone);

                    return memberRepo.save(insert);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 登出
    public Member logout(Member member) {
        member.setLastLoginTime(new Date());

        return memberRepo.save(member);
    }

}
