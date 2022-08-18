package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; //key

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //order table의 member필드에 맵핑이 된다 (readOnly)
    private List<Order> orders = new ArrayList<>(); // 컬렉션은 필드에서 초기화 >> 컬랙션을 바꾸지말고 그대로 사용해라 그래야 하이버네이트가 작동한다
    //null point exception이 날 일이 없다
}
