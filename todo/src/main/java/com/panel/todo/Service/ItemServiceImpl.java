package com.panel.todo.Service;

import com.panel.todo.Entity.Item;
import com.panel.todo.Entity.ItemDetails;
import com.panel.todo.Exception.ItemNotFoundException;
import com.panel.todo.Repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    RestTemplate restTemplate=new RestTemplate();

    @Override
    public Item save(Item item,String to) {

        String url="http://localhost:8075/api/v1/user/token";
        String token = to.substring(7);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =restTemplate.exchange(url, HttpMethod.GET, entity, String.class);




        ItemDetails itemDetails=new ItemDetails(
                item.getItemDetails().getDescription(),
                item.getItemDetails().getCreatedAt(),
                item.getItemDetails().getPriorityItem(),
                item.getItemDetails().isStatus(),
                response.getBody());
        item.setItemDetails(itemDetails);
        return itemRepo.save(item);
    }

    @Override
    public void delete(int id) throws ItemNotFoundException {
        Optional<Item> item=itemRepo.findById(id);
        if(item.isEmpty()){
            throw new ItemNotFoundException("this Item not Found :(");
        }
         itemRepo.deleteById(id);
    }

    @Override
    public Item update(Item item) throws ItemNotFoundException{
        Optional<Item> itemOld=itemRepo.findById(item.getId());
        if(itemOld.isEmpty()){
            throw new ItemNotFoundException("this Item not Found :(");
        }
        itemOld.get().setTitle(item.getTitle());
        itemOld.get().getItemDetails().setDescription(item.getItemDetails().getDescription());
        itemOld.get().getItemDetails().setCreatedAt(item.getItemDetails().getCreatedAt());
        itemOld.get().getItemDetails().setPriorityItem(item.getItemDetails().getPriorityItem());
        itemOld.get().getItemDetails().setStatus(item.getItemDetails().isStatus());

        return itemRepo.save(itemOld.get());
    }

    @Override
    public Item findById(int id) throws ItemNotFoundException{
        if(id <= 0){
            throw new ItemNotFoundException("id invalid :( must be > 0");
        }else{
            Optional<Item> item=itemRepo.findById(id);
            if(item.isEmpty()){
                throw new ItemNotFoundException("this Item not Found :(");
            }else{
                return item.get();
            }
        }
    }

    @Override
    public List<Item> findByName(String title) {
        return itemRepo.findByName(title);
    }

    @Override
    public List<Item> findAll() {
        return itemRepo.findAll();
    }
}
