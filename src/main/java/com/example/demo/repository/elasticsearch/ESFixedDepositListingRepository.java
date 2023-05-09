package com.example.demo.repository.elasticsearch;

import com.example.demo.entity.elasticsearch.ESFixedDepositListing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESFixedDepositListingRepository extends ElasticsearchRepository<ESFixedDepositListing, String> {
}
