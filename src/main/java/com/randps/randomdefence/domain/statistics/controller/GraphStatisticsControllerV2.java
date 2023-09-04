package com.randps.randomdefence.domain.statistics.controller;

import com.randps.randomdefence.domain.statistics.dto.PointBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarGraphStatisticsResponse;
import com.randps.randomdefence.domain.statistics.dto.SolvedBarPair;
import com.randps.randomdefence.domain.statistics.service.PointBarGraphStatisticsService;
import com.randps.randomdefence.domain.statistics.service.SolvedBarGraphStatisticsService;
import com.randps.randomdefence.domain.statistics.service.SolvedBarGraphStatisticsServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/stat/graph")
public class GraphStatisticsControllerV2 {

    private final SolvedBarGraphStatisticsServiceV2 solvedBarGraphStatisticsService;

//    private final PointBarGraphStatisticsService pointBarGraphStatisticsService;

    /*
     * 모든 유저의 난이도별 문제수 바 그래프 통계를 조회한다.
     */
    @GetMapping("/solved")
    public List<SolvedBarPair> findSolvedBarGraphStat() {
        List<SolvedBarPair> response = solvedBarGraphStatisticsService.getAllSolvedBarStatistics();

        return response;
    }

    /*
     * 모든 유저의 난이도별 문제수 바 그래프 통계를 조회한다.
     */
//    @GetMapping("/point")
//    public PointBarGraphStatisticsResponse findPointBarGraphStat() {
//        PointBarGraphStatisticsResponse response = pointBarGraphStatisticsService.getAllPointBarStatistics();
//
//        return response;
//    }

    /*
     * 모든 유저의 오늘의 문제 해결 현황을 조회한다.
     */


    /*
     * 모든 유저의 경고 현황을 조회한다.
     */

}
