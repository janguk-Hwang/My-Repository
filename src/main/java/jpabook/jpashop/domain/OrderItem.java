package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")       //열의 이름은 item_id이고 Item 클래스에서의 주키를 가져온다.
    private Item item;                  //@JoinColumn은 다른 엔티티의 주 키를 가져와서 외부 키로 참조한다는 의미, 새로 생기는 열이므로 이름을 정해야 한다.
                                        //만약 name=""을 따로 정하지 않으면 외부 키의 열 이름으로 정해진다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격

    private int count; //주문

    //생성 메소드//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //비즈니스 로직//
    public void cancel(){
        getItem().addStock(count);
    }

    //조회 로직//
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
