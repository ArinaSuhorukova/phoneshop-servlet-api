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

    private ArrayListProductDao() {
    }

    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        Optional<Product> product = products.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
        if (product.isPresent())
            return product.get();
        throw new NoSuchElementException();
    }

    private static Map<String, Comparator<Product>> comparators = new HashMap<>();

    static {
        comparators.put("description", comparing(Product::getDescription));
        comparators.put("price", comparing(Product::getPrice));
    }

    private Comparator<Product> choseComparator(String sortField, String order) {
        if (sortField != null && order != null) {
            Comparator<Product> result = comparators.get(sortField);
            if (order.equals("desc")) {
                return result.reversed();
            } else if (order.equals("asc")) {
                return result;
            }
        }
        return (product1, product2) -> 0;
    }

    @Override
    public List<Product> findProducts(String query, String sortField, String order) {
        List<Product> result = this.products;
        if (query != null && !query.equals("")) {
            Map<Product, Integer> map = new HashMap<>();
            for (Product product : result) {
                map.put(product, 0);
            }
            String[] words = query.toLowerCase().split("\\s");
            for (String word : words) {
                for (Product product : result.stream().filter(x -> x.getDescription().toLowerCase().contains(word)).collect(Collectors.toList())) {
                    map.put(product, map.get(product) + 1);
                }
            }
            

            map = map.entrySet().stream()
                    .filter(x -> x.getValue() > 0)
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            result = new ArrayList<>(map.keySet());
        }

        return result.stream()
                .filter(x -> (x.getPrice() != null && x.getStock() > 0))
                .sorted(choseComparator(sortField, order))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        boolean productInList = products.stream()
                .noneMatch(product::equals);
        if (productInList) {
            products.add(product);
        } else {
            //???
        }
    }

    @Override
    public void delete(Long id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(products.get(i));
            }
        }
    }

}