package com.example.springbootpractice.service;

import com.example.springbootpractice.config.security.SecurityUtil;
import com.example.springbootpractice.exception.ApiErrorException;
import com.example.springbootpractice.exception.ErrorCode;
import com.example.springbootpractice.model.dto.UpdateWebtoonCoinRequest;
import com.example.springbootpractice.model.dto.WebtoonEvaluationRequest;
import com.example.springbootpractice.model.dto.WebtoonResponse;
import com.example.springbootpractice.model.dto.WebtoonTop3Response;
import com.example.springbootpractice.model.dto.WebtoonViewHistoryResponse;
import com.example.springbootpractice.model.entity.UserEntity;
import com.example.springbootpractice.model.entity.WebtoonEntity;
import com.example.springbootpractice.model.entity.WebtoonEvaluationEntity;
import com.example.springbootpractice.model.entity.WebtoonViewHistoryEntity;
import com.example.springbootpractice.model.enums.UserType;
import com.example.springbootpractice.model.enums.WebtoonEvaluationType;
import com.example.springbootpractice.model.enums.WebtoonRatingType;
import com.example.springbootpractice.repository.WebtoonEvaluationRepository;
import com.example.springbootpractice.repository.WebtoonRepository;
import com.example.springbootpractice.repository.WebtoonViewHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebtoonService {

  private final UserService userService;
  private final WebtoonEvaluationRepository webtoonEvaluationRepository;
  private final WebtoonRepository webtoonRepository;
  private final WebtoonViewHistoryRepository webtoonViewHistoryRepository;


  @Transactional
  public void createWebtoonEvaluationInfo(long webtoonSeq, WebtoonEvaluationRequest request) {
    UserEntity user = userService.getUser(SecurityUtil.getCurrentUserEmail());
    WebtoonEntity webtoon = webtoonRepository.findById(webtoonSeq)
        .orElseThrow(() -> new ApiErrorException(ErrorCode.NOT_FOUND_WEBTOON_INFO));

    // 성인이 아닌 유저가 성인 웹툰 작품을 평가하려고 하는 경우 에러 처리
    if (UserType.NORMAL.equals(user.getType())
        && WebtoonRatingType.ADULT.equals(webtoon.getRatingType())) {
      throw new ApiErrorException(ErrorCode.NOT_ALLOWED_USER_TYPE_FOR_WEBTOON);
    }

    // 유저가 이미 평가 정보를 입력한 경우 에러 처리
    if (webtoonEvaluationRepository.existsByUserSeqAndWebtoonSeq(user.getSeq(), webtoonSeq)) {
      throw new ApiErrorException(ErrorCode.ALREADY_EXIST_WEBTOON_EVALUATION_INFO);
    }

    webtoonEvaluationRepository.save(
        WebtoonEvaluationEntity.of(webtoon, user.getSeq(), request));
  }

  @Transactional(readOnly = true)
  public WebtoonTop3Response getTop3Webtoons() {
    return WebtoonTop3Response.of(
        webtoonEvaluationRepository.findTop3Webtoons(WebtoonEvaluationType.LIKE),
        webtoonEvaluationRepository.findTop3Webtoons(WebtoonEvaluationType.DISLIKE)
    );
  }

  @Transactional
  public WebtoonResponse getWebtoon(long webtoonSeq) {
    WebtoonEntity webtoon = webtoonRepository.findById(webtoonSeq)
        .orElseThrow(() -> new ApiErrorException(ErrorCode.NOT_FOUND_WEBTOON_INFO));

    UserEntity user = userService.getUser(SecurityUtil.getCurrentUserEmail());

    // 일반 회원이 성인 작품을 조회하려고 하는 경우 에러 처리
    if (UserType.NORMAL.equals(user.getType())
        && WebtoonRatingType.ADULT.equals(webtoon.getRatingType())) {
      throw new ApiErrorException(ErrorCode.NOT_ALLOWED_USER_TYPE_FOR_WEBTOON);
    }

    // 웹툰 조회 내역 저장
    webtoonViewHistoryRepository.save(WebtoonViewHistoryEntity.of(user, webtoon));

    // 성인 작품 조회 시 회원의 성인 작품 조회 수 카운트 증가
    if (WebtoonRatingType.ADULT.equals(webtoon.getRatingType())) {
      user.increaseAdultWebtoonViewCount();
    }

    return WebtoonResponse.from(webtoon);
  }

  @Transactional(readOnly = true)
  public Page<WebtoonViewHistoryResponse> getWebtoonViewHistory(long webtoonSeq,
      Pageable pageable) {
    WebtoonEntity webtoon = webtoonRepository.findById(webtoonSeq)
        .orElseThrow(() -> new ApiErrorException(ErrorCode.NOT_FOUND_WEBTOON_INFO));

    Page<WebtoonViewHistoryEntity> page =
        webtoonViewHistoryRepository.findWebtoonViewHistory(webtoon, pageable);
    return page.map(WebtoonViewHistoryResponse::from);
  }

  @Transactional
  public void updateWebtoonCoin(long webtoonSeq, UpdateWebtoonCoinRequest request) {
    WebtoonEntity webtoon = webtoonRepository.findById(webtoonSeq)
        .orElseThrow(() -> new ApiErrorException(ErrorCode.NOT_FOUND_WEBTOON_INFO));
    webtoon.updateCoin(request.coin());
  }
}
