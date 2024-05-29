package com.panel.todo.Entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "ItemDetails")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Schema(description = "entity of ItemDetails")
public class ItemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier of the ItemDetails name id", example = "1")
    private int id;


    @Column(name = "description")
    @Schema(description = "description of your task", example = "write code in spring boot java projects :)")
    private String description;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    @Schema(description = "the date that created at this task", example = "2025-5-6")
    private Date createdAt;


    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    @Schema(description = "priority of your task", example = "LOW")
    private PriorityItem priorityItem;


    @Column(name = "status")
    @Schema(description = "status of your task", example = "true")
    private boolean status;


    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(name = "userEmail")
    @Schema(description = "user email", example = "reem@gmail.com")
    private String userEmail;

    @OneToOne(mappedBy = "itemDetails" , cascade = CascadeType.ALL)
    private Item item;

    public ItemDetails(String description, Date createdAt, PriorityItem priorityItem, boolean status) {
        this.description = description;
        this.createdAt = createdAt;
        this.priorityItem = priorityItem;
        this.status = status;
    }

    public ItemDetails(String description, Date createdAt, PriorityItem priorityItem, boolean status, String userEmail) {
        this.description = description;
        this.createdAt = createdAt;
        this.priorityItem = priorityItem;
        this.status = status;
        this.userEmail = userEmail;
    }

    public ItemDetails(String description, Date createdAt, PriorityItem priorityItem, boolean status, Item item) {
        this.description = description;
        this.createdAt = createdAt;
        this.priorityItem = priorityItem;
        this.status = status;
        this.item = item;
    }
}
