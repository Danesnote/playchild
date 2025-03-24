package com.pod.playchild.service;

import com.pod.playchild.domain.Place;
import com.pod.playchild.dto.request.PlaceRequestDto;
import com.pod.playchild.dto.response.PlaceResponseDto;
import com.pod.playchild.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
장소등록 테스트
1. 장소명, 주소, 위도, 경도 포함 요청
2. 어느 하나라도 비어있거나, 대한민국이 아니라면 등록 거절
3. 그 외에는 모두 등록 승인

* */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RegistPlaceTest {

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceService placeService;

    @Test
    void 장소_정상_등록() {
        // given
        PlaceRequestDto placeRequestDto = new PlaceRequestDto() {
            String name = "놀이터";
            String address = "서울시 강남구";
            Double latitude = 37.5;
            Double longitude = 127.0;
        };

        Place place = new Place(1L, placeRequestDto.getName(), placeRequestDto.getAddress(), placeRequestDto.getLatitude(), placeRequestDto.getLongitude());
        when(placeRepository.save(any())).thenReturn(place);

        // when
        PlaceResponseDto responseDto = placeService.registerPlace(placeRequestDto);

        // then
        assertThat(responseDto.getName()).isEqualTo(placeRequestDto.getName());
        assertThat(responseDto.getAddress()).isEqualTo(placeRequestDto.getAddress());
        verify(placeRepository, times(1)).save(any());
    }

    @Test
    void 필수값_누락_시_예외발생() {
        PlaceRequestDto placeRequestDto = new PlaceRequestDto() {
            // given
            String name = "놀이터";
            String address = null;  // 주소 누락
            //String address = "서울";
            Double latitude = 37.5;
            Double longitude = 127.0;
        };
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                placeService.registerPlace(placeRequestDto));
    }

    @Test
    void 대한민국_외_위치_등록_불가() {
        PlaceRequestDto placeRequestDto = new PlaceRequestDto() {
            // given
            String name = "미국 공원";
            String address = "뉴욕";
            Double latitude = 40.0;  // 대한민국 범위 초과
            Double longitude = -74.0;
        };
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                placeService.registerPlace(placeRequestDto));
    }

    @Test
    void 장소명은_10자이하() {
        PlaceRequestDto placeRequestDto = new PlaceRequestDto() {
            // given
            String name = "미국 공원";
            String address = "뉴욕";
            Double latitude = 37.5;
            Double longitude = 127.0;
        };
        Place place = new Place(1L, placeRequestDto.getName(), placeRequestDto.getAddress(), placeRequestDto.getLatitude(), placeRequestDto.getLongitude());
        when(placeRepository.save(any())).thenReturn(place);
        // when
        PlaceResponseDto responseDto = placeService.registerPlace(placeRequestDto);

        // then
        assertThat(responseDto.getName()).isEqualTo(placeRequestDto.getName());
        assertThat(responseDto.getAddress()).isEqualTo(placeRequestDto.getAddress());
        verify(placeRepository, times(1)).save(any());
    }
}
