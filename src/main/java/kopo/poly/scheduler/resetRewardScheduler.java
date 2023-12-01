package kopo.poly.scheduler;

import kopo.poly.persistance.mapper.ICustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class resetRewardScheduler {

    private final ICustomerMapper customerMapper;

    @Scheduled(cron = "0 0 0 * * ?") // 자정
//    @Scheduled(cron = "0 * * * * *") // 1분(테스트)
    public void resetReward() throws Exception {
        log.info("resetReward start!");
        customerMapper.resetReward();
    }
}
