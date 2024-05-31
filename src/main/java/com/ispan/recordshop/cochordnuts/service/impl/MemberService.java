package com.ispan.recordshop.cochordnuts.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.MemberDTO;
import com.ispan.recordshop.cochordnuts.model.CustomerCase;
import com.ispan.recordshop.cochordnuts.model.Favorite;
import com.ispan.recordshop.cochordnuts.model.FavoriteId;
import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.repository.CustomerCaseRepository;
import com.ispan.recordshop.cochordnuts.repository.FavoriteRepository;
import com.ispan.recordshop.cochordnuts.repository.MemberRepository;
import com.ispan.recordshop.cochordnuts.repository.OrderRepository;
import com.ispan.recordshop.cochordnuts.repository.ProductStyleRepository;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductStyleRepository ProStyleRepo;

    @Autowired
    private FavoriteRepository favoriteRepo;

    @Autowired
    private CustomerCaseRepository caseRepo;

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

    public boolean existById(Integer memberNo) {
        if (memberNo != null) {
            return memberRepo.existsById(memberNo);
        }
        return false;
    }

    // findAll start
    public long count(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            return memberRepo.count(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Member> find(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            return memberRepo.find(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    // findAll end

    public Member findById(Integer memberNo) {
        if (memberNo != null) {
            Optional<Member> optional = memberRepo.findById(memberNo);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }

    // // orders
    // public long countOrders(String json) {
    // try {
    // JSONObject obj = new JSONObject(json);
    // return memberRepo.countOrders(obj);
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // return 0;
    // }

    // public List<Orders> findOrders(String json) {
    // try {
    // JSONObject obj = new JSONObject(json);
    // return memberRepo.findOrders(obj);
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // public List<Orders> findOrdersById(String json) {
    // try {
    // JSONObject obj = new JSONObject(json);
    // return memberRepo.findOrders(obj);
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // public List<Orders> findByMemberNo(Integer memberNo) {
    // if (memberNo != null) {
    // // Integer memberNo = member.getMemberNo();

    // return orderRepo.findByMemberNo(memberNo);
    // }
    // return null;
    // }

    //

    // 查詢會員客服
    public List<CustomerCase> findCaseByMemberNo(Integer memberNo) {
        if (memberNo != null) {
            // Integer memberNo = member.getMemberNo();

            return caseRepo.findByMemberNo(memberNo);
        }
        return null;
    }
    //

    public String getPhoneByEmail(String email) {
        Optional<Member> optional = memberRepo.findByEmail(email);
        if (optional.isPresent()) {
            Member member = optional.get();
            return member.getPhone();
        } else {
            return null;
        }
    }

    public String getPhoneById(Integer memberNo) {
        Optional<Member> optional = memberRepo.findById(memberNo);
        if (optional.isPresent()) {
            Member member = optional.get();
            return member.getPhone();
        } else {
            return null;
        }
    }

    public String getEmailById(Integer memberNo) {
        Optional<Member> optional = memberRepo.findById(memberNo);
        if (optional.isPresent()) {
            Member member = optional.get();
            return member.getEmail();
        } else {
            return null;
        }
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
    public Member create(MemberDTO memberRequest) {
        try {
            String name = memberRequest.getName();
            String email = memberRequest.getEmail();
            String birthday = memberRequest.getBirthday();
            String address = memberRequest.getAddress();
            String phone = memberRequest.getPhone();
            List<Integer> favoriteIds = memberRequest.getFavoriteIds();

            if (email != null && phone != null) {
                Optional<Member> optional = memberRepo.findByEmailAndPhone(email, phone);
                if (optional.isEmpty()) {
                    Member insert = new Member();
                    insert.setName(name);
                    String bcryptPassword = bcrypt.encode(memberRequest.getPassword());
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
                    insert.setMemberStatus(1);
                    Member savedMember = memberRepo.save(insert);

                    for (Integer productStyleId : favoriteIds) {
                        Favorite favorite = new Favorite();
                        FavoriteId favoriteId = new FavoriteId();
                        favoriteId.setMemberId(savedMember.getMemberNo());
                        favoriteId.setProductStyleId(productStyleId);
                        favorite.setFavoriteId(favoriteId);
                        favorite.setMember(savedMember);
                        favorite.setProductStyle(ProStyleRepo.findById(productStyleId).orElse(null));
                        favoriteRepo.save(favorite);
                    }
                    return savedMember;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // public Member create(String json, List<Integer> favoriteIds) {
    // try {
    // JSONObject obj = new JSONObject(json);
    // String name = obj.isNull("name") ? null : obj.getString("name");
    // String email = obj.isNull("email") ? null : obj.getString("email");
    // String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
    // String address = obj.isNull("address") ? null : obj.getString("address");
    // String phone = obj.isNull("phone") ? null : obj.getString("phone");

    // if (email != null && phone != null) {
    // Optional<Member> optional = memberRepo.findByEmailAndPhone(email, phone);
    // if (optional.isEmpty()) {
    // Member insert = new Member();
    // insert.setName(name);
    // String bcryptPassword = bcrypt.encode(obj.getString("password"));
    // insert.setPassword(bcryptPassword);
    // insert.setEmail(email);

    // if (birthday != null && birthday.length() != 0) {
    // java.util.Date temp = DatetimeConverter.parse(birthday, "yyyy-MM-dd");
    // insert.setBirthday(temp);
    // } else {
    // insert.setBirthday(null);
    // }

    // insert.setAddress(address);

    // insert.setRegisterTime(new Date());
    // insert.setPhone(phone);
    // Member savedMember = memberRepo.save(insert);

    // for (Integer productStyleId : favoriteIds) {
    // Favorite favorite = new Favorite();
    // FavoriteId favoriteId = new FavoriteId();
    // favoriteId.setMemberId(savedMember.getMemberNo());
    // favoriteId.setProductStyleId(productStyleId);
    // favorite.setFavoriteId(favoriteId);
    // favorite.setMember(savedMember);
    // favorite.setProductStyle(ProStyleRepo.findById(productStyleId).orElse(null));
    // favoriteRepo.save(favorite);
    // }
    // return savedMember;
    // }
    // }
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // 登出
    public Member logout(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer memberNo = obj.isNull("memberNo") ? null : obj.getInt("memberNo");

            if (memberNo != null) {
                Optional<Member> optional = memberRepo.findById(memberNo);
                if (optional.isPresent()) {
                    Member update = optional.get();
                    String lastLoginTime = obj.isNull("lastLoginTime") ? null : obj.getString("lastLoginTime");
                    java.util.Date loginTime = DatetimeConverter.parse(lastLoginTime, "yyyy-MM-dd HH:mm:ss");
                    update.setLastLoginTime(loginTime);
                    return memberRepo.save(update);
                } else {
                    return null;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改個人資料
    public Member modify(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer memberNo = obj.isNull("memberNo") ? null : obj.getInt("memberNo");
            String name = obj.isNull("name") ? null : obj.getString("name");
            String email = obj.isNull("email") ? null : obj.getString("email");
            String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
            String address = obj.isNull("address") ? null : obj.getString("address");
            String phone = obj.isNull("phone") ? null : obj.getString("phone");
            String recipient = obj.isNull("recipient") ? null : obj.getString("recipient");
            String recipientAddress = obj.isNull("recipientAddress") ? null : obj.getString("recipientAddress");
            String recipientPhone = obj.isNull("recipientPhone") ? null : obj.getString("recipientPhone");

            if (memberNo != null) {
                Optional<Member> optional = memberRepo.findById(memberNo);
                if (optional.isPresent()) {
                    Member update = optional.get();
                    update.setName(name);
                    update.setEmail(email);
                    update.setAddress(address);
                    update.setPhone(phone);
                    if (birthday != null && birthday.length() != 0) {
                        java.util.Date temp = DatetimeConverter.parse(birthday, "yyyy-MM-dd");
                        update.setBirthday(temp);
                    } else {
                        update.setBirthday(null);
                    }
                    update.setRecipient(recipient);
                    update.setRecipientAddress(recipientAddress);
                    update.setRecipientPhone(recipientPhone);
                    return memberRepo.save(update);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 修改密碼
    public Member modifyPwd(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            Integer memberNo = obj.isNull("memberNo") ? null : obj.getInt("memberNo");
            String oriPassword = obj.isNull("oriPassword") ? null : obj.getString("oriPassword");
            System.out.println("memberNo=" + memberNo + "/ori=" + oriPassword);
            if (memberNo != null) {
                Optional<Member> optional = memberRepo.findById(memberNo);
                if (optional.isPresent()) {
                    Member member = optional.get();
                    String storedPasswordHash = member.getPassword();
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    if (passwordEncoder.matches(oriPassword, storedPasswordHash)) {
                        String bcryptPassword = bcrypt.encode(obj.getString("password"));
                        member.setPassword(bcryptPassword);
                        return memberRepo.save(member);
                    } else {
                        return null;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 刪除，需要確認密碼
    public boolean delete(Integer memberNo, String password) {
        if (memberNo != null) {
            Optional<Member> optional = memberRepo.findById(memberNo);
            if (optional.isPresent()) {
                Member member = optional.get();
                String storedPasswordHash = member.getPassword();

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, storedPasswordHash)) {
                    member.setMemberStatus(0);
                    memberRepo.save(member);
                    return true;
                }
            }
        }
        return false;
    }

    public Member modifyRecipient(Integer memberNo, String json) {
        JSONObject obj = new JSONObject(json);

        String recipient = obj.isNull("recipient") ? null : obj.getString("recipient");
        String recipientPhone = obj.isNull("recipientPhone") ? null : obj.getString("recipientPhone");

        if (memberNo != null) {
            Member optional = memberRepo.findById(memberNo).get();
            if (optional != null) {
                optional.setRecipient(recipient);
                optional.setRecipientPhone(recipientPhone);
            }
            return memberRepo.save(optional);
        }

        return null;
    }

}
