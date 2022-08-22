package be.clone.kakao.controller;

import Controller.response.ResponseDto;
import Service.RoomService;
import org.apache.catalina.connector.Response;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Member;

@Controller
public class RoomController {
    private  RoomService roomService;

    @RequestMapping(value="/api/rooms", method = RequestMethod.POST)
    public ResponseDto<?> createRoom(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = userDetails.getMember();
        return roomService.createRoom(memberId);
    }

    @RequestMapping(value = "/api/rooms", method = RequestMethod.GET)
    public ResponseDto<?> getRoom(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = userDetails.getMember();
        return roomService.getRoom(memberId);
    }

    @RequestMapping(value = "/api/rooms/:room_master_id", method = RequestMethod.PUT)
    public Response<?> putRoom(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = userDetails.getMember();
        return roomService.putRoom(MemberId);
    }

    @RequestMapping(value = "api/rooms/:room_master_id/member/:member_id", method = RequestMethod.DELETE)
    public  Response<?> deleteRoom(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = userDetails.getMember();
        return  roomService.deleteRoom(memberId);

    }
}
