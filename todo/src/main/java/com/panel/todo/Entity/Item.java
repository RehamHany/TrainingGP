package com.panel.todo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "Item")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Schema(description = "entity of Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier of the Item name id", example = "1")
    private int id;

    @NotEmpty
    @Column(name = "title")
    @Schema(description = "title of Item :)", example = "write programming code")
    private String title;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "itemDetails_id")
    private ItemDetails itemDetails;

    public Item(String title) {
        this.title = title;
    }

    public Item(String title, ItemDetails itemDetails) {
        this.title = title;
        this.itemDetails = itemDetails;
    }
}
