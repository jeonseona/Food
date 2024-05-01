<<<<<<< HEAD:FoodMateProject/src/main/java/com/demo/dto/QnADTO.java
package com.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnADTO {
	private int id;
    private String title;
    private String content;
    private String author;

}
=======
package com.demo.dto;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QnADTO {
	private int id;
    private String title;
    private String content;
    private String author;

}
>>>>>>> test_food:FoodMateProject/src/main/java/com/dto/QnADTO.java
