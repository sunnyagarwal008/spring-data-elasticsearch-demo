package com.example.demo.controller;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import com.example.demo.entity.elasticsearch.ESFixedDepositListing;
import com.example.demo.repository.elasticsearch.ESFixedDepositListingRepository;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fixed-deposits")
public class FixedDepositSearchController {

    private final ESFixedDepositListingRepository esFixedDepositListingRepository;
    private final ElasticsearchOperations         elasticsearchOperations;

    public FixedDepositSearchController(ESFixedDepositListingRepository esFixedDepositListingRepository, ElasticsearchOperations elasticsearchOperations) {
        this.esFixedDepositListingRepository = esFixedDepositListingRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @GetMapping
    public List<ESFixedDepositListing> searchFds() {
        addListing1();
        addListing2();

        // Aggregation example
        System.out.println(getTenureValues());

        // Query example
        SearchHits<ESFixedDepositListing> matchingResults =
                elasticsearchOperations.search(new CriteriaQuery(new Criteria("fixedDeposits.tenure").is(90)), ESFixedDepositListing.class);
        return matchingResults.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    private List<Long> getTenureValues() {
        SearchHits<ESFixedDepositListing> aggregationResults =
                elasticsearchOperations.search(
                        NativeQuery.builder()
                                .withAggregation("tenure", Aggregation.of(a -> a.terms(ta -> ta.field("fixedDeposits.tenureInDays"))))
                                .withFilter(f -> f.term(t -> t.field("status.keyword").value("ACTIVE")))
                                .withMaxResults(0)
                                .build(), ESFixedDepositListing.class);
        ElasticsearchAggregation aggregation = (ElasticsearchAggregation) ((List<?>) aggregationResults.getAggregations().aggregations()).get(0);
        return aggregation.aggregation().getAggregate().lterms().buckets().array().stream().map(LongTermsBucket::key).sorted().toList();
    }

    private void addListing1() {
        ESFixedDepositListing listing = new ESFixedDepositListing();
        List<ESFixedDepositListing.FixedDeposit> fixedDeposits = new ArrayList<>();
        fixedDeposits.add(new ESFixedDepositListing.FixedDeposit("123", "3 months", 6.0, 100, 10000, 90, 90));
        fixedDeposits.add(new ESFixedDepositListing.FixedDeposit("1234", "4 months", 6.3, 100, 10000, 120, 120));
        listing.setBank("ICICI");
        listing.setId("12345");
        listing.setStatus(ESFixedDepositListing.Status.ACTIVE);
        listing.setFixedDeposits(fixedDeposits);
        esFixedDepositListingRepository.save(listing);
    }

    private void addListing2() {
        ESFixedDepositListing listing = new ESFixedDepositListing();
        List<ESFixedDepositListing.FixedDeposit> fixedDeposits = new ArrayList<>();
        fixedDeposits.add(new ESFixedDepositListing.FixedDeposit("123", "4 months", 6.1, 100, 10000, 120, 120));
        fixedDeposits.add(new ESFixedDepositListing.FixedDeposit("1234", "5 months", 6.4, 100, 10000, 150, 150));
        fixedDeposits.add(new ESFixedDepositListing.FixedDeposit("12345", "6 months", 6.8, 1000, 10000, 180, 180));
        listing.setBank("HDFC");
        listing.setId("54321");
        listing.setStatus(ESFixedDepositListing.Status.ACTIVE);
        listing.setFixedDeposits(fixedDeposits);
        esFixedDepositListingRepository.save(listing);
    }
}
