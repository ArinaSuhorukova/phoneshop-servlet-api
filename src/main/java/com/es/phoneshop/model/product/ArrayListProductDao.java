package com.es.phoneshop.model.product;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

public class ArrayListProductDao implements ProductDao {
    private static volatile ArrayListProductDao instance;

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListProductDao();
                }
            }
        }
        return localInstance;
    }
    private List<Product> products = new CopyOnWriteArrayList<>();

    private ArrayListProductDao(){}

    @Override
    public Product getProduct(Long id) {
        for (Product product: products) {
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }

    private Comparator<Product> choseComparator(String sortField, String order) {
        if (sortField != null && order != null) {
            if (sortField.equals("description")) {
                if (order.equals("asc")) {
                    return comparing(Product::getDescription);
                } else if (order.equals("desc")) {
                    return comparing(Product::getDescription).reversed();
                }
            } else if (sortField.equals("price")) {
                if (order.equals("asc")) {
                    return comparing(Product::getPrice);
                } else if (order.equals("desc")) {
                    return comparing(Product::getPrice).reversed();
                }
            }
        }
        //вот тут я не знаю, как адекватно возвращать пустой компоратор
        return new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return 0;
            }
        };
    }
    @Override
    public List<Product> findProducts(String query, String sortField, String order) {
        List<Product> result = this.products;
        if(query!= null && query != "") {
            Map<Product, Integer> map = new HashMap<>();
            for (Product product : result) {
                map.put(product, 0);
            }
            String[] words = query.split("\\s");
            for (String word : words) {
                for (Product product : result.stream().filter(x -> x.getDescription().contains(word)).collect(Collectors.toList())) {
                    map.put(product, map.get(product) + 1);
                }
            }
            map = map.entrySet().stream()
                    .filter(x -> x.getValue() > 0)
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            result = map.keySet().stream()
                    .collect(Collectors.toList());
        }

        return result.stream()
                .filter(x -> (x.getPrice() != null && x.getStock() > 0))
                .sorted(choseComparator(sortField,order))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
       if(getProduct(product.getId()) == null){
           products.add(product);
       }
    }

    @Override
    public void delete(Long id) {
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getId().equals(id)){
                products.remove(products.get(i));
            }
        }
    }

}