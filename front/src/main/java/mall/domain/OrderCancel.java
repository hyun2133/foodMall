package mall.domain;

import mall.domain.*;
import mall.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class OrderCancel extends AbstractEvent {

    private Long id;
    private String foodId;
    private String options;
    private String address;

    public OrderCancel(Order aggregate){
        super(aggregate);
    }
    public OrderCancel(){
        super();
    }
}
