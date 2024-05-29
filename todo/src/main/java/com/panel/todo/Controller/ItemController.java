package com.panel.todo.Controller;

import com.panel.todo.Entity.Item;
import com.panel.todo.Exception.ItemNotFoundException;
import com.panel.todo.Service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/item")
@Tag(name = "Item Requests", description = "Controller for managing Item ")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/save")
    @Operation(summary = "add task", description = "save task and return this task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item save Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public Item addItem(@RequestBody Item item,@RequestHeader("Authorization")String t) {
           return itemService.save(item,t);
    }

    @PutMapping("/update")
    @Operation(summary = "update task", description = "save updated task and return this task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item updated Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public Item updateItem(@RequestBody Item item) throws ItemNotFoundException {
        return itemService.update(item);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete task", description = "delete task and return message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item deleted Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public String deleteItem(@Parameter(description = "ID of the task to be fetched", required = true)@RequestParam("id") int id) throws ItemNotFoundException {
        itemService.delete(id);
        return "item deleted";
    }

    @GetMapping("/findItem")
    @Operation(summary = "get task", description = "get task by id and return this task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item found Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public Item findItem(@Parameter(description = "ID of the task to be fetched", required = true)@RequestParam("id") int id) throws ItemNotFoundException {
        return itemService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "search tasks", description = "get tasks by match title and return tasks for search by title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "items get Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public List<Item> search(@Parameter(description = "title of the task to be fetched and search", required = true)@RequestParam("title") String title) {
        return itemService.findByName(title);
    }

    @GetMapping("/findAllItem")
    @Operation(summary = "get All task", description = "get all tasks in database and return them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "items get Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public List<Item> findAllItem() {
        return itemService.findAll();
    }

}
