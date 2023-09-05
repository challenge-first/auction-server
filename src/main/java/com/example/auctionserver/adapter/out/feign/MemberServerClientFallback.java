package com.example.auctionserver.adapter.out.feign;

import com.example.auctionserver.application.port.out.model.ResponsePointDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemberServerClientFallback implements MemberServiceClient {


    @Override
    public ResponseEntity<ResponsePointDto> getPoint(Long memberId) {

        log.info("포인트 조회 실패 -> Fallback");
        throw new IllegalStateException("멤버 서버로부터 정보를 불러올 수 없습니다");
    }

    @Override
    public void validatePoint(Long bidPoint, Long memberId) {
        log.info("포인트 검증 실패 -> Fallback");
        throw new IllegalArgumentException("유효하지 않은 멤버 포인트 정보입니다");
    }
}
