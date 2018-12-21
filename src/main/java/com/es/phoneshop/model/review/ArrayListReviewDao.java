package com.es.phoneshop.model.review;

import com.es.phoneshop.model.product.ArrayListProductDao;

import java.util.ArrayList;
import java.util.List;

public class ArrayListReviewDao implements ReviewDao {
    private List<ReviewBean> reviews = new ArrayList<>();

    private static volatile ArrayListReviewDao instance;

    public static ArrayListReviewDao getInstance() {
        ArrayListReviewDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListReviewDao();
                }
            }
        }
        return localInstance;
    }


    @Override
    public void save(ReviewBean reviewBean) {
        reviews.add(reviewBean);
    }

    @Override
    public List<ReviewBean> getReviews() {
        return reviews;
    }
}
