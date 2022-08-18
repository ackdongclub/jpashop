package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //내장타입
@Getter //값타입은 변경이 되면 안된다. > 생성 시만 값이 만들어지고 변경이 절대 안되게 한다.
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { //이러한 생성자를 생성할 때 jpa가 리플렉션이나 프록시 등의 기술을 사용해야 하는데 안될 때는 기본 생성자(public / protected)를 만들어준다
    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }


}
