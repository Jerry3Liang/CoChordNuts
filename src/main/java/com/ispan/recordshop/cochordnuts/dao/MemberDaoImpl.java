package com.ispan.recordshop.cochordnuts.dao;

import com.ispan.recordshop.cochordnuts.model.Member;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class MemberDaoImpl implements MemberDao {

    @PersistenceContext
    private Session session;

    public Session getSession() {
        return this.session;
    }

    @Override
    public long count(JSONObject obj) {

        Integer memberNo = obj.isNull("memberNo") ? null : obj.getInt("memberNo");
        String name = obj.isNull("name") ? null : obj.getString("name");
        String password = obj.isNull("password") ? null : obj.getString("password");
        String email = obj.isNull("email") ? null : obj.getString("email");
        String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
        String address = obj.isNull("address") ? null : obj.getString("address");
        String registerTime = obj.isNull("registerTime") ? null : obj.getString("registerTime");
        String lastLoginTime = obj.isNull("lastLoginTime") ? null : obj.getString("lastLoginTime");
        String phone = obj.isNull("phone") ? null : obj.getString("phone");
        String recipient = obj.isNull("recipient") ? null : obj.getString("recipient");
        String recipientAddress = obj.isNull("recipientAddress") ? null : obj.getString("recipientAddress");
        String recipientPhone = obj.isNull("recipientPhone") ? null : obj.getString("recipientPhone");

        CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        // from product
        Root<Member> table = criteriaQuery.from(Member.class);

        // select count(*)
        criteriaQuery = criteriaQuery.select(criteriaBuilder.count(table));

        // where start
        List<Predicate> predicates = new ArrayList<>();

        if (memberNo != null) {
            Predicate p = criteriaBuilder.equal(table.get("memberNo"), memberNo);
            predicates.add(p);
        }
        if (name != null && name.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("name"), "%" + name + "%"));
        }
        if (email != null && email.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("email"), "%" + email +
                    "%"));
        }

        if (password != null && password.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("password"), "%" + password +
                    "%"));
        }

        if (address != null && address.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("address"), "%" + address +
                    "%"));
        }

        if (phone != null && phone.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("phone"), "%" + phone +
                    "%"));
        }

        if (recipient != null && recipient.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("recipient"), "%" + recipient +
                    "%"));
        }

        if (recipientAddress != null && recipientAddress.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("recipientAddress"), "%" + recipientAddress +
                    "%"));
        }

        if (recipientPhone != null && recipientPhone.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("recipientPhone"), "%" + recipientPhone +
                    "%"));
        }

        if (birthday != null && birthday.length() != 0) {
            java.util.Date temp = DatetimeConverter.parse(birthday, "yyyy-MM-dd");
            predicates.add(criteriaBuilder.equal(table.get("birthday"), temp));
        }
        if (registerTime != null && registerTime.length() != 0) {
            java.util.Date temp = DatetimeConverter.parse(registerTime, "yyyy-MM-dd");
            predicates.add(criteriaBuilder.equal(table.get("registerTime"), temp));
        }
        if (lastLoginTime != null && lastLoginTime.length() != 0) {
            java.util.Date temp = DatetimeConverter.parse(lastLoginTime, "yyyy-MM-dd");
            predicates.add(criteriaBuilder.equal(table.get("lastLoginTime"), temp));
        }

        if (predicates != null && !predicates.isEmpty()) {
            Predicate[] array = predicates.toArray(new Predicate[0]);
            criteriaQuery = criteriaQuery.where(array);
        }
        // where end

        TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
        Long result = typedQuery.getSingleResult();
        if (result != null) {
            return result.longValue();
        } else {
            return 0;
        }
    }

    @Override
    public List<Member> find(JSONObject obj) {
        Integer memberNo = obj.isNull("memberNo") ? null : obj.getInt("memberNo");
        String name = obj.isNull("name") ? null : obj.getString("name");
        String password = obj.isNull("password") ? null : obj.getString("password");
        String email = obj.isNull("email") ? null : obj.getString("email");
        String birthday = obj.isNull("birthday") ? null : obj.getString("birthday");
        String address = obj.isNull("address") ? null : obj.getString("address");
        String registerTime = obj.isNull("registerTime") ? null : obj.getString("registerTime");
        String lastLoginTime = obj.isNull("lastLoginTime") ? null : obj.getString("lastLoginTime");
        String phone = obj.isNull("phone") ? null : obj.getString("phone");
        String recipient = obj.isNull("recipient") ? null : obj.getString("recipient");
        String recipientAddress = obj.isNull("recipientAddress") ? null : obj.getString("recipientAddress");
        String recipientPhone = obj.isNull("recipientPhone") ? null : obj.getString("recipientPhone");

        int start = obj.isNull("start") ? 0 : obj.getInt("start");
        int rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
        String order = obj.isNull("order") ? "id" : obj.getString("order");
        boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");

        CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
        CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);

        // from product
        Root<Member> table = criteriaQuery.from(Member.class);

        // where start
        List<Predicate> predicates = new ArrayList<>();
        if (memberNo != null) {
            Predicate p = criteriaBuilder.equal(table.get("memberNo"), memberNo);
            predicates.add(p);
        }
        if (name != null && name.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("name"), "%" + name + "%"));
        }
        if (email != null && email.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("email"), "%" + email +
                    "%"));
        }

        if (password != null && password.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("password"), "%" + password +
                    "%"));
        }

        if (address != null && address.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("address"), "%" + address +
                    "%"));
        }

        if (phone != null && phone.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("phone"), "%" + phone +
                    "%"));
        }

        if (recipient != null && recipient.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("recipient"), "%" + recipient +
                    "%"));
        }

        if (recipientAddress != null && recipientAddress.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("recipientAddress"), "%" + recipientAddress +
                    "%"));
        }

        if (recipientPhone != null && recipientPhone.length() != 0) {
            predicates.add(criteriaBuilder.like(table.get("recipientPhone"), "%" + recipientPhone +
                    "%"));
        }

        if (birthday != null && birthday.length() != 0) {
            java.util.Date temp = DatetimeConverter.parse(birthday, "yyyy-MM-dd");
            predicates.add(criteriaBuilder.equal(table.get("birthday"), temp));
        }
        if (registerTime != null && registerTime.length() != 0) {
            java.util.Date temp = DatetimeConverter.parse(registerTime, "yyyy-MM-dd");
            predicates.add(criteriaBuilder.equal(table.get("registerTime"), temp));
        }
        if (lastLoginTime != null && lastLoginTime.length() != 0) {
            java.util.Date temp = DatetimeConverter.parse(lastLoginTime, "yyyy-MM-dd");
            predicates.add(criteriaBuilder.equal(table.get("lastLoginTime"), temp));
        }

        if (predicates != null && !predicates.isEmpty()) {
            Predicate[] array = predicates.toArray(new Predicate[0]);
            criteriaQuery = criteriaQuery.where(array);
        }
        // where end

        // Order By
        if (dir) {
            criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get(order)));
        } else {
            criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(order)));
        }

        TypedQuery<Member> typedQuery = this.getSession().createQuery(criteriaQuery)
                .setFirstResult(start)
                .setMaxResults(rows);

        List<Member> result = typedQuery.getResultList();
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
}
