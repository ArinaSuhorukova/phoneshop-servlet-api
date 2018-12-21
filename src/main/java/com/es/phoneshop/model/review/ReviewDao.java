package com.es.phoneshop.model.review;

import java.util.List;

public interface ReviewDao {
    void save(ReviewBean reviewBean);
    List<ReviewBean> getReviews();
}
