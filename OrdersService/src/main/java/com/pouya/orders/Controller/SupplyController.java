package com.pouya.orders.Controller;

import com.pouya.library.Auth.Gate.HasRole;
import com.pouya.library.Controller.Abstracts.BaseController;
import com.pouya.library.DTO.NotebookDto;
import com.pouya.library.DTO.UserInfo;
import com.pouya.library.Enums.UserRoles;
import com.pouya.library.PacketTemplate.ApiResponse;
import com.pouya.orders.GRpc.NotebookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@HasRole(role = UserRoles.SUPPLIER)
public class SupplyController extends BaseController {
    @Autowired
    NotebookClient _notebookClient;
    @GetMapping("/user")
    public ResponseEntity<?> me() {
        UserInfo user = getUser();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/note-book")
    public ResponseEntity<?> notebook() {
        List<NotebookDto> _items = _notebookClient.getNotebooks();
        return ApiResponse.success(_items);
    }

}
