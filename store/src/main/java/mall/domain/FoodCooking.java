package mall.domain;

import mall.domain.OrderAccepted;
import mall.domain.OrderRejected;
import mall.domain.CookFinished;
import mall.StoreApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="FoodCooking_table")
@Data

public class FoodCooking  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String foodId;
    
    
    
    
    
    private String status;
    
    
    
    
    
    private String orderId;
    
    
    
    @ElementCollection
    
    private List<String> options;
    
    
    
    
    
    private String storedId;
    
    
    
    
    
    private String customerId;

    @PostPersist
    public void onPostPersist(){


        OrderAccepted orderAccepted = new OrderAccepted(this);
        orderAccepted.publishAfterCommit();



        OrderRejected orderRejected = new OrderRejected(this);
        orderRejected.publishAfterCommit();



        CookFinished cookFinished = new CookFinished(this);
        cookFinished.publishAfterCommit();

    }

    public static FoodCookingRepository repository(){
        FoodCookingRepository foodCookingRepository = StoreApplication.applicationContext.getBean(FoodCookingRepository.class);
        return foodCookingRepository;
    }



    public void accept(AcceptCommand acceptCommand){
        if(acceptCommand.getAccept()) {
            OrderAccepted orderAccepted = new OrderAccepted(this);
            orderAccepted.publishAfterCommit();

            setStatus("order accepted");
        } else {
            OrderRejected orderRejected = new OrderRejected(this);
            orderRejected.publishAfterCommit();

            setStatus("order rejected");
        }
    }
    
    public void start(){
        CookStarted cookStarted = new CookStarted(this);
        cookStarted.publishAfterCommit();

    }

    public static void orderInfoCopy(OrderPlaced orderPlaced){

        /** Example 1:  new item    */
        FoodCooking foodCooking = new FoodCooking();
        foodCooking.setCustomerId(orderPlaced.getCustomerId());
        foodCooking.setFoodId(orderPlaced.getFoodId());
        //foodCooking.setAddress(orderPlaced.getAddress());
        foodCooking.setOrderId(String.valueOf(orderPlaced.getOrderId()));
        foodCooking.setStatus("미결재");
        repository().save(foodCooking);

     

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(foodCooking->{
            
            foodCooking // do something
            repository().save(foodCooking);


         });
        */

        
    }
    public static void updateStatus(Paid paid){

        /** Example 1:  new item 
        FoodCooking foodCooking = new FoodCooking();
        repository().save(foodCooking);
        */
        
        

        /** Example 2:  finding and process */
        
        repository().findByOrderId(paid.getOrderId()).ifPresent(foodCooking->{
            foodCooking.setStatus("결재됨");
            repository().save(foodCooking);


         });
       

        
    }


}
