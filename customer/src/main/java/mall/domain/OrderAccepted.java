package mall.domain;

import mall.infra.AbstractEvent;
import lombok.Data;
import java.util.*;


@Data
public class OrderAccepted extends AbstractEvent {

    private Long id;
    private String foodId;
    private String status;
    private String orderId;
    private List<String> options;
    private String storedId;
}
