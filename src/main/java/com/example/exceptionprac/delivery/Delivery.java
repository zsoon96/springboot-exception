package com.example.exceptionprac.delivery;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Address address;

    // mappedBy를 통해 해당 필드의 소유자가 누구인지 명시 (연관관계의 주인은 N = DeliveryLog)
        // mappedBy 값은 반대쪽에 자신이 맵핑되어있는 필드명 입력
    // CascadeType.PERSIST를 통해 특정 엔티티(Delivery)를 영속상태로 만들때, 연관된 엔티티(DeliveryLog)도 함께 영속상태로 만들 수 있음 ( Delivery 저장 시, DeliveryLog도 함께 저장)
    // orphanRemoval을 통해 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제할 수 있도록 함 (@OneToMany / @OneToOne만 가능)
    // 실무에서는 거의 fetchType.LAZY로 사용하지만, 이 예제에서는 로그 정보가 4개 정도이기때문에 EAGER로 설정
    @OneToMany (mappedBy = "delivery", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DeliveryLog> logs = new ArrayList<>(); // 리스트 초기화를 함께 해주어야 null일 경우, NPE 방지해주고 Empty 상태가 더욱 직관적이기 때문!

    @Embedded
    private DateTime dateTime;

}
