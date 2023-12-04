package com.board.bulletinboardproject.serviceTest;

import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.repositoryTest.BulletinBoardRepository;
import com.board.bulletinboardproject.service.BulletinBoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    BulletinBoardRepository bulletinBoardRepository;
    @InjectMocks
    BulletinBoardService bulletinBoardService;





    @Test
    @DisplayName("게시판 작성")
    void boardcreateTest(){
        // given
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");

        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);




        // when
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);
        BulletinBoardResponseDto responseDto = bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);

        // then
        verify(bulletinBoardRepository).save(any(BulletinBoard.class));
        assertEquals("테스트입니다.5",responseDto.getTitle());
        assertEquals("테스트중이에요. 5",responseDto.getContents());
    }

    @Test
    @DisplayName("게시판 수정")
     void boardUpdate(){
        // given
        //게시판 작성 부분
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");

        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);
        BulletinBoardResponseDto responseDto = bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);


        //업데이트 부분
        Long id = 55L;
        BulletinBoardRequestDto bulletinBoardRequestDtoChange = new BulletinBoardRequestDto();
        bulletinBoardRequestDtoChange.setTitle("테스트입니다.5수정");
        bulletinBoardRequestDtoChange.setContents("테스트중이에요. 5수정");
        BulletinBoard bulletinBoardchange = new BulletinBoard(bulletinBoardRequestDtoChange, user);


        // when
        when(bulletinBoardRepository.findById(any())).thenReturn(Optional.of(bulletinBoardchange));
        BulletinBoardResponseDto responseDto2 = bulletinBoardService.updateBulletinBoard(id,bulletinBoardRequestDtoChange,user);


        //then
        assertNotEquals(responseDto.getTitle(),responseDto2.getTitle());
        assertNotEquals(responseDto.getContents(),responseDto2.getContents());

    }

    @Test
    @DisplayName("게시판 완료 수정")
    void boardUpdateCompleted(){
        // given
        //게시판 작성 부분
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");
        bulletinBoardRequestDto.setCompleted(false);

        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);
        BulletinBoardResponseDto responseDto = bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);


        //업데이트 부분
        Long id = 55L;
        BulletinBoardRequestDto bulletinBoardRequestDtoChange = new BulletinBoardRequestDto();
        bulletinBoardRequestDtoChange.setCompleted(true);
        BulletinBoard bulletinBoardchange = new BulletinBoard(bulletinBoardRequestDtoChange, user);


        // when
        when(bulletinBoardRepository.findById(any())).thenReturn(Optional.of(bulletinBoardchange));
        BulletinBoardResponseDto responseDto2 = bulletinBoardService.updateCompletedBulletinBoard(id,bulletinBoardRequestDtoChange,user);


        //then
        assertNotEquals(responseDto.getCompleted(),responseDto2.getCompleted());


    }
    @Test
    @DisplayName("게시판 삭제")
    void boardDelete(){
        // given
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");

        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);
        bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);
        Long id = 55L;


        // when
        when(bulletinBoardRepository.findById(any())).thenReturn(Optional.of(bulletinBoard));
        ResponseEntity<StatusDto> responseDtoStatusDto = bulletinBoardService.deleteBulletinBoard(id,user);


        //then
        assertEquals("삭제 성공", Objects.requireNonNull(responseDtoStatusDto.getBody()).getMsg());
        assertEquals(200, Objects.requireNonNull(responseDtoStatusDto.getBody()).getStatusCode());



    }

    @Test
    @DisplayName("게시판 선택 조회")
    void boardOneFind(){
        // given
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");

        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);

        bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);
        Long id = 55L;

        // when
        when(bulletinBoardRepository.findById(any())).thenReturn(Optional.of(bulletinBoard));
        BulletinBoardResponseDto board = bulletinBoardService.findOneBulletinBoard(id);


        //then
        assertEquals(bulletinBoard.getTitle(),board.getTitle());
        assertEquals(bulletinBoard.getContents(),board.getContents());
        assertEquals(bulletinBoard.getUser().getUsername(),board.getUsername());

    }

    @Test
    @DisplayName("게시판 전체 조회")
    void boardList(){
        // given
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");

        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);

        bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);
        List<BulletinBoard> boardList = new ArrayList<>();


        // when
        when(bulletinBoardRepository.findAllByOrderByModifiedAtDesc()).thenReturn(boardList);
        List<BulletinBoardResponseDto> List = bulletinBoardService.getBulletinBoards();


        //then
        for(BulletinBoardResponseDto boardResponseDto : List){
            assertEquals("테스트입니다.5",boardResponseDto.getTitle());
            assertEquals("테스트중이에요. 5",boardResponseDto.getContents());
            assertEquals("gggg",boardResponseDto.getUsername());
        }


    }

}
