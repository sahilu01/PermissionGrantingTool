package com.example.PermissionGrantingTool.Controller;

import com.example.PermissionGrantingTool.DTO.FileRequestDto;
import com.example.PermissionGrantingTool.DTO.PGTdto;
import com.example.PermissionGrantingTool.DTO.PermissionGrantDto;
import com.example.PermissionGrantingTool.DTO.PermissionResponseDto;
import com.example.PermissionGrantingTool.Entity.PGT;
import com.example.PermissionGrantingTool.Service.PGTService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController @CrossOrigin(origins = "*") @RequestMapping("/employee")
public class PGTController {

  @Autowired PGTService pgtService;

  @RequestMapping(method = RequestMethod.GET, value = "/getAll")
  public ResponseEntity<List<PGTdto>> getAllEmployees() {
    List<PGT> pgtList = pgtService.getAllEmployees();
    List<PGTdto> PGTDtoList = new ArrayList<>();
    for (PGT pgt : pgtList) {
      PGTdto pgTdto = new PGTdto();
      BeanUtils.copyProperties(pgt, pgTdto);
      PGTDtoList.add(pgTdto);
    }

    return new ResponseEntity<List<PGTdto>>(PGTDtoList, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/getTest")
  public ResponseEntity<?> isTested(@RequestBody FileRequestDto fileRequestDto)
      throws InterruptedException {
    boolean response = pgtService.isTested(fileRequestDto);
    System.out.println("Response Sent");
    return new ResponseEntity<>(response, HttpStatus.OK);

  }


  @RequestMapping(method = RequestMethod.POST, value = "/getPermission")
  public ResponseEntity<?> isGranted(@RequestBody FileRequestDto fileRequestDto)
      throws InterruptedException {
    List<String> response = null;
    if (pgtService.validateUser(fileRequestDto.getUserId())) {
      try {
        response = pgtService.isGranted(fileRequestDto);
      } catch (Exception e) {
        return new ResponseEntity<>("no such file", HttpStatus.BAD_REQUEST);
      }
      System.out.println("Response Sent");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("No such user exists", HttpStatus.BAD_REQUEST);

    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "/grantPermission")
  public ResponseEntity grantPermission(@RequestBody PermissionGrantDto permissionGrantDto) {
    String response = null;
    response = pgtService.grantPermission(permissionGrantDto);
    return new ResponseEntity(response, HttpStatus.OK);
  }


  @RequestMapping(method = RequestMethod.GET, value = "/getPermissionResponse")
  public ResponseEntity<List<PermissionResponseDto>> getPermissionStatus() {
    List<PermissionResponseDto> response = pgtService.getPermissionStatus();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getFile") public ResponseEntity<?> getFile()
      throws IOException {

    String fileName;
    fileName = pgtService.getFile();
    return new ResponseEntity<>(fileName, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/getReasons")
  public ResponseEntity<?> getReasons() {

    List<String> reasonList = pgtService.getReasons();
    return new ResponseEntity<>(reasonList, HttpStatus.OK);
  }

}
