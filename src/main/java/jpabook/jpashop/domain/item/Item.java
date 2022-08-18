package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity; //stockQuantity를 변경하려면 setter로 가져오지 말고 비즈니스 로직을 사용해서 변경시킨다

    @ManyToMany(mappedBy = "items")
    private List<Category> cartegories = new ArrayList<>();

    //==비즈니스 로직==//
    /*stock 증가*/
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /*stock 감소*/
    public void removeStock(int quantity) {
        int reStock = this.stockQuantity - quantity;
        if(reStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = reStock;
    }

}
